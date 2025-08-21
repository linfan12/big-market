package com.lin.infrastructure.dao;

import com.lin.infrastructure.dao.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 抽奖策略的奖励明细，概率，规则dao
 */
@Mapper
public interface IStrategyAwardDao {
    public List<StrategyAward> queryStrategyAwardList();

    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);

    String queryStrategyAwardRuleModels(StrategyAward strategyAward);

    void updateStrategyAwardStock(StrategyAward strategyAward);

    StrategyAward queryStrategyAward(StrategyAward strategyAwardReq);

    List<StrategyAward> queryOpenActivityStrategyAwardList();

}
