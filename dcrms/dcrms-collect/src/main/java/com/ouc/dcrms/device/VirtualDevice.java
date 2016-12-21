package com.ouc.dcrms.device;

import com.ouc.dcrms.initial.InitialInterface;
import com.ouc.dcrms.util.Instrument;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:51:23
 */

public interface VirtualDevice {

    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType);

    public String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface);
}
