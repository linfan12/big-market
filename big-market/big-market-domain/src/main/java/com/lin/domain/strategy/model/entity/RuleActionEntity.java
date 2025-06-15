package com.lin.domain.strategy.model.entity;

import com.lin.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RuleActionEntity <T extends RuleActionEntity.RaffleEntity>{

    private String code = RuleLogicCheckTypeVO.ALLOW.getCode();
    private String info = RuleLogicCheckTypeVO.ALLOW.getInfo();
    private String ruleModel;
    private T data;

    static public class RaffleEntity{


    }

    /*抽奖之前*/
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    static public class RaffleBeforeEntity extends RaffleEntity{
        /*策略id*/
        private Long strategyId;
        /*权重值key，用于抽奖是可选择权重抽奖*/
        private String ruleWeightValueKey;
        /*奖品id*/
        private Integer awardId;
    }
    /*抽奖中*/
    static public class RaffleCenterEntity extends RaffleEntity{

    }
    /*抽奖之后*/
    static public class RaffleAfterEntity extends RaffleEntity{

    }
}
