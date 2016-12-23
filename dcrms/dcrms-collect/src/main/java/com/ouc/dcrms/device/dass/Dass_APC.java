package com.ouc.dcrms.device.dass;

import com.ouc.dcrms.device.VirtualDevice;
import com.ouc.dcrms.initial.InitialInterface;
import com.ouc.dcrms.util.Instrument;
import com.ouc.dcrms.util.data.GlobalVariable;
import com.ouc.dcrms.util.data.CommandProcessing;

/**
 * @author WuPing
 * @version 2016年12月23日 上午9:50:52
 */

public class Dass_APC implements VirtualDevice {
    @Override
    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType) {
	String flag = "0";
	String[] cmd = new String[6];
	cmd[0] = "B";
	cmd[1] = "C";
	cmd[2] = "F";
	cmd[3] = "L";
	cmd[4] = "O";
	cmd[5] = "P";
	String init = "Y";
	try {
	    if (GlobalVariable.ct[instrument.attribution.globalID - 1] == 0) {
		initialInterface.commInstance.outputStream.write(init
			.getBytes());
		Thread.sleep(500);
		GlobalVariable.ct[instrument.attribution.globalID - 1] = 1;
		byte[] receive = new byte[initialInterface.commInstance.inputStream
			.available()];
		while (initialInterface.commInstance.inputStream.available() > 0) {
		    initialInterface.commInstance.inputStream.read(receive);
		}
	    }

	    // 内部标识只能是0-5
	    if (instrument.sensorNumber >= 0
		    && instrument.sensors[0].internalIdentifier < 6) {
		initialInterface.commInstance.outputStream
			.write(cmd[instrument.sensors[0].internalIdentifier]
				.getBytes());
	    } else {
		flag = "APC的internalIdentifier超出的界限";
	    }

	} catch (Exception e) {
	    flag = "APC发送第" + instrument.sensors[0].internalIdentifier
		    + "个命令失败";
	    System.out.println("站点" + instrument.attribution.siteID + "的"
		    + flag);
	}
	return flag;
    }

    @Override
    public String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface) {
	String strData = "";
	CommandProcessing cd = new CommandProcessing();
	int sensorLenth = cd.getStrSpecialCharNum(sensorsList) + 1;// 传感器个数
	String[] sensorArray = new String[sensorLenth];
	sensorArray = sensorsList.split(",");
	int sensorCount = 0;
	byte[] receive = null;
	try {
	    receive = new byte[initialInterface.commInstance.inputStream
		    .available()];
	    while (initialInterface.commInstance.inputStream.available() > 0) {
		initialInterface.commInstance.inputStream.read(receive);
	    }
	    String receivedata = new String(receive).trim();
	    if (receivedata.length() > 0) {
		if (instrument.sensors[0].internalIdentifier == 0) {
		    strData = "g";
		} else {
		    strData = "m";
		}
		for (int i = 0; i < sensorLenth; i++) {
		    for (int sensorNum = 0; sensorNum < instrument.sensorNumber; sensorNum++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.sensors[sensorNum].globalID) {
			    strData += instrument.sensors[sensorNum].globalID
				    + ":" + receivedata + "@"; // 状态
			}
		    }
		    sensorCount++;
		}
	    } else {
		if (instrument.sensors[0].internalIdentifier == 0) {
		    strData = "b";
		} else {
		    strData = "m";
		}
		for (int i = 0; i < sensorLenth; i++) {
		    for (int sensorNum = 0; sensorNum < instrument.sensorNumber; sensorNum++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.sensors[sensorNum].globalID) {
			    strData = strData + sensorArray[sensorCount]
				    + ":888888@";
			}
		    }
		    sensorCount++;
		}
	    }

	} catch (Exception e) {
	    if (instrument.sensors[0].internalIdentifier == 0) {
		strData = "APC" + instrument.sensors[0].internalIdentifier
			+ "获取数据失败";
	    } else {
		strData = "eAPC" + instrument.sensors[0].internalIdentifier
			+ "获取数据失败";
	    }
	    System.out.println("站点" + instrument.attribution.siteID + "的"
		    + strData);
	}
	return strData;
    }
}
