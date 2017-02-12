package com.ouc.dcrms.collect.util.data;

/**
 * @author WuPing
 * @version 2016年12月20日 下午7:31:46
 */

public class GlobalVariable {
    public static int[] ct;// A40表电流变比
    public static float[] pt;// A40表电压变比
    public static float temp_max = 100;
    public static float temp_min = 0;
    public static float humi_max = 100;
    public static float humi_min = 0;
    public static float u_3p_max = 400;
    public static float u_3p_min = 0;
    public static float i_3p_max = 400;
    public static float i_3p_min = 0;
    public static float u_1p_max = 400;
    public static float u_1p_min = 0;
    public static float i_1p_max = 400;
    public static float i_1p_min = 0;
    public static float u_a40_max = 400;  // A40电压上限
    public static float u_a40_min = 0;    // A40电压下限
    public static float i_a40_max = 400;  // A40电流上限
    public static float i_a40_min = 0;    // A40电流下限
}
