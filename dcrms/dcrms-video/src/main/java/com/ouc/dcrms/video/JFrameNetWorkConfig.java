package com.ouc.dcrms.video;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;

/*****************************************************************************
 * 类 ： JFrameNetWorkConfig 
 * 类描述 ：网络参数配置
 ****************************************************************************/
class JFrameNetWorkConfig extends JFrame {
    private static final long serialVersionUID = 2142842580991347195L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_NETCFG_V30 m_strNetConfig;// 网络参数
    private NativeLong m_lUserID;// 用户句柄

    /*************************************************
     * 函数: JFrameNetWorkConfig 
     * 函数描述: 构造函数 Creates new form JFrameNetWorkConfig
     *************************************************/
    public JFrameNetWorkConfig(NativeLong lUserID) {
	m_lUserID = lUserID;// 保存主窗口传来的用户ID
	initComponents();
	initDialog();
    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jLabel1 = new JLabel();
	jLabel2 = new JLabel();
	jLabel3 = new JLabel();
	jLabel4 = new JLabel();
	jLabel5 = new JLabel();
	jLabel6 = new JLabel();
	jLabel7 = new JLabel();
	jLabel8 = new JLabel();
	jLabel9 = new JLabel();
	jLabel10 = new JLabel();
	jLabel11 = new JLabel();
	jLabel12 = new JLabel();
	jLabel13 = new JLabel();
	jLabel14 = new JLabel();
	jLabel15 = new JLabel();
	jComboBoxAdapter = new JComboBox<Object>();
	jRadioButtonGetIPAddress = new JRadioButton();
	jTextFieldIPAddress = new JTextField();
	jTextFieldSubMask = new JTextField();
	jTextFieldGatewayAddress = new JTextField();
	jTextFieldPreDNS = new JTextField();
	jTextFieldAltDNS = new JTextField();
	jTextFieldPhyAddress = new JTextField();
	jTextFieldPrivateDomain = new JTextField();
	jTextFieldComPort = new JTextField();
	jTextFieldHTTPPort = new JTextField();
	jTextField1MultiAddress = new JTextField();
	jTextField1AlarmAddress = new JTextField();
	jTextField1AlarmPort = new JTextField();
	jTextField1MTU = new JTextField();
	jButtonSetup = new JButton();
	jButtonExit = new JButton();

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	setTitle("网络参数配置");
	getContentPane().setLayout(null);

	jLabel1.setText("网卡类型");
	jLabel1.setAutoscrolls(true);
	jLabel1.setFocusTraversalPolicyProvider(true);
	getContentPane().add(jLabel1);
	jLabel1.setBounds(30, 40, 90, 15);

	jLabel2.setText("自动获得IP");
	getContentPane().add(jLabel2);
	jLabel2.setBounds(30, 70, 90, 15);

	jLabel3.setText("IP地址");
	getContentPane().add(jLabel3);
	jLabel3.setBounds(30, 100, 90, 15);

	jLabel4.setText("IP地址掩码");
	getContentPane().add(jLabel4);
	jLabel4.setBounds(30, 130, 90, 15);

	jLabel5.setText("网关地址");
	getContentPane().add(jLabel5);
	jLabel5.setBounds(30, 160, 90, 15);

	jLabel6.setText("首选DNS地址");
	getContentPane().add(jLabel6);
	jLabel6.setBounds(30, 190, 100, 15);

	jLabel7.setText("备选DNS地址");
	getContentPane().add(jLabel7);
	jLabel7.setBounds(30, 220, 100, 15);

	jLabel8.setText("物理地址");
	getContentPane().add(jLabel8);
	jLabel8.setBounds(30, 250, 90, 15);

	jLabel9.setText("私有域名地址");
	jLabel9.setAutoscrolls(true);
	jLabel9.setFocusTraversalPolicyProvider(true);
	getContentPane().add(jLabel9);
	jLabel9.setBounds(270, 40, 100, 15);

	jLabel10.setText("设备通讯端口号");
	jLabel10.setAutoscrolls(true);
	jLabel10.setFocusTraversalPolicyProvider(true);
	getContentPane().add(jLabel10);
	jLabel10.setBounds(270, 70, 110, 15);

	jLabel11.setText("HTTP端口号");
	jLabel11.setAutoscrolls(true);
	jLabel11.setFocusTraversalPolicyProvider(true);
	getContentPane().add(jLabel11);
	jLabel11.setBounds(270, 100, 100, 15);

	jLabel12.setText("多播组地址");
	getContentPane().add(jLabel12);
	jLabel12.setBounds(270, 130, 100, 15);

	jLabel13.setText("告警管理主机地址");
	getContentPane().add(jLabel13);
	jLabel13.setBounds(270, 160, 110, 15);

	jLabel14.setText("告警管理主机端口");
	getContentPane().add(jLabel14);
	jLabel14.setBounds(270, 190, 120, 15);

	jLabel15.setText("MTU");
	getContentPane().add(jLabel15);
	jLabel15.setBounds(270, 220, 90, 15);

	jComboBoxAdapter.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "10MBase-T", "10MBase-T全双工", "100MBase-TX",
			"4-100M全双工", "10M/100M自适应" }));
	jComboBoxAdapter.setAutoscrolls(true);
	jComboBoxAdapter.setBorder(null);
	jComboBoxAdapter.setFocusTraversalPolicyProvider(true);
	getContentPane().add(jComboBoxAdapter);
	jComboBoxAdapter.setBounds(120, 40, 140, 19);
	getContentPane().add(jRadioButtonGetIPAddress);
	jRadioButtonGetIPAddress.setBounds(140, 70, 21, 21);
	getContentPane().add(jTextFieldIPAddress);
	jTextFieldIPAddress.setBounds(140, 100, 110, 21);
	getContentPane().add(jTextFieldSubMask);
	jTextFieldSubMask.setBounds(140, 130, 110, 21);
	getContentPane().add(jTextFieldGatewayAddress);
	jTextFieldGatewayAddress.setBounds(140, 160, 110, 21);
	getContentPane().add(jTextFieldPreDNS);
	jTextFieldPreDNS.setBounds(140, 190, 110, 21);
	getContentPane().add(jTextFieldAltDNS);
	jTextFieldAltDNS.setBounds(140, 220, 110, 21);
	getContentPane().add(jTextFieldPhyAddress);
	jTextFieldPhyAddress.setBounds(140, 250, 110, 21);
	getContentPane().add(jTextFieldPrivateDomain);
	jTextFieldPrivateDomain.setBounds(390, 40, 110, 21);
	getContentPane().add(jTextFieldComPort);
	jTextFieldComPort.setBounds(390, 70, 110, 21);
	getContentPane().add(jTextFieldHTTPPort);
	jTextFieldHTTPPort.setBounds(390, 100, 110, 21);
	getContentPane().add(jTextField1MultiAddress);
	jTextField1MultiAddress.setBounds(390, 130, 110, 21);
	getContentPane().add(jTextField1AlarmAddress);
	jTextField1AlarmAddress.setBounds(390, 160, 110, 21);
	getContentPane().add(jTextField1AlarmPort);
	jTextField1AlarmPort.setBounds(390, 190, 110, 21);
	getContentPane().add(jTextField1MTU);
	jTextField1MTU.setBounds(390, 220, 110, 20);

	jButtonSetup.setText("设置");
	jButtonSetup.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSetupActionPerformed(evt);
	    }
	});
	getContentPane().add(jButtonSetup);
	jButtonSetup.setBounds(30, 300, 70, 23);

	jButtonExit.setText(" 退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});
	getContentPane().add(jButtonExit);
	jButtonExit.setBounds(120, 300, 80, 23);

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "设置" 按钮单击相应函数 函数描述: 设置网络参数
     *************************************************/
    private void jButtonSetupActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSetupActionPerformed
	m_strNetConfig.struEtherNet[0].struDVRIP.sIpV4 = jTextFieldIPAddress
		.getText().getBytes();// IP地址
	m_strNetConfig.struEtherNet[0].struDVRIPMask.sIpV4 = jTextFieldSubMask
		.getText().getBytes();// 子网掩码
	m_strNetConfig.struGatewayIpAddr.sIpV4 = jTextFieldGatewayAddress
		.getText().getBytes();// 网关
	m_strNetConfig.struAlarmHostIpAddr.sIpV4 = jTextField1AlarmAddress
		.getText().getBytes();// 报警管理主机
	m_strNetConfig.struDnsServer1IpAddr.sIpV4 = jTextFieldPreDNS.getText()
		.getBytes();// 首选DNS服务器
	m_strNetConfig.struDnsServer2IpAddr.sIpV4 = jTextFieldAltDNS.getText()
		.getBytes();// 备选DNS服务器
	m_strNetConfig.struMulticastIpAddr.sIpV4 = jTextField1MultiAddress
		.getText().getBytes(); // 多播组地址

	m_strNetConfig.wAlarmHostIpPort = (short) Integer
		.parseInt(jTextField1AlarmPort.getText());// 报警管理主机端口
	m_strNetConfig.wHttpPortNo = (short) Integer
		.parseInt(jTextFieldHTTPPort.getText());// Http端口
	m_strNetConfig.struEtherNet[0].wMTU = (short) Integer
		.parseInt(jTextField1MTU.getText()); // MTU
	m_strNetConfig.struEtherNet[0].wDVRPort = (short) Integer
		.parseInt(jTextFieldComPort.getText());// 设备通讯端口号
	if (jRadioButtonGetIPAddress.isSelected()) {
	    m_strNetConfig.byUseDhcp = 1;
	} else {
	    m_strNetConfig.byUseDhcp = 0;
	}
	m_strNetConfig.struEtherNet[0].dwNetInterface = jComboBoxAdapter
		.getSelectedIndex() + 1;
	m_strNetConfig.write();
	Pointer lpNetConfig = m_strNetConfig.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_SET_NETCFG_V30, new NativeLong(0),
		lpNetConfig, m_strNetConfig.size());
	m_strNetConfig.read();
	if (setDVRConfigSuc == false) {
	    JOptionPane.showMessageDialog(this, "设置网络参数失败");
	    System.out.print("" + hCNetSDK.NET_DVR_GetLastError());
	} else {
	    JOptionPane.showMessageDialog(this, "设置网络参数成功");
	}
    }// GEN-LAST:event_jButtonSetupActionPerformed

    /*************************************************
     * 函数: initDialog 函数描述: 初始化对话框,显示网络参数
     *************************************************/
    private void initDialog() {
	m_strNetConfig = new HCNetSDK.NET_DVR_NETCFG_V30();
	IntByReference ibrBytesReturned = new IntByReference(0);
	m_strNetConfig.write();
	Pointer lpNetConfig = m_strNetConfig.getPointer();
	boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_GET_NETCFG_V30, new NativeLong(0),
		lpNetConfig, m_strNetConfig.size(), ibrBytesReturned);
	m_strNetConfig.read();
	if (getDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "获取网络参数失败");
	    return;
	}

	// 信息显示
	jTextFieldIPAddress.setText(new String(
		m_strNetConfig.struEtherNet[0].struDVRIP.sIpV4));// IP地址
	jTextFieldSubMask.setText(new String(
		m_strNetConfig.struEtherNet[0].struDVRIPMask.sIpV4));// 子网掩码
	jTextFieldGatewayAddress.setText(new String(
		m_strNetConfig.struGatewayIpAddr.sIpV4));// 网关
	jTextField1AlarmAddress.setText(new String(
		m_strNetConfig.struAlarmHostIpAddr.sIpV4));// 报警管理主机
	jTextFieldPreDNS.setText(new String(
		m_strNetConfig.struDnsServer1IpAddr.sIpV4));// 首选DNS服务器
	jTextFieldAltDNS.setText(new String(
		m_strNetConfig.struDnsServer2IpAddr.sIpV4));// 备选DNS服务器
	jTextField1MultiAddress.setText(new String(
		m_strNetConfig.struMulticastIpAddr.sIpV4));// 多播组地址
	int iTemp = m_strNetConfig.wAlarmHostIpPort; // 报警管理主机端口
	String MyString = "" + iTemp;
	jTextField1AlarmPort.setText(MyString);
	iTemp = m_strNetConfig.wHttpPortNo; // HTTP端口
	MyString = "" + iTemp;
	jTextFieldHTTPPort.setText(MyString);
	iTemp = m_strNetConfig.struEtherNet[0].wMTU; // MTU最大传输单元
	MyString = "" + iTemp;
	jTextField1MTU.setText(MyString);
	iTemp = m_strNetConfig.struEtherNet[0].wDVRPort; // MTU最大传输单元
	MyString = "" + iTemp;
	jTextFieldComPort.setText(MyString); // 通信端口
	String sPhyAddress = "";
	for (int i = 0; i < 6; i++) {
	    iTemp = m_strNetConfig.struEtherNet[0].byMACAddr[i];
	    if (iTemp < 0) {
		iTemp += 256;
	    }
	    if (i != 5) {
		sPhyAddress = sPhyAddress + Integer.toHexString(iTemp) + ".";
	    } else {
		sPhyAddress = sPhyAddress + Integer.toHexString(iTemp);
	    }
	}
	jTextFieldPhyAddress.setText(sPhyAddress);// 物理地址
	jTextFieldPhyAddress.setEnabled(false);
	boolean b = m_strNetConfig.byUseDhcp > 0;
	jRadioButtonGetIPAddress.setSelected(b);// 是否自动获取IP地址
	jRadioButtonGetIPAddress.setEnabled(false);
	jComboBoxAdapter
		.setSelectedIndex(m_strNetConfig.struEtherNet[0].dwNetInterface - 1);
    }

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExitActionPerformed
	this.dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private JButton jButtonExit;
    private JButton jButtonSetup;
    JComboBox<Object> jComboBoxAdapter;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    JRadioButton jRadioButtonGetIPAddress;
    JTextField jTextField1AlarmAddress;
    JTextField jTextField1AlarmPort;
    JTextField jTextField1MTU;
    JTextField jTextField1MultiAddress;
    JTextField jTextFieldAltDNS;
    JTextField jTextFieldComPort;
    JTextField jTextFieldGatewayAddress;
    JTextField jTextFieldHTTPPort;
    JTextField jTextFieldIPAddress;
    JTextField jTextFieldPhyAddress;
    JTextField jTextFieldPreDNS;
    JTextField jTextFieldPrivateDomain;
    JTextField jTextFieldSubMask;
    // End of variables declaration//GEN-END:variables
}
