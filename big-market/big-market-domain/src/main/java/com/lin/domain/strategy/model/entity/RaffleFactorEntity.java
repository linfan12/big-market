package com.lin.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    /** 结束时间 */
    private Date endDateTime;
}
