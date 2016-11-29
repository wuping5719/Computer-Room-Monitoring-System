package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Logs;

public interface LogsDao {
    int deleteByPrimaryKey(Integer logid);

    int insert(Logs record);

    int insertSelective(Logs record);

    Logs selectByPrimaryKey(Integer logid);

    int updateByPrimaryKeySelective(Logs record);

    int updateByPrimaryKey(Logs record);
}