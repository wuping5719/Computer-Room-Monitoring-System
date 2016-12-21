package com.ouc.dcrms.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author WuPing
 * @version 2016年12月20日 下午9:01:55
 */

public class ParseConfigureXML {
    private DocumentBuilderFactory factory;
    private DocumentBuilder build;
    private Document doc;
    private Element rootElement;
    private NodeList childrenList;
    public int childrenLength;

    // 构造函数
    public ParseConfigureXML() throws ParserConfigurationException, SAXException, IOException{
	factory = DocumentBuilderFactory.newInstance();// 得到DOM解析器的工厂实例
	factory.setValidating(true);// 开启验证XML功能
	build = factory.newDocumentBuilder();// 得到DOM解析器
	doc = build.parse("collect-configure.xml");
	rootElement = doc.getDocumentElement();
	childrenList = rootElement.getChildNodes();
	childrenLength = childrenList.getLength();
    }

    public int getInstrumentNum() // 得到配置文件中数据采集设备的个数
    {
	int instrumentLenth = 0;  // 配置文件中数据采集设备的个数
	Node SiteNode = null;
	for (int k = 0; k < childrenLength; k++) {
	    SiteNode = childrenList.item(k);
	    if (SiteNode.getNodeType() == Node.ELEMENT_NODE)// 得到站点
	    {
		NodeList InstrumentList = SiteNode.getChildNodes();
		Node InstrumentNode = null;
		for (int n = 0; n < InstrumentList.getLength(); n++) {
		    InstrumentNode = InstrumentList.item(n);
		    if (InstrumentNode.getNodeType() == Node.ELEMENT_NODE) {// 得到设备
			instrumentLenth++;
		    }
		}
	    }
	}
	return instrumentLenth;
    }

    // 根据设备ID得到对应设备的信息
    public Instrument getInstrument(int instrumentID) {
	int sensorsLength = getSensorsNum(instrumentID); // 对应设备的传感器个数
	Instrument instrument = new Instrument(sensorsLength);
	Node SiteNode = null;
	label: for (int k = 0; k < childrenLength; k++) {
	    SiteNode = childrenList.item(k);
	    if (SiteNode.getNodeType() == Node.ELEMENT_NODE) {
		NodeList instrumentList = SiteNode.getChildNodes();
		Node instrumentNode = null;
		for (int m = 0; m < instrumentList.getLength(); m++) {
		    instrumentNode = instrumentList.item(m);
		    if (instrumentNode.getNodeType() == Node.ELEMENT_NODE) {
			NamedNodeMap map = instrumentNode.getAttributes();
			Node nodeAttri = null;
			for (int n2 = 0; n2 < map.getLength(); n2++) {
			    nodeAttri = map.item(n2);
			    if (nodeAttri.getNodeName().equals("GlobalID")) {
				if (nodeAttri.getNodeValue().equals(
					Integer.toString(instrumentID))) {
				    instrument = getInstrument(k, m);
				    break label;
				}
			    }
			}
		    }
		}
	    }
	}
	return instrument;
    }

    // 指定设备号, 得到该设备的传感器个数
    private int getSensorsNum(int instrumentID) {
	int sensorsLength = 0;
	Node SiteNode = null;
	label: for (int k = 0; k < childrenLength; k++) {
	    SiteNode = childrenList.item(k);
	    if (SiteNode.getNodeType() == Node.ELEMENT_NODE) {
		NodeList instrumentList = SiteNode.getChildNodes();// 设备节点集合
		Node instrumentNode = null;
		for (int m = 0; m < instrumentList.getLength(); m++) {
		    instrumentNode = instrumentList.item(m);
		    if (instrumentNode.getNodeType() == Node.ELEMENT_NODE) {
			NamedNodeMap map = instrumentNode.getAttributes();
			Node nodeAttri = null;
			for (int k1 = 0; k1 < map.getLength(); k1++) {
			    nodeAttri = map.item(k1);// 获得设备的GlobalID属性
			    if (nodeAttri.getNodeName().equals("GlobalID")) {
				if (nodeAttri.getNodeValue().equals(
					Integer.toString(instrumentID))) {
				    for (int m1 = 1; m1 < map.getLength(); m1++) {
					Node nodeSensor = map.item(m1);
					if (nodeSensor.getNodeName().equals(
						"ParameterNum")) {
					    sensorsLength = Integer
						    .parseInt(nodeSensor
							    .getNodeValue()); // 找到设备，返回传感器个数
					    break label;
					}
				    }

				}
			    }
			}
		    }
		}
	    }
	}
	return sensorsLength;
    }

