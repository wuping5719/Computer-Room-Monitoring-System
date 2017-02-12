package com.ouc.dcrms.web.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class CasUtil {

    private static final String[] URL_PREFIX = { "http://", "https://" };

    public static String getServerName(HttpServletRequest request) {
	// serverName like http://ip:port or http://domain:port
	String serverName = request
		.getRequestURL()
		.delete(request.getRequestURL().length()
			- request.getRequestURI().length(),
			request.getRequestURL().length()).toString();
	String[] serverNames = serverName.split(":");
	// 如果是80端口访问,weblogic取得的serverName是带80的,这里把80去掉
	String tmpServerName = "";
	for (String str : serverNames) {
	    if ("80".equals(str)) {
		continue;
	    }
	    tmpServerName = tmpServerName + str + ":";
	}
	return tmpServerName.substring(0, tmpServerName.length() - 1);
    }

    public static String getDomainName(String url) {
	if (StringUtils.isBlank(url)) {
	    return url;
	}
	for (String prefix : URL_PREFIX) {
	    if (!url.startsWith(prefix)) {
		continue;
	    }
	    String tmp = url.substring(prefix.length());
	    return tmp.indexOf("/") > 0 ? tmp.substring(0, tmp.indexOf("/"))
		    : tmp;
	}
	return url.indexOf("/") > 0 ? url.substring(0, url.indexOf("/")) : url;
    }
}
