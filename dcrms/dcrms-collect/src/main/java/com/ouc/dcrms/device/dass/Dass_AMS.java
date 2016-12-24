package com.ouc.dcrms.device.dass;

import com.ouc.dcrms.device.VirtualDevice;
import com.ouc.dcrms.initial.InitialInterface;
import com.ouc.dcrms.util.Instrument;
import com.ouc.dcrms.util.data.CommandProcessing;
import com.ouc.dcrms.util.data.PostProtocol;

/**
 * @author WuPing
 * @version 2016年12月20日 下午8:14:02
 */

public class Dass_AMS implements VirtualDevice {

    @Override
    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType) {
	String[] cmd = new String[6];
	CommandProcessing cd = new CommandProcessing();
	String flag = "0";
	String address = String.valueOf(instrument.getCommInterface().getAddress());
	if (address.length() == 1) { // 一位地址长度补齐为两位格式，如1补齐为01
	    address = "0" + address;
	}
	cmd[0] = "20" + address + "42410000"; // 读直流模拟量(电压 电流)
	cmd[1] = "20" + address + "41430000"; // 浮充/均充/测试
	cmd[2] = "20" + address + "4044E00200"; // 交流告警
	cmd[3] = "20" + address + "42440000"; // 直流
	cmd[4] = "20" + address + "42410000"; // 整流
	cmd[5] = "20" + address + "41410000"; // 整流模拟量
	for (int i = 0; i < 6; i++) {
	    // cmd[i] ="7E" + checkDJ(cmd[i]) + "0D"; // 这句也是正确的
	    cmd[i] = PostProtocol.transFormAsciiCommand(cmd[i]);
	}
	try {
	    if (instrument.getSensors().size() >= 0
		    && instrument.getSensors().get(0).getInternalIdentifier() < 6) // 内部标识只能是0-4
	    {
		initialInterface.commInstance.outputStream.write(cd
			.toByte(cmd[instrument.getSensors().get(0).getInternalIdentifier()]));
	    } else {
		flag = "AMS的internalIdentifier超出的界限";
	    }
	} catch (Exception e) {
	    flag = "AMS发送第" + instrument.getSensors().get(0).getInternalIdentifier()
		    + "个命令失败";
	}
	return flag;
    }

    @Override
    public String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface) {
	StringBuilder strData = new StringBuilder();
	String recdata;
	int flag = 0;
	CommandProcessing cd = new CommandProcessing();
	int sensorLength = cd.getStrSpecialCharNum(sensorsList) + 1; // 传感器个数
	String[] sensorArray = new String[sensorLength];
	sensorArray = sensorsList.split(",");
	int sensorCount = 0;
	byte[] receive = null;

	try {
	    receive = new byte[initialInterface.commInstance.inputStream
		    .available()];
	    while (initialInterface.commInstance.inputStream.available() > 0) {
		initialInterface.commInstance.inputStream.read(receive);
	    }
	    recdata = cd.toHexString(receive);

	    if (recdata.length() == 248) {
		if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		    strData = new StringBuilder("g");
		} else {
		    strData = new StringBuilder("m");
		}
		flag = 1;
		for (int i = 0; i < sensorLength; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {

			    switch (instrument.getSensors().get(j).getRelativeID())// A相
			    {
			    case 1:
				strData.append(instrument.getSensors().get(j).getGlobalID());
				strData.append(":");
				strData.append(stringToFloat(recdata.substring(34,50))); // 直流电压
				strData.append("@");    
				break;   
			    case 2:
				strData.append(instrument.getSensors().get(j).getGlobalID());
				strData.append(":");
				strData.append(stringToFloat(recdata.substring(50,66))); // 直流电流
				strData.append("@");    
				break;
			    }
			}
		    }
		    sensorCount++;
		}

	    }

	    if (recdata.length() == 188) {
		strData = new StringBuilder("m");
		flag = 1;
		for (int i = 0; i < sensorLength; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {
			    String chargeStr;
			    chargeStr = recdata.substring(42, 46);// 充电状态
			    if (chargeStr.equals("3030")) {
				strData.append(instrument.getSensors().get(j).getGlobalID());
				strData.append(":0@");
			    } else if (chargeStr.equals("3031")) {
				strData.append(instrument.getSensors().get(j).getGlobalID());
				strData.append(":1@");
			    } else if (chargeStr.equals("3032")) {
				strData.append(instrument.getSensors().get(j).getGlobalID());
				strData.append(":2@");
			    }
			    chargeStr = null;
			}
		    }
		    sensorCount++;
		}
	    }

	    if (recdata.length() == 120) {
		strData = new StringBuilder("m");
		flag = 1;
		// 第1路交流输入告警量
		String err;
		err = recdata.substring(34, 38);
		if (err.equals("3031")) {
		    // 第1路输入线/相电压AB/A低于下限 rtid 70
		    // insertAlert(5, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(34, 38).equals("3032")) {
		    // 第1路输入线/相电压AB/A高于上限 rtid 71
		    // insertAlert(6, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (recdata.substring(38, 42).equals("3031")) {
		    // 第1路输入线/相电压BC/B低于下限 rtid 72
		    // insertAlert(7, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(38, 42).equals("3032")) {
		    // 第1路输入线/相电压BC/B高于上限 rtid 73
		    // insertAlert(8, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (recdata.substring(42, 46).equals("3031")) {
		    // 第1路输入线/相电压CA/C低于下限 rtid 74
		    // insertAlert(9, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(42, 46).equals("3032")) {
		    // 第1路输入线/相电压CA/C高于上限 rtid 75
		    // insertAlert(10, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}

		// 第2路交流输入告警量
		if (recdata.substring(74, 78).equals("3031")) {
		    // 第2路输入线/相电压AB/A低于下限 rtid 76
		    // insertAlert(11, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(74, 78).equals("3032")) {
		    // 第2路输入线/相电压AB/A高于上限 rtid 77
		    // insertAlert(12, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (recdata.substring(78, 82).equals("3031")) {
		    // 第2路输入线/相电压BC/B低于下限 rtid 78
		    // insertAlert(13, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(78, 82).equals("3032")) {
		    // 第2路输入线/相电压BC/B高于上限 rtid 79
		    // insertAlert(14, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (recdata.substring(82, 86).equals("3031")) {
		    // 第2路输入线/相电压CA/C低于下限 rtid 80
		    // insertAlert(15, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(82, 86).equals("3032")) {
		    // 第3路输入线/相电压CA/C高于上限 rtid 81
		    // insertAlert(16, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
	    }

	    if (recdata.length() == 200) {
		strData = new StringBuilder("m");
		flag = 1;
		if (recdata.substring(34, 38).equals("3031")) {
		    // 直流电压低于下限
		    // insertAlert(17, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		} else if (recdata.substring(34, 38).equals("3032")) {
		    // 直流电压高于上限
		    // insertAlert(18, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}

		for (int i = 1; i <= 10; i++) {
		    if (recdata.substring(42 + 4 * (i - 1), 42 + 4 * i).equals(
			    "3033")) {
			// 直流熔丝/开关i断
			// insertAlert(18 + i, instrument.attribution.globalID,
			// instrument.attribution.siteID);
		    }
		}
	    }

	    if (recdata.length() == 172) {
		strData = new StringBuilder("m");
		flag = 1;
		for (int i = 1; i <= 4; i++) {
		    if (recdata.substring(34 + 32 * (i - 1), 38 + 32 * (i - 1))
			    .equals("3031")) {
			// 整流模块i故障
			// insertAlert(28 + i, instrument.attribution.globalID,
			// instrument.attribution.siteID);
		    }
		}
	    } else {
		if (flag == 0) {

		    if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
			strData = new StringBuilder("b");
		    } else {
			strData = new StringBuilder("m");
		    }
		    for (int i = 0; i < sensorLength; i++) {
			for (int j = 0; j < instrument.getSensors().size(); j++) {
			    if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {
				strData.append(sensorArray[sensorCount]);
				strData.append(":888888@");
			    }
			}
			sensorCount++;
		    }
		}
	    }
	} catch (Exception e) {
	    if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		strData.append("AMS");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    } else {
		strData.append("eAMS");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    }
	}
	return strData.toString();
    }

    private float stringToFloat(String str) {
	if (str.length() != 16)
	    return (float) 0.0;

	char[] c = new char[8];
	String[] s = new String[8];
	int[] inte = new int[8];

	for (int i = 0, j = 0; i < 8 && j < 16; i++, j += 2)
	    s[i] = str.substring(j, j + 2);

	for (int i = 0; i < 8; i++)
	    inte[i] = Integer.valueOf(s[i], 16).intValue();

	for (int i = 0; i < 8; i++)
	    c[i] = (char) inte[i];

	String strRes = null;
	if (c[6] >= '8' && c[6] <= '9') {
	    c[6] = (char) ((int) c[6] - 8);
	    strRes = "-" + String.valueOf(c[6]) + String.valueOf(c[7])
		    + String.valueOf(c[4]) + String.valueOf(c[5])
		    + String.valueOf(c[2]) + String.valueOf(c[3])
		    + String.valueOf(c[0]) + String.valueOf(c[1]);
	} else if (c[6] >= 'A' && c[6] <= 'F') {
	    c[6] = (char) ((int) c[6] - 15);
	    strRes = "-" + String.valueOf(c[6]) + String.valueOf(c[7])
		    + String.valueOf(c[4]) + String.valueOf(c[5])
		    + String.valueOf(c[2]) + String.valueOf(c[3])
		    + String.valueOf(c[0]) + String.valueOf(c[1]);
	} else {
	    strRes = String.valueOf(c[6]) + String.valueOf(c[7])
		    + String.valueOf(c[4]) + String.valueOf(c[5])
		    + String.valueOf(c[2]) + String.valueOf(c[3])
		    + String.valueOf(c[0]) + String.valueOf(c[1]);
	}

	return Float.intBitsToFloat(Integer.valueOf(strRes, 16));
    }

}
