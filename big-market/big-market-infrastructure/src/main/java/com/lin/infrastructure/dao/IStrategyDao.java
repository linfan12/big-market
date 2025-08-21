package com.lin.infrastructure.dao;

import com.lin.infrastructure.dao.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface IStrategyDao {
    public List<Strategy> queryStrategyList();

    Strategy queryStrategyByStrategyId(Long strategyId);
}
