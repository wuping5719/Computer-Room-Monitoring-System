package com.ouc.dcrms.test;

import com.ouc.dcrms.core.jmf.Camera;

/**
 * @author WuPing
 * @version 2017年2月21日 下午2:34:03
 */

public class CameraTest {

    public static void main(String[] args) {
	try {
	    @SuppressWarnings("unused")
	    Camera cam = new Camera();
	} catch (Exception e) {
	    e.printStackTrace();
	} 
    }

}
