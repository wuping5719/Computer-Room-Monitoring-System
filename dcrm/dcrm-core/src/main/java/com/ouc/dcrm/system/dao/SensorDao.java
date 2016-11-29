package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Sensor;

public interface SensorDao {
    int deleteByPrimaryKey(Integer sensorid);

    int insert(Sensor record);

    int insertSelective(Sensor record);

    Sensor selectByPrimaryKey(Integer sensorid);

    int updateByPrimaryKeySelective(Sensor record);

    int updateByPrimaryKey(Sensor record);
}