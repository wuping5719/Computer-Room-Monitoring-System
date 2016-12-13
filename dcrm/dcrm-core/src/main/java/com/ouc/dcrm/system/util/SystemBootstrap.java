package com.ouc.dcrm.system.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import mx4j.tools.adaptor.http.HttpAdaptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author WuPing
 * @version 2016年12月13日 上午10:35:32
 */

public class SystemBootstrap implements InitializingBean {
    private static final String CONFIG_FILE_PATH = "/env.properties";
    private static final Log LOG = LogFactory.getLog(SystemBootstrap.class);

    private HttpAdaptor httpAdaptor;

    public static void init() {
	InputStream inputStream = null;
	Properties properties = new Properties();
	try {
	    inputStream = SystemBootstrap.class.getResourceAsStream(CONFIG_FILE_PATH);
	    properties.load(inputStream);
	    LOG.info("系统配置项:" + properties);
	} catch (Exception e) {
	    LOG.error("读取系统配置文件时发生错误：", e);
	} finally {
	    if (inputStream != null) {
		try {
		    inputStream.close();
		} catch (IOException e) {
		    LOG.error("关闭文件输入流失败：", e);
		}
	    }
	}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	httpAdaptor.start();
    }

    public void setHttpAdaptor(HttpAdaptor httpAdaptor) {
	this.httpAdaptor = httpAdaptor;
    }
}
