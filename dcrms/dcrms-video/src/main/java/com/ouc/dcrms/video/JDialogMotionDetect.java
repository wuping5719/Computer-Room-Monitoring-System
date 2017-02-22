/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDialogMotionDetect.java
 *
 * Created on 2009-12-10, 11:18:41
 */
/**
 *
 * @author Administrator
 */

package com.ouc.dcrms.video;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.examples.win32.GDI32.RECT;
import com.sun.jna.examples.win32.User32.POINT;
import com.sun.jna.examples.win32.W32API.HDC;
import com.sun.jna.examples.win32.W32API.HWND;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ：JDialogMotionDetect 
 * 类描述 ：移动侦测参数配置
 ****************************************************************************/
public class JDialogMotionDetect extends JDialog {
    private static final long serialVersionUID = 5441436682237685245L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    static GDI32 gDi = GDI32.INSTANCE;
    static USER32 uSer = USER32.INSTANCE;

    public static final int MAX_MOTION_NUM = 4;

    private HCNetSDK.NET_DVR_PICCFG_V30 m_struPicCfg;// 图像参数,初始化后直接指向通道配置里的图像参数结构体
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息参数
    private HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;// 预览参数

    private NativeLong m_lUserID;// 用户句柄
    private NativeLong m_lPlayHandle;// 预览句柄
    private int m_iChanShowNum;// 父窗口传来的通道号,预览此通道
    private boolean m_bInitialed;// 是否已经初始化

