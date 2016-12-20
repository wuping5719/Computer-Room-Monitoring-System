package com.ouc.dcrm.system.util;

import java.io.File;

/**
 * @author WuPing
 * @version 2016年12月20日 下午4:00:48
 */

public class FreeSpace {
    // 查询磁盘剩余空间大小
    public static long[] getDiskFreeeSpace(String dirName) {
	long[] space = new long[2];
	File[] roots = File.listRoots();
	for (File _file : roots) {
	    if (_file.getPath().equals(dirName)) {
		space[0] = (long) (_file.getTotalSpace());
		space[1] = (long) (_file.getFreeSpace());
	    }
	}
	return space;
    }
}
