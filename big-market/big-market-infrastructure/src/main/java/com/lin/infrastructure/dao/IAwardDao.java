package com.lin.infrastructure.dao;

import com.lin.infrastructure.dao.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 奖品表dao
 */
@Mapper
public interface IAwardDao {

    public List<Award> queryAwardList();

    String queryAwardKeyByAwardId(Integer awardId);

    String queryAwardConfigByAwardId(Integer awardId);


}
