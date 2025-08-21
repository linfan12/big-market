package com.lin.domain.rebate.repository;

import com.lin.domain.rebate.model.aggregate.BehaviorRebateAggregate;
import com.lin.domain.rebate.model.entity.BehaviorRebateOrderEntity;
import com.lin.domain.rebate.model.valobj.BehaviorTypeVO;
import com.lin.domain.rebate.model.valobj.DailyBehaviorRebateVO;

import java.util.List;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 行为返利服务仓储接口
 * @create 2024-04-30 14:58
 */
public interface IBehaviorRebateRepository {

    /**
     * 签到可能返利的类型有，增加抽奖额度以及积分
     * @param behaviorTypeVO
     * @return
     */
    List<DailyBehaviorRebateVO> queryDailyBehaviorRebateConfig(BehaviorTypeVO behaviorTypeVO);

    void saveUserRebateRecord(String userId, List<BehaviorRebateAggregate> behaviorRebateAggregates);

    List<BehaviorRebateOrderEntity> queryOrderByOutBusinessNo(String userId, String outBusinessNo);
}
