package com.ouc.dcrms.collect.device;

import com.ouc.dcrms.collect.initial.InitialInterface;
import com.ouc.dcrms.collect.util.Instrument;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:51:23
 */

public interface VirtualDevice {

    public abstract String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType);

    public abstract String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface);
}
