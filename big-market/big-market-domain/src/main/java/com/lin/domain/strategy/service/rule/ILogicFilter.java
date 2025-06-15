package com.lin.domain.strategy.service.rule;

import com.lin.domain.strategy.model.entity.RuleActionEntity;
import com.lin.domain.strategy.model.entity.RuleMatterEntity;

/**
 * 抽奖规则过滤接口
 * @param <T>
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity>{

    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
