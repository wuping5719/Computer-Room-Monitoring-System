package com.ouc.dcrms.collect.device.dass;

import com.ouc.dcrms.collect.device.VirtualDevice;
import com.ouc.dcrms.collect.initial.InitialInterface;
import com.ouc.dcrms.collect.util.Instrument;
import com.ouc.dcrms.collect.util.data.CRC16;
import com.ouc.dcrms.collect.util.data.CommandProcessing;
import com.ouc.dcrms.collect.util.data.GlobalVariable;

public class Dass_A40 implements VirtualDevice {

    @Override
    public String writeData(InitialInterface initialInterface,
	    Instrument instrument, int commandType) {
	String flag = "0";
	CommandProcessing cd = new CommandProcessing();
	String[] cmd = new String[2];
	String address = String.valueOf(instrument.getCommInterface().getAddress());
	if (address.length() == 1) {   //一位地址长度补齐为两位格式，如1补齐为01
	    address = "0" + address;
	}
	cmd[0] = address + "0302000007"; // 获取参数 获取CT，一个字是四个字节
	cmd[1] = address + "0307000064";
	for (int i = 0; i < 2; i++) {
	    cmd[i] = cmd[i] + CRC16.getCRC16(cmd[i]);
	}

	try {
	    if (instrument.getSensors().size() >= 0
		    && instrument.getSensors().get(0).getInternalIdentifier() < 2) // 内部标识只能是0-1
	    {
		initialInterface.commInstance.outputStream.write(cd
			.toByte(cmd[instrument.getSensors().get(0).getInternalIdentifier()]));
	    } else {
		flag = "KSP1的internalIdentifier超出的界限";
	    }
	} catch (Exception e) {
	    flag = "A40发送第" + instrument.getSensors().get(0).getInternalIdentifier()
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
		strData = new StringBuilder("g");
		float[] u = { 0, 0, 0 };
		float[] a = { 0, 0, 0 };
		float[] px = { 0, 0, 0 };
		float[] py = { 0, 0, 0 };
		float[] pz = { 0, 0, 0 };
		float[] pp = { 0, 0, 0 };
		float f;

		int ct;
		float pt;

		ct = GlobalVariable.ct[instrument.getAttribution().getGlobalID() - 1];
		pt = GlobalVariable.pt[instrument.getAttribution().getGlobalID() - 1];

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
		    for (int sensorNum = 0; sensorNum < instrument.getSensors().size(); sensorNum++) {
			if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(sensorNum).getGlobalID()) {
			    switch (instrument.getSensors().get(sensorNum).getRelativeID()) // A相
			    {
			    case 1:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(u[0]);
				strData.append("@");
				break;
			    case 2:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(u[1]);
				strData.append("@");
				break;
			    case 3:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(u[2]);
				strData.append("@");
				break;
			    case 4:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(a[0]);
				strData.append("@");
				break;
			    case 5:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(a[1]);
				strData.append("@");
				break;
			    case 6:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(a[2]);
				strData.append("@");
				break;
			    case 7:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(px[0]);
				strData.append("@");
				break;
			    case 8:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(px[1]);
				strData.append("@");
				break;
			    case 9:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(px[2]);
				strData.append("@");
				break;
			    case 10:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(py[0]);
				strData.append("@");
				break;
			    case 11:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(py[1]);
				strData.append("@");
				break;
			    case 12:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(py[2]);
				strData.append("@");
				break;
			    case 13:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pz[0]);
				strData.append("@");
				break;
			    case 14:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pz[1]);
				strData.append("@");
				break;
			    case 15:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pz[2]);
				strData.append("@");
				break;
			    case 16:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pp[0]);
				strData.append("@");
				break;
			    case 17:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pp[1]);
				strData.append("@");
				break;
			    case 18:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(pp[2]);
				strData.append("@");
				break;
			    case 19:
				strData.append(instrument.getSensors().get(sensorNum).getGlobalID());
				strData.append(":");
				strData.append(f);
				strData.append("@");
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
		strData = new StringBuilder("m");
		getCTAndPT(instrument, recdata);
	    } else {
		if (flag == 0) {
		    if (instrument.getSensors().get(0).getInternalIdentifier() == 1) {
			strData = new StringBuilder("b");
			// dbo.insertAlert(34, instrument.attribution.globalID,
			// instrument.attribution.siteID);
		    } else {
			strData = new StringBuilder("m");
		    }
		    for (int i = 0; i < sensorLength; i++) {
			for (int sensorNum = 0; sensorNum < instrument.getSensors().size(); sensorNum++) {
			    if (Integer.parseInt(sensorArray[sensorCount]) == instrument.getSensors().get(sensorNum).getGlobalID()) {
				strData.append(sensorArray[sensorCount] + ":888888@");
			    }
			}
			sensorCount++;
		    }
		}
	    }
	    System.out.println("A40:" + strData.toString());
	} catch (Exception e) {
	    if (instrument.getSensors().get(0).getInternalIdentifier() == 0) {
		strData.append("KSP1");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    } else {
		strData.append("eKSP1");
		strData.append(instrument.getSensors().get(0).getInternalIdentifier());
		strData.append("获取数据失败!");
	    }
	}
	return strData.toString();
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

	GlobalVariable.pt[instrument.getAttribution().getGlobalID() - 1] = pt;

	int ctSecondary = 0, ctPrimary = 0;

	ctSecondary = Integer.parseInt(recdata.substring(10, 14), 16);
	ctPrimary = Integer.parseInt(recdata.substring(14, 18), 16);
	int ct = ctPrimary / ctSecondary;
	GlobalVariable.ct[instrument.getAttribution().getGlobalID() - 1] = ct;
    }
}
