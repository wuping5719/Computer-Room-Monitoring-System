package com.ouc.dcrms.test;

import com.ouc.dcrms.core.sms.NeteaseSms;

/**
 * @author WuPing
 * @version 2017年2月19日 下午3:56:16
 */

public class NeteaseSmsTest {

    public static void main(String[] args) {
	NeteaseSms neteaseSms = new NeteaseSms();
	String telephone = "18765951838";
	String siteName = "金家岭";
	String reason = "水浸1告警";
	neteaseSms.sendMsg(telephone, siteName, reason);
    }
}

