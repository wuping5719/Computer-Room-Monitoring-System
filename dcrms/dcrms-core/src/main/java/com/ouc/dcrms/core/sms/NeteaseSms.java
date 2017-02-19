package com.ouc.dcrms.core.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ouc.dcrms.core.util.CheckSumBuilder;

/**
 * @author WuPing
 * @version 2017年2月19日 下午3:56:16
 */

public class NeteaseSms {
    /**
     * 发送POST方法的请求
     * @return 所代表远程资源的响应结果
     */
    public static String sendMsg() {
	String appKey = "1686974c7ffa5bf61f7ab4cd752b1fed";
	String appSecret = "444dc898f580";
	String nonce = "Nick";    // 随机数(最大长度128个字符)
	
	Date now = new Date();
	String curTime = String.valueOf(now.getTime() / 1000L); // 当前UTC时间戳
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	System.out.println("当前系统时间：" + sdf.format(now));

	String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
	System.out.println("CheckSum: " + checkSum);

	PrintWriter out = null;
	BufferedReader in = null;
	String result = "";
	try {
	    String url = "https://api.netease.im/sms/sendtemplate.action";
	    String telephone = URLEncoder.encode("18765951838", "utf-8");
	    String siteName = URLEncoder.encode("李村", "utf-8");
	    String reason = URLEncoder.encode("烟感1异常", "utf-8");  // url编码, 防止不识别中文
	    
	    String params = "templateid=1007&mobiles=[\"" + telephone + "\"]"
		    + "&params=" + "[\"" + siteName + "\",\"" + reason + "\"]";
	    System.out.println("params" + params);

	    URL realUrl = new URL(url);
	    // 打开和URL之间的连接
	    URLConnection conn = realUrl.openConnection();
	    // 设置通用的请求属性
	    conn.setRequestProperty("AppKey", appKey);
	    conn.setRequestProperty("CheckSum", checkSum);
	    conn.setRequestProperty("CurTime", curTime);
	    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	    conn.setRequestProperty("Nonce", nonce);

	    // 发送POST请求必须设置如下两行
	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    // 获取URLConnection对象对应的输出流
	    out = new PrintWriter(conn.getOutputStream());
	    // 发送请求参数
	    out.print(params);
	    // flush输出流的缓冲
	    out.flush();
	    // 定义BufferedReader输入流来读取URL的响应
	    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while ((line = in.readLine()) != null) {
		result += line;
	    }
	} catch (Exception e) {
	    System.out.println("发送 POST 请求出现异常！\n" + e);
	    e.printStackTrace();
	} finally {  // 使用finally块来关闭输出流、输入流
	    try {
		if (out != null) {
		    out.close();
		}
		if (in != null) {
		    in.close();
		}
	    } catch (IOException ex) {
		ex.printStackTrace();
	    }
	}
	return result;
    }
}
