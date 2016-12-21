package com.ouc.dcrms.device.dass;

import com.ouc.dcrms.device.VirtualDevice;
import com.ouc.dcrms.initial.InitialInterface;
import com.ouc.dcrms.util.Instrument;
import com.ouc.dcrms.util.data.CRC16;
import com.ouc.dcrms.util.data.CommandProcessing;
import com.ouc.dcrms.util.data.GlobalVariable;

public class Dass_A40 implements VirtualDevice {

    @Override
    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType) {
	String flag = "0";
	CommandProcessing cd = new CommandProcessing();
	String[] cmd = new String[2];
	String address = String.valueOf(instrument.commInterface.address);
	if (address.length() == 1) {   //一位地址长度补齐为两位格式，如1补齐为01
	    address = "0" + address;
	}
	cmd[0] = address + "0302000007"; // 获取参数 获取CT，一个字是四个字节
	cmd[1] = address + "0307000064";
	for (int i = 0; i < 2; i++) {
	    cmd[i] = cmd[i] + CRC16.getCRC16(cmd[i]);
	}

	try {
	    if (instrument.sensorNumber >= 0
		    && instrument.sensors[0].internalIdentifier < 2) // 内部标识只能是0-1
	    {
		initialInterface.commInstance.outputStream.write(cd
			.toByte(cmd[instrument.sensors[0].internalIdentifier]));
	    } else {
		flag = "KSP1的internalIdentifier超出的界限";
	    }
	} catch (Exception e) {
	    flag = "A40发送第" + instrument.sensors[0].internalIdentifier
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
	    System.out.println("RecDataLength: " + recdata.length() + " A40:"
		    + recdata);

	    if (recdata.length() == 410 && CRC16.checkCrc16(recdata)) {
		flag = 1;
		strData = "g";
		float[] u = { 0, 0, 0 };
		float[] a = { 0, 0, 0 };
		float[] px = { 0, 0, 0 };
		float[] py = { 0, 0, 0 };
		float[] pz = { 0, 0, 0 };
		float[] pp = { 0, 0, 0 };
		float f;

		int ct;
		float pt;

		ct = GlobalVariable.ct[instrument.attribution.globalID - 1];
		pt = GlobalVariable.pt[instrument.attribution.globalID - 1];

		String iResponse = recdata.substring(6, 18);
		String uResponse = recdata.substring(34, 46);
		String fResponse = recdata.substring(46, 50);
		String powerResponse = recdata.substring(66, 102);
		String ppResponse = recdata.substring(102, 114);

		for (int i = 0; i < 3; i++) {
		    u[i] = (float) (Integer.parseInt(
			    uResponse.substring(4 * i, 4 * (i + 1)), 16))
			    * pt / 10;
		}
		for (int i = 0; i < 3; i++) {
		    a[i] = (float) (Integer.parseInt(
			    iResponse.substring(4 * i, 4 * (i + 1)), 16))
			    * ct / 1000; // /1000 由mA变为A
		}
		f = ((float) Integer.parseInt(fResponse, 16)) / 100;
		for (int i = 0; i < 3; i++) {
		    pp[i] = (float) ((0xffff - Integer.parseInt(
			    ppResponse.substring(4 * i, 4 * (i + 1)), 16))) / 1000;
		}
		for (int i = 0; i < 3; i++) {
		    int x = Integer.parseInt(
			    powerResponse.substring(4 * i, 4 * (i + 1)), 16);
		    if (x > 32767)
			x = (-1) * (65536 - x);
		    px[i] = (float) x * ct / 1000;
		}
		for (int i = 0; i < 3; i++) {
		    int x = Integer.parseInt(powerResponse.substring(
			    4 * (i + 3), 4 * (i + 3 + 1)), 16);
		    if (x > 32767)
			x = (-1) * (65536 - x);
		    py[i] = (float) x * ct / 1000;
		}
		for (int i = 0; i < 3; i++) {
		    pz[i] = (float) (Integer.parseInt(powerResponse.substring(
			    4 * (i + 6), 4 * (i + 6 + 1)), 16))
			    * ct / 1000;
		}
		if ((u[0] < GlobalVariable.u_a40_min && u[0] > 0)
			|| (u[1] < GlobalVariable.u_a40_min && u[1] > 0)
			|| (u[2] < GlobalVariable.u_a40_min && u[2] > 0)) { // A40电压超下限
		    // insertAlert(1, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (u[0] > GlobalVariable.u_a40_max
			|| u[1] > GlobalVariable.u_a40_max
			|| u[2] > GlobalVariable.u_a40_max) { // A40电压超上限
		    // insertAlert(1, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if ((a[0] < GlobalVariable.i_a40_min && a[0] > 0)
			|| (a[1] < GlobalVariable.i_a40_min && a[1] > 0)
			|| (a[2] < GlobalVariable.i_a40_min && a[2] > 0)) { // A40电流超下限
		    // insertAlert(3, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}
		if (a[0] > GlobalVariable.i_a40_max
			|| a[1] > GlobalVariable.i_a40_max
			|| a[2] > GlobalVariable.i_a40_max) {
		    // insertAlert(4, instrument.attribution.globalID,
		    // instrument.attribution.siteID);
		}

		for (int i = 0; i < sensorLength; i++) {
		    for (int sensorNum = 0; sensorNum < instrument.sensorNumber; sensorNum++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.sensors[sensorNum].globalID) {

			    switch (instrument.sensors[sensorNum].relativeID) // A相
			    {
			    case 1:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + u[0] + "@";
				break;
			    case 2:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + u[1] + "@";
				break;
			    case 3:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + u[2] + "@";
				break;
			    case 4:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + a[0] + "@";
				break;
			    case 5:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + a[1] + "@";
				break;
			    case 6:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + a[2] + "@";
				break;
			    case 7:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + px[0] + "@";
				break;
			    case 8:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + px[1] + "@";
				break;
			    case 9:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + px[2] + "@";
				break;
			    case 10:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + py[0] + "@";
				break;
			    case 11:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + py[1] + "@";
				break;
			    case 12:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + py[2] + "@";
				break;
			    case 13:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pz[0] + "@";
				break;
			    case 14:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pz[1] + "@";
				break;
			    case 15:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pz[2] + "@";
				break;
			    case 16:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pp[0] + "@";
				break;
			    case 17:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pp[1] + "@";
				break;
			    case 18:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + pp[2] + "@";
				break;
			    case 19:
				strData += instrument.sensors[sensorNum].globalID
					+ ":" + f + "@";
				break;
			    }
			}
		    }
		    sensorCount++;
		}

	    }

	    // 计算电流ct和电压pt
	    // 从地址700读来的数据是没有考虑电流和电压变比的，所以需要乘这两个参数
	    if (recdata.length() == 38 && CRC16.checkCrc16(recdata)) {
		flag = 1;
		strData = "m";

		getCTAndPT(instrument, recdata);
	    } else {
		if (flag == 0) {
		    if (instrument.sensors[0].internalIdentifier == 1) {
			strData = "b";
			// dbo.insertAlert(34, instrument.attribution.globalID,
			// instrument.attribution.siteID);
		    } else {
			strData = "m";
		    }
		    for (int i = 0; i < sensorLength; i++) {
			for (int sensorNum = 0; sensorNum < instrument.sensorNumber; sensorNum++) {
			    if (Integer.parseInt(sensorArray[sensorCount]) == instrument.sensors[sensorNum].globalID) {
				strData = strData + sensorArray[sensorCount]
					+ ":888888@";
			    }
			}
			sensorCount++;
		    }
		}
	    }
	    System.out.println("A40:" + strData);
	} catch (Exception e) {
	    if (instrument.sensors[0].internalIdentifier == 0) {
		strData = "KSP1" + instrument.sensors[0].internalIdentifier
			+ "获取数据失败";
	    } else {
		strData = "eKSP1" + instrument.sensors[0].internalIdentifier
			+ "获取数据失败";
	    }
	}
	return strData;
    }

    private void getCTAndPT(Instrument instrument, String recdata) {
	int ptPrimary = 0, ptSecondary = 0;
	int ptFlag = 0;
	float pt = 1;

	ptFlag = Integer.parseInt(recdata.substring(18, 22), 16);
	ptPrimary = Integer.parseInt(recdata.substring(22, 30), 16);
	ptSecondary = Integer.parseInt(recdata.substring(30, 34), 16);

	if (ptFlag == 1) {
	    pt = ptPrimary / ptSecondary;
	}

	GlobalVariable.pt[instrument.attribution.globalID - 1] = pt;

	int ctSecondary = 0, ctPrimary = 0;

	ctSecondary = Integer.parseInt(recdata.substring(10, 14), 16);
	ctPrimary = Integer.parseInt(recdata.substring(14, 18), 16);
	int ct = ctPrimary / ctSecondary;
	GlobalVariable.ct[instrument.attribution.globalID - 1] = ct;
    }
}
