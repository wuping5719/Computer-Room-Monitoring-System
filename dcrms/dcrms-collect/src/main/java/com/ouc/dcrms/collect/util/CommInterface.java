package com.ouc.dcrms.collect.util;

/**
 * @author WuPing
 * @version 2016年12月20日 下午3:47:03
 */

public class CommInterface {
    private String interfaceName; // 通信接口类型
    private int comport; // 接口接在PC的通讯端口
    private int baudRate; // 波特率
    private float stopBit; // 停止位
    private int dataBit; // 数据位字节数
    private String parity; // 校验方式: 奇偶校验
    private long address; // 本身的通讯地址
    private String IPAddress; // IP地址
    private int port; // 端口号

    public String getInterfaceName() {
	return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
	this.interfaceName = interfaceName;
    }

    public int getComport() {
	return comport;
    }

    public void setComport(int comport) {
	this.comport = comport;
    }

    public int getBaudRate() {
	return baudRate;
    }

    public void setBaudRate(int baudRate) {
	this.baudRate = baudRate;
    }

    public float getStopBit() {
	return stopBit;
    }

    public void setStopBit(float stopBit) {
	this.stopBit = stopBit;
    }

    public int getDataBit() {
	return dataBit;
    }

    public void setDataBit(int dataBit) {
	this.dataBit = dataBit;
    }

    public String getParity() {
	return parity;
    }

    public void setParity(String parity) {
	this.parity = parity;
    }

    public long getAddress() {
	return address;
    }

    public void setAddress(long address) {
	this.address = address;
    }

    public String getIPAddress() {
	return IPAddress;
    }

    public void setIPAddress(String iPAddress) {
	IPAddress = iPAddress;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }
}
