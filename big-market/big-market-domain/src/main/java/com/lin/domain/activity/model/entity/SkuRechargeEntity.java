package com.lin.domain.activity.model.entity;

import lombok.Data;

/**
 * 活动商品冲值实体对象
 */
@Data
public class SkuRechargeEntity {

    /*用户id*/
    private String userId;

    /*商品sku - activity + activity count */
    private Long sku;

    /*幂等业务单号。外部谁充值谁透传，这样来保证幂等（多次调用也能保证结果的唯一，不会多次充值）*/
    private String outBusinessNo;
}
