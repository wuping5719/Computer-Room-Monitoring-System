package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.AlertRecord;

public interface AlertRecordDao {
    int deleteByPrimaryKey(Long alertid);

    int insert(AlertRecord record);

    int insertSelective(AlertRecord record);

    AlertRecord selectByPrimaryKey(Long alertid);

    int updateByPrimaryKeySelective(AlertRecord record);

    int updateByPrimaryKey(AlertRecord record);
}