package com.ouc.dcrm.system.util;

/**
 * @author WuPing
 * @version 2016年12月20日 下午3:47:03
 */

public class CommInterface {
    public String interfaceName; // 通信接口类型
    public int comport; // 接口接在PC的通讯端口
    public int baudRate; // 波特率
    public float stopBit; // 停止位
    public int dataBit; // 数据位字节数
    public String parity; // 校验方式: 奇偶校验
    public long address; // 本身的通讯地址
    public String IPAddress; // IP地址
    public int port; // 端口号
}
