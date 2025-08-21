package com.lin.infrastructure.dao.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RaffleActivitySku {
    /**
     * 自增id
     */
    private Long id;
    /**
     * 商品sku
     */
    private Long sku;
    /**
     * 活动id
     */
    private Long activityId;
    /**
     *  活动个人参与次数id
     */
    private Long activityCountId;
    /**
     * 库存总量
     */
    private Integer stockCount;
    /**
     * 剩余库存量
     */
    private Integer stockCountSurplus;
    /**
     * 商品金额【积分】
     */
    private BigDecimal productAmount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
