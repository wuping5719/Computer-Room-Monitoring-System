package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Actiontype;

public interface ActiontypeDao {
    int deleteByPrimaryKey(Integer actionid);

    int insert(Actiontype record);

    int insertSelective(Actiontype record);

    Actiontype selectByPrimaryKey(Integer actionid);

    int updateByPrimaryKeySelective(Actiontype record);

    int updateByPrimaryKey(Actiontype record);
}