    private CheckListItem m_traggerAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];// 报警输出通道checkbox对应值
    private CheckListItem m_traggerRecord[] = new CheckListItem[HCNetSDK.MAX_CHANNUM_V30];// 触发录像通道checkbox对应值

    private boolean m_bDrawArea;// draw motion detect area
    private boolean m_bSetMotion;// set motion detect area

    private FDrawFunGet MotionDetectGetCallBack; // 显示移动侦测区域回调函数
    private FDrawFunSet MotionDetectSetCallBack;// 设置移动侦测区域回调函数

    private int g_iDetectIndex = 0; // motion detect zone index
    private int g_dwPrecision = 16;// the precision of the 22*11 detect unit
				   // under D1(704*576) resolution is 32*32, in
				   // the demothepicture is displayed in
				   // cif(352*288), and precision/2
    private RECT[] g_rectMotionDetectSet = new RECT[MAX_MOTION_NUM]; // motion
								     // detect
								     // zone
								     // display
								     // rectangle
    private RECT[] g_rectMotionDetectMouse = new RECT[MAX_MOTION_NUM];// mouse
								      // drawing
								      // line

    /*************************************************
     * 函数: JDialogMotionDetect 
     * 函数描述: 构造函数 Creates new form JDialogMotionDetect
     *************************************************/
    public JDialogMotionDetect(JDialog parent, boolean modal,
	    NativeLong lUserID, int iChannelNum,
	    HCNetSDK.NET_DVR_PICCFG_V30 struPicCfg,
	    HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo) {
	super(parent, modal);
	initComponents();

	m_lUserID = lUserID;
	m_iChanShowNum = iChannelNum;
	m_lPlayHandle = new NativeLong(-1);
	m_struPicCfg = struPicCfg;
	m_strDeviceInfo = strDeviceInfo;

	MotionDetectSetCallBack = new FDrawFunSet();
	MotionDetectGetCallBack = new FDrawFunGet();

	for (int i = 0; i < MAX_MOTION_NUM; i++) {
	    g_rectMotionDetectSet[i] = new RECT();
	    g_rectMotionDetectMouse[i] = new RECT();
	}

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_traggerAlarmOut[i] = new CheckListItem(false, null);
	}

	for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++) {
	    m_traggerRecord[i] = new CheckListItem(false, null);
	}

	// 初始化对话框,预览,list初始化等操作
	initialDialog();
    }

    /*************************************************
     * 函数: initialDialog 函数描述: 初始化对话框
     *************************************************/
    private void initialDialog() {
	// 移动侦测区域 预览
	HWND hwnd = new HWND(Native.getComponentPointer(panelPlay));
	m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
	m_strClientInfo.lChannel = new NativeLong(m_iChanShowNum);
	m_strClientInfo.hPlayWnd = hwnd;
	m_lPlayHandle = hCNetSDK.NET_DVR_RealPlay_V30(m_lUserID,
		m_strClientInfo, null, null, true);
	if (m_lPlayHandle.intValue() == -1) {
	    JOptionPane.showMessageDialog(this,
		    "预览失败,错误值:" + hCNetSDK.NET_DVR_GetLastError());
	}

	// 触发报警输出通道list
	jListTraggerAlarmOut.setCellRenderer(new CheckListItemRenderer());
	DefaultListModel<CheckListItem> listModelTraggerAlarmOut = new DefaultListModel<CheckListItem>();
	jListTraggerAlarmOut.setModel(listModelTraggerAlarmOut);
	jListTraggerAlarmOut.addMouseListener(new CheckListMouseListener());
	for (int i = 0; i < m_strDeviceInfo.byAlarmOutPortNum; i++) {
	    m_traggerAlarmOut[i] = new CheckListItem(false, "AlarmOut"
		    + (i + 1));
	    listModelTraggerAlarmOut.addElement(m_traggerAlarmOut[i]); // 为触发报警输出List增加报警输出
	}
	// 触发录像通道list
	jListTraggerRecord.setCellRenderer(new CheckListItemRenderer());
	DefaultListModel<CheckListItem> listModelTraggerRecord = new DefaultListModel<CheckListItem>();
	jListTraggerRecord.setModel(listModelTraggerRecord);
	jListTraggerRecord.addMouseListener(new CheckListMouseListener());
	for (int i = 0; i < m_strDeviceInfo.byChanNum; i++) {
	    m_traggerRecord[i] = new CheckListItem(false, "Camara" + (i + 1));
	    listModelTraggerRecord.addElement(m_traggerRecord[i]); // 为触发录像List增加报警输出
	}

	// 将数据反映到对话框
	initialData();

	m_bInitialed = true;
    }

    /*************************************************
     * 函数: initialData 函数描述: 显示初始数据
     *************************************************/
    private void initialData() {
	if (m_struPicCfg.struMotion.byMotionSensitive == -1) {
	    jComboBoxSensitive.setSelectedIndex(0);
	} else {
	    jComboBoxSensitive
		    .setSelectedIndex(m_struPicCfg.struMotion.byMotionSensitive);
	}

	jCheckBoxMonitorAlarm
		.setSelected((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType & 0x01) == 1);
	jCheckBoxAudioAlarm
		.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 1) & 0x01) == 1);
	jCheckBoxCenter
		.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 2) & 0x01) == 1);
	jCheckBoxAlarmout
		.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 3) & 0x01) == 1);
	jCheckBoxJPEG
		.setSelected(((m_struPicCfg.struMotion.struMotionHandleType.dwHandleType >> 4) & 0x01) == 1);

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_traggerAlarmOut[i]
		    .setCheck(m_struPicCfg.struMotion.struMotionHandleType.byRelAlarmOut[i] == 1);
	}

	for (int i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++) {
	    m_traggerRecord[i]
		    .setCheck(m_struPicCfg.struMotion.byRelRecordChan[i] == 1);
	}

	// 显示布防时间参数
	showAlarmTime();
    }

    /*************************************************
     * 函数: showAlarmTime 函数描述: 显示布防时间
     *************************************************/
    private void showAlarmTime() {
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	jTextFieldInBeginHour1
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour
			+ "");
	jTextFieldInBeginMin1
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin
			+ "");
	jTextFieldInEndHour1
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour
			+ "");
	jTextFieldInEndMin1
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin
			+ "");

	jTextFieldInBeginHour2
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour
			+ "");
	jTextFieldInBeginMin2
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin
			+ "");
	jTextFieldInEndHour2
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour
			+ "");
	jTextFieldInEndMin2
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin
			+ "");

	jTextFieldInBeginHour3
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour
			+ "");
	jTextFieldInBeginMin3
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin
			+ "");
	jTextFieldInEndHour3
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour
			+ "");
	jTextFieldInEndMin3
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin
			+ "");

	jTextFieldInBeginHour4
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour
			+ "");
	jTextFieldInBeginMin4
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin
			+ "");
	jTextFieldInEndHour4
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour
			+ "");
	jTextFieldInEndMin4
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin
			+ "");

	jTextFieldInBeginHour5
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour
			+ "");
	jTextFieldInBeginMin5
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin
			+ "");
	jTextFieldInEndHour5
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour
			+ "");
	jTextFieldInEndMin5
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin
			+ "");

	jTextFieldInBeginHour6
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour
			+ "");
	jTextFieldInBeginMin6
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin
			+ "");
	jTextFieldInEndHour6
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour
			+ "");
	jTextFieldInEndMin6
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin
			+ "");

	jTextFieldInBeginHour7
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour
			+ "");
	jTextFieldInBeginMin7
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin
			+ "");
	jTextFieldInEndHour7
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour
			+ "");
	jTextFieldInEndMin7
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin
			+ "");

	jTextFieldInBeginHour8
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour
			+ "");
	jTextFieldInBeginMin8
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin
			+ "");
	jTextFieldInEndHour8
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour
			+ "");
	jTextFieldInEndMin8
		.setText(m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin
			+ "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	panelPlay = new java.awt.Panel();
	jPanel1 = new JPanel();
	jCheckBoxGetMotionArea = new JCheckBox();
	jCheckBoxSetMotionArea = new JCheckBox();
	jLabel1 = new JLabel();
	jComboBoxSensitive = new JComboBox();
	jPanel2 = new JPanel();
	jScrollPane1 = new JScrollPane();
	jListTraggerRecord = new JList();
	jPanel3 = new JPanel();
	jCheckBoxMonitorAlarm = new JCheckBox();
	jCheckBoxCenter = new JCheckBox();
	jCheckBoxAudioAlarm = new JCheckBox();
	jCheckBoxJPEG = new JCheckBox();
	jCheckBoxAlarmout = new JCheckBox();
	jScrollPane2 = new JScrollPane();
	jListTraggerAlarmOut = new JList();
	jPanel4 = new JPanel();
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
	jButtonConfirm = new JButton();
	jLabel53 = new JLabel();
	jTextFieldInBeginHour7 = new JTextField();
	jLabel54 = new JLabel();
	jTextFieldInBeginMin7 = new JTextField();
	jTextFieldInEndMin7 = new JTextField();
	jLabel55 = new JLabel();
	jTextFieldInEndHour7 = new JTextField();
	jLabel56 = new JLabel();
	jTextFieldInEndHour8 = new JTextField();
	jLabel57 = new JLabel();
	jLabel58 = new JLabel();
	jTextFieldInEndMin8 = new JTextField();
	jLabel59 = new JLabel();
	jTextFieldInBeginHour8 = new JTextField();
	jLabel60 = new JLabel();
	jTextFieldInBeginMin8 = new JTextField();
	jComboBoxDate = new JComboBox();
	jLabel7 = new JLabel();
	jButtonSave = new JButton();
	jButtonExit = new JButton();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("移动侦测设置");

	panelPlay.setBackground(new java.awt.Color(204, 255, 255));
	panelPlay.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(java.awt.event.MouseEvent evt) {
		panelPlayMousePressed(evt);
	    }
	});
	panelPlay
		.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent evt) {
			panelPlayMouseDragged(evt);
		    }
		});
	panelPlay.setLayout(null);

	jPanel1.setBorder(BorderFactory.createTitledBorder("区域设置"));

	jCheckBoxGetMotionArea.setText("显示移动侦测区域");
	jCheckBoxGetMotionArea
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jCheckBoxGetMotionAreaActionPerformed(evt);
		    }
		});

	jCheckBoxSetMotionArea.setText("设置移动侦测区域");
	jCheckBoxSetMotionArea
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jCheckBoxSetMotionAreaActionPerformed(evt);
		    }
		});

	jLabel1.setText("灵敏度");

	jComboBoxSensitive.setModel(new DefaultComboBoxModel(new String[] {
		"关闭", "0--最低", "1", "2", "3", "4", "5--最高" }));

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap(
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxGetMotionArea)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addGap(21,
										21,
										21)
									.addComponent(
										jLabel1)))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxSetMotionArea)
							.addComponent(
								jComboBoxSensitive,
								GroupLayout.PREFERRED_SIZE,
								62,
								GroupLayout.PREFERRED_SIZE))));
	jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel1Layout
			.createSequentialGroup()
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jCheckBoxGetMotionArea)
					.addComponent(jCheckBoxSetMotionArea))
			.addGap(8, 8, 8)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jLabel1)
					.addComponent(jComboBoxSensitive,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
			.addContainerGap()));

	jPanel2.setBorder(BorderFactory.createTitledBorder("触发录像通道"));
	jPanel2.setPreferredSize(new java.awt.Dimension(304, 97));

	jScrollPane1.setViewportView(jListTraggerRecord);

	GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
	jPanel2.setLayout(jPanel2Layout);
	jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel2Layout
			.createSequentialGroup()
			.addContainerGap()
			.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE,
				227, GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));
	jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addComponent(jScrollPane1,
		GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE));

	jPanel3.setBorder(BorderFactory.createTitledBorder("报警处理方式"));

	jCheckBoxMonitorAlarm.setText("监视器警告");

	jCheckBoxCenter.setText("上传中心");

	jCheckBoxAudioAlarm.setText("声音警告");

	jCheckBoxJPEG.setText("Email JPEG");

	jCheckBoxAlarmout.setText("触发报警输出");

	jScrollPane2.setViewportView(jListTraggerAlarmOut);

	GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
	jPanel3.setLayout(jPanel3Layout);
	jPanel3Layout
		.setHorizontalGroup(jPanel3Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel3Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxMonitorAlarm)
							.addComponent(
								jCheckBoxCenter)
							.addComponent(
								jCheckBoxJPEG)
							.addComponent(
								jCheckBoxAudioAlarm))
					.addGap(28, 28, 28)
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(
								jCheckBoxAlarmout)
							.addComponent(
								jScrollPane2,
								GroupLayout.PREFERRED_SIZE,
								116,
								GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE)));
	jPanel3Layout
		.setVerticalGroup(jPanel3Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel3Layout
					.createSequentialGroup()
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								jCheckBoxMonitorAlarm)
							.addComponent(
								jCheckBoxAlarmout))
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
									.addGap(2,
										2,
										2)
									.addComponent(
										jCheckBoxCenter)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jCheckBoxJPEG))
							.addComponent(
								jScrollPane2,
								0, 0,
								Short.MAX_VALUE))));

	jPanel4.setBorder(BorderFactory.createTitledBorder("布防时间"));

	jLabel8.setText("时间段4");

	jLabel9.setText("时间段3");

	jLabel10.setText("时间段2");

	jLabel11.setText("时间段5");

	jLabel12.setText("时间段6");

	jLabel13.setText("时间段7");

	jLabel14.setText("时间段8");

	jLabel16.setText("时间段1");

	jLabel15.setText("时");

	jLabel17.setText("分 --");

	jLabel18.setText("时");

	jLabel19.setText("分");

	jLabel33.setText("分");

	jLabel34.setText("时");

	jLabel35.setText("分 --");

	jLabel36.setText("时");

	jLabel37.setText("分");

	jLabel38.setText("时");

	jLabel39.setText("分 --");

	jLabel40.setText("时");

	jLabel41.setText("分");

	jLabel42.setText("时");

	jLabel43.setText("分 --");

	jLabel44.setText("时");

	jLabel45.setText("分");

	jLabel46.setText("时");

	jLabel47.setText("分 --");

	jLabel48.setText("时");

	jLabel49.setText("分");

	jLabel50.setText("时");

	jLabel51.setText("分 --");

	jLabel52.setText("时");

	jButtonConfirm.setText("确定");
	jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonConfirmActionPerformed(evt);
	    }
	});

	jLabel53.setText("时");

	jLabel54.setText("分 --");

	jLabel55.setText("分");

	jLabel56.setText("时");

	jLabel57.setText("分");

	jLabel58.setText("时");

	jLabel59.setText("时");

	jLabel60.setText("分 --");

	jComboBoxDate.setModel(new DefaultComboBoxModel(new String[] { "星期一",
		"星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
	jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jComboBoxDateActionPerformed(evt);
	    }
	});

	jLabel7.setText("日期");

	GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
	jPanel4.setLayout(jPanel4Layout);
	jPanel4Layout
		.setHorizontalGroup(jPanel4Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel4Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING,
								false)
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING,
												false)
											.addComponent(
												jLabel9,
												GroupLayout.DEFAULT_SIZE,
												54,
												Short.MAX_VALUE)
											.addComponent(
												jLabel8,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												jLabel10,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
											.addComponent(
												jLabel16))
									.addGap(18,
										18,
										18)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour1,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel18,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin1,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour2,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel36,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin2,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour3,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel40,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin3,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour4,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel44,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin4,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jLabel17,
												GroupLayout.PREFERRED_SIZE,
												30,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel35,
												GroupLayout.PREFERRED_SIZE,
												30,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel39,
												GroupLayout.PREFERRED_SIZE,
												30,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel43,
												GroupLayout.PREFERRED_SIZE,
												30,
												GroupLayout.PREFERRED_SIZE)))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jLabel7,
										GroupLayout.PREFERRED_SIZE,
										50,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
									.addComponent(
										jComboBoxDate,
										GroupLayout.PREFERRED_SIZE,
										90,
										GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInEndHour1,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel15,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jTextFieldInEndMin1,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel19,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInEndHour2,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel34,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jTextFieldInEndMin2,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel33,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInEndHour3,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel38,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jTextFieldInEndMin3,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel37,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInEndHour4,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel42,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jTextFieldInEndMin4,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel41,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)))
					.addGap(60, 60, 60)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING,
								false)
							.addComponent(
								jLabel11,
								GroupLayout.DEFAULT_SIZE,
								52,
								Short.MAX_VALUE)
							.addComponent(
								jLabel12,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
							.addComponent(
								jLabel13,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
							.addComponent(jLabel14))
					.addGap(18, 18, 18)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour5,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel48,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin5,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel47,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour6,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel52,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin6,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel51,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)))
									.addGap(18,
										18,
										18)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInEndHour5,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel46,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInEndHour6,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel50,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInBeginHour7,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel53,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jTextFieldInBeginMin7,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel54,
										GroupLayout.PREFERRED_SIZE,
										30,
										GroupLayout.PREFERRED_SIZE)
									.addGap(18,
										18,
										18)
									.addComponent(
										jTextFieldInEndHour7,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE)
									.addComponent(
										jLabel56,
										GroupLayout.PREFERRED_SIZE,
										20,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
											.addGroup(
												GroupLayout.Alignment.LEADING,
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jTextFieldInBeginHour8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel59,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel60,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
													.addGap(18,
														18,
														18)
													.addComponent(
														jTextFieldInEndHour8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel58,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addGap(6,
														6,
														6)
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInEndMin5,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jTextFieldInEndMin6,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jTextFieldInEndMin7,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jTextFieldInEndMin8,
																GroupLayout.PREFERRED_SIZE,
																20,
																GroupLayout.PREFERRED_SIZE)))
											.addComponent(
												jButtonConfirm))
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jLabel45,
												GroupLayout.PREFERRED_SIZE,
												20,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel57,
												GroupLayout.PREFERRED_SIZE,
												20,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel55,
												GroupLayout.PREFERRED_SIZE,
												20,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel49,
												GroupLayout.PREFERRED_SIZE,
												20,
												GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(19, Short.MAX_VALUE)));
	jPanel4Layout
		.setVerticalGroup(jPanel4Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel4Layout
					.createSequentialGroup()
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel7)
							.addComponent(
								jComboBoxDate,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInEndHour1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel15)
											.addComponent(
												jTextFieldInEndMin1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel19))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInEndHour2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel34)
											.addComponent(
												jTextFieldInEndMin2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel33))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInEndHour3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel38)
											.addComponent(
												jTextFieldInEndMin3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel37))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInEndHour4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel42)
											.addComponent(
												jTextFieldInEndMin4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel41)))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jLabel17)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel35)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel39)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel43))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInBeginHour1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel18)
											.addComponent(
												jTextFieldInBeginMin1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInBeginHour2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel36)
											.addComponent(
												jTextFieldInBeginMin2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInBeginHour3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel40)
											.addComponent(
												jTextFieldInBeginMin3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addComponent(
												jTextFieldInBeginHour4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel44)
											.addComponent(
												jTextFieldInBeginMin4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jLabel16)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel10)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel9)
									.addGap(15,
										15,
										15)
									.addComponent(
										jLabel8))
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jLabel11)
													.addGap(15,
														15,
														15)
													.addComponent(
														jLabel12))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInEndHour5,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel46))
													.addGap(9,
														9,
														9)
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInEndHour6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel50)))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInBeginHour5,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel48)
															.addComponent(
																jTextFieldInBeginMin5,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel47))
													.addGap(9,
														9,
														9)
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInBeginHour6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel52)
															.addComponent(
																jTextFieldInBeginMin6,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel51))))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addComponent(
														jLabel13)
													.addGap(15,
														15,
														15)
													.addComponent(
														jLabel14))
											.addGroup(
												jPanel4Layout
													.createSequentialGroup()
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInEndHour7,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel56)
															.addComponent(
																jTextFieldInBeginHour7,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel53)
															.addComponent(
																jTextFieldInBeginMin7,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel54))
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addGroup(
														jPanel4Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addComponent(
																jTextFieldInEndHour8,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel58)
															.addComponent(
																jTextFieldInBeginHour8,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel59)
															.addComponent(
																jTextFieldInBeginMin8,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
															.addComponent(
																jLabel60)))))
							.addComponent(jLabel45)
							.addGroup(
								jPanel4Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldInEndMin5,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
											.addComponent(
												jTextFieldInEndMin6,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel49))
									.addGap(9,
										9,
										9)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
											.addComponent(
												jTextFieldInEndMin7,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel55))
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										jPanel4Layout
											.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
											.addComponent(
												jTextFieldInEndMin8,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
											.addComponent(
												jLabel57))))
					.addGap(10, 10, 10)
					.addComponent(jButtonConfirm)));

	jButtonSave.setText("确定");
	jButtonSave.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSaveActionPerformed(evt);
	    }
	});

	jButtonExit.setText("退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(panelPlay,
					GroupLayout.PREFERRED_SIZE, 352,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(
							jPanel1,
							GroupLayout.PREFERRED_SIZE,
							GroupLayout.DEFAULT_SIZE,
							GroupLayout.PREFERRED_SIZE)
						.addGroup(
							layout.createParallelGroup(
								GroupLayout.Alignment.TRAILING,
								false)
								.addComponent(
									jPanel3,
									GroupLayout.Alignment.LEADING,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
								.addComponent(
									jPanel2,
									GroupLayout.Alignment.LEADING,
									GroupLayout.DEFAULT_SIZE,
									263,
									Short.MAX_VALUE))))
		.addGroup(
			layout.createSequentialGroup()
				.addGap(9, 9, 9)
				.addComponent(jPanel4,
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE))
		.addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup()
				.addContainerGap(449, Short.MAX_VALUE)
				.addComponent(jButtonSave).addGap(26, 26, 26)
				.addComponent(jButtonExit).addGap(49, 49, 49)));
	layout.setVerticalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING,
						false)
						.addGroup(
							layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(
									jPanel1,
									GroupLayout.PREFERRED_SIZE,
									89,
									GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									jPanel2,
									GroupLayout.PREFERRED_SIZE,
									90,
									GroupLayout.PREFERRED_SIZE)
								.addGap(1, 1, 1)
								.addComponent(
									jPanel3,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE))
						.addGroup(
							GroupLayout.Alignment.TRAILING,
							layout.createSequentialGroup()
								.addContainerGap(
									GroupLayout.DEFAULT_SIZE,
									Short.MAX_VALUE)
								.addComponent(
									panelPlay,
									GroupLayout.PREFERRED_SIZE,
									288,
									GroupLayout.PREFERRED_SIZE)
								.addGap(19, 19,
									19)))
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel4,
					GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
						.addComponent(jButtonSave)
						.addComponent(jButtonExit))
				.addContainerGap(14, Short.MAX_VALUE)));

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "日期" 组合框事件响应函数 函数描述: 显示对应日期布防时间
     *************************************************/
    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jComboBoxDateActionPerformed
    {// GEN-HEADEREND:event_jComboBoxDateActionPerformed
	if (m_bInitialed) {
	    showAlarmTime();
	}
    }// GEN-LAST:event_jComboBoxDateActionPerformed

    /*************************************************
     * 函数: "显示移动侦测区域" 多选框事件响应函数 函数描述: 显示移动侦测区域
     *************************************************/
    private void jCheckBoxGetMotionAreaActionPerformed(
	    java.awt.event.ActionEvent evt)// GEN-FIRST:event_jCheckBoxGetMotionAreaActionPerformed
    {// GEN-HEADEREND:event_jCheckBoxGetMotionAreaActionPerformed
	if (m_lPlayHandle.intValue() < 0) {
	    return;
	}
	if (jCheckBoxSetMotionArea.isSelected()) {
	    jCheckBoxSetMotionArea.setSelected(false);
	    m_bDrawArea = false;
	}
	if (jCheckBoxGetMotionArea.isSelected()) {
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);

	    try {
		Thread.sleep(200);
	    } catch (InterruptedException ex) {
		Logger.getLogger(JDialogMotionDetect.class.getName()).log(
			Level.SEVERE, null, ex);
	    }

	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,
		    MotionDetectGetCallBack, 0);
	} else {
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
	}
    }// GEN-LAST:event_jCheckBoxGetMotionAreaActionPerformed

    /*************************************************
     * 函数: "设置移动侦测区域" 多选框事件响应函数 函数描述: 设置移动侦测区域
     *************************************************/
    private void jCheckBoxSetMotionAreaActionPerformed(
	    java.awt.event.ActionEvent evt)// GEN-FIRST:event_jCheckBoxSetMotionAreaActionPerformed
    {// GEN-HEADEREND:event_jCheckBoxSetMotionAreaActionPerformed
     // Set motion detect zone
	if (m_lPlayHandle.intValue() < 0) {
	    return;
	}
	if (jCheckBoxGetMotionArea.isSelected()) {
	    jCheckBoxGetMotionArea.setSelected(false);
	}
	if (jCheckBoxSetMotionArea.isSelected()) {
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
	    g_iDetectIndex = 0;
	    m_bDrawArea = true;
	    m_bSetMotion = true;
	} else {
	    m_bDrawArea = false;
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
	}
    }// GEN-LAST:event_jCheckBoxSetMotionAreaActionPerformed

    /*************************************************
     * 函数: 预览窗口单击 事件响应函数 函数描述: 注册回调函数开始画图
     *************************************************/
    private void panelPlayMousePressed(java.awt.event.MouseEvent evt)// GEN-FIRST:event_panelPlayMousePressed
    {// GEN-HEADEREND:event_panelPlayMousePressed
	if (m_bDrawArea) {
	    POINT point = new POINT(evt.getX(), evt.getY());
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle,
		    MotionDetectSetCallBack, 0);
	    if (g_iDetectIndex >= MAX_MOTION_NUM) {
		g_iDetectIndex = 0;
	    }

	    g_rectMotionDetectMouse[g_iDetectIndex].left = point.x
		    / g_dwPrecision * g_dwPrecision;
	    g_rectMotionDetectMouse[g_iDetectIndex].top = point.y
		    / g_dwPrecision * g_dwPrecision;
	    g_rectMotionDetectMouse[g_iDetectIndex].right = g_rectMotionDetectMouse[g_iDetectIndex].left;
	    g_rectMotionDetectMouse[g_iDetectIndex].bottom = g_rectMotionDetectMouse[g_iDetectIndex].top;

	    g_rectMotionDetectSet[g_iDetectIndex].left = point.x
		    / g_dwPrecision * g_dwPrecision;
	    g_rectMotionDetectSet[g_iDetectIndex].top = point.y / g_dwPrecision
		    * g_dwPrecision;

	    g_rectMotionDetectSet[g_iDetectIndex].right = point.x
		    / g_dwPrecision * g_dwPrecision + 1;
	    g_rectMotionDetectSet[g_iDetectIndex].bottom = point.y
		    / g_dwPrecision * g_dwPrecision + 1;
	    g_iDetectIndex++;
	}
    }// GEN-LAST:event_panelPlayMousePressed

    /*************************************************
     * 函数: 预览窗口鼠标按下移动 事件响应函数 函数描述: 画框
     *************************************************/
    private void panelPlayMouseDragged(java.awt.event.MouseEvent evt)// GEN-FIRST:event_panelPlayMouseDragged
    {// GEN-HEADEREND:event_panelPlayMouseDragged
	if (m_bDrawArea) {
	    POINT point = new POINT(evt.getX(), evt.getY());
	    g_rectMotionDetectMouse[g_iDetectIndex - 1].right = point.x
		    / g_dwPrecision * g_dwPrecision;
	    g_rectMotionDetectMouse[g_iDetectIndex - 1].bottom = point.y
		    / g_dwPrecision * g_dwPrecision;

	    g_rectMotionDetectSet[g_iDetectIndex - 1].right = point.x
		    / g_dwPrecision * g_dwPrecision;
	    g_rectMotionDetectSet[g_iDetectIndex - 1].bottom = point.y
		    / g_dwPrecision * g_dwPrecision;
	}
    }// GEN-LAST:event_panelPlayMouseDragged

    /*************************************************
     * 函数: 布防时间 "确定" 按钮 单击 事件响应函数 函数描述: 保存对应日期布防时间
     *************************************************/
    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonConfirmActionPerformed
    {// GEN-HEADEREND:event_jButtonConfirmActionPerformed
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour1.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin1.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour1.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin1.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour2.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin2.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour2.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin2.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour3.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin3.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour3.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin3.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour4.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin4.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour4.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin4.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour5.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin5.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour5.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin5.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour6.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin6.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour6.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin6.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour7.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin7.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour7.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin7.getText());

	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour8.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin8.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour8.getText());
	m_struPicCfg.struMotion.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin8.getText());
    }// GEN-LAST:event_jButtonConfirmActionPerformed

    /*************************************************
     * 函数: "退出" 按钮 单击事件响应函数 函数描述: 销毁对话框
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonExitActionPerformed
    {// GEN-HEADEREND:event_jButtonExitActionPerformed
	if (m_lPlayHandle.intValue() >= 0) {
	    hCNetSDK.NET_DVR_RigisterDrawFun(m_lPlayHandle, null, 0);
	    hCNetSDK.NET_DVR_StopRealPlay(m_lPlayHandle);
	    m_lPlayHandle.setValue(-1);
	}
	dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
     * 函数: "确定" 按钮 单击事件响应函数 函数描述: 保存设置到结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonSaveActionPerformed
    {// GEN-HEADEREND:event_jButtonSaveActionPerformed
	int i = 0, j = 0, k = 0;
	if (m_bSetMotion) {
	    // clear
	    for (i = 0; i < 64; i++) {
		for (j = 0; j < 96; j++) {
		    m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 0;
		}
	    }
	    // save zone settings on the device
	    for (k = 0; k < g_iDetectIndex; k++) {
		if (g_rectMotionDetectMouse[k].top <= g_rectMotionDetectMouse[k].bottom) {
		    if (g_rectMotionDetectMouse[k].left <= g_rectMotionDetectMouse[k].right) {// draw
											      // from
											      // top-left
											      // to
											      // bottom-right
			for (i = g_rectMotionDetectMouse[k].top / g_dwPrecision; i < 64
				&& i < g_rectMotionDetectMouse[k].bottom
					/ g_dwPrecision; i++) {
			    for (j = g_rectMotionDetectMouse[k].left
				    / g_dwPrecision; j < g_rectMotionDetectMouse[k].right
				    / g_dwPrecision; j++) {
				m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
			    }
			}
		    } else {// draw from top-right to bottom-left
			for (i = g_rectMotionDetectMouse[k].top / g_dwPrecision; i < 64
				&& i < g_rectMotionDetectMouse[k].bottom
					/ g_dwPrecision; i++) {
			    for (j = g_rectMotionDetectMouse[k].right
				    / g_dwPrecision; j < g_rectMotionDetectMouse[k].left
				    / g_dwPrecision; j++) {
				m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
			    }
			}
		    }
		} else {
		    if (g_rectMotionDetectMouse[k].left <= g_rectMotionDetectMouse[k].right) {// draw
											      // from
											      // bottom-left
											      // to
											      // top-right
			for (i = g_rectMotionDetectMouse[k].bottom
				/ g_dwPrecision; i < g_rectMotionDetectMouse[k].top
				/ g_dwPrecision; i++) {
			    for (j = g_rectMotionDetectMouse[k].left
				    / g_dwPrecision; j < g_rectMotionDetectMouse[k].right
				    / g_dwPrecision; j++) {
				m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
			    }
			}
		    } else {// draw from bottom-right to top-left
			for (i = g_rectMotionDetectMouse[k].bottom
				/ g_dwPrecision; i < g_rectMotionDetectMouse[k].top
				/ g_dwPrecision; i++) {
			    for (j = g_rectMotionDetectMouse[k].right
				    / g_dwPrecision; j < g_rectMotionDetectMouse[k].left
				    / g_dwPrecision; j++) {
				m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] = 1;
			    }
			}
		    }
		}
	    }
	}

	if (jComboBoxSensitive.getSelectedIndex() == 0) {
	    m_struPicCfg.struMotion.byMotionSensitive = -1;
	} else {
	    m_struPicCfg.struMotion.byMotionSensitive = (byte) (jComboBoxSensitive
		    .getSelectedIndex() - 1);
	}

	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType = 0;
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxMonitorAlarm
		.isSelected() ? 1 : 0) << 0);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxAudioAlarm
		.isSelected() ? 1 : 0) << 1);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxCenter
		.isSelected() ? 1 : 0) << 2);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxAlarmout
		.isSelected() ? 1 : 0) << 3);
	m_struPicCfg.struMotion.struMotionHandleType.dwHandleType |= ((jCheckBoxJPEG
		.isSelected() ? 1 : 0) << 4);

	for (i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_struPicCfg.struMotion.struMotionHandleType.byRelAlarmOut[i] = (byte) (m_traggerAlarmOut[i]
		    .getCheck() ? 1 : 0);
	}

	for (i = 0; i < HCNetSDK.MAX_CHANNUM_V30; i++) {
	    m_struPicCfg.struMotion.byRelRecordChan[i] = (byte) (m_traggerRecord[i]
		    .getCheck() ? 1 : 0);
	}
    }// GEN-LAST:event_jButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonConfirm;
    private JButton jButtonExit;
    private JButton jButtonSave;
    private JCheckBox jCheckBoxAlarmout;
    private JCheckBox jCheckBoxAudioAlarm;
    private JCheckBox jCheckBoxCenter;
    private JCheckBox jCheckBoxGetMotionArea;
    private JCheckBox jCheckBoxJPEG;
    private JCheckBox jCheckBoxMonitorAlarm;
    private JCheckBox jCheckBoxSetMotionArea;
    private JComboBox<?> jComboBoxDate;
    private JComboBox<?> jComboBoxSensitive;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel33;
    private JLabel jLabel34;
    private JLabel jLabel35;
    private JLabel jLabel36;
    private JLabel jLabel37;
    private JLabel jLabel38;
    private JLabel jLabel39;
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
    private JLabel jLabel60;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JList<CheckListItem> jListTraggerAlarmOut;
    private JList<CheckListItem> jListTraggerRecord;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
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
    private Panel panelPlay;

    // End of variables declaration//GEN-END:variables

    /*************************************************
     * 类: FDrawFunSet 函数描述: 设置移动侦测区域回调函数
     *************************************************/
    class FDrawFunSet implements HCNetSDK.FDrawFun {
	public void invoke(NativeLong lRealHandle, HDC hDc, int dwUser) {
	    int i = 0;
	    for (i = 0; i < g_iDetectIndex; i++) {
		uSer.DrawEdge(hDc, g_rectMotionDetectSet[i],
			USER32.BDR_SUNKENOUTER, USER32.BF_RECT);
	    }
	    gDi.SetBkMode(hDc, GDI32.TRANSPARENT);
	}
    }

    /*************************************************
     * 类: FDrawFunGet 函数描述: 显示移动侦测区域回调函数
     *************************************************/
    class FDrawFunGet implements HCNetSDK.FDrawFun {
	public void invoke(NativeLong lRealHandle, HDC hDc, int dwUser) {
	    RECT rect = new RECT();
	    int i = 0, j = 0;
	    POINT point = new POINT();
	    for (i = 0; i < 64; i++) {
		for (j = 0; j < 96; j++) {
		    if (m_struPicCfg.struMotion.byMotionScope[i].byMotionScope[j] == 1) {
			point.x = j * g_dwPrecision;
			point.y = i * g_dwPrecision;
			rect.left = point.x;
			rect.top = point.y;
			rect.right = point.x + g_dwPrecision;
			rect.bottom = point.y + g_dwPrecision;
			uSer.DrawEdge(hDc, rect, USER32.BDR_SUNKENOUTER,
				USER32.BF_RECT);
		    }
		}
	    }
	    gDi.SetBkMode(hDc, GDI32.TRANSPARENT);
	}
    }

    /******************************************************************************
     * 类: CheckListItemRenderer JCheckBox ListCellRenderer
     ******************************************************************************/
    public class CheckListItemRenderer extends JCheckBox implements
	    ListCellRenderer<Object> {
	private static final long serialVersionUID = 6330888477638661940L;

	public Component getListCellRendererComponent(
		@SuppressWarnings("rawtypes") JList list, Object value,
		int index, boolean isSelected, boolean cellHasFocus) {
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
