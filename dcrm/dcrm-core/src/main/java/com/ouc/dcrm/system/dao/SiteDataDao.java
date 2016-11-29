package com.ouc.dcrm.system.dao;

import com.ouc.dcrm.system.model.SiteData;

public interface SiteDataDao {
    int deleteByPrimaryKey(Long dataid);

    int insert(SiteData record);

    int insertSelective(SiteData record);

    SiteData selectByPrimaryKey(Long dataid);

    int updateByPrimaryKeySelective(SiteData record);

    int updateByPrimaryKey(SiteData record);
}