    // 得到第index个结点的传感器个数。
    private int getSensorNum(int i, int j) {
	int count = 0;
	int length = 0; // 假设传感器的个数为0

	Node current = childrenList.item(i).getChildNodes().item(j);
	if (current.getNodeType() == Node.ELEMENT_NODE) {
	    NodeList information = current.getChildNodes();
	    int informationlenth = information.getLength();
	    Node current1 = null; // 定义设备名下当前子结点的指向
	    for (int k = 0; k < informationlenth; k++) {
		current1 = information.item(k); // node指向属性子结点，也就是sensor这一层的结点
		if (current1.getNodeType() == Node.ELEMENT_NODE) {
		    Element element = (Element) current1;
		    if (element.getTagName().equalsIgnoreCase("Parameter")) {
			NodeList sensorlist = current1.getChildNodes();
			length = sensorlist.getLength(); // 得到传感器下子结点的个数
			Node current3 = null;
			for (int n = 0; n < length; n++) {
			    current3 = sensorlist.item(n);
			    if (current3.getNodeType() == Node.ELEMENT_NODE
				    && current3.hasChildNodes()) {
				count++;
			    }
			}
		    }
		}
	    }
	}
	return count;
    }

    // 得到XML第i站点的第j设备
    public Instrument getInstrument(int i, int j) {
	int num = 1; // 假设传感器的个数为1
	num = getSensorNum(i, j); // 得到第j个设备结点中传感器的个数
	Node current = childrenList.item(i).getChildNodes().item(j);
	Instrument instrument = new Instrument(num);
	instrument.sensorNumber = num; // 把传感器的个数传给instrument
	if (current.getNodeType() == Node.ELEMENT_NODE) {
	    NamedNodeMap map = current.getAttributes();
	    Node attri = null;
	    for (int k1 = 0; k1 < map.getLength(); k1++) {
		attri = map.item(k1);
		if (attri.getNodeType() == Node.ATTRIBUTE_NODE) {
		    if (attri.getNodeName().equals("GlobalID")) {
			instrument.attribution.globalID = Integer
				.parseInt(attri.getFirstChild().getNodeValue());
		    }
		    if (attri.getNodeName().equals("Name")) {
			instrument.attribution.name = attri.getFirstChild()
				.getNodeValue();
		    }
		    if (attri.getNodeName().equals("Manufacturer")) {
			instrument.attribution.manufacturer = attri
				.getFirstChild().getNodeValue();
		    }
		    if (attri.getNodeName().equalsIgnoreCase("Model")) {
			instrument.attribution.model = attri.getFirstChild()
				.getNodeValue();
		    }
		    if (attri.getNodeName().equalsIgnoreCase("ParameterNum")) {
			instrument.attribution.parameterNum = Integer
				.parseInt(attri.getFirstChild().getNodeValue());
		    }
		    if (attri.getNodeName().equals("RelID")) {
			instrument.attribution.relativeID = Integer
				.parseInt(attri.getFirstChild().getNodeValue());
		    }

		    instrument.attribution.siteID = searchSiteID(i); // SiteID
		}
	    }

	    NodeList information = current.getChildNodes();
	    int informationlenth = information.getLength();
	    Node current1 = null;// 定义设备名下当前子结点的指向

	    for (int k1 = 0; k1 < informationlenth; k1++) {
		current1 = information.item(k1);
		if (current1.getNodeType() == Node.ELEMENT_NODE) {
		    Element element = (Element) current1;
		    if (element.getTagName().equalsIgnoreCase("Interface")) {
			NamedNodeMap map1 = current1.getAttributes();
			Node nodeAttri = null;
			for (int k2 = 0; k2 < map1.getLength(); k2++) {
			    nodeAttri = map1.item(k2);
			    if (nodeAttri.getNodeName().equals("Standard")) {
				if (nodeAttri.getNodeValue().equals("RS232")) {
				    instrument.commInterface.interfaceName = "RS232";
				    NodeList rs232List = current1
					    .getChildNodes();
				    int rs232length = rs232List.getLength();
				    Node current3 = null;
				    for (int k3 = 0; k3 < rs232length; k3++) {
					current3 = rs232List.item(k3);
					if (current3.getNodeType() == Node.ELEMENT_NODE) {
					    Element rs232information = (Element) current3;

					    if (rs232information
						    .getNodeName()
						    .equalsIgnoreCase("Comport")) {
						instrument.commInterface.comport = Integer
							.parseInt(rs232information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs232information.getNodeName()
						    .equalsIgnoreCase(
							    "Baudrate")) {
						instrument.commInterface.baudRate = Integer
							.parseInt(rs232information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs232information
						    .getNodeName()
						    .equalsIgnoreCase("Stopbit")) {
						instrument.commInterface.stopBit = Integer
							.parseInt(rs232information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs232information
						    .getNodeName()
						    .equalsIgnoreCase("Databit")) {
						instrument.commInterface.dataBit = Integer
							.parseInt(rs232information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs232information.getNodeName()
						    .equalsIgnoreCase("Parity")) {
						instrument.commInterface.parity = rs232information
							.getFirstChild()
							.getNodeValue();
					    }
					}

				    }

				}

				if (nodeAttri.getNodeValue().equals("RS485")) {
				    instrument.commInterface.interfaceName = "RS485";
				    NodeList rs485List = current1
					    .getChildNodes();
				    int rs485length = rs485List.getLength();
				    Node current3 = null;
				    for (int k3 = 0; k3 < rs485length; k3++) {
					current3 = rs485List.item(k3);
					if (current3.getNodeType() == Node.ELEMENT_NODE) {
					    Element rs485information = (Element) current3;
					    if (rs485information
						    .getNodeName()
						    .equalsIgnoreCase("Comport")) {
						instrument.commInterface.comport = Integer
							.parseInt(rs485information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs485information.getNodeName()
						    .equalsIgnoreCase(
							    "Baudrate")) {
						instrument.commInterface.baudRate = Integer
							.parseInt(rs485information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs485information
						    .getNodeName()
						    .equalsIgnoreCase("Stopbit")) {
						instrument.commInterface.stopBit = Integer
							.parseInt(rs485information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs485information
						    .getNodeName()
						    .equalsIgnoreCase("Databit")) {
						instrument.commInterface.dataBit = Integer
							.parseInt(rs485information
								.getFirstChild()
								.getNodeValue());
					    }
					    if (rs485information.getNodeName()
						    .equalsIgnoreCase("Parity")) {
						instrument.commInterface.parity = rs485information
							.getFirstChild()
							.getNodeValue();
					    }
					    if (rs485information
						    .getNodeName()
						    .equalsIgnoreCase("Address")) {
						instrument.commInterface.address = Integer
							.parseInt(rs485information
								.getFirstChild()
								.getNodeValue());
					    }
					}

				    }
				}

				if (nodeAttri.getNodeValue().equals("Ethernet")) {
				    instrument.commInterface.interfaceName = "Ethernet";
				    NodeList ethernetList = current1
					    .getChildNodes();
				    int ethernetlength = ethernetList
					    .getLength();
				    Node current3 = null;
				    for (int k3 = 0; k3 < ethernetlength; k3++) {
					current3 = ethernetList.item(k3);
					if (current3.getNodeType() == Node.ELEMENT_NODE) {
					    Element Ethernetinformation = (Element) current3;
					    if (Ethernetinformation
						    .getNodeName()
						    .equalsIgnoreCase(
							    "IPAddress")) {
						instrument.commInterface.IPAddress = Ethernetinformation
							.getFirstChild()
							.getNodeValue();
					    }
					    if (Ethernetinformation
						    .getNodeName()
						    .equalsIgnoreCase("Port")) {
						instrument.commInterface.port = Integer
							.parseInt(Ethernetinformation
								.getFirstChild()
								.getNodeValue());
					    }
					}

				    }
				}

				if (nodeAttri.getNodeValue().equals("USB")) {
				    instrument.commInterface.interfaceName = "USB";
				    NodeList GPIBList = current1
					    .getChildNodes();
				    int GPIBlength = GPIBList.getLength();
				    Node current3 = null;
				    for (int k3 = 0; k3 < GPIBlength; k3++) {
					current3 = GPIBList.item(k3);
					if (current3.getNodeType() == Node.ELEMENT_NODE) {
					    Element USBinformation = (Element) current3;
					    if (USBinformation
						    .getNodeName()
						    .equalsIgnoreCase("address")) {
						instrument.commInterface.address = Integer
							.parseInt(USBinformation
								.getFirstChild()
								.getNodeValue());
					    }
					}

				    }
				}

				if (nodeAttri.getNodeValue().equals("GPIB")) {
				    instrument.commInterface.interfaceName = "GPIB";
				    NodeList GPIBList = current1
					    .getChildNodes();
				    int GPIBlenth = GPIBList.getLength();
				    Node current3 = null;
				    for (int n = 0; n < GPIBlenth; n++) {
					current3 = GPIBList.item(n);
					if (current3.getNodeType() == Node.ELEMENT_NODE) {
					    Element GPIBinformation = (Element) current3;

					    if (GPIBinformation
						    .getNodeName()
						    .equalsIgnoreCase("address")) {
						instrument.commInterface.address = Integer
							.parseInt(GPIBinformation
								.getFirstChild()
								.getNodeValue());
					    }

					}

				    }
				}

			    }
			}
		    }

		    if (element.getTagName().equalsIgnoreCase("Parameter")) {
			int sensorcount = 0; // 指定为哪个传感器
			NodeList sensorlist = current1.getChildNodes();
			num = sensorlist.getLength();
			Node current2 = null; // 传感器结点下的各个子传感器结点

			for (int k = 0; k < num; k++) {
			    current2 = sensorlist.item(k);
			    if (current2.getNodeType() == Node.ELEMENT_NODE) {
				NodeList list = current2.getChildNodes();
				int listlenth = list.getLength();
				Node current3 = null;
				for (int n = 0; n < listlenth; n++) {
				    current3 = list.item(n);
				    if (current3.getNodeType() == Node.ELEMENT_NODE) {
					Element sensor = (Element) current3;
					if (sensor.getNodeName().equals("Name")) {
					    instrument.sensors[sensorcount].name = sensor
						    .getFirstChild()
						    .getNodeValue();

					}
					if (sensor.getNodeName().equals(
						"GlobalID")) {
					    instrument.sensors[sensorcount].globalID = Integer
						    .parseInt(sensor
							    .getFirstChild()
							    .getNodeValue());

					}
					if (sensor.getNodeName()
						.equalsIgnoreCase("RelativeID")) {
					    instrument.sensors[sensorcount].relativeID = Integer
						    .parseInt(sensor
							    .getFirstChild()
							    .getNodeValue());
					}
					if (sensor.getNodeName()
						.equalsIgnoreCase("Unit")) {
					    instrument.sensors[sensorcount].unit = sensor
						    .getFirstChild()
						    .getNodeValue();

					}
					if (sensor.getNodeName()
						.equalsIgnoreCase(
							"InternalIdentifie")) {
					    instrument.sensors[sensorcount].internalIdentifier = Integer
						    .parseInt(sensor
							    .getFirstChild()
							    .getNodeValue());

					}
				    }
				}
				sensorcount++;
			    }

			}
		    }
		}
	    }
	}
	return instrument;
    }

    // 判断第i站点的第j设备节点是否为有值的节点
    public boolean IsInstrNode(int i, int j) {
	Node currenti = null;
	NodeList currentlist = null;
	currenti = childrenList.item(i);
	currentlist = currenti.getChildNodes();
	Node currentj = null;
	currentj = currentlist.item(j);
	if (currentj.getNodeType() == Node.ELEMENT_NODE) {
	    return true;
	}
	return false;
    }

    // 判断i站点节点是否为有值的节点
    public boolean isSiteNode(int i) {
	Node current1 = null;
	current1 = childrenList.item(i);
	if (current1.getNodeType() == Node.ELEMENT_NODE) {
	    return true;
	}
	return false;
    }

    // 得到序号为k的站点节点的设备节点数目
    public int getInsNumOfSiteK(int k) {
	int instrtLenth = 0;
	Node SiteNode = null;
	SiteNode = childrenList.item(k);
	NodeList InstrumentList = SiteNode.getChildNodes();
	instrtLenth = InstrumentList.getLength();
	return instrtLenth;
    }

    // 得到型号相同的设备的数目
    public int getModelNum(String strModel) {
	int num = 0;
	Node SiteNode = null;
	for (int k = 0; k < childrenLength; k++) {
	    SiteNode = childrenList.item(k);
	    if (SiteNode.getNodeType() == Node.ELEMENT_NODE) {
		NodeList instrumentList = SiteNode.getChildNodes(); // 设备节点集合
		Node instrumentNode = null;
		for (int m = 0; m < instrumentList.getLength(); m++) {
		    instrumentNode = instrumentList.item(m);
		    if (instrumentNode.getNodeType() == Node.ELEMENT_NODE) {
			NamedNodeMap map = instrumentNode.getAttributes();
			Node nodeAttri = null;
			for (int n2 = 0; n2 < map.getLength(); n2++) {
			    nodeAttri = map.item(n2);
			    if (nodeAttri.getNodeName().equals("Model")) {
				if (nodeAttri.getNodeValue().equals(strModel)) {
				    num++;
				}
			    }
			}
		    }
		}
	    }
	}
	return num;
    }

    // 得到第i个站点的SiteID
    private int searchSiteID(int index) {
	int SiteID = 1; // 站点ID
	Node SiteNode = childrenList.item(index);
	NamedNodeMap map = SiteNode.getAttributes();
	Node nodeAttri = null;
	for (int n2 = 0; n2 < map.getLength(); n2++) {
	    nodeAttri = map.item(n2);
	    if (nodeAttri.getNodeName().equals("SiteID")) {
		SiteID = Integer.parseInt(nodeAttri.getNodeValue());
	    }
	}
	return SiteID;
    }
}
