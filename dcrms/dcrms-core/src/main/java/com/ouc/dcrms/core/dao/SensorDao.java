package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Sensor;

public interface SensorDao {
    int deleteByPrimaryKey(Integer sensorid);

    int insert(Sensor record);

    int insertSelective(Sensor record);

    Sensor selectByPrimaryKey(Integer sensorid);

    int updateByPrimaryKeySelective(Sensor record);

    int updateByPrimaryKey(Sensor record);
}