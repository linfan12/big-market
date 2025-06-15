package com.lin.domain.strategy.repository;

import com.lin.domain.strategy.model.entity.StrategyAwardEntity;
import com.lin.domain.strategy.model.entity.StrategyEntity;
import com.lin.domain.strategy.model.entity.StrategyRuleEntity;

import java.math.BigDecimal;
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


    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);


    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);
}
