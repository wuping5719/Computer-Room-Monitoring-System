package com.ouc.dcrms.collect.util;

import java.util.List;

import com.ouc.dcrms.collect.util.Attribution;
import com.ouc.dcrms.collect.util.CommInterface;
import com.ouc.dcrms.collect.util.Sensor;

/**
 * @author WuPing
 * @version 2016年12月20日 下午3:55:11
 */

public class Instrument {
    private Attribution attribution;
    private CommInterface commInterface;
    private List<Sensor> sensors;
    
    public Attribution getAttribution() {
	return attribution;
    }

    public void setAttribution(Attribution attribution) {
	this.attribution = attribution;
    }

    public CommInterface getCommInterface() {
	return commInterface;
    }

    public void setCommInterface(CommInterface commInterface) {
	this.commInterface = commInterface;
    }

    public List<Sensor> getSensors() {
	return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
	this.sensors = sensors;
    }
}
