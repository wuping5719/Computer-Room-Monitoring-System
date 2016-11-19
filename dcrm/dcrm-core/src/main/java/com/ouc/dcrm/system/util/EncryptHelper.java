package com.ouc.dcrm.system.util;

import java.security.MessageDigest;

public class EncryptHelper {
    /***
     * MD5加密 生成32位md5码
     * 
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public String md5Encode(String inStr) throws Exception {
	MessageDigest md5 = null;
	try {
	    md5 = MessageDigest.getInstance("MD5");
	} catch (Exception e) {
	    System.out.println(e.toString());
	    e.printStackTrace();
	    return "";
	}

	byte[] byteArray = inStr.getBytes("UTF-8");
	byte[] md5Bytes = md5.digest(byteArray);
	StringBuffer hexValue = new StringBuffer();
	for (int i = 0; i < md5Bytes.length; i++) {
	    int val = ((int) md5Bytes[i]) & 0xff;
	    if (val < 16) {
		hexValue.append("0");
	    }
	    hexValue.append(Integer.toHexString(val));
	}
	return hexValue.toString();
    }

    /***
     * SHA加密 生成40位SHA码
     * 
     * @param 待加密字符串
     * @return 返回40位SHA码
     */
    public String shaEncode(String inStr) throws Exception {
	MessageDigest sha = null;
	try {
	    sha = MessageDigest.getInstance("SHA");
	} catch (Exception e) {
	    System.out.println(e.toString());
	    e.printStackTrace();
	    return "";
	}

	byte[] byteArray = inStr.getBytes("UTF-8");
	byte[] md5Bytes = sha.digest(byteArray);
	StringBuffer hexValue = new StringBuffer();
	for (int i = 0; i < md5Bytes.length; i++) {
	    int val = ((int) md5Bytes[i]) & 0xff;
	    if (val < 16) {
		hexValue.append("0");
	    }
	    hexValue.append(Integer.toHexString(val));
	}
	return hexValue.toString();
    }
}
