package com.lin.infrastructure.persistent.dao;

import com.lin.infrastructure.persistent.po.Strategy;
import com.lin.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 抽奖策略的奖励明细，概率，规则dao
 */
@Mapper
public interface IStrategyAwardDao {
    public List<StrategyAward> queryStrategyAwardList();
}
