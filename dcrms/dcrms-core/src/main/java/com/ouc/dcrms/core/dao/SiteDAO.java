package com.ouc.dcrms.core.dao;

import com.ouc.dcrms.core.model.Site;

public interface SiteDAO {
    int deleteByPrimaryKey(Integer siteid);

    int insert(Site record);

    int insertSelective(Site record);

    Site selectByPrimaryKey(Integer siteid);

    int updateByPrimaryKeySelective(Site record);

    int updateByPrimaryKey(Site record);
}