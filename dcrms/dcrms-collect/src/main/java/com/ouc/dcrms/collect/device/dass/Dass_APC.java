package com.ouc.dcrms.collect.device.dass;

import com.ouc.dcrms.collect.device.VirtualDevice;
import com.ouc.dcrms.collect.initial.InitialInterface;
import com.ouc.dcrms.collect.util.Instrument;
import com.ouc.dcrms.collect.util.data.CommandProcessing;
import com.ouc.dcrms.collect.util.data.GlobalVariable;

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
	    if (GlobalVariable.ct[instrument.getAttribution().getGlobalID() - 1] == 0) {
		initialInterface.commInstance.outputStream.write(init
			.getBytes());
		Thread.sleep(500);
		GlobalVariable.ct[instrument.getAttribution().getGlobalID() - 1] = 1;
		byte[] receive = new byte[initialInterface.commInstance.inputStream
			.available()];
		while (initialInterface.commInstance.inputStream.available() > 0) {
		    initialInterface.commInstance.inputStream.read(receive);
		}
	    }

	    // 内部标识只能是0-5
	    if (instrument.getSensors().size() >= 0
		    && instrument.getSensors().get(0).getInternalIdentifier() < 6) {
		initialInterface.commInstance.outputStream
			.write(cmd[instrument.getSensors().get(0).getInternalIdentifier()]
				.getBytes());
	    } else {
		flag = "APC的internalIdentifier超出的界限";
	    }

	} catch (Exception e) {
	    flag = "APC发送第" + instrument.getSensors().get(0).getInternalIdentifier()
		    + "个命令失败";
	    System.out.println("站点" + instrument.getAttribution().getSiteID() + "的"
		    + flag);
	}
	return flag;
    }

    @Override
    public String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface) {
	StringBuilder strData = new StringBuilder();
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
		if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		    strData = new StringBuilder("g");
		} else {
		    strData = new StringBuilder("m");
		}
		for (int i = 0; i < sensorLenth; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {
			    strData.append(instrument.getSensors().get(j).getGlobalID());
			    strData.append(":");
			    strData.append(receivedata);   // 状态
			    strData.append("@");
			}
		    }
		    sensorCount++;
		}
	    } else {
		if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		    strData = new StringBuilder("b");
		} else {
		    strData = new StringBuilder("m");
		}
		for (int i = 0; i < sensorLenth; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {
			    strData.append(strData);
			    strData.append(sensorArray[sensorCount]);
			    strData.append(":888888@");
			}
		    }
		    sensorCount++;
		}
	    }

	} catch (Exception e) {
	    if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		strData.append("APC");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    } else {
		strData.append("eAPC");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    }
	    System.out.println("站点" + instrument.getAttribution().getSiteID() + "的"
		    + strData);
	}
	return strData.toString();
    }
}
