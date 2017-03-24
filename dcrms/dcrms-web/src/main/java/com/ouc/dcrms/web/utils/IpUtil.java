package com.ouc.dcrms.web.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class IpUtil {
	
	private static final Pattern pattern = Pattern
            .compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
	
	public static boolean isValidIp(String ip){
    	
        return pattern.matcher(ip).matches();
    }
    
	public static String getIpByDomainName(String domainName){
    	try {
			return InetAddress.getByName(domainName).getHostAddress();
		} catch (UnknownHostException e) {
			System.out.println("unknowDomainName---" + domainName);
		}
    	return null;
    }
}
