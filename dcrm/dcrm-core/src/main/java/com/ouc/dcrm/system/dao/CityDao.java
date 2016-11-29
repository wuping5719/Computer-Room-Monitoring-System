package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.City;

public interface CityDao {
    int deleteByPrimaryKey(Integer cityid);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cityid);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}