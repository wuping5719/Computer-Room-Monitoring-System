package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.Site;

public interface SiteDao {
    int deleteByPrimaryKey(Integer siteid);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(Integer siteid);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
}