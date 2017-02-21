package com.ouc.dcrms.core.jmf;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JFrame;

/**
 * @author WuPing
 * @version 2017年2月21日 下午2:22:36
 */

/**
 * JMF（Java Media Framework）是Java媒体框架，
 * 包含了许多用于处理多媒体的API可以让基于JAVA的应用实现音频、视频的捕捉、处理、播放、传输等功能。
 * 利用它，可以实现音、视频播放，网络视频聊天、视频会议等功能。
 * 
   1.下载地址：http://www.oracle.com/technetwork/java/javase/download-142937.html ，
        自从sun被oracle 收购后，网址都改成oracle.com了，下载的版本是：jmf-2_1_1e-windows-i586

   2.安装：按提示一步步安装下去就可以了，安装后要重启；安装完毕后，在你的JDK安装目录\jre\lib\ext下，会多出两个文件：jmf.jar和sound.jar

   3.测试摄像头：安装完毕后，在桌面会多一个"JMStudio"的快捷方式，运行它，点击"File->Capture"菜单，如果启动摄像头成功，说明你安装jmf成功。
        如果失败的话，按网上的说法，必须是安装32位的JDK才可以，因为JMF只支持32位JDK。

   4.编写JAVA代码打开电脑摄像头，以下代码也是从网上修改而来，但是必须做以下修改才运行正常：
     a.必须先初始化摄像头、再设置Jframe的一些属性并显示，如果先后顺序颠倒的话，则窗体上不会正确显示摄像头。(本机64位win7 + jdk1.6);
     b.在eclipse中选择jdk的时候，最好选择安装jmf时所识别的jdk，否则可能报错。
             网上的解决方式：将jmf安装目录\lib目录下的jmf.jar，sound.jar，mediaplayer.jar三个文件导入工程，
             目的是要引入该目录下的jmf.properties
 */

public class Camera extends JFrame {

    private static final long serialVersionUID = -5632036368589445389L;

    public Camera() throws Exception {
	// 先启动摄像头，再做后续的初始化窗体，可保证正确显示摄像头
	initCapture();

	// 设置窗体的一些属性
	this.setTitle("Capture");
	this.setBounds(500, 100, 800, 500);
	this.setVisible(true);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // 初始化摄像头
    private void initCapture() throws Exception {
	// 获取所有音频、视频设备
	@SuppressWarnings("unchecked")
	Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager
		.getDeviceList(null);

	// 获取视频设备，视频设备以vfw打头
	CaptureDeviceInfo cameraDevice = null;
	for (CaptureDeviceInfo cameraDeviceTmp : deviceList) {
	    if (cameraDeviceTmp.getName().startsWith("vfw")) {
		cameraDevice = cameraDeviceTmp;
		break;
	    }
	}

	if (cameraDevice == null) {
	    throw new Exception("找不到摄像头设备!");
	}

	// 创建视频播放器
	MediaLocator ml = cameraDevice.getLocator();
	Player player = Manager.createRealizedPlayer(ml);

	if (player == null) {
	    throw new Exception("创建摄像头播放器失败!");
	}

	// 播放视频
	player.start();

	// 将播放器加入窗体
	Component comp = null;
	if ((comp = player.getVisualComponent()) != null)
	    add(comp, BorderLayout.CENTER);
    }
}
