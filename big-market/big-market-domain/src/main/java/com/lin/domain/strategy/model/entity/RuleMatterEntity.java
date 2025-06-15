package com.lin.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleMatterEntity {

    /*用户id*/
    private String userId;
    /*策略ID*/
    private Long strategyId;
    /*抽奖奖品id【规则类型为策略，则不需要奖品ID】*/
    private Integer awardId;
    /*抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖（兜底奖品）】*/
    private String ruleModel;
}
