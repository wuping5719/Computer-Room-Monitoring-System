package com.ouc.dcrms.collect.util.data;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:41:41
 */

public class PostProtocol {
    // 根据协议将命令进行转换，输入为ASCII码
    public static String transFormAsciiCommand(String sourceAsciiCommand) {
	sourceAsciiCommand = sourceAsciiCommand
		+ computeAsciiComplement(sourceAsciiCommand);
	byte[] hexValue = sourceAsciiCommand.getBytes();
	String transformedCommand = "";
	for (int i = 0; i < hexValue.length; i++) {
	    transformedCommand += Integer.toHexString(hexValue[i]);
	}

	return "7E" + transformedCommand.toUpperCase() + "0D";
    }

    // 根据协议将命令进行转换，输入为16进制
    public static String transformHexCommand(String sourceHexCommand) {
	String transformedCommand = sourceHexCommand
		+ computeHexComplement(sourceHexCommand);

	return "7E" + transformedCommand.toUpperCase() + "0D";
    }

    // 计算补码(校验码)，以字符串(字符为ASCII码)形式返回
    public static String computeAsciiComplement(String source) {
	// 计算补码（数值）
	byte[] asciiHexValue = source.getBytes();
	int complementValue = 0;
	for (int i = 0; i < asciiHexValue.length; i++) {
	    complementValue += asciiHexValue[i];
	}
	complementValue %= 0xFFFF; // 保证result为单字
	complementValue = 0xFFFF - complementValue + 1; // 取反加1求补码

	// 将数值补码转换为字符串形式的补码
	String complementString = Integer.toHexString(complementValue);
	for (int i = complementString.length(); i < 4; i++) {
	    complementString = "0" + complementString;
	}

	return complementString.toUpperCase();
    }

    // 计算补码(校验码)，以字符串(字符为十六进制)形式返回
    public static String computeHexComplement(String source) {
	// 计算补码（数值）
	int complementValue = 0;
	for (int i = 0; i < source.length(); i += 2) {
	    complementValue += Integer.parseInt(source.substring(i, i + 2), 16);
	}

	complementValue %= 0xFFFF; // 保证result为单字
	complementValue = 0xFFFF - complementValue + 1; // 取反加1求补码

	// 将数值补码转换为字符串形式的补码
	String complementString = Integer.toHexString(complementValue);
	for (int i = complementString.length(); i < 4; i++) {
	    complementString = "0" + complementString;
	}
	complementString = complementString.toUpperCase(); // 大小写字母对应的ASCII码的十六进制数值是不同的,但用的都是大写字母的ascii值
	byte[] hexValue = complementString.getBytes();
	String computedChecksum = "";
	for (int i = 0; i < hexValue.length; i++) {
	    computedChecksum += Integer.toHexString(hexValue[i]);
	}

	return computedChecksum.toUpperCase();
    }

    public static int toHexValue(String asciiStr) {
	if (asciiStr == null || asciiStr.length() != 8) {
	    return 0;
	}
	String s = "";
	for (int i = 0; i < 4; i++) {
	    s += (char) Integer.parseInt(asciiStr.substring(i * 2, 2 + i * 2),
		    16);
	}
	return Integer.parseInt(s, 16);
    }

    // 检查返回值的校验码
    public static Boolean checkResult(String result) {
	int length = result.length();
	if (length <= 14 || length % 2 != 0) {
	    return false;
	}

	String toCheck = result.substring(2, length - 10);
	String returnedChecksum = result.substring(length - 10,
		result.length() - 2);
	String computedChecksum = computeHexComplement(toCheck);
	if (computedChecksum.equalsIgnoreCase(returnedChecksum)) {
	    return true;
	}
	return false;
    }
}
