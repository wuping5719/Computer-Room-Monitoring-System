package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Coordinate;

public interface CoordinateDao {
    int deleteByPrimaryKey(Integer coordinateid);

    int insert(Coordinate record);

    int insertSelective(Coordinate record);

    Coordinate selectByPrimaryKey(Integer coordinateid);

    int updateByPrimaryKeySelective(Coordinate record);

    int updateByPrimaryKey(Coordinate record);
}