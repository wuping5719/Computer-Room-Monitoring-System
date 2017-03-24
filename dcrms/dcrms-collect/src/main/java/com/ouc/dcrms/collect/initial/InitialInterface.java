package com.ouc.dcrms.collect.initial;

import java.io.IOException;
import java.net.UnknownHostException;

import com.ouc.dcrms.collect.initial.InitialComm;
import com.ouc.dcrms.collect.initial.InitialInternet;
import com.ouc.dcrms.collect.util.Instrument;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:53:14
 */

public class InitialInterface {
    public InitialInternet internetInstance;
    public InitialComm commInstance;
    public String instrumentModel;
    public int[] insIDArray; // 设备的绝对ID号，用于设备找到相应的初始化接口对象
    public String interfaceName;

    private enum InterfaceType {
	    Ethernet, GPIB, RS485, RS232, USB
    };

    public InitialInterface(Instrument instrument, int instrumentNum)
	    throws UnknownHostException, IOException {
	instrumentModel = instrument.getAttribution().getModel();
	insIDArray = new int[instrumentNum];
	this.interfaceName = instrument.getCommInterface().getInterfaceName();
	switch (InterfaceType.valueOf(this.interfaceName)) {
	  case Ethernet:
	     internetInstance = new InitialInternet(instrument);
	     break;
	  case GPIB:
	     break;
	  case RS485:
	     commInstance = new InitialComm(instrument);
	     break;
	  case RS232:
	     commInstance = new InitialComm(instrument);
	     break;
	  case USB:
	     break;
	}
    }
}
