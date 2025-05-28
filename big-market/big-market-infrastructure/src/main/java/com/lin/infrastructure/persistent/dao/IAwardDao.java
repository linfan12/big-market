package com.lin.infrastructure.persistent.dao;

import com.lin.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 奖品表dao
 */
@Mapper
public interface IAwardDao {

    public List<Award> queryAwardList();
}
