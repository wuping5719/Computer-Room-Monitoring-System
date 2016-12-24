package com.ouc.dcrms.device.dass;

import com.ouc.dcrms.device.VirtualDevice;
import com.ouc.dcrms.initial.InitialInterface;
import com.ouc.dcrms.util.Instrument;
import com.ouc.dcrms.util.data.CommandProcessing;
import com.ouc.dcrms.util.data.GlobalVariable;

/**
 * @author WuPing
 * @version 2016年12月23日 上午10:09:41
 */

public class Dass_KLW8000 implements VirtualDevice {

    @Override
    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType) {
	String flag = "0";
	byte[] cmd = new byte[8];
	cmd[0] = (byte) 0x01;
	cmd[1] = (byte) 0x04;
	cmd[2] = (byte) 0x07;
	cmd[3] = (byte) 0xD0;
	cmd[4] = (byte) 0x00;
	cmd[5] = (byte) 0x36;
	cmd[6] = (byte) 0x70;
	cmd[7] = (byte) 0x91;
	try {
	    initialInterface.commInstance.outputStream.write(cmd);
	} catch (Exception e) {
	    flag = "KLW8000 发命令失败!";
	    System.out.println("站点" + instrument.getAttribution().getSiteID() + "的"
		    + flag);
	}
	return flag;
    }

    @Override
    public String readData(String sensorsList, int sensorsLength,
	    Instrument instrument, InitialInterface initialInterface) {
	String data = "";
	String digital = "";
	String simulation = "";
	StringBuilder strData = new StringBuilder(); // 保存数据变量
	String uData = "";
	CommandProcessing cd = new CommandProcessing();
	int sensorLenth = cd.getStrSpecialCharNum(sensorsList) + 1; // 传感器个数
	String[] sensorArray = new String[sensorLenth];
	sensorArray = sensorsList.split(",");
	byte[] receive = null;
	int sensorCount = 0;
	try {
	    receive = new byte[initialInterface.commInstance.inputStream
		    .available()];
	    while (initialInterface.commInstance.inputStream.available() > 0) {
		initialInterface.commInstance.inputStream
			.read(receive);
	    }
	    data = cd.toHexString(receive);

	    if (!data.equals("")) {
		strData = new StringBuilder("g");
		digital = data.substring(6, 10);
		simulation = data.substring(22, 54);
		for (int i = 0; i < sensorLenth; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(j).getGlobalID()) {
			    if (instrument.getSensors().get(j).getInternalIdentifier() == 0) {
				uData = String.valueOf(currentData(simulation, instrument.getSensors().get(j).getRelativeID(), 1));
				strData.append(sensorArray[sensorCount]); 
				strData.append(":");
				strData.append(uData);
				strData.append("@");
				if ((Float.parseFloat(uData) < GlobalVariable.temp_min)
					&& (Float.parseFloat(uData) > 0)) {
				    // dbo.insertAlert(351,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (((Float.parseFloat(uData) > GlobalVariable.temp_max) && (Float
					.parseFloat(uData) < 100))) {
				    // dbo.insertAlert(352,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
			    }

			    if (instrument.getSensors().get(j).getInternalIdentifier() == 1) {
				uData = String.valueOf(currentData(simulation, instrument.getSensors().get(j).getRelativeID(), 2));
				strData.append(sensorArray[sensorCount]); 
				strData.append(":");
				strData.append(uData);
				strData.append("@");
				if ((Float.parseFloat(uData) < GlobalVariable.humi_min)
					&& (Float.parseFloat(uData) > 0)) {
				    // dbo.insertAlert(353,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (((Float.parseFloat(uData) > GlobalVariable.humi_max) && (Float
					.parseFloat(uData) < 100))) {
				    // dbo.insertAlert(352,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
			    }

			    // 0-温度、1-湿度（内部标识）；2-烟感、3-火感、4-红外、5-水浸、6-稳压电源、7-门禁（内部标识）
			    // 内部标识为1表示开关量
			    if (instrument.getSensors().get(j).getInternalIdentifier() > 1) {
				uData = String.valueOf(getDigital(digital, instrument.getSensors().get(j).getRelativeID())); // 开关量的值
				if ((instrument.getSensors().get(j).getInternalIdentifier() == 4 || instrument.getSensors().get(j).getInternalIdentifier() == 7)
					&& instrument.getAttribution().getSiteID() != 1) {
				    uData = String.valueOf((Integer.parseInt(uData) + 1) % 2);
				}

				strData.append(sensorArray[sensorCount]); 
				strData.append(":");
				strData.append(uData);
				strData.append("@");
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 2) {
				    // dbo.insertAlert(354,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 3) {
				    // dbo.insertAlert(355,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 4) {
				    // dbo.insertAlert(356,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 5) {
				    // dbo.insertAlert(357,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 6) {
				    // dbo.insertAlert(358,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
				if (uData.equals("0") && instrument.getSensors().get(j).getInternalIdentifier() == 7) {
				    // dbo.insertAlert(359,
				    // instrument.attribution.globalID,instrument.attribution.siteID);
				}
			    }
			}
		    }

		    sensorCount++;
		}

	    } else {
		strData = new StringBuilder("b");
		for (int i = 0; i < sensorLenth; i++) {
		    for (int j = 0; j < instrument.getSensors().size(); j++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getAttribution().getGlobalID()) {
			    uData = "888888";
			    strData.append(sensorArray[sensorCount]);
			    strData.append(":");
			    strData.append(uData);
			    strData.append("@");
			}
		    }
		    sensorCount++;
		}
	    }
	} catch (Exception e) {
	    strData = new StringBuilder("KLW8000读取数据失败!");
	    System.out.println("站点" + instrument.getAttribution().getSiteID() + "的"
		    + strData);
	}
	return strData.toString();
    }

    // 将电流转为相对应的参数
    // 电流输出4-20mA, 温度量程:0-50 湿度量程:0-100
    private float currentData(String data, int channelNum, int flag) {
	// flag 1:电流转温度; 2：电流转湿度
	String hex = data.substring(((channelNum - 1) * 4), 4 * channelNum);
	float result = 0;
	float tmp = ((float) Integer.parseInt(hex, 16)) / 1000;
	if (flag == 1) {
	    result = (float) (3.125 * tmp - 12.5);
	}
	if (flag == 2) {
	    result = (float) (6.25 * tmp - 25);
	}
	return result;
    }

    private int getDigital(String data, int channelNum) {
	int first = Integer.parseInt(data.substring(0, 2), 16);
	int second = Integer.parseInt(data.substring(2), 16);
	int result = 0;
	int tmp = 1;
	if (channelNum != 1 && channelNum != 9) {
	    if (channelNum <= 8 && channelNum > 1) {
		for (int i = 1; i < channelNum; i++) {
		    tmp = tmp * 2;
		}
	    }
	    if (channelNum <= 16 && channelNum > 9) {
		for (int i = 1; i < channelNum - 8; i++) {
		    tmp = tmp * 2;
		}
	    }
	}
	if (channelNum <= 8 && channelNum >= 1) {
	    result = first & (tmp);
	}
	if (channelNum >= 9 && channelNum <= 16) {
	    result = second & (tmp);
	}
	if (result != 0) {
	    result = 1;
	}
	return result;
    }

}
