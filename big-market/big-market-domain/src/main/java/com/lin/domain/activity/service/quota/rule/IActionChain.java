package com.lin.domain.activity.service.quota.rule;

import com.lin.domain.activity.model.entity.ActivityCountEntity;
import com.lin.domain.activity.model.entity.ActivityEntity;
import com.lin.domain.activity.model.entity.ActivitySkuEntity;

/**
 * 下单规则过滤接口
 */
public interface IActionChain extends IActionChainArmory{

    boolean action(ActivitySkuEntity activitySkuEntity, ActivityEntity activityEntity, ActivityCountEntity activityCountEntity);
}
