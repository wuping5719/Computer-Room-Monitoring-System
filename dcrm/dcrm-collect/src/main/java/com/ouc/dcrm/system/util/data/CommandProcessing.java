package com.ouc.dcrm.system.util.data;

public class CommandProcessing {
    
    // 判断发送过来的命令是否出错
    public boolean CommandIsCorrect(String command)
    {
	String temp = command;
	String strCheckCode;
	String strBeforeCheckCode;
	String checkCode;
	int pose = temp.indexOf('&');
	strCheckCode = temp.substring(pose + 1, temp.length() - 1); // 得到校验位字符串
	strBeforeCheckCode = temp.substring(0, pose); // 得到校验位之前的字符串
	int ASCIIValue = GetASCIIValue(strBeforeCheckCode);

	checkCode = Integer.toHexString(ASCIIValue % 256); // 十进制转化为十六进制的字符串
	if (checkCode.length() == 1) {
	    checkCode = "0" + checkCode;
	}
	if (checkCode.equals(strCheckCode)) {
	    return true;
	} else {
	    return false;
	}
    }

    // 得到字符串中每个字符的ASCII码值之和
    public int GetASCIIValue(String strTemp)  
    {
	int ASCIIValue = 0;
	for (int i = 0; i < strTemp.length(); i++) {
	    char ch = strTemp.charAt(i);
	    ASCIIValue += ch;
	}
	return ASCIIValue;
    }

    // 得到字符串中特定字符的个数
    public int getStrSpecialCharNum(String str)
    {
	int num = 0;
	for (int i = 0; i < str.length(); i++) {
	    if (str.charAt(i) == ',') {
		num++;
	    }
	}
	return num;
    }

    public boolean Is$InString(String str) {
	for (int i = 0; i < str.length(); i++) {
	    if (str.charAt(i) == '$') {
		return true;

	    }
	}
	return false;
    }

    public boolean IsEinString(String str) {
	for (int i = 1; i < str.length(); i++) {
	    if ((str.charAt(i - 1) == 'N' && str.charAt(i) == 'E')
		    || (str.charAt(i - 1) == 'E' && str.charAt(i) == 'E')
		    || (str.charAt(i - 1) == 'O' && str.charAt(i) == 'E')) {
		return true;
	    }
	}
	return false;
    }

    // 分解数据请求命令
    public String Delete0(String str)
    {
	String strData = "";
	if (str.equals("000000000")) {
	    strData = "0";
	} else {
	    int i = 0;
	    while (str.charAt(i) == '0') {
		i++;
	    }
	    strData = str.substring(i, str.length());
	}
	return strData;
    }

    // str表示返回的数据
    public boolean instrumentState(String str)
    {
	String strTemp = "";
	if (str.length() < 11) {
	    return true;
	} else {
	    strTemp = str.substring(str.length() - 8, str.length() - 1);// 第一个数据是否为七个9
	    if (strTemp.equals("9999999")) {
		return false;
	    }
	    return true;
	}
    }

    public int getStrNum(String str, char ch)  // 判断字符串中有多少个@字符
    {
	int num = 0;
	for (int i = 0; i < str.length(); i++) {
	    if (str.charAt(i) == ch) {
		num++;
	    }
	}
	return num;
    }

    public byte[] toByte(String source) {
	int ii = source.length() / 2;
	byte[] tar = new byte[ii];
	for (int i = 0; i < ii; i++) {
	    tar[i] = (byte) Integer.parseInt(
		    source.substring(i * 2, i * 2 + 2), 16);
	}
	return tar;
    }

    // 将字节数组转为16进制字符串
    public String toHexString(byte[] source) {
	StringBuffer buf = new StringBuffer(source.length * 2);
	// 将字节数组中每个字节拆解成2位16进制整数
	for (int i = 0; i < source.length; i++) {
	    if (((int) source[i] & 0xff) < 0x10) {
		buf.append("0");
	    }
	    buf.append(Long.toString((int) source[i] & 0xff, 16));
	}
	return buf.toString();
    }

    public int stringToByte(String str) {
	if (str.length() != 4)
	    return 0;

	char[] c = new char[2];
	String[] s = new String[2];
	int[] inte = new int[2];
	for (int i = 0, j = 0; i < 2 && j < 4; i++, j += 2)
	    s[i] = str.substring(j, j + 2);

	for (int i = 0; i < 2; i++)
	    inte[i] = Integer.valueOf(s[i], 16).intValue();

	for (int i = 0; i < 2; i++)
	    c[i] = (char) inte[i];

	String strRes = String.valueOf(c[0]) + String.valueOf(c[1]);

	return Integer.parseInt(strRes);
    }
}
