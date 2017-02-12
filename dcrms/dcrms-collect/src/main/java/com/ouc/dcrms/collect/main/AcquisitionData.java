package com.ouc.dcrms.collect.main;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TimerTask;

import com.ouc.dcrms.collect.initial.InitialInterface;
import com.ouc.dcrms.collect.util.Instrument;
import com.ouc.dcrms.collect.util.InstrumentArray;
import com.ouc.dcrms.collect.util.ParseConfigureXML;
import com.ouc.dcrms.collect.util.data.CommandProcessing;

/**
 * @author WuPing
 * @version 2016年12月24日 下午7:59:22
 */

public class AcquisitionData extends TimerTask {

    public static boolean isInitial = false; // 接口是否初始化变量

    //private static boolean IsOpen = false;

    private int instrumentNum = 0; // XML中设备的总数

    public ParseConfigureXML paseXml; // 解析配置文件

    private static InitialInterface[] initialInterfaceArray; // 设备初始化接口类数组

    // 用于保存设备的信息，每个元素含有3个值：设备编号、设备需要采集的传感器编号序列、设备信息
    private InstrumentArray[] instrumentArray;
    
    private String[] sendArray;
    
    private Map<Integer, String> map;   // 保存的站点数据
    
    private StringBuffer instrumentData;  // 传感器数据

    private StringBuffer instrumentState;

    public AcquisitionData(ParseConfigureXML paseXml) {
	this.paseXml = paseXml;
	instrumentNum = paseXml.instrumentNum;
	initialInterfaceArray = new InitialInterface[instrumentNum]; // 初始化类数组的长度定义为配置文件中设备个数，已被所有的设备都能包括在里面
	instrumentArray = new InstrumentArray[instrumentNum];
	
	System.out.println("----开始初始化-----");
	//initialInterface();
	System.out.println("----初始化完毕-----");
	//initialInstrumentArray();
	
	paseXml = null;

	instrumentData = new StringBuffer("");  // 传感器数据
	instrumentState = new StringBuffer("");
    }

    public void run() {
	try {
	    getInstrumentsData();
	} catch (Exception e) {
	    System.out.println("该类没有找到,没有对应的设备,请核对硬件配置文件!");
	}
    }

