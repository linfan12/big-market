package com.lin.domain.award.repository;

import com.lin.domain.award.model.aggregate.GiveOutPrizesAggregate;
import com.lin.domain.award.model.aggregate.UserAwardRecordAggregate;

/**
 * @author Fuzhengwei bugstack.cn @小傅哥
 * @description 奖品仓储服务
 * @create 2024-04-06 09:02
 */
public interface IAwardRepository {

    void saveUserAwardRecord(UserAwardRecordAggregate userAwardRecordAggregate);

    String queryAwardKey(Integer awardId);

    String queryAwardConfig(Integer awardId);

    void saveGiveOutPrizesAggregate(GiveOutPrizesAggregate giveOutPrizesAggregate);
}
