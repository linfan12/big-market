package com.lin.domain.strategy.repository;

import com.lin.domain.strategy.model.entity.StrategyAwardEntity;
import com.lin.domain.strategy.model.entity.StrategyEntity;
import com.lin.domain.strategy.model.entity.StrategyRuleEntity;
import com.lin.domain.strategy.model.valobj.RuleTreeVO;
import com.lin.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import com.lin.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

import java.util.HashMap;
import java.util.List;

public interface IStrategyRepository {
    //根据策略id查询所有奖品
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    //查询策略对应的奖品概率并转换成个数，并转换成打乱顺序，存储到redis
    void storeStrategyAwardSearchRateTables(String key, Integer rateRange, HashMap<Integer, Integer> shuffleStrategyAwardSearchRateTables);

    //在redis中查询策略key对应的奖品总个数
    int getRateRange(Long strategyId) ;
    int getRateRange(String key) ;

    //根据查询策略和存储的随机id查询奖品id
    Integer getStrategyAwardAssemble(String key, Integer rateKey);

    //根据策略id查询策略实体
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    //查询策略规则实体
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);

    //查询策略规则的值
    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);


    //获取决策树对象：从数据库或者缓存中获取
    RuleTreeVO queryRuleTreeVOByTreeId(String tree_lock);
    //缓存奖品库存到Redis
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 缓存key，decr 方式扣减库存
     *
     * @param cacheKey 缓存Key
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(String cacheKey);

    /**
     * 写入奖品库存消费队列
     *
     * @param strategyAwardStockKeyVO 对象值对象
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 获取奖品库存消费队列
     */
    StrategyAwardStockKeyVO takeQueueValue();

    /**
     * 更新奖品库存消耗
     *
     * @param strategyId 策略ID
     * @param awardId 奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

    /**
     * 根据策略ID+奖品ID的唯一值组合，查询奖品信息
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 奖品信息
     */
    StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId);

    Long queryStrategyIdByActivityId(Long activityId);

    Integer queryTodayUserRaffleCount(String userId, Long strategyId);
}
