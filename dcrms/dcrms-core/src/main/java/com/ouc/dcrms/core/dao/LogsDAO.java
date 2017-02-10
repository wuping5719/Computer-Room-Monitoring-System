package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Logs;

public interface LogsDAO {
    int deleteByPrimaryKey(Integer logid);

    int insert(Logs record);

    int insertSelective(Logs record);

    Logs selectByPrimaryKey(Integer logid);

    int updateByPrimaryKeySelective(Logs record);

    int updateByPrimaryKey(Logs record);
}