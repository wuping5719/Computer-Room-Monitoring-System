package com.ouc.dcrms.initial;

import java.io.InputStream;
import java.io.OutputStream;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

import com.ouc.dcrms.util.Instrument;

/**
 * @author WuPing
 * @version 2016年12月20日 下午3:34:43
 */

public class InitialComm {
    private CommPortIdentifier portId;
    public OutputStream outputStream;
    public InputStream inputStream;
    public SerialPort serialPort;
    public int comPort;

    public InitialComm(Instrument instrument) {
	comPort = instrument.commInterface.comport;
	String portName = "";
	try {
	    portName = "COM" + instrument.commInterface.comport;
	    portId = CommPortIdentifier.getPortIdentifier(portName);
	    serialPort = (SerialPort) portId.open("Serial_Communication", 2000);

	    switch (instrument.commInterface.dataBit) {
	    case 5:
		if (instrument.commInterface.stopBit == 1.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 1.5) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 2.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_2,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_2,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_5, SerialPort.STOPBITS_2,
				SerialPort.PARITY_EVEN);
		    }
		}
		break;
	    case 6:
		if (instrument.commInterface.stopBit == 1.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 1.5) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 2.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_2,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_2,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_6, SerialPort.STOPBITS_2,
				SerialPort.PARITY_EVEN);
		    }
		}
		break;
	    case 7:
		if (instrument.commInterface.stopBit == 1.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 1.5) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 2.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_7, SerialPort.STOPBITS_2,
				SerialPort.PARITY_EVEN);
		    }
		}
		break;
	    case 8:
		if (instrument.commInterface.stopBit == 1.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 1.5) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_1_5,
				SerialPort.PARITY_EVEN);
		    }
		}
		if (instrument.commInterface.stopBit == 2.0) {
		    if (instrument.commInterface.parity.equals("NONE")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_2,
				SerialPort.PARITY_NONE);
		    }
		    if (instrument.commInterface.parity.equals("ODD")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_2,
				SerialPort.PARITY_ODD);
		    }
		    if (instrument.commInterface.parity.equals("EVEN")) {
			serialPort.setSerialPortParams(
				instrument.commInterface.baudRate,
				SerialPort.DATABITS_8, SerialPort.STOPBITS_2,
				SerialPort.PARITY_EVEN);
		    }
		}
		break;
	    }

	    outputStream = serialPort.getOutputStream();
	    inputStream = serialPort.getInputStream();
	} catch (Exception e) {
	    System.out.println(portName + "初始化失败!");
	}
    }
}
