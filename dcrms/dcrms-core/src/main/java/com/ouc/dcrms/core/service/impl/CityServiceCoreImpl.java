package com.ouc.dcrms.core.service.impl;

import java.util.List;

import com.ouc.dcrms.core.service.CityServiceCore;
import com.ouc.dcrms.core.dao.CityDAO;
import com.ouc.dcrms.core.model.City;

public class CityServiceCoreImpl implements CityServiceCore {
    
    private CityDAO cityDAO;

    @Override
    public List<City> searchAllCity() {
	List<City> cityList = cityDAO.selectAllCity();
	return cityList;
    }

    @Override
    public City selectByPrimaryKey(Integer cityid) {
	City city = cityDAO.selectByPrimaryKey(cityid);
	return city;
    }
    
    public CityDAO getCityDAO() {
	return cityDAO;
    }

    public void setCityDAO(CityDAO cityDAO) {
	this.cityDAO = cityDAO;
    }
    
}