    // 采集设备中数据的函数, 第二个参数代表命令类型，0表示获取数据命令，1表示控制设备命令
    private void getInstrumentsData() {
	try {
	    for (int i = 0; i < sendArray.length; i++) {
		String[] insID;    // 保存设备ID
		insID = sendArray[i].split(",");
		InstrumentArray insArray = new InstrumentArray();
		for (int j = 0; j < insID.length; j++) {
		   // 以下是对逐个设备进行发送命令
		    for (int n = 0; n < instrumentNum; n++)  {
			if (instrumentArray[n].getInstrumentID() == Integer.parseInt(insID[j])) {
			    insArray = instrumentArray[n];
			    break;
			}
		    }
		    label1: for (int initLength = 0; initLength < instrumentNum; initLength++) {
			for (int insLength = 0; insLength < instrumentNum; insLength++) {
			    if (initialInterfaceArray[initLength] != null) {
				if (initialInterfaceArray[initLength].insIDArray[insLength] 
					== Integer.parseInt(insID[j])) {
				    
				    int commandType = 0;
				    @SuppressWarnings("rawtypes")
				    Class virInstrument = Class.forName("com.ouc.dcrms.device.dass.Dass_"
						    + insArray.getInstrument().getAttribution().getModel());
				  
				    Object[] parameters = new Object[3];  // 确定需要传递的参数
				    parameters[0] = initialInterfaceArray[initLength];
				    parameters[1] = insArray.getInstrument();   // 设备描述信息
				    parameters[2] = commandType;   // 命令类型
				    
				    @SuppressWarnings("rawtypes")
				    Class[] parametersType = new Class[3];
				    parametersType[0] = InitialInterface.class;
				    parametersType[1] = Instrument.class;
				    parametersType[2] = int.class;
				    
				    @SuppressWarnings("unchecked")
				    Method method = virInstrument.getMethod("writeData", parametersType);
				    method.invoke(virInstrument.newInstance(), parameters).toString();
				    break label1;
				}
			    }
			}
		    }

		}

		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		for (int j = 0; j < insID.length; j++) {
		    // 以下是对逐个设备进行发送命令
		    for (int n = 0; n < instrumentNum; n++) {
			if (instrumentArray[n].getInstrumentID() 
				== Integer.parseInt(insID[j])) {
			    insArray = instrumentArray[n];
			    break;
			}
		    }
		    
		    label2: for (int initLength = 0; initLength < instrumentNum; initLength++) {
			for (int insLength = 0; insLength < instrumentNum; insLength++) {
			    if (initialInterfaceArray[initLength] != null) {
				if (initialInterfaceArray[initLength].insIDArray[insLength]
                                        == Integer.parseInt(insID[j])) {
				
				    @SuppressWarnings("rawtypes")
				    Class virInstrument = Class.forName("com.ouc.dcrms.device.dass.Dass_"
						    + insArray.getInstrument().getAttribution().getModel());
				    
				    Object[] parameters = new Object[4];// 确定需要传递的参数
				    parameters[0] = insArray.getSensorsList();    // 传感器序列
				    parameters[1] = insArray.getInstrument().getSensors().size();  // 传感器个数
				    parameters[2] = insArray.getInstrument();  // 设备信息
				    parameters[3] = initialInterfaceArray[initLength];

				    @SuppressWarnings("rawtypes")
				    Class[] parametersType = new Class[4];
				    parametersType[0] = String.class;
				    parametersType[1] = int.class;
				    parametersType[2] = Instrument.class;
				    parametersType[3] = InitialInterface.class;

				    @SuppressWarnings("unchecked")
				    Method method = virInstrument.getMethod("getData", parametersType);
				    
				    String isGood = "";
				    char inStr;
				    isGood = method.invoke(virInstrument.newInstance(), parameters).toString();
				    
				    inStr = isGood.charAt(0);
				    if (inStr == 'g' || inStr == 'b' || inStr == 'm') {
					instrumentData.append(map.get(insArray.getInstrument().getAttribution().getSiteID()));
					instrumentData.append(isGood.substring(1, isGood.length()));
					
					map.put(insArray.getInstrument().getAttribution().getSiteID(), instrumentData.toString());
					instrumentData.delete(0, instrumentData.length());
				    } 
				    
				    if (inStr == 'g') {
					instrumentState.append(map.get(insArray.getInstrument().getAttribution().getSiteID()));
					instrumentState.append(map.get(insArray.getInstrument().getAttribution().getGlobalID()));
					instrumentState.append(":");
					instrumentState.append("0");
					instrumentState.append("@");
					
					instrumentState.delete(0, instrumentState.length());
				    } else {
					if (inStr != 'm' && inStr != 'e') {
					    instrumentState.append(map.get(insArray.getInstrument().getAttribution().getSiteID()));
					    instrumentState.append(map.get(insArray.getInstrument().getAttribution().getGlobalID()));
					    instrumentState.append(":");
					    instrumentState.append("1");
					    instrumentState.append("@");
					    
					    instrumentState.delete(0,instrumentState.length());
					}
				    }
				    break label2;
				}
			    }
			}
		    }

		}
		insArray = null;
		insID = null;
	    }
	    
	    //String strTime = MessageFormat.format("{0, date, yyyy-MM-dd HH:mm:ss}",
		//	    new Object[] { new Date(System.currentTimeMillis()) });
	    System.gc();
	} catch (Exception e) {
	}
    }

