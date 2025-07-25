package com.lin.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动账户实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityAccountEntity {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 总次数-剩余
     */
    private Integer totalCountSurplus;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 日次数-剩余
     */
    private Integer dayCountSurplus;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 月次数-剩余
     */
    private Integer monthCountSurplus;
}
