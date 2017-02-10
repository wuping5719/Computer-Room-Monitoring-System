package com.ouc.dcrms.client.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ouc.dcrms.client.dto.CityDTO;
import com.ouc.dcrms.client.service.SiteServiceClient;
import com.ouc.dcrms.core.service.CityServiceCore;
import com.ouc.dcrms.core.model.City;

public class SiteServiceClientImpl implements SiteServiceClient {
    
    private CityServiceCore cityServiceCore;

    @Override
    public List<CityDTO> getAllCity() {
	List<City> cityList  = cityServiceCore.searchAllCity();
	List<CityDTO> cityDTOList = new ArrayList<>();
	for(City city: cityList) {
	    CityDTO cityDTO = new CityDTO();
	    cityDTO.setCityid(city.getCityid());
	    cityDTO.setCityname(city.getCityname());
	    cityDTOList.add(cityDTO);
	}
	return cityDTOList;
    }

    public CityServiceCore getCityServiceCore() {
	return cityServiceCore;
    }

    public void setCityServiceCore(CityServiceCore cityServiceCore) {
	this.cityServiceCore = cityServiceCore;
    }
}
