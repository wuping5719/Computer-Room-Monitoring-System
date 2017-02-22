package com.ouc.dcrms.video;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ：JDialogAlarmCfg 
 * 类描述 ：报警输入,报警输出,异常参数配置
 ****************************************************************************/
public class JDialogAlarmCfg extends JDialog {
    private static final long serialVersionUID = 1L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserID;// 用户ID
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
    private CheckListItem m_exceptionAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];// 异常配置报警输出通道checkbox对应值
    private CheckListItem m_traggerAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];// 报警输入配置中报警输出通道checkbox对应值
    private CheckListItem m_traggerRecord[] = new CheckListItem[HCNetSDK.MAX_CHANNUM_V30];// 报警输入配置中触发录像通道checkbox对应值
    private HCNetSDK.NET_DVR_EXCEPTION_V30 m_struExceptionInfo;// 异常信息结构体
    private HCNetSDK.NET_DVR_ALARMINCFG_V30 m_struAlarmInCfg;// 报警输入结构体
    private HCNetSDK.NET_DVR_ALARMOUTCFG_V30 m_struAlarmOutCfg;// 报警输出结构体
    private boolean bInitialed; // Dialog是否已初始化,主要用在控制组合框状态改变响应函数的调用,只在初始化后调用

    /*************************************************
     * 函数: 构造函数 函数描述: Creates new form JDialogAlarmCfg
     *************************************************/
    public JDialogAlarmCfg(Frame parent, boolean modal, NativeLong lUserID,
	    HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo) {
	super(parent, modal);

	// 赋值
	m_lUserID = lUserID;
	m_strDeviceInfo = strDeviceInfo;
	bInitialed = false;
	initComponents();

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_exceptionAlarmOut[i] = new CheckListItem(false, null);
	    m_traggerAlarmOut[i] = new CheckListItem(false, null);
	}
	for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++) {
	    m_traggerRecord[i] = new CheckListItem(false, null);
	}

	// 异常中的报警输出通道列表初始化
	jListExceptionTraggerAlarmOut
		.setCellRenderer(new CheckListItemRenderer());
	DefaultListModel<CheckListItem> listModelException = new DefaultListModel<CheckListItem>();
	jListExceptionTraggerAlarmOut.setModel(listModelException);
	jListExceptionTraggerAlarmOut
		.addMouseListener(new CheckListMouseListener());
	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_exceptionAlarmOut[i] = new CheckListItem(false, "AlarmOut"
		    + (i + 1));
	    listModelException.addElement(m_exceptionAlarmOut[i]); // 为异常List增加报警输出
	}
	showExceptionCfg();// 调用接口获取异常参数并显示

	// 报警输入里的触发报警输出通道list
	jListTraggerAlarmOut.setCellRenderer(new CheckListItemRenderer());
	DefaultListModel<CheckListItem> listModelTraggerAlarmOut = new DefaultListModel<CheckListItem>();
	jListTraggerAlarmOut.setModel(listModelTraggerAlarmOut);
	jListTraggerAlarmOut.addMouseListener(new CheckListMouseListener());
	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_traggerAlarmOut[i] = new CheckListItem(false, "AlarmOut"
		    + (i + 1));
	    listModelTraggerAlarmOut.addElement(m_traggerAlarmOut[i]); // 为报警输入触发报警输出List增加报警输出
	}
	// 报警输入里的触发录像通道list
	jListTraggerRecord.setCellRenderer(new CheckListItemRenderer());
	DefaultListModel<CheckListItem> listModelTraggerRecord = new DefaultListModel<CheckListItem>();
	jListTraggerRecord.setModel(listModelTraggerRecord);
	jListTraggerRecord.addMouseListener(new CheckListMouseListener());
	for (int i = 0; i < m_strDeviceInfo.byChanNum; i++) {
	    m_traggerRecord[i] = new CheckListItem(false, "Camara" + (i + 1));
	    listModelTraggerRecord.addElement(m_traggerRecord[i]); // 为报警输入触发录像通道List增加录像通道
	}

	// 报警输入 通道号 combobox
	for (int i = 0; i < m_strDeviceInfo.byAlarmInPortNum; i++) {
	    jComboBoxAlarmInChannel.addItem("AlarmIn" + (i + 1));
	}

	// 报警输入 PTZ通道号 combobox
	for (int i = 0; i < m_strDeviceInfo.byChanNum; i++) {
	    jComboBoxPTZChannel.addItem("通道"
		    + (i + m_strDeviceInfo.byStartChan));
	}
	for (int i = 0; i < HCNetSDK.MAX_CRUISE_V30; i++) {
	    jComboBoxCruise.addItem(i + 1);
	}
	for (int i = 0; i < HCNetSDK.MAX_TRACK_V30; i++) {
	    jComboBoxTrack.addItem(i + 1);
	}
	for (int i = 0; i < HCNetSDK.MAX_PRESET_V30; i++) {
	    jComboBoxPreset.addItem(i + 1);
	}
	jComboBoxPTZChannel
		.addActionListener(new java.awt.event.ActionListener() {

		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxPTZChannelActionPerformed(evt);
		    }
		});

	// 报警输出通道
	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    jComboBoxAlarmOutChannel.addItem("Alarm" + (i + 1));
	}

	bInitialed = true;
	jComboBoxAlarmInChannel.setSelectedIndex(0);// 状态改变响应函数中获取并显示报警输入参数
	jComboBoxAlarmOutChannel.setSelectedIndex(0);// 状态改变响应函数中获取并显示报警输出参数
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	buttonGroup1 = new ButtonGroup();
	jTabbedPaneAlarmCfg = new JTabbedPane();
	jPanelException = new JPanel();
	jLabel1 = new JLabel();
	jComboBoxExceptionType = new JComboBox<Object>();
	jPanelExceptionTragger = new JPanel();
	jCheckBoxMoniter = new JCheckBox();
	jCheckBoxAudio = new JCheckBox();
	jCheckBoxCenter = new JCheckBox();
	jCheckBoxTraggerAlarmOut = new JCheckBox();
	jCheckBoxEMail = new JCheckBox();
	jScrollPane3 = new JScrollPane();
	jListExceptionTraggerAlarmOut = new JList<CheckListItem>();
	jButtonComfirmException = new JButton();
	jButtonSetupException = new JButton();
	jSeparator1 = new JSeparator();
	jButtonExit1 = new JButton();
	jPanelAlarmIn = new JPanel();
	jLabel2 = new JLabel();
	jLabel3 = new JLabel();
	jLabel4 = new JLabel();
	jLabel5 = new JLabel();
	jLabel6 = new JLabel();
	jComboBoxAlarmInChannel = new JComboBox<Object>();
	jTextFieldDeviceAddress = new JTextField();
	jTextFieldAlarmInChannel = new JTextField();
	jTextFieldAlarmInName = new JTextField();
	jComboBoxAlarmType = new JComboBox<Object>();
	jPanel1 = new JPanel();
	jPanel3 = new JPanel();
	jCheckBoxMoniterAlarm = new JCheckBox();
	jCheckBoxAudioAlarm = new JCheckBox();
	jCheckBoxCenterAlarm = new JCheckBox();
	jCheckBoxEMailAlarm = new JCheckBox();
	jCheckBoxTraggerAlarmOutAlarm = new JCheckBox();
	jScrollPane2 = new JScrollPane();
	jListTraggerAlarmOut = new JList<CheckListItem>();
	jPanel4 = new JPanel();
	jScrollPane1 = new JScrollPane();
	jListTraggerRecord = new JList<CheckListItem>();
	jPanel2 = new JPanel();
	jLabel7 = new JLabel();
	jComboBoxDate = new JComboBox<Object>();
	jLabel8 = new JLabel();
	jLabel9 = new JLabel();
	jLabel10 = new JLabel();
	jLabel11 = new JLabel();
	jLabel12 = new JLabel();
	jLabel13 = new JLabel();
	jLabel14 = new JLabel();
	jLabel16 = new JLabel();
	jTextFieldInEndMin1 = new JTextField();
	jTextFieldInBeginHour1 = new JTextField();
	jTextFieldInBeginMin1 = new JTextField();
	jTextFieldInEndHour1 = new JTextField();
	jLabel15 = new JLabel();
	jLabel17 = new JLabel();
	jLabel18 = new JLabel();
	jLabel19 = new JLabel();
	jPanel5 = new JPanel();
	jLabel20 = new JLabel();
	jComboBox2 = new JComboBox<Object>();
	jLabel21 = new JLabel();
	jLabel22 = new JLabel();
	jLabel23 = new JLabel();
	jLabel24 = new JLabel();
	jLabel25 = new JLabel();
	jLabel26 = new JLabel();
	jLabel27 = new JLabel();
	jLabel28 = new JLabel();
	jTextField5 = new JTextField();
	jTextField6 = new JTextField();
	jTextField7 = new JTextField();
	jTextField8 = new JTextField();
	jLabel29 = new JLabel();
	jLabel30 = new JLabel();
	jLabel31 = new JLabel();
	jLabel32 = new JLabel();
	jLabel33 = new JLabel();
	jTextFieldInEndMin2 = new JTextField();
	jLabel34 = new JLabel();
	jTextFieldInEndHour2 = new JTextField();
	jLabel35 = new JLabel();
	jTextFieldInBeginMin2 = new JTextField();
	jLabel36 = new JLabel();
	jTextFieldInBeginHour2 = new JTextField();
	jLabel37 = new JLabel();
	jTextFieldInEndMin3 = new JTextField();
	jLabel38 = new JLabel();
	jTextFieldInEndHour3 = new JTextField();
	jLabel39 = new JLabel();
	jTextFieldInBeginMin3 = new JTextField();
	jLabel40 = new JLabel();
	jTextFieldInBeginHour3 = new JTextField();
	jLabel41 = new JLabel();
	jTextFieldInEndMin4 = new JTextField();
	jLabel42 = new JLabel();
	jTextFieldInEndHour4 = new JTextField();
	jLabel43 = new JLabel();
	jTextFieldInBeginMin4 = new JTextField();
	jLabel44 = new JLabel();
	jTextFieldInBeginHour4 = new JTextField();
	jLabel45 = new JLabel();
	jTextFieldInEndMin5 = new JTextField();
	jLabel46 = new JLabel();
	jTextFieldInEndHour5 = new JTextField();
	jLabel47 = new JLabel();
	jTextFieldInBeginMin5 = new JTextField();
	jLabel48 = new JLabel();
	jTextFieldInBeginHour5 = new JTextField();
	jLabel49 = new JLabel();
	jTextFieldInEndMin6 = new JTextField();
	jLabel50 = new JLabel();
	jTextFieldInEndHour6 = new JTextField();
	jLabel51 = new JLabel();
	jTextFieldInBeginMin6 = new JTextField();
	jLabel52 = new JLabel();
	jTextFieldInBeginHour6 = new JTextField();
	jLabel53 = new JLabel();
	jTextFieldInEndMin7 = new JTextField();
	jLabel54 = new JLabel();
	jTextFieldInEndHour7 = new JTextField();
	jLabel55 = new JLabel();
	jTextFieldInBeginMin7 = new JTextField();
	jLabel56 = new JLabel();
	jTextFieldInBeginHour7 = new JTextField();
	jLabel57 = new JLabel();
	jTextFieldInEndMin8 = new JTextField();
	jLabel58 = new JLabel();
	jTextFieldInEndHour8 = new JTextField();
	jLabel59 = new JLabel();
	jTextFieldInBeginMin8 = new JTextField();
	jLabel60 = new JLabel();
	jTextFieldInBeginHour8 = new JTextField();
	jButtonConfirmInTime = new JButton();
	jCheckBoxAlarmInHandle = new JCheckBox();
	jPanel6 = new JPanel();
	jLabel61 = new JLabel();
	jComboBoxPTZChannel = new JComboBox<Object>();
	jRadioButtonCruise = new JRadioButton();
	jComboBoxCruise = new JComboBox<Object>();
	jRadioButtonPreset = new JRadioButton();
	jComboBoxPreset = new JComboBox<Object>();
	jRadioButtonTrack = new JRadioButton();
	jComboBoxTrack = new JComboBox<Object>();
	jButtonPTZConfirm = new JButton();
	jButtonSetupAlarmIn = new JButton();
	jButtonExit3 = new JButton();
	jPanelAlarmOut = new JPanel();
	jLabel62 = new JLabel();
	jComboBoxAlarmOutChannel = new JComboBox<Object>();
	jLabel63 = new JLabel();
	jTextField1 = new JTextField();
	jLabel64 = new JLabel();
	jTextField2 = new JTextField();
	jLabel65 = new JLabel();
	jTextFieldAlarmOutName = new JTextField();
	jLabel66 = new JLabel();
	jTextFieldAlarmOutDelay = new JTextField();
	jPanel7 = new JPanel();
	jLabel67 = new JLabel();
	jTextFieldfHour1 = new JTextField();
	jLabel68 = new JLabel();
	jLabel69 = new JLabel();
	jTextFieldfMin1 = new JTextField();
	jLabel70 = new JLabel();
	jTextFieldsHour1 = new JTextField();
	jLabel71 = new JLabel();
	jTextFieldsMin1 = new JTextField();
	jLabel72 = new JLabel();
	jLabel73 = new JLabel();
	jTextFieldfHour2 = new JTextField();
	jLabel74 = new JLabel();
	jTextFieldfMin2 = new JTextField();
	jLabel75 = new JLabel();
	jLabel76 = new JLabel();
	jTextFieldsHour2 = new JTextField();
	jLabel77 = new JLabel();
	jTextFieldsMin2 = new JTextField();
	jLabel78 = new JLabel();
	jLabel79 = new JLabel();
	jTextFieldfHour3 = new JTextField();
	jLabel80 = new JLabel();
	jTextFieldfMin3 = new JTextField();
	jLabel81 = new JLabel();
	jLabel82 = new JLabel();
	jTextFieldsHour3 = new JTextField();
	jLabel83 = new JLabel();
	jTextFieldsMin3 = new JTextField();
	jLabel84 = new JLabel();
	jLabel85 = new JLabel();
	jTextFieldfHour4 = new JTextField();
	jLabel86 = new JLabel();
	jTextFieldfMin4 = new JTextField();
	jLabel87 = new JLabel();
	jLabel88 = new JLabel();
	jTextFieldsHour4 = new JTextField();
	jLabel89 = new JLabel();
	jTextFieldsMin4 = new JTextField();
	jLabel90 = new JLabel();
	jLabel91 = new JLabel();
	jTextFieldfHour5 = new JTextField();
	jLabel92 = new JLabel();
	jTextFieldfMin5 = new JTextField();
	jLabel93 = new JLabel();
	jLabel94 = new JLabel();
	jTextFieldsHour5 = new JTextField();
	jLabel95 = new JLabel();
	jTextFieldsMin5 = new JTextField();
	jLabel96 = new JLabel();
	jLabel97 = new JLabel();
	jTextFieldfHour6 = new JTextField();
	jLabel98 = new JLabel();
	jTextFieldfMin6 = new JTextField();
	jLabel99 = new JLabel();
	jLabel100 = new JLabel();
	jTextFieldsHour6 = new JTextField();
	jLabel101 = new JLabel();
	jTextFieldsMin6 = new JTextField();
	jLabel102 = new JLabel();
	jLabel103 = new JLabel();
	jTextFieldfHour7 = new JTextField();
	jLabel104 = new JLabel();
	jTextFieldfMin7 = new JTextField();
	jLabel105 = new JLabel();
	jLabel106 = new JLabel();
	jTextFieldsHour7 = new JTextField();
	jLabel107 = new JLabel();
	jTextFieldsMin7 = new JTextField();
	jLabel108 = new JLabel();
	jLabel109 = new JLabel();
	jTextFieldfHour8 = new JTextField();
	jLabel110 = new JLabel();
	jTextFieldfMin8 = new JTextField();
	jLabel111 = new JLabel();
	jLabel112 = new JLabel();
	jTextFieldsHour8 = new JTextField();
	jLabel113 = new JLabel();
	jTextFieldsMin8 = new JTextField();
	jLabel114 = new JLabel();
	jLabel115 = new JLabel();
	jComboBoxWeekDay = new JComboBox<Object>();
	jButtonConfirm = new JButton();
	jToggleButtonSetupAlarmOut = new JToggleButton();
	jButtonExit2 = new JButton();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("报警参数配置");

	jPanelException.setLayout(null);

	jLabel1.setText("异常类型");
	jPanelException.add(jLabel1);
	jLabel1.setBounds(30, 40, 60, 15);

	jComboBoxExceptionType.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "硬盘满", "硬盘出错", "网线断", "IP 地址冲突", "非法访问",
			"输入/输出视频制式不匹配", "视频信号异常" }));
	jComboBoxExceptionType
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxExceptionTypeActionPerformed(evt);
		    }
		});
	jPanelException.add(jComboBoxExceptionType);
	jComboBoxExceptionType.setBounds(100, 30, 180, 21);

	jPanelExceptionTragger.setBorder(BorderFactory
		.createTitledBorder("报警处理方式"));

	jCheckBoxMoniter.setText("监视器上警告");

	jCheckBoxAudio.setText("声音警告");

	jCheckBoxCenter.setText("上传中心");

	jCheckBoxTraggerAlarmOut.setText("触发报警输出");

	jCheckBoxEMail.setText("发送邮件");

	jScrollPane3.setViewportView(jListExceptionTraggerAlarmOut);

	GroupLayout jPanelExceptionTraggerLayout = new GroupLayout(
		jPanelExceptionTragger);
	jPanelExceptionTragger.setLayout(jPanelExceptionTraggerLayout);
	jPanelExceptionTraggerLayout
		.setHorizontalGroup(jPanelExceptionTraggerLayout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanelExceptionTraggerLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanelExceptionTraggerLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxMoniter,
								GroupLayout.PREFERRED_SIZE,
								107,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								jCheckBoxEMail)
							.addComponent(
								jCheckBoxAudio)
							.addComponent(
								jCheckBoxCenter))
					.addGap(34, 34, 34)
					.addGroup(
						jPanelExceptionTraggerLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxTraggerAlarmOut)
							.addComponent(
								jScrollPane3,
								GroupLayout.PREFERRED_SIZE,
								218,
								GroupLayout.PREFERRED_SIZE))
					.addGap(19, 19, 19)));
	jPanelExceptionTraggerLayout
		.setVerticalGroup(jPanelExceptionTraggerLayout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanelExceptionTraggerLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanelExceptionTraggerLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								jCheckBoxMoniter)
							.addComponent(
								jCheckBoxTraggerAlarmOut))
					.addGroup(
						jPanelExceptionTraggerLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanelExceptionTraggerLayout
									.createSequentialGroup()
									.addGap(29,
										29,
										29)
									.addComponent(
										jCheckBoxAudio)
									.addGap(18,
										18,
										18)
									.addComponent(
										jCheckBoxCenter)
									.addGap(26,
										26,
										26)
									.addComponent(
										jCheckBoxEMail)
									.addGap(60,
										60,
										60))
							.addGroup(
								jPanelExceptionTraggerLayout
									.createSequentialGroup()
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jScrollPane3,
										GroupLayout.DEFAULT_SIZE,
										266,
										Short.MAX_VALUE)
									.addContainerGap()))));

	jPanelException.add(jPanelExceptionTragger);
	jPanelExceptionTragger.setBounds(20, 90, 400, 340);

	jButtonComfirmException.setText("确认");
	jButtonComfirmException
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonComfirmExceptionActionPerformed(evt);
		    }
		});
	jPanelException.add(jButtonComfirmException);
	jButtonComfirmException.setBounds(40, 450, 70, 23);

	jButtonSetupException.setText("设置异常配置");
	jButtonSetupException
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonSetupExceptionActionPerformed(evt);
		    }
		});
	jPanelException.add(jButtonSetupException);
	jButtonSetupException.setBounds(20, 520, 120, 23);

	jSeparator1.setBorder(BorderFactory.createTitledBorder(""));
	jPanelException.add(jSeparator1);
	jSeparator1.setBounds(10, 10, 570, 490);

	jButtonExit1.setText("退出");
	jButtonExit1.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExit1ActionPerformed(evt);
	    }
	});
	jPanelException.add(jButtonExit1);
	jButtonExit1.setBounds(460, 520, 70, 23);

	jTabbedPaneAlarmCfg.addTab("异常配置信息", jPanelException);

	jLabel2.setText("报警输入");

	jLabel3.setText("IP设备地址");

	jLabel4.setText("IP输入通道");

	jLabel5.setText("报警输入名称");

	jLabel6.setText("报警器类型");

	jComboBoxAlarmInChannel
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxAlarmInChannelActionPerformed(evt);
		    }
		});

	jTextFieldAlarmInChannel.setText("           ");

	jTextFieldAlarmInName.setText("           ");

	jComboBoxAlarmType.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "常开", "常闭" }));

	jPanel1.setBorder(BorderFactory.createTitledBorder("报警输入处理"));

	jPanel3.setBorder(BorderFactory.createTitledBorder("报警处理方式"));

	jCheckBoxMoniterAlarm.setText("监视器上警告");

	jCheckBoxAudioAlarm.setText("声音警告");

	jCheckBoxCenterAlarm.setText("上传中心");

	jCheckBoxEMailAlarm.setText("发送邮件");

	jCheckBoxTraggerAlarmOutAlarm.setText("触发报警输出");

	jScrollPane2.setViewportView(jListTraggerAlarmOut);

	GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
	jPanel3.setLayout(jPanel3Layout);
	jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel3Layout
			.createSequentialGroup()
			.addContainerGap()
			.addGroup(
				jPanel3Layout
					.createParallelGroup(
						GroupLayout.Alignment.LEADING)
					.addComponent(jCheckBoxMoniterAlarm)
					.addComponent(jCheckBoxAudioAlarm)
					.addComponent(jCheckBoxCenterAlarm)
					.addComponent(jCheckBoxEMailAlarm))
			.addGap(33, 33, 33)
			.addGroup(
				jPanel3Layout
					.createParallelGroup(
						GroupLayout.Alignment.LEADING)
					.addComponent(
						jCheckBoxTraggerAlarmOutAlarm)
					.addComponent(jScrollPane2,
						GroupLayout.DEFAULT_SIZE, 180,
						Short.MAX_VALUE))
			.addContainerGap()));
	jPanel3Layout
		.setVerticalGroup(jPanel3Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel3Layout
					.createSequentialGroup()
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel3Layout
									.createSequentialGroup()
									.addContainerGap()
									.addComponent(
										jCheckBoxMoniterAlarm))
							.addComponent(
								jCheckBoxTraggerAlarmOutAlarm))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel3Layout
									.createSequentialGroup()
									.addComponent(
										jCheckBoxAudioAlarm)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jCheckBoxCenterAlarm)
									.addGap(4,
										4,
										4)
									.addComponent(
										jCheckBoxEMailAlarm))
							.addComponent(
								jScrollPane2,
								GroupLayout.PREFERRED_SIZE,
								85,
								GroupLayout.PREFERRED_SIZE))));

	jPanel4.setBorder(BorderFactory.createTitledBorder("触发录像通道"));

	jScrollPane1.setViewportView(jListTraggerRecord);

	GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
	jPanel4.setLayout(jPanel4Layout);
	jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
		GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE));
	jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
		GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE));

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel1Layout
			.createSequentialGroup()
			.addContainerGap()
			.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(
				LayoutStyle.ComponentPlacement.UNRELATED)
			.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));
	jPanel1Layout.setVerticalGroup(jPanel1Layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addComponent(jPanel4, 0, GroupLayout.DEFAULT_SIZE,
			Short.MAX_VALUE)
		.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE,
			GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

	jPanel2.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createTitledBorder("布防时间")));
	jPanel2.setLayout(null);

	jLabel7.setText("日期");
	jPanel2.add(jLabel7);
	jLabel7.setBounds(20, 20, 50, 15);

	jComboBoxDate.setModel(new DefaultComboBoxModel<Object>(new String[] {
		"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
	jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jComboBoxDateActionPerformed(evt);
	    }
	});
	jPanel2.add(jComboBoxDate);
	jComboBoxDate.setBounds(80, 20, 90, 21);

	jLabel8.setText("Time4");
	jPanel2.add(jLabel8);
	jLabel8.setBounds(20, 150, 42, 15);

	jLabel9.setText("Time3");
	jPanel2.add(jLabel9);
	jLabel9.setBounds(20, 120, 40, 15);

	jLabel10.setText("Time2");
	jPanel2.add(jLabel10);
	jLabel10.setBounds(20, 90, 40, 15);

	jLabel11.setText("Time5");
	jPanel2.add(jLabel11);
	jLabel11.setBounds(260, 60, 40, 15);

	jLabel12.setText("Time6");
	jPanel2.add(jLabel12);
	jLabel12.setBounds(260, 90, 40, 15);

	jLabel13.setText("Time7");
	jPanel2.add(jLabel13);
	jLabel13.setBounds(260, 120, 40, 15);

	jLabel14.setText("Time8");
	jPanel2.add(jLabel14);
	jLabel14.setBounds(260, 150, 40, 15);

	jLabel16.setText("Time1");
	jPanel2.add(jLabel16);
	jLabel16.setBounds(20, 60, 40, 15);
	jPanel2.add(jTextFieldInEndMin1);
	jTextFieldInEndMin1.setBounds(210, 60, 20, 21);
	jPanel2.add(jTextFieldInBeginHour1);
	jTextFieldInBeginHour1.setBounds(80, 60, 20, 21);
	jPanel2.add(jTextFieldInBeginMin1);
	jTextFieldInBeginMin1.setBounds(120, 60, 20, 21);
	jPanel2.add(jTextFieldInEndHour1);
	jTextFieldInEndHour1.setBounds(170, 60, 20, 21);

	jLabel15.setText("时");
	jPanel2.add(jLabel15);
	jLabel15.setBounds(190, 60, 20, 15);

	jLabel17.setText("分--");
	jPanel2.add(jLabel17);
	jLabel17.setBounds(140, 60, 30, 15);

	jLabel18.setText("时");
	jPanel2.add(jLabel18);
	jLabel18.setBounds(100, 60, 20, 15);

	jLabel19.setText("分");
	jPanel2.add(jLabel19);
	jLabel19.setBounds(230, 60, 20, 15);

	jPanel5.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createTitledBorder("布防时间")));
	jPanel5.setLayout(null);

	jLabel20.setText("日期");
	jPanel5.add(jLabel20);
	jLabel20.setBounds(20, 20, 24, 15);

	jComboBox2.setModel(new DefaultComboBoxModel<Object>(new String[] {
		"Item 1", "Item 2", "Item 3", "Item 4" }));
	jPanel5.add(jComboBox2);
	jComboBox2.setBounds(60, 20, 62, 21);

	jLabel21.setText("Time4");
	jPanel5.add(jLabel21);
	jLabel21.setBounds(20, 150, 42, 15);

	jLabel22.setText("Time3");
	jPanel5.add(jLabel22);
	jLabel22.setBounds(20, 120, 30, 15);

	jLabel23.setText("Time2");
	jPanel5.add(jLabel23);
	jLabel23.setBounds(20, 90, 30, 15);

	jLabel24.setText("Time5");
	jPanel5.add(jLabel24);
	jLabel24.setBounds(370, 60, 30, 15);

	jLabel25.setText("Time6");
	jPanel5.add(jLabel25);
	jLabel25.setBounds(350, 100, 30, 15);

	jLabel26.setText("Time7");
	jPanel5.add(jLabel26);
	jLabel26.setBounds(350, 130, 30, 15);

	jLabel27.setText("Time8");
	jPanel5.add(jLabel27);
	jLabel27.setBounds(360, 150, 30, 15);

	jLabel28.setText("Time1");
	jPanel5.add(jLabel28);
	jLabel28.setBounds(20, 60, 30, 15);

	jTextField5.setText("24");
	jPanel5.add(jTextField5);
	jTextField5.setBounds(190, 60, 20, 21);

	jTextField6.setText("12");
	jPanel5.add(jTextField6);
	jTextField6.setBounds(60, 60, 20, 21);

	jTextField7.setText("12");
	jPanel5.add(jTextField7);
	jTextField7.setBounds(100, 60, 20, 21);

	jTextField8.setText("12");
	jPanel5.add(jTextField8);
	jTextField8.setBounds(150, 60, 20, 21);

	jLabel29.setText("时");
	jPanel5.add(jLabel29);
	jLabel29.setBounds(170, 60, 20, 15);

	jLabel30.setText("分--");
	jPanel5.add(jLabel30);
	jLabel30.setBounds(120, 60, 30, 15);

	jLabel31.setText("时");
	jPanel5.add(jLabel31);
	jLabel31.setBounds(80, 60, 20, 15);

	jLabel32.setText("分");
	jPanel5.add(jLabel32);
	jLabel32.setBounds(210, 60, 20, 15);

	jPanel2.add(jPanel5);
	jPanel5.setBounds(0, 0, 0, 0);

	jLabel33.setText("分");
	jPanel2.add(jLabel33);
	jLabel33.setBounds(230, 90, 20, 15);
	jPanel2.add(jTextFieldInEndMin2);
	jTextFieldInEndMin2.setBounds(210, 90, 20, 21);

	jLabel34.setText("时");
	jPanel2.add(jLabel34);
	jLabel34.setBounds(190, 90, 20, 15);
	jPanel2.add(jTextFieldInEndHour2);
	jTextFieldInEndHour2.setBounds(170, 90, 20, 21);

	jLabel35.setText("分--");
	jPanel2.add(jLabel35);
	jLabel35.setBounds(140, 90, 30, 15);
	jPanel2.add(jTextFieldInBeginMin2);
	jTextFieldInBeginMin2.setBounds(120, 90, 20, 21);

	jLabel36.setText("时");
	jPanel2.add(jLabel36);
	jLabel36.setBounds(100, 90, 20, 15);
	jPanel2.add(jTextFieldInBeginHour2);
	jTextFieldInBeginHour2.setBounds(80, 90, 20, 21);

	jLabel37.setText("分");
	jPanel2.add(jLabel37);
	jLabel37.setBounds(230, 120, 20, 15);
	jPanel2.add(jTextFieldInEndMin3);
	jTextFieldInEndMin3.setBounds(210, 120, 20, 21);

	jLabel38.setText("时");
	jPanel2.add(jLabel38);
	jLabel38.setBounds(190, 120, 20, 15);
	jPanel2.add(jTextFieldInEndHour3);
	jTextFieldInEndHour3.setBounds(170, 120, 20, 21);

	jLabel39.setText("分--");
	jPanel2.add(jLabel39);
	jLabel39.setBounds(140, 120, 30, 15);
	jPanel2.add(jTextFieldInBeginMin3);
	jTextFieldInBeginMin3.setBounds(120, 120, 20, 21);

	jLabel40.setText("时");
	jPanel2.add(jLabel40);
	jLabel40.setBounds(100, 120, 20, 15);
	jPanel2.add(jTextFieldInBeginHour3);
	jTextFieldInBeginHour3.setBounds(80, 120, 20, 21);

	jLabel41.setText("分");
	jPanel2.add(jLabel41);
	jLabel41.setBounds(230, 150, 20, 15);
	jPanel2.add(jTextFieldInEndMin4);
	jTextFieldInEndMin4.setBounds(210, 150, 20, 21);

	jLabel42.setText("时");
	jPanel2.add(jLabel42);
	jLabel42.setBounds(190, 150, 20, 15);
	jPanel2.add(jTextFieldInEndHour4);
	jTextFieldInEndHour4.setBounds(170, 150, 20, 21);

	jLabel43.setText("分--");
	jPanel2.add(jLabel43);
	jLabel43.setBounds(140, 150, 30, 15);
	jPanel2.add(jTextFieldInBeginMin4);
	jTextFieldInBeginMin4.setBounds(120, 150, 20, 21);

	jLabel44.setText("时");
	jPanel2.add(jLabel44);
	jLabel44.setBounds(100, 150, 20, 15);
	jPanel2.add(jTextFieldInBeginHour4);
	jTextFieldInBeginHour4.setBounds(80, 150, 20, 21);

	jLabel45.setText("分");
	jPanel2.add(jLabel45);
	jLabel45.setBounds(460, 60, 20, 15);
	jPanel2.add(jTextFieldInEndMin5);
	jTextFieldInEndMin5.setBounds(440, 60, 20, 21);

	jLabel46.setText("时");
	jPanel2.add(jLabel46);
	jLabel46.setBounds(420, 60, 20, 15);
	jPanel2.add(jTextFieldInEndHour5);
	jTextFieldInEndHour5.setBounds(400, 60, 20, 21);

	jLabel47.setText("分--");
	jPanel2.add(jLabel47);
	jLabel47.setBounds(370, 60, 30, 15);
	jPanel2.add(jTextFieldInBeginMin5);
	jTextFieldInBeginMin5.setBounds(350, 60, 20, 21);

	jLabel48.setText("时");
	jPanel2.add(jLabel48);
	jLabel48.setBounds(330, 60, 20, 15);
	jPanel2.add(jTextFieldInBeginHour5);
	jTextFieldInBeginHour5.setBounds(310, 60, 20, 21);

	jLabel49.setText("分");
	jPanel2.add(jLabel49);
	jLabel49.setBounds(460, 90, 20, 15);
	jPanel2.add(jTextFieldInEndMin6);
	jTextFieldInEndMin6.setBounds(440, 90, 20, 21);

	jLabel50.setText("时");
	jPanel2.add(jLabel50);
	jLabel50.setBounds(420, 90, 20, 15);
	jPanel2.add(jTextFieldInEndHour6);
	jTextFieldInEndHour6.setBounds(400, 90, 20, 21);

	jLabel51.setText("分--");
	jPanel2.add(jLabel51);
	jLabel51.setBounds(370, 90, 30, 15);
	jPanel2.add(jTextFieldInBeginMin6);
	jTextFieldInBeginMin6.setBounds(350, 90, 20, 21);

	jLabel52.setText("时");
	jPanel2.add(jLabel52);
	jLabel52.setBounds(330, 90, 20, 15);
	jPanel2.add(jTextFieldInBeginHour6);
	jTextFieldInBeginHour6.setBounds(310, 90, 20, 21);

	jLabel53.setText("分");
	jPanel2.add(jLabel53);
	jLabel53.setBounds(460, 120, 20, 15);
	jPanel2.add(jTextFieldInEndMin7);
	jTextFieldInEndMin7.setBounds(440, 120, 20, 21);

	jLabel54.setText("时");
	jPanel2.add(jLabel54);
	jLabel54.setBounds(420, 120, 20, 15);
	jPanel2.add(jTextFieldInEndHour7);
	jTextFieldInEndHour7.setBounds(400, 120, 20, 21);

	jLabel55.setText("分--");
	jPanel2.add(jLabel55);
	jLabel55.setBounds(370, 120, 24, 15);
	jPanel2.add(jTextFieldInBeginMin7);
	jTextFieldInBeginMin7.setBounds(350, 120, 20, 21);

	jLabel56.setText("时");
	jPanel2.add(jLabel56);
	jLabel56.setBounds(330, 120, 20, 15);
	jPanel2.add(jTextFieldInBeginHour7);
	jTextFieldInBeginHour7.setBounds(310, 120, 20, 21);

	jLabel57.setText("分");
	jPanel2.add(jLabel57);
	jLabel57.setBounds(460, 150, 20, 15);
	jPanel2.add(jTextFieldInEndMin8);
	jTextFieldInEndMin8.setBounds(440, 150, 20, 21);

	jLabel58.setText("时");
	jPanel2.add(jLabel58);
	jLabel58.setBounds(420, 150, 20, 15);
	jPanel2.add(jTextFieldInEndHour8);
	jTextFieldInEndHour8.setBounds(400, 150, 20, 21);

	jLabel59.setText("分--");
	jPanel2.add(jLabel59);
	jLabel59.setBounds(370, 150, 30, 15);
	jPanel2.add(jTextFieldInBeginMin8);
	jTextFieldInBeginMin8.setBounds(350, 150, 20, 21);

	jLabel60.setText("时");
	jPanel2.add(jLabel60);
	jLabel60.setBounds(330, 150, 20, 15);
	jPanel2.add(jTextFieldInBeginHour8);
	jTextFieldInBeginHour8.setBounds(310, 150, 20, 21);

	jButtonConfirmInTime.setText("确定");
	jButtonConfirmInTime
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonConfirmInTimeActionPerformed(evt);
		    }
		});
	jPanel2.add(jButtonConfirmInTime);
	jButtonConfirmInTime.setBounds(490, 150, 60, 23);

	jCheckBoxAlarmInHandle.setText("报警输入处理");

	jPanel6.setBorder(BorderFactory.createTitledBorder(BorderFactory
		.createTitledBorder("PTZ联动")));
	jPanel6.setLayout(null);

	jLabel61.setText("通道号");
	jPanel6.add(jLabel61);
	jLabel61.setBounds(30, 20, 50, 15);

	jComboBoxPTZChannel
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxPTZChannelActionPerformed(evt);
		    }
		});
	jPanel6.add(jComboBoxPTZChannel);
	jComboBoxPTZChannel.setBounds(100, 20, 60, 21);

	buttonGroup1.add(jRadioButtonCruise);
	jRadioButtonCruise.setText("巡航");
	jPanel6.add(jRadioButtonCruise);
	jRadioButtonCruise.setBounds(30, 60, 60, 23);

	jPanel6.add(jComboBoxCruise);
	jComboBoxCruise.setBounds(100, 60, 60, 21);

	buttonGroup1.add(jRadioButtonPreset);
	jRadioButtonPreset.setText("预置点");
	jPanel6.add(jRadioButtonPreset);
	jRadioButtonPreset.setBounds(170, 60, 70, 23);

	jPanel6.add(jComboBoxPreset);
	jComboBoxPreset.setBounds(240, 60, 60, 21);

	buttonGroup1.add(jRadioButtonTrack);
	jRadioButtonTrack.setText("轨迹");
	jPanel6.add(jRadioButtonTrack);
	jRadioButtonTrack.setBounds(310, 60, 60, 23);

	jPanel6.add(jComboBoxTrack);
	jComboBoxTrack.setBounds(370, 60, 60, 21);

	jButtonPTZConfirm.setText("确定");
	jButtonPTZConfirm
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonPTZConfirmActionPerformed(evt);
		    }
		});
	jPanel6.add(jButtonPTZConfirm);
	jButtonPTZConfirm.setBounds(490, 50, 60, 23);

	jButtonSetupAlarmIn.setText("设置当前报警通道");
	jButtonSetupAlarmIn
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonSetupAlarmInActionPerformed(evt);
		    }
		});

	jButtonExit3.setText("退出");
	jButtonExit3.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExit3ActionPerformed(evt);
	    }
	});

	GroupLayout jPanelAlarmInLayout = new GroupLayout(jPanelAlarmIn);
	jPanelAlarmIn.setLayout(jPanelAlarmInLayout);
	jPanelAlarmInLayout
		.setHorizontalGroup(jPanelAlarmInLayout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanelAlarmInLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanelAlarmInLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanelAlarmInLayout
									.createSequentialGroup()
									.addGap(10,
										10,
										10)
									.addComponent(
										jButtonSetupAlarmIn)
									.addGap(308,
										308,
										308)
									.addComponent(
										jButtonExit3,
										GroupLayout.PREFERRED_SIZE,
										68,
										GroupLayout.PREFERRED_SIZE)
									.addGap(79,
										79,
										79))
							.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanelAlarmInLayout
									.createSequentialGroup()
									.addGroup(
										jPanelAlarmInLayout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
											.addComponent(
												jPanel6,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												571,
												Short.MAX_VALUE)
											.addComponent(
												jPanel2,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												571,
												Short.MAX_VALUE)
											.addComponent(
												jPanel1,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addGroup(
												GroupLayout.Alignment.LEADING,
												jPanelAlarmInLayout
													.createSequentialGroup()
													.addGroup(
														jPanelAlarmInLayout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING,
																false)
															.addGroup(
																jPanelAlarmInLayout
																	.createSequentialGroup()
																	.addComponent(
																		jLabel5)
																	.addGap(18,
																		18,
																		18)
																	.addComponent(
																		jTextFieldAlarmInName))
															.addGroup(
																jPanelAlarmInLayout
																	.createSequentialGroup()
																	.addComponent(
																		jLabel2,
																		GroupLayout.PREFERRED_SIZE,
																		62,
																		GroupLayout.PREFERRED_SIZE)
																	.addGap(28,
																		28,
																		28)
																	.addComponent(
																		jComboBoxAlarmInChannel,
																		GroupLayout.PREFERRED_SIZE,
																		94,
																		GroupLayout.PREFERRED_SIZE)))
													.addGap(18,
														18,
														18)
													.addGroup(
														jPanelAlarmInLayout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jLabel3,
																GroupLayout.PREFERRED_SIZE,
																68,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel6))
													.addGap(18,
														18,
														18)
													.addGroup(
														jPanelAlarmInLayout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldDeviceAddress,
																GroupLayout.DEFAULT_SIZE,
																81,
																Short.MAX_VALUE)
															.addComponent(
																jComboBoxAlarmType,
																0,
																81,
																Short.MAX_VALUE))
													.addGap(18,
														18,
														18)
													.addGroup(
														jPanelAlarmInLayout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addGroup(
																jPanelAlarmInLayout
																	.createSequentialGroup()
																	.addComponent(
																		jLabel4)
																	.addGap(18,
																		18,
																		18)
																	.addComponent(
																		jTextFieldAlarmInChannel))
															.addComponent(
																jCheckBoxAlarmInHandle,
																GroupLayout.PREFERRED_SIZE,
																131,
																GroupLayout.PREFERRED_SIZE))
													.addGap(34,
														34,
														34)))
									.addGap(23,
										23,
										23)))));
	jPanelAlarmInLayout
		.setVerticalGroup(jPanelAlarmInLayout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanelAlarmInLayout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanelAlarmInLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel2)
							.addComponent(
								jComboBoxAlarmInChannel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel3)
							.addComponent(
								jTextFieldDeviceAddress,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel4)
							.addComponent(
								jTextFieldAlarmInChannel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addGap(6, 6, 6)
					.addGroup(
						jPanelAlarmInLayout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel5)
							.addComponent(
								jTextFieldAlarmInName,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel6)
							.addComponent(
								jComboBoxAlarmType,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								jCheckBoxAlarmInHandle))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jPanel1,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jPanel2,
						GroupLayout.PREFERRED_SIZE,
						180, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(jPanel6,
						GroupLayout.PREFERRED_SIZE, 91,
						GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanelAlarmInLayout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jButtonExit3)
							.addComponent(
								jButtonSetupAlarmIn))));

	jTabbedPaneAlarmCfg.addTab("报警输入", jPanelAlarmIn);

	jPanelAlarmOut.setLayout(null);

	jLabel62.setText("报警输出");
	jPanelAlarmOut.add(jLabel62);
	jLabel62.setBounds(40, 30, 48, 15);

	jComboBoxAlarmOutChannel
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxAlarmOutChannelActionPerformed(evt);
		    }
		});
	jPanelAlarmOut.add(jComboBoxAlarmOutChannel);
	jComboBoxAlarmOutChannel.setBounds(120, 30, 70, 21);

	jLabel63.setText("IP设备地址");
	jPanelAlarmOut.add(jLabel63);
	jLabel63.setBounds(210, 30, 70, 15);
	jPanelAlarmOut.add(jTextField1);
	jTextField1.setBounds(300, 30, 70, 21);

	jLabel64.setText("IP输出通道");
	jPanelAlarmOut.add(jLabel64);
	jLabel64.setBounds(400, 30, 70, 15);
	jPanelAlarmOut.add(jTextField2);
	jTextField2.setBounds(490, 30, 80, 21);

	jLabel65.setText("报警输出名称");
	jPanelAlarmOut.add(jLabel65);
	jLabel65.setBounds(40, 80, 90, 15);
	jPanelAlarmOut.add(jTextFieldAlarmOutName);
	jTextFieldAlarmOutName.setBounds(120, 80, 70, 21);

	jLabel66.setText("报警输出延时");
	jPanelAlarmOut.add(jLabel66);
	jLabel66.setBounds(210, 80, 90, 15);
	jPanelAlarmOut.add(jTextFieldAlarmOutDelay);
	jTextFieldAlarmOutDelay.setBounds(300, 80, 70, 21);

	jPanel7.setBorder(BorderFactory.createTitledBorder("报警输出触发时间"));

	jLabel67.setText("时间段1");

	jLabel68.setText("时");

	jLabel69.setText("分");

	jLabel70.setText("到");

	jLabel71.setText("时");

	jLabel72.setText("分");

	jLabel73.setText("时间段2");

	jLabel74.setText("时");

	jLabel75.setText("分");

	jLabel76.setText("到");

	jLabel77.setText("时");

	jLabel78.setText("分");

	jLabel79.setText("时间段3");

	jLabel80.setText("时");

	jLabel81.setText("分");

	jLabel82.setText("到");

	jLabel83.setText("时");

	jLabel84.setText("分");

	jLabel85.setText("时间段4");

	jLabel86.setText("时");

	jLabel87.setText("分");

	jLabel88.setText("到");

	jLabel89.setText("时");

	jLabel90.setText("分");

	jLabel91.setText("时间段5");

	jLabel92.setText("时");

	jLabel93.setText("分");

	jLabel94.setText("到");

	jLabel95.setText("时");

	jLabel96.setText("分");

	jLabel97.setText("时间段6");

	jLabel98.setText("时");

	jLabel99.setText("分");

	jLabel100.setText("到");

	jLabel101.setText("时");

	jLabel102.setText("分");

	jLabel103.setText("时间段7");

	jLabel104.setText("时");

	jLabel105.setText("分");

	jLabel106.setText("到");

	jLabel107.setText("时");

	jLabel108.setText("分");

	jLabel109.setText("时间段8");

	jLabel110.setText("时");

	jLabel111.setText("分");

	jLabel112.setText("到");

	jLabel113.setText("时");

	jLabel114.setText("分");

	jLabel115.setText("日期");

	jComboBoxWeekDay
		.setModel(new DefaultComboBoxModel<Object>(new String[] {
			"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
	jComboBoxWeekDay.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jComboBoxWeekDayActionPerformed(evt);
	    }
	});

	jButtonConfirm.setText("确定");
	jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonConfirmActionPerformed(evt);
	    }
	});

	GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
	jPanel7.setLayout(jPanel7Layout);
	jPanel7Layout
		.setHorizontalGroup(jPanel7Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel7Layout
					.createSequentialGroup()
					.addGap(18, 18, 18)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.TRAILING)
							.addGroup(
								jPanel7Layout
									.createParallelGroup(
										GroupLayout.Alignment.LEADING)
									.addGroup(
										jPanel7Layout
											.createSequentialGroup()
											.addComponent(
												jLabel103,
												GroupLayout.PREFERRED_SIZE,
												57,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jTextFieldfHour7,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel104,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldfMin7,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel105,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel106,
												GroupLayout.PREFERRED_SIZE,
												28,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsHour7,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel107,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsMin7,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel108,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel7Layout
											.createSequentialGroup()
											.addComponent(
												jLabel97,
												GroupLayout.PREFERRED_SIZE,
												57,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jTextFieldfHour6,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel98,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldfMin6,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel99,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel100,
												GroupLayout.PREFERRED_SIZE,
												28,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsHour6,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel101,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsMin6,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel102,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel7Layout
											.createSequentialGroup()
											.addComponent(
												jLabel91,
												GroupLayout.PREFERRED_SIZE,
												57,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jTextFieldfHour5,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel92,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldfMin5,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel93,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel94,
												GroupLayout.PREFERRED_SIZE,
												28,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsHour5,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel95,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsMin5,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel96,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel7Layout
											.createSequentialGroup()
											.addComponent(
												jLabel85,
												GroupLayout.PREFERRED_SIZE,
												57,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jTextFieldfHour4,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel86,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldfMin4,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel87,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel88,
												GroupLayout.PREFERRED_SIZE,
												28,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsHour4,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel89,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsMin4,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel90,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel7Layout
											.createSequentialGroup()
											.addComponent(
												jLabel79,
												GroupLayout.PREFERRED_SIZE,
												57,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jTextFieldfHour3,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel80,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldfMin3,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel81,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel82,
												GroupLayout.PREFERRED_SIZE,
												28,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsHour3,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel83,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldsMin3,
												GroupLayout.PREFERRED_SIZE,
												37,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel84,
												GroupLayout.PREFERRED_SIZE,
												26,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel7Layout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
											.addGroup(
												jPanel7Layout
													.createSequentialGroup()
													.addComponent(
														jLabel73,
														GroupLayout.PREFERRED_SIZE,
														57,
														GroupLayout.PREFERRED_SIZE)
													.addGap(18,
														18,
														18)
													.addComponent(
														jTextFieldfHour2,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jLabel74,
														GroupLayout.PREFERRED_SIZE,
														26,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(
														jTextFieldfMin2,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jLabel75,
														GroupLayout.PREFERRED_SIZE,
														26,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jLabel76,
														GroupLayout.PREFERRED_SIZE,
														28,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(
														jTextFieldsHour2,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jLabel77,
														GroupLayout.PREFERRED_SIZE,
														26,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(
														jTextFieldsMin2,
														GroupLayout.PREFERRED_SIZE,
														37,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jLabel78,
														GroupLayout.PREFERRED_SIZE,
														26,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel7Layout
													.createSequentialGroup()
													.addGroup(
														jPanel7Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addGroup(
																GroupLayout.Alignment.TRAILING,
																jPanel7Layout
																	.createSequentialGroup()
																	.addComponent(
																		jLabel67,
																		GroupLayout.PREFERRED_SIZE,
																		57,
																		GroupLayout.PREFERRED_SIZE)
																	.addGap(18,
																		18,
																		18))
															.addGroup(
																jPanel7Layout
																	.createSequentialGroup()
																	.addComponent(
																		jLabel115,
																		GroupLayout.PREFERRED_SIZE,
																		33,
																		GroupLayout.PREFERRED_SIZE)
																	.addGap(42,
																		42,
																		42)))
													.addGroup(
														jPanel7Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addGroup(
																jPanel7Layout
																	.createSequentialGroup()
																	.addComponent(
																		jTextFieldfHour1,
																		GroupLayout.PREFERRED_SIZE,
																		37,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel68,
																		GroupLayout.PREFERRED_SIZE,
																		26,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																	.addComponent(
																		jTextFieldfMin1,
																		GroupLayout.PREFERRED_SIZE,
																		37,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel69,
																		GroupLayout.PREFERRED_SIZE,
																		26,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel70,
																		GroupLayout.PREFERRED_SIZE,
																		28,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																	.addComponent(
																		jTextFieldsHour1,
																		GroupLayout.PREFERRED_SIZE,
																		37,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel71,
																		GroupLayout.PREFERRED_SIZE,
																		26,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																	.addComponent(
																		jTextFieldsMin1,
																		GroupLayout.PREFERRED_SIZE,
																		37,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel72,
																		GroupLayout.PREFERRED_SIZE,
																		26,
																		GroupLayout.PREFERRED_SIZE))
															.addComponent(
																jComboBoxWeekDay,
																GroupLayout.PREFERRED_SIZE,
																96,
																GroupLayout.PREFERRED_SIZE)))))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										jLabel109,
										GroupLayout.PREFERRED_SIZE,
										57,
										GroupLayout.PREFERRED_SIZE)
									.addGap(18,
										18,
										18)
									.addComponent(
										jTextFieldfHour8,
										GroupLayout.PREFERRED_SIZE,
										37,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel110,
										GroupLayout.PREFERRED_SIZE,
										26,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jTextFieldfMin8,
										GroupLayout.PREFERRED_SIZE,
										37,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel111,
										GroupLayout.PREFERRED_SIZE,
										26,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel112,
										GroupLayout.PREFERRED_SIZE,
										28,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jTextFieldsHour8,
										GroupLayout.PREFERRED_SIZE,
										37,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel113,
										GroupLayout.PREFERRED_SIZE,
										26,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jTextFieldsMin8,
										GroupLayout.PREFERRED_SIZE,
										37,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel114,
										GroupLayout.PREFERRED_SIZE,
										26,
										GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(109, Short.MAX_VALUE))
			.addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel7Layout
					.createSequentialGroup()
					.addContainerGap(420, Short.MAX_VALUE)
					.addComponent(jButtonConfirm,
						GroupLayout.PREFERRED_SIZE, 70,
						GroupLayout.PREFERRED_SIZE)
					.addGap(54, 54, 54)));
	jPanel7Layout
		.setVerticalGroup(jPanel7Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel7Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel115)
							.addComponent(
								jComboBoxWeekDay,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addGap(31, 31, 31)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel67)
							.addComponent(
								jTextFieldfHour1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel68)
							.addComponent(
								jTextFieldfMin1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel69)
							.addComponent(jLabel70)
							.addComponent(
								jTextFieldsHour1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel71)
							.addComponent(
								jTextFieldsMin1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel72))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel73)
							.addComponent(
								jTextFieldfHour2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel74)
							.addComponent(
								jTextFieldfMin2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel75)
							.addComponent(jLabel76)
							.addComponent(
								jTextFieldsHour2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel77)
							.addComponent(
								jTextFieldsMin2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel78))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel79)
							.addComponent(
								jTextFieldfHour3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel80)
							.addComponent(
								jTextFieldfMin3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel81)
							.addComponent(jLabel82)
							.addComponent(
								jTextFieldsHour3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel83)
							.addComponent(
								jTextFieldsMin3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel84))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel85)
							.addComponent(
								jTextFieldfHour4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel86)
							.addComponent(
								jTextFieldfMin4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel87)
							.addComponent(jLabel88)
							.addComponent(
								jTextFieldsHour4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel89)
							.addComponent(
								jTextFieldsMin4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel90))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel91)
							.addComponent(
								jTextFieldfHour5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel92)
							.addComponent(
								jTextFieldfMin5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel93)
							.addComponent(jLabel94)
							.addComponent(
								jTextFieldsHour5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel95)
							.addComponent(
								jTextFieldsMin5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel96))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel97)
							.addComponent(
								jTextFieldfHour6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel98)
							.addComponent(
								jTextFieldfMin6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel99)
							.addComponent(jLabel100)
							.addComponent(
								jTextFieldsHour6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel101)
							.addComponent(
								jTextFieldsMin6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel102))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel103)
							.addComponent(
								jTextFieldfHour7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel104)
							.addComponent(
								jTextFieldfMin7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel105)
							.addComponent(jLabel106)
							.addComponent(
								jTextFieldsHour7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel107)
							.addComponent(
								jTextFieldsMin7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel108))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel109)
							.addComponent(
								jTextFieldfHour8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel110)
							.addComponent(
								jTextFieldfMin8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel111)
							.addComponent(jLabel112)
							.addComponent(
								jTextFieldsHour8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel113)
							.addComponent(
								jTextFieldsMin8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel114))
					.addGap(18, 18, 18)
					.addComponent(jButtonConfirm)
					.addContainerGap(
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	jPanelAlarmOut.add(jPanel7);
	jPanel7.setBounds(20, 120, 560, 360);

	jToggleButtonSetupAlarmOut.setText("设置当前通道");
	jToggleButtonSetupAlarmOut
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jToggleButtonSetupAlarmOutActionPerformed(evt);
		    }
		});
	jPanelAlarmOut.add(jToggleButtonSetupAlarmOut);
	jToggleButtonSetupAlarmOut.setBounds(40, 510, 117, 23);

	jButtonExit2.setText("退出");
	jButtonExit2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExit2ActionPerformed(evt);
	    }
	});
	jPanelAlarmOut.add(jButtonExit2);
	jButtonExit2.setBounds(450, 510, 70, 23);

	jTabbedPaneAlarmCfg.addTab("报警输出", jPanelAlarmOut);

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		GroupLayout.Alignment.TRAILING,
		layout.createSequentialGroup()
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)
			.addComponent(jTabbedPaneAlarmCfg,
				GroupLayout.PREFERRED_SIZE, 594,
				GroupLayout.PREFERRED_SIZE).addContainerGap()));
	layout.setVerticalGroup(layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup()
			.addComponent(jTabbedPaneAlarmCfg,
				GroupLayout.PREFERRED_SIZE, 592,
				GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: 异常 "确认" 按钮单击响应函数 函数描述: 保存对应异常类型的参数至结构体
     *************************************************/
    private void jButtonComfirmExceptionActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonComfirmExceptionActionPerformed
	int iExceptionType = jComboBoxExceptionType.getSelectedIndex();

	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType = 0;
	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxMoniter
		.isSelected() == true ? 1 : 0) << 0);
	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxAudio
		.isSelected() == true ? 1 : 0) << 1);
	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxCenter
		.isSelected() == true ? 1 : 0) << 2);
	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxTraggerAlarmOut
		.isSelected() == true ? 1 : 0) << 3);
	m_struExceptionInfo.struExceptionHandleType[iExceptionType].dwHandleType |= ((jCheckBoxEMail
		.isSelected() == true ? 1 : 0) << 4);

	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_struExceptionInfo.struExceptionHandleType[iExceptionType].byRelAlarmOut[i] = (byte) ((m_exceptionAlarmOut[i].check == true) ? 1
		    : 0);
	}
    }// GEN-LAST:event_jButtonComfirmExceptionActionPerformed

    /*************************************************
     * 函数: 异常 "设置异常配置" 按钮单击响应函数 函数描述: 调用接口设置异常参数
     *************************************************/
    private void jButtonSetupExceptionActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSetupExceptionActionPerformed
	m_struExceptionInfo.write();
	Pointer lpConfig = m_struExceptionInfo.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_SET_EXCEPTIONCFG_V30, new NativeLong(0),
		lpConfig, m_struExceptionInfo.size());
	m_struExceptionInfo.read();
	if (setDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "设置异常配置失败");
	    return;
	} else {
	    JOptionPane.showMessageDialog(this, "设置异常配置成功");
	}
    }// GEN-LAST:event_jButtonSetupExceptionActionPerformed

    /*************************************************
     * 函数: "异常类型" 下拉框 选项改变事件响应函数 函数描述: 显示对应该异常类型的参数
     *************************************************/
    private void jComboBoxExceptionTypeActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxExceptionTypeActionPerformed
	showExceptionTypeCfg();
    }// GEN-LAST:event_jComboBoxExceptionTypeActionPerformed

    /*************************************************
     * 函数: "报警输入" 下拉框 选项改变事件响应函数 函数描述: 显示对应报警输入通道的参数
     *************************************************/
    private void jComboBoxAlarmInChannelActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxAlarmInChannelActionPerformed
	if (bInitialed) {
	    showAlarmInCfg();
	    jComboBoxDate.setSelectedIndex(0);// 调用函数jComboBoxDateActionPerformed()
	    jComboBoxPTZChannel.setSelectedIndex(0);// 调用函数jComboBoxPTZChannelActionPerformed()
	}
    }// GEN-LAST:event_jComboBoxAlarmInChannelActionPerformed

    /*************************************************
     * 函数: "日期" 下拉框 选项改变事件响应函数 函数描述: 显示对应星期的时间段参数
     *************************************************/
    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxDateActionPerformed
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	jTextFieldInBeginHour1
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour
			+ "");
	jTextFieldInBeginMin1
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin
			+ "");
	jTextFieldInEndHour1
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour
			+ "");
	jTextFieldInEndMin1
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin
			+ "");

	jTextFieldInBeginHour2
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour
			+ "");
	jTextFieldInBeginMin2
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin
			+ "");
	jTextFieldInEndHour2
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour
			+ "");
	jTextFieldInEndMin2
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin
			+ "");

	jTextFieldInBeginHour3
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour
			+ "");
	jTextFieldInBeginMin3
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin
			+ "");
	jTextFieldInEndHour3
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour
			+ "");
	jTextFieldInEndMin3
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin
			+ "");

	jTextFieldInBeginHour4
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour
			+ "");
	jTextFieldInBeginMin4
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin
			+ "");
	jTextFieldInEndHour4
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour
			+ "");
	jTextFieldInEndMin4
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin
			+ "");

	jTextFieldInBeginHour5
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour
			+ "");
	jTextFieldInBeginMin5
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin
			+ "");
	jTextFieldInEndHour5
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour
			+ "");
	jTextFieldInEndMin5
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin
			+ "");

	jTextFieldInBeginHour6
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour
			+ "");
	jTextFieldInBeginMin6
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin
			+ "");
	jTextFieldInEndHour6
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour
			+ "");
	jTextFieldInEndMin6
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin
			+ "");

	jTextFieldInBeginHour7
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour
			+ "");
	jTextFieldInBeginMin7
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin
			+ "");
	jTextFieldInEndHour7
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour
			+ "");
	jTextFieldInEndMin7
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin
			+ "");

	jTextFieldInBeginHour8
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour
			+ "");
	jTextFieldInBeginMin8
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin
			+ "");
	jTextFieldInEndHour8
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour
			+ "");
	jTextFieldInEndMin8
		.setText(m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin
			+ "");
    }// GEN-LAST:event_jComboBoxDateActionPerformed

    /*************************************************
     * 函数: PTZ联动 "通道号" 下拉框 选项改变事件响应函数 函数描述: 选择对应星期的时间段参数
     *************************************************/
    private void jComboBoxPTZChannelActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxPTZChannelActionPerformed
	if (bInitialed) {
	    int iPTZChannel = jComboBoxPTZChannel.getSelectedIndex();
	    jRadioButtonCruise
		    .setSelected(m_struAlarmInCfg.byEnableCruise[iPTZChannel] == 1);
	    jRadioButtonPreset
		    .setSelected(m_struAlarmInCfg.byEnablePreset[iPTZChannel] == 1);
	    jRadioButtonTrack
		    .setSelected(m_struAlarmInCfg.byEnablePtzTrack[iPTZChannel] == 1);
	    jComboBoxCruise
		    .setSelectedIndex(m_struAlarmInCfg.byCruiseNo[iPTZChannel]);
	    jComboBoxPreset
		    .setSelectedIndex(m_struAlarmInCfg.byPresetNo[iPTZChannel]);
	    jComboBoxTrack
		    .setSelectedIndex(m_struAlarmInCfg.byPTZTrack[iPTZChannel]);
	}
    }// GEN-LAST:event_jComboBoxPTZChannelActionPerformed

    /*************************************************
     * 函数: 报警输入时间段 "确认" 按钮双击响应函数 函数描述: 保存当日的时间段信息至结构体
     *************************************************/
    private void jButtonConfirmInTimeActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonConfirmInTimeActionPerformed
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour1.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin1.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour1.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin1.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour2.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin2.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour2.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin2.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour3.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin3.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour3.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin3.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour4.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin4.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour4.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin4.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour5.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin5.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour5.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin5.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour6.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin6.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour6.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin6.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour7.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin7.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour7.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin7.getText());

	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour8.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin8.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour8.getText());
	m_struAlarmInCfg.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin8.getText());
    }// GEN-LAST:event_jButtonConfirmInTimeActionPerformed

    /*************************************************
     * 函数: 报警输入PTZ "确认" 按钮双击响应函数 函数描述: 保存PTZ配置信息至结构体
     *************************************************/
    private void jButtonPTZConfirmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonPTZConfirmActionPerformed
	int iPTZChannel = jComboBoxPTZChannel.getSelectedIndex();
	m_struAlarmInCfg.byEnableCruise[iPTZChannel] = (byte) ((jRadioButtonCruise
		.isSelected() == true) ? 1 : 0);
	m_struAlarmInCfg.byEnablePreset[iPTZChannel] = (byte) ((jRadioButtonPreset
		.isSelected() == true) ? 1 : 0);
	m_struAlarmInCfg.byEnablePtzTrack[iPTZChannel] = (byte) ((jRadioButtonTrack
		.isSelected() == true) ? 1 : 0);
	m_struAlarmInCfg.byCruiseNo[iPTZChannel] = (byte) jComboBoxCruise
		.getSelectedIndex();
	m_struAlarmInCfg.byPresetNo[iPTZChannel] = (byte) jComboBoxPreset
		.getSelectedIndex();
	m_struAlarmInCfg.byPTZTrack[iPTZChannel] = (byte) jComboBoxTrack
		.getSelectedIndex();
    }// GEN-LAST:event_jButtonPTZConfirmActionPerformed

    /*************************************************
     * 函数: PTZ联动 "通道号" 下拉框 选项改变事件响应函数 函数描述: 选择对应星期的时间段参数
     *************************************************/
    private void jComboBoxAlarmOutChannelActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxAlarmOutChannelActionPerformed
	if (bInitialed) {
	    showAlarmOutCfg();
	    jComboBoxWeekDay.setSelectedIndex(0);
	}
    }// GEN-LAST:event_jComboBoxAlarmOutChannelActionPerformed

    /*************************************************
     * 函数: "日期" 下拉框 选项改变事件响应函数 函数描述: 显示对应星期的时间段参数
     *************************************************/
    private void jComboBoxWeekDayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxWeekDayActionPerformed
	int iWeekDay = jComboBoxWeekDay.getSelectedIndex();
	jTextFieldsHour1
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartHour
			+ "");
	jTextFieldsMin1
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartMin
			+ "");
	jTextFieldfHour1
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopHour
			+ "");
	jTextFieldfMin1
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopMin
			+ "");

	jTextFieldsHour2
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartHour
			+ "");
	jTextFieldsMin2
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartMin
			+ "");
	jTextFieldfHour2
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopHour
			+ "");
	jTextFieldfMin2
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopMin
			+ "");

	jTextFieldsHour3
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartHour
			+ "");
	jTextFieldsMin3
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartMin
			+ "");
	jTextFieldfHour3
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopHour
			+ "");
	jTextFieldfMin3
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopMin
			+ "");

	jTextFieldsHour4
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartHour
			+ "");
	jTextFieldsMin4
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartMin
			+ "");
	jTextFieldfHour4
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopHour
			+ "");
	jTextFieldfMin4
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopMin
			+ "");

	jTextFieldsHour5
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartHour
			+ "");
	jTextFieldsMin5
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartMin
			+ "");
	jTextFieldfHour5
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopHour
			+ "");
	jTextFieldfMin5
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopMin
			+ "");

	jTextFieldsHour6
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartHour
			+ "");
	jTextFieldsMin6
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartMin
			+ "");
	jTextFieldfHour6
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopHour
			+ "");
	jTextFieldfMin6
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopMin
			+ "");

	jTextFieldsHour7
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartHour
			+ "");
	jTextFieldsMin7
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartMin
			+ "");
	jTextFieldfHour7
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopHour
			+ "");
	jTextFieldfMin7
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopMin
			+ "");

	jTextFieldsHour8
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartHour
			+ "");
	jTextFieldsMin8
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartMin
			+ "");
	jTextFieldfHour8
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopHour
			+ "");
	jTextFieldfMin8
		.setText(m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopMin
			+ "");

    }// GEN-LAST:event_jComboBoxWeekDayActionPerformed

    /*************************************************
     * 函数: 报警输出时间段 "确认" 按钮单击响应函数 函数描述: 保存当日的时间段信息至结构体
     *************************************************/
    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonConfirmActionPerformed
	int iWeekDay = jComboBoxWeekDay.getSelectedIndex();
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour1.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin1.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour1.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin1.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour2.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin2.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour2.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin2.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour3.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin3.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour3.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin3.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour4.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin4.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour4.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin4.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour5.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin5.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour5.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin5.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour6.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin6.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour6.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin6.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour7.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin7.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour7.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin7.getText());

	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer
		.parseInt(jTextFieldfHour8.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer
		.parseInt(jTextFieldfMin8.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer
		.parseInt(jTextFieldsHour8.getText());
	m_struAlarmOutCfg.struAlarmOutTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer
		.parseInt(jTextFieldsMin8.getText());

    }// GEN-LAST:event_jButtonConfirmActionPerformed

    /*************************************************
     * 函数: 报警输出 "设置当前通道" 按钮单击事件响应函数 函数描述: 调用接口设置报警输出参数
     *************************************************/
    private void jToggleButtonSetupAlarmOutActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleButtonSetupAlarmOutActionPerformed
	m_struAlarmOutCfg.sAlarmOutName = (jTextFieldAlarmOutName.getText() + "\0")
		.getBytes(); // 通道名称
	m_struAlarmOutCfg.dwAlarmOutDelay = (byte) Integer
		.parseInt(jTextFieldAlarmOutDelay.getText());
	m_struAlarmOutCfg.write();
	Pointer lpConfig = m_struAlarmOutCfg.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_SET_ALARMOUTCFG_V30, new NativeLong(
			jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig,
		m_struAlarmOutCfg.size());
	m_struAlarmOutCfg.read();
	if (setDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "设置失败");
	    return;
	} else {
	    JOptionPane.showMessageDialog(this, "设置成功");
	}
    }// GEN-LAST:event_jToggleButtonSetupAlarmOutActionPerformed

    /*************************************************
     * 函数: 报警输出 "设置当前报警通道" 按钮单击事件响应函数 函数描述: 调用接口设置报警输入参数
     *************************************************/
    private void jButtonSetupAlarmInActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSetupAlarmInActionPerformed
	m_struAlarmInCfg.sAlarmInName = (jTextFieldAlarmInName.getText() + "\0")
		.getBytes(); // 通道名称
	m_struAlarmInCfg.byAlarmInHandle = (byte) ((jCheckBoxAlarmInHandle
		.isSelected() == true) ? 1 : 0);
	m_struAlarmInCfg.byAlarmType = (byte) jComboBoxAlarmType
		.getSelectedIndex();

	m_struAlarmInCfg.struAlarmHandleType.dwHandleType = 0;
	m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxMoniterAlarm
		.isSelected() ? 1 : 0) << 0);
	m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxAudioAlarm
		.isSelected() ? 1 : 0) << 1);
	m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxCenter
		.isSelected() ? 1 : 0) << 2);
	m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxTraggerAlarmOutAlarm
		.isSelected() ? 1 : 0) << 3);
	m_struAlarmInCfg.struAlarmHandleType.dwHandleType |= ((jCheckBoxEMailAlarm
		.isSelected() ? 1 : 0) << 4);

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_struAlarmInCfg.struAlarmHandleType.byRelAlarmOut[i] = (byte) (m_traggerAlarmOut[i]
		    .getCheck() ? 1 : 0);
	}

	for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++) {
	    m_struAlarmInCfg.byRelRecordChan[i] = (byte) (m_traggerRecord[i]
		    .getCheck() ? 1 : 0);
	}

	m_struAlarmInCfg.write();
	Pointer lpConfig = m_struAlarmInCfg.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_SET_ALARMINCFG_V30, new NativeLong(
			jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig,
		m_struAlarmInCfg.size());
	m_struAlarmInCfg.read();
	if (setDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "设置失败");
	    return;
	} else {
	    JOptionPane.showMessageDialog(this, "设置成功");
	}
    }// GEN-LAST:event_jButtonSetupAlarmInActionPerformed

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁对话框
     *************************************************/
    private void jButtonExit2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExit2ActionPerformed
	dispose();
    }// GEN-LAST:event_jButtonExit2ActionPerformed

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁对话框
     *************************************************/
    private void jButtonExit3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExit3ActionPerformed
	dispose();
    }// GEN-LAST:event_jButtonExit3ActionPerformed

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁对话框
     *************************************************/
    private void jButtonExit1ActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonExit1ActionPerformed
    {// GEN-HEADEREND:event_jButtonExit1ActionPerformed
	dispose();
    }// GEN-LAST:event_jButtonExit1ActionPerformed

    /*************************************************
     * 函数: showExceptionCfg 函数描述: 获取异常参数
     *************************************************/
    private void showExceptionCfg() {
	IntByReference ibrBytesReturned = new IntByReference(0);
	boolean getDVRConfigSuc = false;
	m_struExceptionInfo = new HCNetSDK.NET_DVR_EXCEPTION_V30();
	m_struExceptionInfo.write();
	Pointer lpConfig = m_struExceptionInfo.getPointer();
	getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_GET_EXCEPTIONCFG_V30, new NativeLong(0),
		lpConfig, m_struExceptionInfo.size(), ibrBytesReturned);
	m_struExceptionInfo.read();
	if (getDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "获取异常处理参数失败");
	    return;
	}
	jComboBoxExceptionType.setSelectedIndex(0);
    }

    /*************************************************
     * 函数: showExceptionTypeCfg 函数描述: 显示对应类型异常参数
     *************************************************/
    private void showExceptionTypeCfg() {
	int iType = jComboBoxExceptionType.getSelectedIndex();

	jCheckBoxMoniter
		.setSelected((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType & 0x01) == 1);
	jCheckBoxAudio
		.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 1) & 0x01) == 1);
	jCheckBoxCenter
		.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 2) & 0x01) == 1);
	jCheckBoxTraggerAlarmOut
		.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 3) & 0x01) == 1);
	jCheckBoxEMail
		.setSelected(((m_struExceptionInfo.struExceptionHandleType[iType].dwHandleType >> 4) & 0x01) == 1);

	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_exceptionAlarmOut[i]
		    .setCheck(m_struExceptionInfo.struExceptionHandleType[iType].byRelAlarmOut[i] == 1);
	}
	jListExceptionTraggerAlarmOut.repaint();
    }

    /*************************************************
     * 函数: showAlarmInCfg 函数描述: 获取并显示报警输入参数
     *************************************************/
    private void showAlarmInCfg() {
	IntByReference ibrBytesReturned = new IntByReference(0);
	m_struAlarmInCfg = new HCNetSDK.NET_DVR_ALARMINCFG_V30();
	m_struAlarmInCfg.write();
	Pointer lpConfig = m_struAlarmInCfg.getPointer();
	boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_GET_ALARMINCFG_V30, new NativeLong(
			jComboBoxAlarmInChannel.getSelectedIndex()), lpConfig,
		m_struAlarmInCfg.size(), ibrBytesReturned);
	m_struAlarmInCfg.read();
	if (getDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "获取报警输入参数失败");
	    return;
	}

	jCheckBoxAlarmInHandle
		.setSelected(m_struAlarmInCfg.byAlarmInHandle == 1);// 是否启用报警输入处理

	String[] sName = new String[2];// 报警输入名称
	sName = new String(m_struAlarmInCfg.sAlarmInName).split("\0", 2);
	this.jTextFieldAlarmInName.setText(sName[0]);

	jComboBoxAlarmType.setSelectedIndex(m_struAlarmInCfg.byAlarmType);

	// 触发报警输出参数,触发类型
	jCheckBoxMoniterAlarm
		.setSelected((m_struAlarmInCfg.struAlarmHandleType.dwHandleType & 0x01) == 1);
	jCheckBoxAudioAlarm
		.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 1) & 0x01) == 1);
	jCheckBoxCenterAlarm
		.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 2) & 0x01) == 1);
	jCheckBoxTraggerAlarmOutAlarm
		.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 3) & 0x01) == 1);
	jCheckBoxEMailAlarm
		.setSelected(((m_struAlarmInCfg.struAlarmHandleType.dwHandleType >> 4) & 0x01) == 1);

	// 触发报警输出的通道
	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_traggerAlarmOut[i]
		    .setCheck(m_struAlarmInCfg.struAlarmHandleType.byRelAlarmOut[i] == 1);
	}
	jListTraggerRecord.repaint();

	for (int i = 0; i < m_strDeviceInfo.byChanNum; i++) {
	    m_traggerRecord[i]
		    .setCheck(m_struAlarmInCfg.byRelRecordChan[i] == 1);
	}
	jListTraggerAlarmOut.repaint();
    }

    /*************************************************
     * 函数: showAlarmOutCfg 函数描述: 获取并显示报警输出参数
     *************************************************/
    private void showAlarmOutCfg() {
	IntByReference ibrBytesReturned = new IntByReference(0);
	m_struAlarmOutCfg = new HCNetSDK.NET_DVR_ALARMOUTCFG_V30();
	m_struAlarmOutCfg.write();
	Pointer lpConfig = m_struAlarmOutCfg.getPointer();
	boolean getDVRConfigSuc = hCNetSDK.NET_DVR_GetDVRConfig(m_lUserID,
		HCNetSDK.NET_DVR_GET_ALARMOUTCFG_V30, new NativeLong(
			jComboBoxAlarmOutChannel.getSelectedIndex()), lpConfig,
		m_struAlarmOutCfg.size(), ibrBytesReturned);
	m_struAlarmOutCfg.read();
	if (getDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "获取报警输出参数失败");
	    return;
	}
	String[] sName = new String[2];// 报警输入名称
	sName = new String(m_struAlarmOutCfg.sAlarmOutName).split("\0", 2);
	this.jTextFieldAlarmOutName.setText(sName[0]);

	jTextFieldAlarmOutDelay.setText(m_struAlarmOutCfg.dwAlarmOutDelay + "");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ButtonGroup buttonGroup1;
    private JButton jButtonComfirmException;
    private JButton jButtonConfirm;
    private JButton jButtonConfirmInTime;
    private JButton jButtonExit1;
    private JButton jButtonExit2;
    private JButton jButtonExit3;
    private JButton jButtonPTZConfirm;
    private JButton jButtonSetupAlarmIn;
    private JButton jButtonSetupException;
    private JCheckBox jCheckBoxAlarmInHandle;
    private JCheckBox jCheckBoxAudio;
    private JCheckBox jCheckBoxAudioAlarm;
    private JCheckBox jCheckBoxCenter;
    private JCheckBox jCheckBoxCenterAlarm;
    private JCheckBox jCheckBoxEMail;
    private JCheckBox jCheckBoxEMailAlarm;
    private JCheckBox jCheckBoxMoniter;
    private JCheckBox jCheckBoxMoniterAlarm;
    private JCheckBox jCheckBoxTraggerAlarmOut;
    private JCheckBox jCheckBoxTraggerAlarmOutAlarm;
    private JComboBox<Object> jComboBox2;
    private JComboBox<Object> jComboBoxAlarmInChannel;
    private JComboBox<Object> jComboBoxAlarmOutChannel;
    private JComboBox<Object> jComboBoxAlarmType;
    private JComboBox<Object> jComboBoxCruise;
    private JComboBox<Object> jComboBoxDate;
    private JComboBox<Object> jComboBoxExceptionType;
    private JComboBox<Object> jComboBoxPTZChannel;
    private JComboBox<Object> jComboBoxPreset;
    private JComboBox<Object> jComboBoxTrack;
    private JComboBox<Object> jComboBoxWeekDay;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel100;
    private JLabel jLabel101;
    private JLabel jLabel102;
    private JLabel jLabel103;
    private JLabel jLabel104;
    private JLabel jLabel105;
    private JLabel jLabel106;
    private JLabel jLabel107;
    private JLabel jLabel108;
    private JLabel jLabel109;
    private JLabel jLabel11;
    private JLabel jLabel110;
    private JLabel jLabel111;
    private JLabel jLabel112;
    private JLabel jLabel113;
    private JLabel jLabel114;
    private JLabel jLabel115;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel27;
    private JLabel jLabel28;
    private JLabel jLabel29;
    private JLabel jLabel3;
    private JLabel jLabel30;
    private JLabel jLabel31;
    private JLabel jLabel32;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel35;
    private JLabel jLabel36;
    private JLabel jLabel37;
    private JLabel jLabel38;
    private JLabel jLabel39;
    private JLabel jLabel4;
    private JLabel jLabel40;
    private JLabel jLabel41;
    private JLabel jLabel42;
    private JLabel jLabel43;
    private JLabel jLabel44;
    private JLabel jLabel45;
    private JLabel jLabel46;
    private JLabel jLabel47;
    private JLabel jLabel48;
    private JLabel jLabel49;
    private JLabel jLabel5;
    private JLabel jLabel50;
    private JLabel jLabel51;
    private JLabel jLabel52;
    private JLabel jLabel53;
    private JLabel jLabel54;
    private JLabel jLabel55;
    private JLabel jLabel56;
    private JLabel jLabel57;
    private JLabel jLabel58;
    private JLabel jLabel59;
    private JLabel jLabel6;
    private JLabel jLabel60;
    private JLabel jLabel61;
    private JLabel jLabel62;
    private JLabel jLabel63;
    private JLabel jLabel64;
    private JLabel jLabel65;
    private JLabel jLabel66;
    private JLabel jLabel67;
    private JLabel jLabel68;
    private JLabel jLabel69;
    private JLabel jLabel7;
    private JLabel jLabel70;
    private JLabel jLabel71;
    private JLabel jLabel72;
    private JLabel jLabel73;
    private JLabel jLabel74;
    private JLabel jLabel75;
    private JLabel jLabel76;
    private JLabel jLabel77;
    private JLabel jLabel78;
    private JLabel jLabel79;
    private JLabel jLabel8;
    private JLabel jLabel80;
    private JLabel jLabel81;
    private JLabel jLabel82;
    private JLabel jLabel83;
    private JLabel jLabel84;
    private JLabel jLabel85;
    private JLabel jLabel86;
    private JLabel jLabel87;
    private JLabel jLabel88;
    private JLabel jLabel89;
    private JLabel jLabel9;
    private JLabel jLabel90;
    private JLabel jLabel91;
    private JLabel jLabel92;
    private JLabel jLabel93;
    private JLabel jLabel94;
    private JLabel jLabel95;
    private JLabel jLabel96;
    private JLabel jLabel97;
    private JLabel jLabel98;
    private JLabel jLabel99;
    private JList<CheckListItem> jListExceptionTraggerAlarmOut;
    private JList<CheckListItem> jListTraggerAlarmOut;
    private JList<CheckListItem> jListTraggerRecord;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanelAlarmIn;
    private JPanel jPanelAlarmOut;
    private JPanel jPanelException;
    private JPanel jPanelExceptionTragger;
    private JRadioButton jRadioButtonCruise;
    private JRadioButton jRadioButtonPreset;
    private JRadioButton jRadioButtonTrack;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JTabbedPane jTabbedPaneAlarmCfg;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField5;
    private JTextField jTextField6;
    private JTextField jTextField7;
    private JTextField jTextField8;
    private JTextField jTextFieldAlarmInChannel;
    private JTextField jTextFieldAlarmInName;
    private JTextField jTextFieldAlarmOutDelay;
    private JTextField jTextFieldAlarmOutName;
    private JTextField jTextFieldDeviceAddress;
    private JTextField jTextFieldInBeginHour1;
    private JTextField jTextFieldInBeginHour2;
    private JTextField jTextFieldInBeginHour3;
    private JTextField jTextFieldInBeginHour4;
    private JTextField jTextFieldInBeginHour5;
    private JTextField jTextFieldInBeginHour6;
    private JTextField jTextFieldInBeginHour7;
    private JTextField jTextFieldInBeginHour8;
    private JTextField jTextFieldInBeginMin1;
    private JTextField jTextFieldInBeginMin2;
    private JTextField jTextFieldInBeginMin3;
    private JTextField jTextFieldInBeginMin4;
    private JTextField jTextFieldInBeginMin5;
    private JTextField jTextFieldInBeginMin6;
    private JTextField jTextFieldInBeginMin7;
    private JTextField jTextFieldInBeginMin8;
    private JTextField jTextFieldInEndHour1;
    private JTextField jTextFieldInEndHour2;
    private JTextField jTextFieldInEndHour3;
    private JTextField jTextFieldInEndHour4;
    private JTextField jTextFieldInEndHour5;
    private JTextField jTextFieldInEndHour6;
    private JTextField jTextFieldInEndHour7;
    private JTextField jTextFieldInEndHour8;
    private JTextField jTextFieldInEndMin1;
    private JTextField jTextFieldInEndMin2;
    private JTextField jTextFieldInEndMin3;
    private JTextField jTextFieldInEndMin4;
    private JTextField jTextFieldInEndMin5;
    private JTextField jTextFieldInEndMin6;
    private JTextField jTextFieldInEndMin7;
    private JTextField jTextFieldInEndMin8;
    private JTextField jTextFieldfHour1;
    private JTextField jTextFieldfHour2;
    private JTextField jTextFieldfHour3;
    private JTextField jTextFieldfHour4;
    private JTextField jTextFieldfHour5;
    private JTextField jTextFieldfHour6;
    private JTextField jTextFieldfHour7;
    private JTextField jTextFieldfHour8;
    private JTextField jTextFieldfMin1;
    private JTextField jTextFieldfMin2;
    private JTextField jTextFieldfMin3;
    private JTextField jTextFieldfMin4;
    private JTextField jTextFieldfMin5;
    private JTextField jTextFieldfMin6;
    private JTextField jTextFieldfMin7;
    private JTextField jTextFieldfMin8;
    private JTextField jTextFieldsHour1;
    private JTextField jTextFieldsHour2;
    private JTextField jTextFieldsHour3;
    private JTextField jTextFieldsHour4;
    private JTextField jTextFieldsHour5;
    private JTextField jTextFieldsHour6;
    private JTextField jTextFieldsHour7;
    private JTextField jTextFieldsHour8;
    private JTextField jTextFieldsMin1;
    private JTextField jTextFieldsMin2;
    private JTextField jTextFieldsMin3;
    private JTextField jTextFieldsMin4;
    private JTextField jTextFieldsMin5;
    private JTextField jTextFieldsMin6;
    private JTextField jTextFieldsMin7;
    private JTextField jTextFieldsMin8;
    private JToggleButton jToggleButtonSetupAlarmOut;

    // End of variables declaration//GEN-END:variables

    /******************************************************************************
     * 类: CheckListItemRenderer JCheckBox ListCellRenderer
     ******************************************************************************/
    public class CheckListItemRenderer extends JCheckBox implements
	    ListCellRenderer<Object> {
	private static final long serialVersionUID = -1165288764182362213L;

	public Component getListCellRendererComponent(JList<?> list,
		Object value, int index, boolean isSelected,
		boolean cellHasFocus) {
	    CheckListItem item = (CheckListItem) value;
	    this.setSelected(item.getCheck());
	    this.setText(item.getText());
	    this.setFont(list.getFont());
	    this.setEnabled(list.isEnabled());
	    return this;
	}
    }

    /******************************************************************************
     * 类: CheckListItem
     * 
     ******************************************************************************/
    public class CheckListItem {

	boolean check;
	String text;

	public CheckListItem(boolean check, String text) {
	    this.check = check;
	    this.text = text;
	}

	public boolean getCheck() {
	    return check;
	}

	public void setCheck(boolean _check) {
	    check = _check;
	}

	public String getText() {
	    return text;
	}

	public void setText(String _text) {
	    text = _text;
	}
    }

    /******************************************************************************
     * 类: CheckListMouseListener
     * 
     ******************************************************************************/
    class CheckListMouseListener extends MouseAdapter {

	public void mousePressed(MouseEvent e) {
	    JList<?> list = (JList<?>) e.getSource();
	    int index = list.locationToIndex(e.getPoint());
	    CheckListItem item = (CheckListItem) list.getModel().getElementAt(
		    index);
	    item.setCheck(!item.getCheck());
	    Rectangle rect = list.getCellBounds(index, index);
	    list.repaint(rect);
	}
    }
}
