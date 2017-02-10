package com.ouc.dcrms.core.dao;

import java.util.List;

import com.ouc.dcrms.core.model.City;

public interface CityDAO {
    int deleteByPrimaryKey(Integer cityid);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer cityid);

    List<City> selectAllCity();
    
    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}