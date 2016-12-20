package com.ouc.dcrm.system.util;

import com.ouc.dcrm.system.util.Attribution;
import com.ouc.dcrm.system.util.CommInterface;
import com.ouc.dcrm.system.util.Sensor;

/**
 * @author WuPing
 * @version 2016年12月20日 下午3:55:11
 */

public class Instrument {
    public Attribution attribution;
    public CommInterface commInterface;
    public Sensor[] sensors;
    public int sensorNumber = 0; // 定义传感器个数

    // 构造函数：根据传入参数n初始化拥有n个传感器的设备
    public Instrument(int n) {
	attribution = new Attribution();
	commInterface = new CommInterface();
	sensors = new Sensor[n];
	for (int i = 0; i < n; i++) {
	    sensors[i] = new Sensor();
	}
	sensorNumber = n;
    }
}
