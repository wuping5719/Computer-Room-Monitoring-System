package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Resources;

public interface ResourcesDAO {
    int deleteByPrimaryKey(Integer resid);

    int insert(Resources record);

    int insertSelective(Resources record);

    Resources selectByPrimaryKey(Integer resid);

    int updateByPrimaryKeySelective(Resources record);

    int updateByPrimaryKey(Resources record);
}