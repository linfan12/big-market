package com.lin.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 抽奖因子实体，perform执行的使用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleFactorEntity {
    //用户id
    private String userId;
    //策略id
    private Long strategyId;
    //奖品id
    private Integer awardId;
}
