package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Reason;

public interface ReasonDAO {
    int deleteByPrimaryKey(Integer reasonid);

    int insert(Reason record);

    int insertSelective(Reason record);

    Reason selectByPrimaryKey(Integer reasonid);

    int updateByPrimaryKeySelective(Reason record);

    int updateByPrimaryKey(Reason record);
}