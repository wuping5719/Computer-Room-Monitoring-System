package com.ouc.dcrms.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ouc.dcrms.util.Instrument;
import com.ouc.dcrms.util.ParseConfigureXML;

/**
 * @author WuPing
 * @version 2016年12月23日 下午10:13:50
 */

public class TestParseXML {

    public static void main(String[] args) {
	try {
	    String filePath = "config/LiCun-1-Configure.xml";
	    ParseConfigureXML paseXml = new ParseConfigureXML(1, filePath);
	    Instrument ins = paseXml.getInstrument(1);
	    System.out.println("设备属性名称：" + ins.getAttribution().getName());
	    System.out.println("设备接口：" + ins.getCommInterface().getInterfaceName());
	    System.out.println("设备接口数据位：" + ins.getCommInterface().getDataBit());
	    System.out.println("设备的第一个传感器：" + ins.getSensors().get(0).getName());
	
	    int num = paseXml.getSensorsNum(1);
	    System.out.println("设备传感器数：" + num);
	    
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	} catch (SAXException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
