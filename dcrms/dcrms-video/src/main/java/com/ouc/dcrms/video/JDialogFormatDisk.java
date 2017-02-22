/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogFormatDisk.java
 *
 * Created on 2009-10-9, 16:15:51
 */
/**
 *
 * @author Administrator
 */

package com.ouc.dcrms.video;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ：JDialogFormatDisk 
 * 类描述 ：格式化硬盘
 ****************************************************************************/
public class JDialogFormatDisk extends JDialog {
    private static final long serialVersionUID = 1196932836822008157L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserId;
    private NativeLong m_lFormatHandle;
    private HCNetSDK.NET_DVR_HDCFG m_struHDCfg;

    /*************************************************
     * 函数: JDialogFormatDisk 
     * 函数描述: 构造函数 Creates new form JDialogFormatDisk
     *************************************************/
    public JDialogFormatDisk(Frame parent, boolean modal,
	    NativeLong userId) {
	super(parent, modal);
	initComponents();
	jProgressBar.setMinimum(0);
	jProgressBar.setMaximum(100);

	m_lUserId = userId;
	m_lFormatHandle = new NativeLong(-1);
	m_struHDCfg = new HCNetSDK.NET_DVR_HDCFG();
	m_struHDCfg.write();
	Pointer lpPicConfig = m_struHDCfg.getPointer();
	IntByReference ibrBytesReturned = new IntByReference(0);
	boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserId,
		HCNetSDK.NET_DVR_GET_HDCFG, new NativeLong(0), lpPicConfig,
		m_struHDCfg.size(), ibrBytesReturned);
	m_struHDCfg.read();
	if (getDVRConfigSuc != true) {
	    System.out.println("获取硬盘信息失败");
	} else {
	    jComboBoxDisk.addItem("全部硬盘");
	    for (int i = 0; i < m_struHDCfg.dwHDCount; i++) {
		String s = "硬盘" + m_struHDCfg.struHDInfo[i].dwHDNo;
		jComboBoxDisk.addItem(s);
	    }
	}
    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jPanel1 = new JPanel();
	jButtonFormat = new JButton();
	jComboBoxDisk = new JComboBox<String>();
	jLabel1 = new JLabel();
	jLabel3 = new JLabel();
	jProgressBar = new JProgressBar();
	jLabel2 = new JLabel();
	jButtonExit = new JButton();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("格式化硬盘");

	jPanel1.setBorder(BorderFactory.createTitledBorder(""));

