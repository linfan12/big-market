package com.lin.domain.strategy.service.rule.chain.Impl;

import com.lin.domain.strategy.repository.IStrategyRepository;
import com.lin.domain.strategy.service.armory.IStrategyDispatch;
import com.lin.domain.strategy.service.rule.chain.AbstractLogicChain;
import com.lin.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.lin.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 权重
 */
@Slf4j
@Component("rule_weight")
public class RuleWeightLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository repository;

    @Resource
    protected IStrategyDispatch strategyDispatch;

    public Long userScore = 0L;

    /**
     * 权重规则过滤
     * 1.权重规则格式：4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
     * 2.解析数据格式，判断哪个范围符合用户的特定抽奖范围
     * @param userId      用户id
     * @param strategyId 策略id
     * @return
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-权重开始 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());

        //4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
        String ruleValue = repository.queryStrategyRuleValue(strategyId, ruleModel());

        //1.根据用户id查询用户消耗的积分值，本章节我们可以先写死为固定的值，后续需要从数据可中查询
        Map<Long, String> analyticalValueGroup = getAnalyticalValue(ruleValue);
        if (null == analyticalValueGroup || analyticalValueGroup.isEmpty()) return null;

        //2.转换keys值，并排序
        List<Long> analyticalSortedKeys = new ArrayList<>(analyticalValueGroup.keySet());
        Collections.sort(analyticalSortedKeys);

        //3.找出最小的符合的值，也就是【4500积分，能找到4000：102,102,103,104,105】
        Long nextValue = analyticalSortedKeys.stream()
                .filter(key -> userScore >= key)
                .findFirst()
                .orElse(null);
        if (null != nextValue){
            //根据4000：102,102,103,104,105查询奖品id
            Integer awardId = strategyDispatch.getRandomAwardId(strategyId, analyticalValueGroup.get(nextValue));
            log.info("抽奖责任链-权重接管 userId:{} strategyId:{} ruleModel:{} awardId:{}", userId, strategyId, ruleModel(), awardId);
            return DefaultChainFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .logicModel(ruleModel())
                    .build();
        }

        //放行过滤其他责任链
        log.info("抽奖责任链-权重放行 userId:{} strategyId:{} ruleModel:{}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode();
    }

    /*解析获取规则值，ruleValueMap={{key=4000，value=4000:102,103,104,105}，{5000，5000:102,103,104,105,106,107} ，{6000:102,103,104,105,106,107,108,109}}*/
    private Map<Long, String> getAnalyticalValue(String ruleValue){
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        //检查是否为空
        for (String ruleValueKey : ruleValueGroups) {
            if (ruleValueKey == null || ruleValueKey.isEmpty()){
                return ruleValueMap;
            }

            //分割字符串，以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);
            if (parts.length != 2){
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValueKey);
            }
            //key=4000，value=4000:102,103,104,105
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }
}
