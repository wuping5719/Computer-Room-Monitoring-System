package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Resources;

public interface ResourcesDao {
    int deleteByPrimaryKey(Integer resid);

    int insert(Resources record);

    int insertSelective(Resources record);

    Resources selectByPrimaryKey(Integer resid);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);
}