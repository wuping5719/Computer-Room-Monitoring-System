package com.ouc.dcrms.util.data;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:29:07
 */

public class CRC16 {
    private static final int CPH = 0;
    private static final int CPL = 1;

    private static final byte[] tbcrch = { (byte) 0, (byte) 193, (byte) 129,
	    (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 1,
	    (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 1, (byte) 192,
	    (byte) 128, (byte) 65, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 1, (byte) 192,
	    (byte) 128, (byte) 65, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 0, (byte) 193, (byte) 129, (byte) 64, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 0, (byte) 193, (byte) 129, (byte) 64,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, (byte) 1, (byte) 192, (byte) 128, (byte) 65,
	    (byte) 1, (byte) 192, (byte) 128, (byte) 65, (byte) 0, (byte) 193,
	    (byte) 129, (byte) 64, };

    private static final byte[] tbcrcl = { (byte) 0, (byte) 192, (byte) 193,
	    (byte) 1, (byte) 195, (byte) 3, (byte) 2, (byte) 194, (byte) 198,
	    (byte) 6, (byte) 7, (byte) 199, (byte) 5, (byte) 197, (byte) 196,
	    (byte) 4, (byte) 204, (byte) 12, (byte) 13, (byte) 205, (byte) 15,
	    (byte) 207, (byte) 206, (byte) 14, (byte) 10, (byte) 202,
	    (byte) 203, (byte) 11, (byte) 201, (byte) 9, (byte) 8, (byte) 200,
	    (byte) 216, (byte) 24, (byte) 25, (byte) 217, (byte) 27,
	    (byte) 219, (byte) 218, (byte) 26, (byte) 30, (byte) 222,
	    (byte) 223, (byte) 31, (byte) 221, (byte) 29, (byte) 28,
	    (byte) 220, (byte) 20, (byte) 212, (byte) 213, (byte) 21,
	    (byte) 215, (byte) 23, (byte) 22, (byte) 214, (byte) 210,
	    (byte) 18, (byte) 19, (byte) 211, (byte) 17, (byte) 209,
	    (byte) 208, (byte) 16, (byte) 240, (byte) 48, (byte) 49,
	    (byte) 241, (byte) 51, (byte) 243, (byte) 242, (byte) 50,
	    (byte) 54, (byte) 246, (byte) 247, (byte) 55, (byte) 245,
	    (byte) 53, (byte) 52, (byte) 244, (byte) 60, (byte) 252,
	    (byte) 253, (byte) 61, (byte) 255, (byte) 63, (byte) 62,
	    (byte) 254, (byte) 250, (byte) 58, (byte) 59, (byte) 251,
	    (byte) 57, (byte) 249, (byte) 248, (byte) 56, (byte) 40,
	    (byte) 232, (byte) 233, (byte) 41, (byte) 235, (byte) 43,
	    (byte) 42, (byte) 234, (byte) 238, (byte) 46, (byte) 47,
	    (byte) 239, (byte) 45, (byte) 237, (byte) 236, (byte) 44,
	    (byte) 228, (byte) 36, (byte) 37, (byte) 229, (byte) 39,
	    (byte) 231, (byte) 230, (byte) 38, (byte) 34, (byte) 226,
	    (byte) 227, (byte) 35, (byte) 225, (byte) 33, (byte) 32,
	    (byte) 224, (byte) 160, (byte) 96, (byte) 97, (byte) 161,
	    (byte) 99, (byte) 163, (byte) 162, (byte) 98, (byte) 102,
	    (byte) 166, (byte) 167, (byte) 103, (byte) 165, (byte) 101,
	    (byte) 100, (byte) 164, (byte) 108, (byte) 172, (byte) 173,
	    (byte) 109, (byte) 175, (byte) 111, (byte) 110, (byte) 174,
	    (byte) 170, (byte) 106, (byte) 107, (byte) 171, (byte) 105,
	    (byte) 169, (byte) 168, (byte) 104, (byte) 120, (byte) 184,
	    (byte) 185, (byte) 121, (byte) 187, (byte) 123, (byte) 122,
	    (byte) 186, (byte) 190, (byte) 126, (byte) 127, (byte) 191,
	    (byte) 125, (byte) 189, (byte) 188, (byte) 124, (byte) 180,
	    (byte) 116, (byte) 117, (byte) 181, (byte) 119, (byte) 183,
	    (byte) 182, (byte) 118, (byte) 114, (byte) 178, (byte) 179,
	    (byte) 115, (byte) 177, (byte) 113, (byte) 112, (byte) 176,
	    (byte) 80, (byte) 144, (byte) 145, (byte) 81, (byte) 147,
	    (byte) 83, (byte) 82, (byte) 146, (byte) 150, (byte) 86, (byte) 87,
	    (byte) 151, (byte) 85, (byte) 149, (byte) 148, (byte) 84,
	    (byte) 156, (byte) 92, (byte) 93, (byte) 157, (byte) 95,
	    (byte) 159, (byte) 158, (byte) 94, (byte) 90, (byte) 154,
	    (byte) 155, (byte) 91, (byte) 153, (byte) 89, (byte) 88,
	    (byte) 152, (byte) 136, (byte) 72, (byte) 73, (byte) 137,
	    (byte) 75, (byte) 139, (byte) 138, (byte) 74, (byte) 78,
	    (byte) 142, (byte) 143, (byte) 79, (byte) 141, (byte) 77,
	    (byte) 76, (byte) 140, (byte) 68, (byte) 132, (byte) 133,
	    (byte) 69, (byte) 135, (byte) 71, (byte) 70, (byte) 134,
	    (byte) 130, (byte) 66, (byte) 67, (byte) 131, (byte) 65,
	    (byte) 129, (byte) 128, (byte) 64, };

    public static String getCRC16(String cmd) {
	CommandProcessing cd = new CommandProcessing();
	int i = 0;
	int j = 0;
	byte[] message = cd.toByte(cmd);
	byte[] cval = new byte[4];
	cval[0] = (byte) 0xFF;
	cval[1] = (byte) 0xFF;
	int length = message.length;
	while (i < length) {
	    j = (int) ((message[i] ^ cval[CPH]) & 0xFF); // 0xFF
	    cval[CPH] = (byte) (tbcrch[j] ^ cval[CPL]);
	    cval[CPL] = (byte) tbcrcl[j];
	    i++;
	}

	int x1 = (cval[0] & 0xFF) << 8;
	int x2 = (cval[1] & 0xFF);
	int x = x1 | x2;
	if ((x | 0x000F) == 0x000F) {
	    return "000" + Integer.toHexString(x);
	} else if ((x | 0x00FF) == 0x00FF) {
	    return "00" + Integer.toHexString(x);
	} else if ((x | 0x0FFF) == 0x0FFF) {
	    return "0" + Integer.toHexString(x);
	} else {
	    return Integer.toHexString(x);
	}
    }

    public static boolean checkCrc16(String str) {
	if (str.length() <= 4) {
	    return false;
	}

	String receivedCrc16 = str.substring(str.length() - 4, str.length());
	String computedCrc16 = getCRC16(str.substring(0, str.length() - 4));
	if (receivedCrc16.equals(computedCrc16)) {
	    return true;
	}

	return false;
    }

    public static String getCRC16(byte[] cmd) {
	int i = 0;
	int j = 0;
	byte[] message = cmd;
	byte[] cval = new byte[4];
	cval[0] = (byte) 0xFF;
	cval[1] = (byte) 0xFF;
	int length = message.length;
	while (i < length) {
	    j = (int) ((message[i] ^ cval[CPH]) & 0xFF); // 0xFF
	    cval[CPH] = (byte) (tbcrch[j] ^ cval[CPL]);
	    cval[CPL] = (byte) tbcrcl[j];
	    i++;
	}

	int x1 = (cval[0] & 0xFF) << 8;
	int x2 = (cval[1] & 0xFF);
	int x = x1 | x2;
	if ((x | 0x000F) == 0x000F) {
	    return "000" + Integer.toHexString(x);
	} else if ((x | 0x00FF) == 0x00FF) {
	    return "00" + Integer.toHexString(x);
	} else if ((x | 0x0FFF) == 0x0FFF) {
	    return "0" + Integer.toHexString(x);
	} else {
	    return Integer.toHexString(x);
	}
    }
}
