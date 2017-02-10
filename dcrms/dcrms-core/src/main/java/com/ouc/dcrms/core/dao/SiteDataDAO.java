package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.SiteData;

public interface SiteDataDAO {
    int deleteByPrimaryKey(Long dataid);

    int insert(SiteData record);

    int insertSelective(SiteData record);

    SiteData selectByPrimaryKey(Long dataid);

    int updateByPrimaryKeySelective(SiteData record);

    int updateByPrimaryKey(SiteData record);
}