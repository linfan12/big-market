package com.lin.infrastructure.persistent.dao;

import com.lin.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IStrategyDao {
    public List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);
}
