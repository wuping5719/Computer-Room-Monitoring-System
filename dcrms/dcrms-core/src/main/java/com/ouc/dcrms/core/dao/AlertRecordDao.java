package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.AlertRecord;

public interface AlertRecordDao {
    int deleteByPrimaryKey(Long alertid);

    int insert(AlertRecord record);

    int insertSelective(AlertRecord record);

    AlertRecord selectByPrimaryKey(Long alertid);

    int updateByPrimaryKeySelective(AlertRecord record);

    int updateByPrimaryKey(AlertRecord record);
}