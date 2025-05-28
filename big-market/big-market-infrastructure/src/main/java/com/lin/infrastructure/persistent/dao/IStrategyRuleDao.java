package com.lin.infrastructure.persistent.dao;

import com.lin.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 策略规则dao
 */
@Mapper
public interface IStrategyRuleDao {
    public List<StrategyRule> queryStrategyRuleList();
}
