package com.ouc.dcrms.core.service.impl;

import com.ouc.dcrms.core.model.Site;
import com.ouc.dcrms.core.service.SiteServiceCore;
import com.ouc.dcrms.core.dao.SiteDAO;

public class SiteServiceCoreImpl implements SiteServiceCore {
    
    private SiteDAO siteDAO;

    @Override
    public Site selectByPrimaryKey(Integer siteid) {
	Site site = siteDAO.selectByPrimaryKey(siteid);
	return site;
    }

    public SiteDAO getSiteDAO() {
	return siteDAO;
    }

    public void setSiteDAO(SiteDAO siteDAO) {
	this.siteDAO = siteDAO;
    }
    
}