	jButtonFormat.setText("格式化");
	jButtonFormat.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonFormatActionPerformed(evt);
	    }
	});

	jLabel1.setText("盘符");

	jLabel3.setText("0");

	jLabel2.setText("进度:");

	jButtonExit.setText("退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jLabel1)
									.addGap(18,
										18,
										18)
									.addComponent(
										jComboBoxDisk,
										GroupLayout.PREFERRED_SIZE,
										99,
										GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jLabel2)
									.addGap(18,
										18,
										18)
									.addComponent(
										jLabel3,
										GroupLayout.DEFAULT_SIZE,
										203,
										Short.MAX_VALUE)
									.addGap(41,
										41,
										41))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jButtonFormat)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										144,
										Short.MAX_VALUE)
									.addComponent(
										jButtonExit,
										GroupLayout.PREFERRED_SIZE,
										67,
										GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jProgressBar,
										GroupLayout.DEFAULT_SIZE,
										282,
										Short.MAX_VALUE)
									.addContainerGap()))));

	jPanel1Layout.linkSize(SwingConstants.HORIZONTAL,
		new Component[] { jButtonExit, jButtonFormat });

	jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel1Layout
			.createSequentialGroup()
			.addGap(18, 18, 18)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel1)
					.addComponent(jComboBoxDisk,
						GroupLayout.PREFERRED_SIZE, 18,
						GroupLayout.PREFERRED_SIZE))
			.addGap(18, 18, 18)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel2)
					.addComponent(jLabel3))
			.addPreferredGap(
				LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(jProgressBar, GroupLayout.PREFERRED_SIZE,
				11, GroupLayout.PREFERRED_SIZE)
			.addGap(18, 18, 18)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jButtonFormat)
					.addComponent(jButtonExit))
			.addContainerGap(7, Short.MAX_VALUE)));

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));
	layout.setVerticalGroup(layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		GroupLayout.Alignment.TRAILING,
		layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 154,
				Short.MAX_VALUE).addContainerGap()));

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "格式化"按钮响应函数 函数描述: 调用接口格式化硬盘
     *************************************************/
    private void jButtonFormatActionPerformed(ActionEvent evt) {// GEN-FIRST:event_jButtonFormatActionPerformed
	NativeLongByReference pCurrentFormatDisk = new NativeLongByReference(
		new NativeLong(-1));
	NativeLongByReference pCurrentDiskPos = new NativeLongByReference(
		new NativeLong(-1));
	NativeLongByReference pFormatStatic = new NativeLongByReference(
		new NativeLong(-1));
	int iDiskNumber = jComboBoxDisk.getSelectedIndex();
	if (iDiskNumber == 0) {
	    m_lFormatHandle = hCNetSDK.NET_DVR_FormatDisk(m_lUserId,
		    new NativeLong(0xff));// 0xFF表示所有硬盘
	    if (m_lFormatHandle.intValue() < 0) {
		int err = hCNetSDK.NET_DVR_GetLastError();
		if (err == HCNetSDK.NET_DVR_DISK_FORMATING) {
		    JOptionPane.showMessageDialog(this, "硬盘正在格式化,不能启动操作");
		} else {
		    JOptionPane.showMessageDialog(this, "格式化失败");
		}
	    } else {
		System.out.println("正在格式化!");
		do {
		    hCNetSDK.NET_DVR_GetFormatProgress(m_lFormatHandle,
			    pCurrentFormatDisk, pCurrentDiskPos, pFormatStatic);
		    System.out.println("正在格式化硬盘:"
			    + pCurrentFormatDisk.getValue().intValue());// 正在格式化的硬盘号
		    jProgressBar
			    .setValue(pCurrentDiskPos.getValue().intValue());
		    try {// 等待500ms
			TimeUnit.MILLISECONDS.sleep(500);
		    } catch (InterruptedException e) {
		    }
		} while (pFormatStatic.getValue().intValue() == 0);// 正在格式化
		System.out.println("static"
			+ pFormatStatic.getValue().intValue());
		if (pFormatStatic.getValue().intValue() == 2) {
		    JOptionPane.showMessageDialog(this, "格式化出错!");
		    return;
		}
		if (pFormatStatic.getValue().intValue() == 3) {
		    JOptionPane.showMessageDialog(this, "网络异常!");
		    return;
		}
		if (pFormatStatic.getValue().intValue() == 3) {
		    JOptionPane.showMessageDialog(this, "格式化完成!");
		    return;
		}
	    }
	} else {// 格式化单个硬盘
	    m_lFormatHandle = hCNetSDK.NET_DVR_FormatDisk(m_lUserId,
		    new NativeLong(
			    m_struHDCfg.struHDInfo[iDiskNumber - 1].dwHDNo));
	    if (m_lFormatHandle.intValue() < 0) {
		int err = hCNetSDK.NET_DVR_GetLastError();
		if (err == HCNetSDK.NET_DVR_DISK_FORMATING) {
		    JOptionPane.showMessageDialog(this, "硬盘正在格式化,不能启动操作");
		} else {
		    System.out.println(err);
		    JOptionPane.showMessageDialog(this, "格式化失败");
		}
	    } else {
		System.out.println("正在格式化!");
		do {
		    hCNetSDK.NET_DVR_GetFormatProgress(m_lFormatHandle,
			    pCurrentFormatDisk, pCurrentDiskPos, pFormatStatic);
		    jProgressBar
			    .setValue(pCurrentDiskPos.getValue().intValue());
		    try {// 等待500ms
			TimeUnit.MILLISECONDS.sleep(500);
		    } catch (InterruptedException e) {
		    }
		} while (pFormatStatic.getValue().intValue() == 0);// 正在格式化

		if (pFormatStatic.getValue().intValue() == 2) {
		    JOptionPane.showMessageDialog(this, "格式化出错!");
		    return;
		}
		if (pFormatStatic.getValue().intValue() == 3) {
		    JOptionPane.showMessageDialog(this, "网络异常!");
		    return;
		}
		if (pFormatStatic.getValue().intValue() == 3) {
		    JOptionPane.showMessageDialog(this, "格式化完成!");
		    return;
		}
	    }
	}

    }// GEN-LAST:event_jButtonFormatActionPerformed

    /*************************************************
     * 函数: "退出" 按钮响应函数 函数描述: 销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExitActionPerformed
	this.dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private JButton jButtonExit;
    private JButton jButtonFormat;
    private JComboBox<String> jComboBoxDisk;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JProgressBar jProgressBar;
    // End of variables declaration//GEN-END:variables
}
