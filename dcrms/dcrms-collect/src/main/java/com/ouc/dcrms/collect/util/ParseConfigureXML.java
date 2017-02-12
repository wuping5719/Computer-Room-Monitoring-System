package com.ouc.dcrms.collect.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    private Element rootElement;      //根节点
    private NodeList instrumentList;  //设备节点
    public int instrumentNum;         //设备节点个数
    public int siteID;                //该配置文件所属站点
    public String siteName;           //该配置文件所属站点名称
    
    // 构造函数
    public ParseConfigureXML(int siteID, String filePath) throws ParserConfigurationException, SAXException, IOException{
	factory = DocumentBuilderFactory.newInstance();// 得到DOM解析器的工厂实例
	factory.setValidating(true);// 开启验证XML功能
	build = factory.newDocumentBuilder();// 得到DOM解析器
	InputStream in = this.getClass().getClassLoader().getResourceAsStream(filePath);  
	doc = build.parse(in);
	rootElement = doc.getDocumentElement();
	instrumentList = rootElement.getChildNodes();    
	this.instrumentNum = instrumentList.getLength();  
	this.siteID = siteID;
	
	NamedNodeMap map = rootElement.getAttributes();
	Node nodeAttr = null;
	for (int i = 0; i < map.getLength(); i++) {
	    nodeAttr = map.item(i);
	    if (nodeAttr.getNodeName().equals("SiteName")) {
		this.siteName = nodeAttr.getNodeValue();
	    }
	}
    }
    
    // 根据设备ID得到对应设备的信息
    public Instrument getInstrument(int instrumentID) {
	Instrument instrument = new Instrument();

	Node instrumentNode = null;
	label: for (int i = 0; i < instrumentNum; i++) {
	    instrumentNode = instrumentList.item(i);
	    if (instrumentNode.getNodeType() == Node.ELEMENT_NODE) {
		NodeList insChildrenList = instrumentNode.getChildNodes(); // 设备子节点集合
		Node insChildrenNode = null;
		for (int j = 0; j < insChildrenList.getLength(); j++) {
		    insChildrenNode = insChildrenList.item(j);
		    if (insChildrenNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) insChildrenNode;
			if (element.getTagName().equalsIgnoreCase("Attribution")) {
			    NodeList attrList = insChildrenNode.getChildNodes(); // 设备属性子节点集合
			    Node attrNode = null;
			    for (int n = 0; n < attrList.getLength(); n++) {
				attrNode = attrList.item(n);
				if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
				    Element attr = (Element) attrNode;
				    if (attr.getNodeName().equalsIgnoreCase("GlobalID")) {
					int globalID = Integer.parseInt(attr.getFirstChild().getNodeValue());
					if(globalID == instrumentID) {
					    //给设备属性赋值
					    instrument.setAttribution(getInsAttribution(attrList));
					    instrument.getAttribution().setSiteID(siteID);
					    
					    //给设备接口赋值
					    instrument.setCommInterface(getCommInterface(insChildrenList));
					    
					    //给设备传感器数组赋值
					    instrument.setSensors(getSensorList(insChildrenList));
					    
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
	return instrument;
    }

    //根据属性节点集合attrList, 获取设备属性信息
    private Attribution getInsAttribution(NodeList attrList) {
	Attribution attribution = new Attribution();
	Node attrNode = null;
	for (int n = 0; n < attrList.getLength(); n++) {
	    attrNode = attrList.item(n);
	    if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
		Element attr = (Element) attrNode;
		if (attr.getNodeName().equalsIgnoreCase("GlobalID")) {
		    attribution.setGlobalID(Integer.parseInt(attr.getFirstChild().getNodeValue()));
		}
		if (attr.getNodeName().equalsIgnoreCase("Name")) {
		    attribution.setName(attr.getFirstChild().getNodeValue());
		}
		if (attr.getNodeName().equalsIgnoreCase("EnglishName")) {
		    attribution.setEnglishName(attr.getFirstChild().getNodeValue());
		}
		if (attr.getNodeName().equalsIgnoreCase("Manufacturer")) {
		    attribution.setManufacturer(attr.getFirstChild().getNodeValue());
		}
		if (attr.getNodeName().equalsIgnoreCase("Model")) {
		    attribution.setModel(attr.getFirstChild().getNodeValue());
		}
		if (attr.getNodeName().equalsIgnoreCase("ParameterNum")) {
		    attribution.setParameterNum(Integer.parseInt(attr.getFirstChild().getNodeValue()));
		}
		if (attr.getNodeName().equalsIgnoreCase("Serialnumber")) {
		    attribution.setSerialnumber(attr.getFirstChild().getNodeValue());
		}
		if (attr.getNodeName().equalsIgnoreCase("RelativeID")) {
		    attribution.setRelativeID(Integer.parseInt(attr.getFirstChild().getNodeValue()));
		}
		if (attr.getNodeName().equalsIgnoreCase("InstrumentType")) {
		    attribution.setInstrumentType(Integer.parseInt(attr.getFirstChild().getNodeValue()));
		}
	    }
	}
	return attribution;
    }
    
    // 根据设备子节点集合insChildrenList, 获取设备接口信息
    private CommInterface getCommInterface(NodeList insChildrenList) {
	CommInterface commInterface = new CommInterface();
	Node insChildrenNode = null;
	label: for (int i = 0; i < insChildrenList.getLength(); i++) {
	    insChildrenNode = insChildrenList.item(i);
	    if (insChildrenNode.getNodeType() == Node.ELEMENT_NODE) {
		Element element = (Element) insChildrenNode;
		if (element.getTagName().equalsIgnoreCase("Interface")) {
		    NodeList interfaceList = insChildrenNode.getChildNodes(); // 设备端口节点集合
		    Node interfaceNode = null;
		    for (int j = 0; j < interfaceList.getLength(); j++) {
			interfaceNode = interfaceList.item(j);
			if (interfaceNode.getNodeType() == Node.ELEMENT_NODE) {
			    Element inter = (Element) interfaceNode;
			    
			    if (inter.getNodeName().equalsIgnoreCase("RS232")) {
				commInterface.setInterfaceName("RS232");
				// 设备端口节点子属性节点集合
				NodeList interChildList = interfaceNode.getChildNodes(); 
				Node interChildNode = null;
				for (int n = 0; n < interChildList.getLength(); n++) {
				    interChildNode = interChildList.item(n);
				    if (interChildNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interChild = (Element) interChildNode;
					if (interChild.getNodeName().equalsIgnoreCase("Comport")) {
					    commInterface.setComport(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Baudrate")) {
					    commInterface.setBaudRate(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Databit")) {
					    commInterface.setDataBit(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Stopbit")) {
					    commInterface.setStopBit(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Parity")) {
					    commInterface.setParity(interChild.getFirstChild().getNodeValue());
					}
				    }
				}
			    }
			    
			    if (inter.getNodeName().equalsIgnoreCase("RS485")) {
				commInterface.setInterfaceName("RS485");
				// 设备端口节点子属性节点集合
				NodeList interChildList = interfaceNode.getChildNodes(); 
				Node interChildNode = null;
				for (int n = 0; n < interChildList.getLength(); n++) {
				    interChildNode = interChildList.item(n);
				    if (interChildNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interChild = (Element) interChildNode;
					if (interChild.getNodeName().equalsIgnoreCase("Comport")) {
					    commInterface.setComport(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Baudrate")) {
					    commInterface.setBaudRate(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Databit")) {
					    commInterface.setDataBit(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Stopbit")) {
					    commInterface.setStopBit(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
					if (interChild.getNodeName().equalsIgnoreCase("Parity")) {
					    commInterface.setParity(interChild.getFirstChild().getNodeValue());
					}
					if (interChild.getNodeName().equalsIgnoreCase("Address")) {
					    commInterface.setAddress(Long.parseLong(interChild.getFirstChild().getNodeValue()));
					}
				    }
				}
			    }
			    
			    if (inter.getNodeName().equalsIgnoreCase("Ethernet")) {
				commInterface.setInterfaceName("Ethernet");
				// 设备端口节点子属性节点集合
				NodeList interChildList = interfaceNode.getChildNodes(); 
				Node interChildNode = null;
				for (int n = 0; n < interChildList.getLength(); n++) {
				    interChildNode = interChildList.item(n);
				    if (interChildNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interChild = (Element) interChildNode;
					if (interChild.getNodeName().equalsIgnoreCase("IPAddress")) {
					    commInterface.setIPAddress(interChild.getFirstChild().getNodeValue());
					}
					if (interChild.getNodeName().equalsIgnoreCase("Port")) {
					    commInterface.setPort(Integer.parseInt(interChild.getFirstChild().getNodeValue()));
					}
				    }
				}
			    }
			    
			    if (inter.getNodeName().equalsIgnoreCase("USB")) {
				commInterface.setInterfaceName("USB");
				// 设备端口节点子属性节点集合
				NodeList interChildList = interfaceNode.getChildNodes(); 
				Node interChildNode = null;
				for (int n = 0; n < interChildList.getLength(); n++) {
				    interChildNode = interChildList.item(n);
				    if (interChildNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interChild = (Element) interChildNode;
					if (interChild.getNodeName().equalsIgnoreCase("Address")) {
					    commInterface.setAddress(Long.parseLong(interChild.getFirstChild().getNodeValue()));
					}
				    }
				}
			    }
			    
			    if (inter.getNodeName().equalsIgnoreCase("GPIB")) {
				commInterface.setInterfaceName("GPIB");
				// 设备端口节点子属性节点集合
				NodeList interChildList = interfaceNode.getChildNodes(); 
				Node interChildNode = null;
				for (int n = 0; n < interChildList.getLength(); n++) {
				    interChildNode = interChildList.item(n);
				    if (interChildNode.getNodeType() == Node.ELEMENT_NODE) {
					Element interChild = (Element) interChildNode;
					if (interChild.getNodeName().equalsIgnoreCase("Address")) {
					    commInterface.setAddress(Long.parseLong(interChild.getFirstChild().getNodeValue()));
					}
				    }
				}
			    }
			    
			}
		    }
		    break label;
		}
	    }
	}
	return commInterface;
    }
    
    // 根据设备子节点集合insChildrenList, 获取设备传感器列表信息
    private List<Sensor> getSensorList(NodeList insChildrenList) {
	List<Sensor> sensorsList = new ArrayList<Sensor>();
	Node insChildrenNode = null;
	label: for (int i = 0; i < insChildrenList.getLength(); i++) {
	    insChildrenNode = insChildrenList.item(i);
	    if (insChildrenNode.getNodeType() == Node.ELEMENT_NODE) {
		Element insChildElement = (Element) insChildrenNode;
		if (insChildElement.getTagName().equalsIgnoreCase("Parameter")) {
		    NodeList pList = insChildrenNode.getChildNodes(); // 设备传感器节点集合
		    Node pNode = null;
		    for (int j = 0; j < pList.getLength(); j++) {
			pNode = pList.item(j);
	
			if (pNode.getNodeType() == Node.ELEMENT_NODE) {
			    Sensor sensor = new Sensor();
			   
			    NodeList pChildList = pNode.getChildNodes(); // 设备传感器属性子节点集合
			    Node pChildNode = null;
			    for (int n = 0; n < pChildList.getLength(); n++) {
				pChildNode = pChildList.item(n);
				if (pChildNode.getNodeType() == Node.ELEMENT_NODE) {
				    Element pChild = (Element) pChildNode;
				    if (pChild.getNodeName().equalsIgnoreCase("GlobalID")) {
					sensor.setGlobalID(Integer.parseInt(pChild.getFirstChild().getNodeValue()));
				    }
				    if (pChild.getNodeName().equalsIgnoreCase("Name")) {
					sensor.setName(pChild.getFirstChild().getNodeValue());
				    }
				    if (pChild.getNodeName().equalsIgnoreCase("Unit")) {
					sensor.setUnit(pChild.getFirstChild().getNodeValue());
				    }
				    if (pChild.getNodeName().equalsIgnoreCase("InternalIdentifier")) {
					sensor.setInternalIdentifier(Integer.parseInt(pChild.getFirstChild().getNodeValue()));
				    }
				    if (pChild.getNodeName().equalsIgnoreCase("RelativeID")) {
					sensor.setRelativeID(Integer.parseInt(pChild.getFirstChild().getNodeValue()));
				    }
				}
			    }
			    sensorsList.add(sensor);
			}
		    }
		    break label;
		}
	    }
	}
	return sensorsList;
    }
    
    // 根据设备ID得到设备的传感器数目
    public int getSensorsNum(int instrumentID) {
	int num = 0; //设备的传感器数目

	Node instrumentNode = null;
	label: for (int i = 0; i < instrumentNum; i++) {
	    instrumentNode = instrumentList.item(i);
	    if (instrumentNode.getNodeType() == Node.ELEMENT_NODE) {
		NodeList insChildrenList = instrumentNode.getChildNodes(); // 设备子节点集合
		Node insChildrenNode = null;
		for (int j = 0; j < insChildrenList.getLength(); j++) {
		    insChildrenNode = insChildrenList.item(j);
		    if (insChildrenNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) insChildrenNode;
			if (element.getTagName().equalsIgnoreCase("Attribution")) {
			    NodeList attrList = insChildrenNode.getChildNodes(); // 设备属性子节点集合
			    Node attrNode = null;
			    for (int n = 0; n < attrList.getLength(); n++) {
				attrNode = attrList.item(n);
				if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
				    Element attrElement = (Element) attrNode;
				    if (attrElement.getNodeName().equalsIgnoreCase("GlobalID")) {
					int globalID = Integer.parseInt(attrElement.getFirstChild().getNodeValue());
					if(globalID == instrumentID) {
					    num = getSensorList(insChildrenList).size();
					    
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
	return num;
    }
}