    // 初始化接口函数:
    // list: 当comandType=0时list表示传感器ID序列，
    // 当commandType=4时为设备ID序列.
    public void initialInterfaceCore(ParseConfigureXML parseXML, String list,
	    int commandType) throws UnknownHostException, IOException  {
	int xmlInstrumentNum = parseXML.instrumentNum; // xml中设备的个数
	CommandProcessing cp = new CommandProcessing();
	// 得到序列中实际传感器个数或者数据的个数
	int listLength = cp.getStrSpecialCharNum(list) + 1; 
	String[] listArray = new String[listLength];
	listArray = list.split(",");
	if (commandType == 0) {
	    int initialCount = 0;
	    for (int n = 0; n < listLength; n++) {
		int instrumentID = 1; // 根据传感器ID找到设备
		Instrument ins = parseXML.getInstrument(instrumentID);

		// 判断该设备是否初始化
		boolean isInitial = false; // 判断是否初始化变量
		label1: for (int initLength = 0; initLength < xmlInstrumentNum; initLength++) {
		    for (int insLength = 0; insLength < xmlInstrumentNum; insLength++) {
			if (initialInterfaceArray[initLength] != null) {
			    // 该设备已经初始化
			    if (initialInterfaceArray[initLength].insIDArray[insLength] == instrumentID) {
				isInitial = true;
				break label1;
			    }
			}
		    }
		}
		
		if (!isInitial) {
		    label2: for (int initLength = 0; initLength < xmlInstrumentNum; initLength++) {
			for (int insLength = 0; insLength < xmlInstrumentNum; insLength++) {
			    if (initialInterfaceArray[initLength] != null) {
				if (initialInterfaceArray[initLength].interfaceName.equals("RS485")
					|| initialInterfaceArray[initLength].interfaceName.equals("RS232")) {
				    // 判断串口是否被占用
				    if (ins.getCommInterface().getComport() == initialInterfaceArray[initLength].commInstance.comPort) {
					isInitial = true;
					break label2;
				    }
				}
				
				if (initialInterfaceArray[initLength].interfaceName.equals("Ethernet")) {
				    // 判断端口是否被占用
				    if (ins.getCommInterface().getPort() == initialInterfaceArray[initLength].internetInstance.port
					    && initialInterfaceArray[initLength].internetInstance.IPAddress.equals(ins.getCommInterface().getIPAddress())) {
					isInitial = true;
					break label2;
				    }
				}
			    }
			}
		    }

		    if (!isInitial) {
			initialInterfaceArray[initialCount] = new InitialInterface(ins, xmlInstrumentNum);
			initialCount++;
		    }
		}
	    }
	} else {
	    int initialCount = 0;
	    isInitial = false;
	    for (int n = 0; n < listLength; n++) {
		int instrumentID = Integer.parseInt(listArray[n]);
		Instrument ins = parseXML.getInstrument(instrumentID);
		boolean isInitial = false;// 判断是否初始化变量
		label3: for (int initLength = 0; initLength < xmlInstrumentNum; initLength++) {
		    for (int insLength = 0; insLength < xmlInstrumentNum; insLength++) {
			if (initialInterfaceArray[initLength] != null) {
			    // 该设备已经初始化
			    if (initialInterfaceArray[initLength].insIDArray[insLength] == instrumentID) {
				isInitial = true;
				break label3;
			    }
			}
		    }
		}
		
		if (!isInitial) {
		    label4: for (int initLength = 0; initLength < xmlInstrumentNum; initLength++) {
			for (int instrumentLenth = 0; instrumentLenth < xmlInstrumentNum; instrumentLenth++) {
			    if (initialInterfaceArray[initLength] != null) {
				if (initialInterfaceArray[initLength].interfaceName.equals("RS485")
					|| initialInterfaceArray[initLength].interfaceName.equals("RS232")) {
				    // 判断串口是否被占用
				    if (ins.getCommInterface().getComport() == initialInterfaceArray[initLength].commInstance.comPort) {
					isInitial = true;
					break label4;
				    }
				}
				
				if (initialInterfaceArray[initLength].interfaceName.equals("Ethernet")) {
				    // 判断端口是否被占用
				    if (ins.getCommInterface().getPort() == initialInterfaceArray[initLength].internetInstance.port
					    && initialInterfaceArray[initLength].internetInstance.IPAddress.equals(ins.getCommInterface().getIPAddress())) {
					isInitial = true;
					break label4;
				    }
				}
			    }
			}
		    }

		    if (!isInitial) {
			initialInterfaceArray[initialCount] = new InitialInterface(ins, xmlInstrumentNum);
			initialCount++;
		    }
		}
	    }
	}
    }
}
