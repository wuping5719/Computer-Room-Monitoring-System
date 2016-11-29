package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Reason;

public interface ReasonDao {
    int deleteByPrimaryKey(Integer reasonid);

    int insert(Reason record);

    int insertSelective(Reason record);

    Reason selectByPrimaryKey(Integer reasonid);

    int updateByPrimaryKeySelective(Reason record);

    int updateByPrimaryKey(Reason record);
}