package com.lin.domain.strategy.service.armory;

import com.lin.domain.strategy.model.entity.StrategyAwardEntity;
import com.lin.domain.strategy.model.entity.StrategyEntity;
import com.lin.domain.strategy.model.entity.StrategyRuleEntity;
import com.lin.domain.strategy.repository.IStrategyRepository;
import com.lin.types.common.Constants;
import com.lin.types.enums.ResponseCode;
import com.lin.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * 策略初始化的实现类
 */
@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory,IStrategyDispatch{

    @Resource
    private IStrategyRepository repository;

    /**
     * 根据策略id查询，把策略信息从mysql中查询出来进行处理，方便进行后续抽奖，并且装配到redis中
     * @param strategyId
     */
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.查询策略配置
        List<StrategyAwardEntity>strategyAwardEntities  = repository.queryStrategyAwardList(strategyId);

        // 2 缓存奖品库存【用于decr扣减库存使用】
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            Integer awardCount = strategyAward.getAwardCount();
            cacheStrategyAwardCount(strategyId, awardId, awardCount);
        }

        // 3.1 默认装配配置【全量抽奖概率】，装配所有奖品
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 3.2 权重策略配置 - 适用于 rule_weight 权重规则配置【4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109】
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if(ruleWeight == null) return true;

        //查询权重规则配置【4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109】
        StrategyRuleEntity strategyRuleEntity = repository.queryStrategyRule(strategyId, ruleWeight);
        if(null == strategyRuleEntity){
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        //【"4000:102,103,104,105": [102,103,104,105] "5000:102,103,104,105,106,107": [102,103,104,105,106,107] "6000:102,103,104,105,106,107,108,109": [102,103,104,105,106,107,108,109]】转换成map格式
        Map<String, List<Integer>> ruleWeightValueMap = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValueMap.keySet();
        for (String key : keys) {
            List<Integer> ruleWeightValues = ruleWeightValueMap.get(key);
            //克隆按照策略查询出来的所有奖品列表strategyAwardEntities
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);

            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValues.contains(entity.getAwardId()));
            //100001_4000:102,103,104,105:  [ StrategyAwardEntity(strategyId=100001, awardId=102, awardCount=10000, awardCountSurplus=10000, awardRate=0.2000)
            //                              , StrategyAwardEntity(strategyId=100001, awardId=103, awardCount=5000, awardCountSurplus=5000, awardRate=0.2000)
            //                              , StrategyAwardEntity(strategyId=100001, awardId=104, awardCount=4000, awardCountSurplus=4000, awardRate=0.1000)
            //                              , StrategyAwardEntity(strategyId=100001, awardId=105, awardCount=600, awardCountSurplus=600, awardRate=0.1000)
            //                              ]
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }

        return true;
    }

    public void assembleLotteryStrategy(String key, List<StrategyAwardEntity>strategyAwardEntities){
        //1.获取最小概率值
        BigDecimal miniAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        //2.获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //3.用1 % 0.0001获取概率范围，转换成每种情况有多少个格子
        BigDecimal rateRange = totalAwardRate.divide(miniAwardRate, 0, RoundingMode.CEILING);

        //4.计算每种概率的格子数
        ArrayList<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
            Integer awardId = strategyAward.getAwardId();
            BigDecimal awardRate = strategyAward.getAwardRate();
            //计算出每个概率值需要存放到查找表的数量，循环填充
            for(int i = 0; i < rateRange.multiply(awardRate).setScale(0,RoundingMode.CEILING).intValue(); i++){
                strategyAwardSearchRateTables.add(awardId);
            }
        }

        //5.乱序
        Collections.shuffle(strategyAwardSearchRateTables);

        //6. 存入hashmap
        HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables = new HashMap<>();
        for(int i = 0; i < strategyAwardSearchRateTables.size(); i++){
            shuffleStrategyAwardSearchRateTables.put(i, strategyAwardSearchRateTables.get(i));
        }

        //7.存储到redis
        repository.storeStrategyAwardSearchRateTables(key, shuffleStrategyAwardSearchRateTables.size(), shuffleStrategyAwardSearchRateTables);

    }

    /**
     * 缓存奖品库存到Redis
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @param awardCount 奖品库存
     */
    private void cacheStrategyAwardCount(Long strategyId, Integer awardId, Integer awardCount) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        repository.cacheStrategyAwardCount(cacheKey, awardCount);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        //生成随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        int rateRange = repository.getRateRange(key);
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Boolean subtractionAwardStock(Long strategyId, Integer awardId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_KEY + strategyId + Constants.UNDERLINE + awardId;
        return repository.subtractionAwardStock(cacheKey);
    }

}
