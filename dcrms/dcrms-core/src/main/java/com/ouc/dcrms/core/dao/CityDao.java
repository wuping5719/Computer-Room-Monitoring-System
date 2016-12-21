package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.City;

public interface CityDao {
    int deleteByPrimaryKey(Integer cityid);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cityid);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}