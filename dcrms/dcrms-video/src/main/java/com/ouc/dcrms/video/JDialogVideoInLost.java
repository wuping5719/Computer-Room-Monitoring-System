package com.ouc.dcrms.video;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ： JDialogVideoInLost 
 * 类描述 ：视频信号丢失
 ****************************************************************************/
public class JDialogVideoInLost extends JDialog {

    private static final long serialVersionUID = 6001287588851232044L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_PICCFG_V30 m_struPicCfg;// 图片参数
    private HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
    private CheckListItem m_traggerAlarmOut[] = new CheckListItem[HCNetSDK.MAX_ALARMOUT_V30];// 报警输出通道checkbox对应值
    private boolean m_bInitialed;// Dialog是否已初始化

    /*************************************************
     * 函数: JDialogVideoInLost 函数描述: 构造函数 Creates new form JDialogVideoInLost
     *************************************************/
    public JDialogVideoInLost(JDialog parent, boolean modal,
	    HCNetSDK.NET_DVR_PICCFG_V30 struPicCfg,
	    HCNetSDK.NET_DVR_DEVICEINFO_V30 strDeviceInfo) {
	super(parent, modal);
	initComponents();
	m_struPicCfg = struPicCfg;
	m_strDeviceInfo = strDeviceInfo;

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_traggerAlarmOut[i] = new CheckListItem(false, null);
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

	initialDialog();
	m_bInitialed = true;
    }

    /*************************************************
     * 函数: initialDialog 函数描述: 初始化对话框
     *************************************************/
    private void initialDialog() {
	jCheckBoxMonitorAlarm
		.setSelected((m_struPicCfg.struAULost.strVILostHandleType.dwHandleType & 0x01) == 1);
	jCheckBoxAudioAlarm
		.setSelected(((m_struPicCfg.struAULost.strVILostHandleType.dwHandleType >> 1) & 0x01) == 1);
	jCheckBoxCenter
		.setSelected(((m_struPicCfg.struAULost.strVILostHandleType.dwHandleType >> 2) & 0x01) == 1);
	jCheckBoxAlarmout
		.setSelected(((m_struPicCfg.struAULost.strVILostHandleType.dwHandleType >> 3) & 0x01) == 1);
	jCheckBoxJPEG
		.setSelected(((m_struPicCfg.struAULost.strVILostHandleType.dwHandleType >> 4) & 0x01) == 1);

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_traggerAlarmOut[i]
		    .setCheck(m_struPicCfg.struAULost.strVILostHandleType.byRelAlarmOut[i] == 1);
	}

	showAlarmTime();
    }

    /*************************************************
     * 函数: showAlarmTime 函数描述: 显示布防时间
     *************************************************/
    private void showAlarmTime() {
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	jTextFieldInBeginHour1
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour
			+ "");
	jTextFieldInBeginMin1
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin
			+ "");
	jTextFieldInEndHour1
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour
			+ "");
	jTextFieldInEndMin1
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin
			+ "");

	jTextFieldInBeginHour2
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour
			+ "");
	jTextFieldInBeginMin2
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin
			+ "");
	jTextFieldInEndHour2
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour
			+ "");
	jTextFieldInEndMin2
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin
			+ "");

	jTextFieldInBeginHour3
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour
			+ "");
	jTextFieldInBeginMin3
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin
			+ "");
	jTextFieldInEndHour3
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour
			+ "");
	jTextFieldInEndMin3
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin
			+ "");

	jTextFieldInBeginHour4
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour
			+ "");
	jTextFieldInBeginMin4
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin
			+ "");
	jTextFieldInEndHour4
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour
			+ "");
	jTextFieldInEndMin4
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin
			+ "");

	jTextFieldInBeginHour5
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour
			+ "");
	jTextFieldInBeginMin5
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin
			+ "");
	jTextFieldInEndHour5
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour
			+ "");
	jTextFieldInEndMin5
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin
			+ "");

	jTextFieldInBeginHour6
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour
			+ "");
	jTextFieldInBeginMin6
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin
			+ "");
	jTextFieldInEndHour6
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour
			+ "");
	jTextFieldInEndMin6
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin
			+ "");

	jTextFieldInBeginHour7
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour
			+ "");
	jTextFieldInBeginMin7
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin
			+ "");
	jTextFieldInEndHour7
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour
			+ "");
	jTextFieldInEndMin7
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin
			+ "");

	jTextFieldInBeginHour8
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour
			+ "");
	jTextFieldInBeginMin8
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin
			+ "");
	jTextFieldInEndHour8
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour
			+ "");
	jTextFieldInEndMin8
		.setText(m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin
			+ "");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jPanel1 = new JPanel();
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
	jButtonConfirm = new JButton();
	jComboBoxDate = new JComboBox<Object>();
	jLabel7 = new JLabel();
	jPanel4 = new JPanel();
	jCheckBoxMonitorAlarm = new JCheckBox();
	jCheckBoxCenter = new JCheckBox();
	jCheckBoxAudioAlarm = new JCheckBox();
	jCheckBoxJPEG = new JCheckBox();
	jCheckBoxAlarmout = new JCheckBox();
	jScrollPane3 = new JScrollPane();
	jListTraggerAlarmOut = new JList<CheckListItem>();
	jButtonExit = new JButton();
	jButtonSave = new JButton();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("视频信号丢失报警设置");

	jPanel1.setBorder(BorderFactory.createTitledBorder("布防时间"));

	jLabel8.setText("Time4");

	jLabel9.setText("Time3");

	jLabel10.setText("Time2");

	jLabel11.setText("Time5");

	jLabel12.setText("Time6");

	jLabel13.setText("Time7");

	jLabel14.setText("Time8");

	jLabel16.setText("Time1");

	jLabel15.setText("时");

	jLabel17.setText("分--");

	jLabel18.setText("时");

	jLabel19.setText("分");

	jLabel33.setText("分");

	jLabel34.setText("时");

	jLabel35.setText("分--");

	jLabel36.setText("时");

	jLabel37.setText("分");

	jLabel38.setText("时");

	jLabel39.setText("分--");

	jLabel40.setText("时");

	jLabel41.setText("分");

	jLabel42.setText("时");

	jLabel43.setText("分--");

	jLabel44.setText("时");

	jLabel45.setText("分");

	jLabel46.setText("时");

	jLabel47.setText("分--");

	jLabel48.setText("时");

	jLabel49.setText("分");

	jLabel50.setText("时");

	jLabel51.setText("分--");

	jLabel52.setText("时");

	jLabel53.setText("分");

	jLabel54.setText("时");

	jLabel55.setText("分--");

	jLabel56.setText("时");

	jLabel57.setText("分");

	jLabel58.setText("时");

	jLabel59.setText("分--");

	jLabel60.setText("时");

	jButtonConfirm.setText("确定");
	jButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonConfirmActionPerformed(evt);
	    }
	});

	jComboBoxDate.setModel(new DefaultComboBoxModel<Object>(new String[] {
		"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" }));
	jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jComboBoxDateActionPerformed(evt);
	    }
	});

	jLabel7.setText("日期");

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
									.addGroup(
										jPanel1Layout
											.createParallelGroup(
												GroupLayout.Alignment.LEADING)
											.addGroup(
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel16,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel17,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
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
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel10,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel35,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
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
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel9,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel39,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
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
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel8,
														GroupLayout.PREFERRED_SIZE,
														42,
														GroupLayout.PREFERRED_SIZE)
													.addGap(18,
														18,
														18)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel43,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
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
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel11,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInEndHour5,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel46,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInEndMin5,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel45,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel12,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
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
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInEndHour6,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel50,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInEndMin6,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel49,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel13,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
													.addComponent(
														jTextFieldInBeginHour7,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel56,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin7,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel55)
													.addGap(6,
														6,
														6)
													.addComponent(
														jTextFieldInEndHour7,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel54,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInEndMin7,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel53,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE))
											.addGroup(
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel14,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)
													.addGap(20,
														20,
														20)
													.addComponent(
														jTextFieldInBeginHour8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel60,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jTextFieldInBeginMin8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel59,
														GroupLayout.PREFERRED_SIZE,
														30,
														GroupLayout.PREFERRED_SIZE)
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
													.addComponent(
														jTextFieldInEndMin8,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addComponent(
														jLabel57,
														GroupLayout.PREFERRED_SIZE,
														20,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.RELATED)
													.addComponent(
														jButtonConfirm)))
									.addContainerGap(
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jLabel7,
										GroupLayout.PREFERRED_SIZE,
										50,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED,
										26,
										Short.MAX_VALUE)
									.addComponent(
										jComboBoxDate,
										GroupLayout.PREFERRED_SIZE,
										90,
										GroupLayout.PREFERRED_SIZE)
									.addGap(135,
										135,
										135)))));
	jPanel1Layout
		.setVerticalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel1Layout
					.createSequentialGroup()
					.addGroup(
						jPanel1Layout
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
						10, Short.MAX_VALUE)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel16)
							.addComponent(
								jTextFieldInBeginHour1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel18)
							.addComponent(
								jTextFieldInBeginMin1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel17)
							.addComponent(
								jTextFieldInEndHour1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel15)
							.addComponent(
								jTextFieldInEndMin1,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel19))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel10)
							.addComponent(
								jTextFieldInBeginHour2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel36)
							.addComponent(
								jTextFieldInBeginMin2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel35)
							.addComponent(
								jTextFieldInEndHour2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel34)
							.addComponent(
								jTextFieldInEndMin2,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel33))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel9)
							.addComponent(
								jTextFieldInBeginHour3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel40)
							.addComponent(
								jTextFieldInBeginMin3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel39)
							.addComponent(
								jTextFieldInEndHour3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel38)
							.addComponent(
								jTextFieldInEndMin3,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel37))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel8)
							.addComponent(
								jTextFieldInBeginHour4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel44)
							.addComponent(
								jTextFieldInBeginMin4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel43)
							.addComponent(
								jTextFieldInEndHour4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel42)
							.addComponent(
								jTextFieldInEndMin4,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel41))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel11)
							.addComponent(
								jTextFieldInBeginHour5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel48)
							.addComponent(
								jTextFieldInBeginMin5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel47)
							.addComponent(
								jTextFieldInEndHour5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel46)
							.addComponent(
								jTextFieldInEndMin5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel45))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel12)
							.addComponent(
								jTextFieldInBeginHour6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel52)
							.addComponent(
								jTextFieldInBeginMin6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel51)
							.addComponent(
								jTextFieldInEndHour6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel50)
							.addComponent(
								jTextFieldInEndMin6,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel49))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel13)
							.addComponent(
								jTextFieldInBeginHour7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel56)
							.addComponent(
								jTextFieldInBeginMin7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel55)
							.addComponent(
								jTextFieldInEndHour7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel54)
							.addComponent(
								jTextFieldInEndMin7,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel53))
					.addGap(9, 9, 9)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addComponent(jLabel14)
							.addComponent(
								jTextFieldInBeginHour8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel60)
							.addComponent(
								jTextFieldInBeginMin8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel59)
							.addComponent(
								jTextFieldInEndHour8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel58)
							.addComponent(
								jTextFieldInEndMin8,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addGroup(
								jPanel1Layout
									.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
									.addComponent(
										jLabel57)
									.addComponent(
										jButtonConfirm)))));

	jPanel4.setBorder(BorderFactory.createTitledBorder("报警处理方式"));

	jCheckBoxMonitorAlarm.setText("监视器警告");

	jCheckBoxCenter.setText("上传中心");

	jCheckBoxAudioAlarm.setText("声音警告");

	jCheckBoxJPEG.setText("Email JPEG");

	jCheckBoxAlarmout.setText("触发报警输出");

	jScrollPane3.setViewportView(jListTraggerAlarmOut);

	GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
	jPanel4.setLayout(jPanel4Layout);
	jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel4Layout
			.createSequentialGroup()
			.addContainerGap()
			.addGroup(
				jPanel4Layout
					.createParallelGroup(
						GroupLayout.Alignment.LEADING)
					.addComponent(jCheckBoxMonitorAlarm)
					.addComponent(jCheckBoxCenter)
					.addComponent(jCheckBoxJPEG)
					.addComponent(jCheckBoxAudioAlarm))
			.addGap(38, 38, 38)
			.addGroup(
				jPanel4Layout
					.createParallelGroup(
						GroupLayout.Alignment.LEADING)
					.addComponent(jCheckBoxAlarmout)
					.addComponent(jScrollPane3,
						GroupLayout.DEFAULT_SIZE, 172,
						Short.MAX_VALUE))
			.addContainerGap()));
	jPanel4Layout
		.setVerticalGroup(jPanel4Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel4Layout
					.createSequentialGroup()
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								jCheckBoxMonitorAlarm)
							.addComponent(
								jCheckBoxAlarmout))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel4Layout
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
								jScrollPane3,
								GroupLayout.DEFAULT_SIZE,
								86,
								Short.MAX_VALUE))));

	jButtonExit.setText("退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});

	jButtonSave.setText("确定");
	jButtonSave.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSaveActionPerformed(evt);
	    }
	});

	GroupLayout layout = new GroupLayout(getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup()
				.addGap(28, 28, 28)
				.addComponent(jButtonSave,
					GroupLayout.PREFERRED_SIZE, 70,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.RELATED,
					152, Short.MAX_VALUE)
				.addComponent(jButtonExit,
					GroupLayout.PREFERRED_SIZE, 70,
					GroupLayout.PREFERRED_SIZE)
				.addGap(27, 27, 27))
		.addGroup(
			GroupLayout.Alignment.TRAILING,
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPanel4,
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE).addContainerGap())
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPanel1, 0,
					GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE).addContainerGap()));
	layout.setVerticalGroup(layout
		.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPanel4,
					GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel1,
					GroupLayout.PREFERRED_SIZE,
					GroupLayout.DEFAULT_SIZE,
					GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(
					layout.createParallelGroup(
						GroupLayout.Alignment.LEADING)
						.addComponent(jButtonExit)
						.addComponent(jButtonSave))
				.addContainerGap(GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE)));

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "日期" 组合框 事件响应函数 函数描述: 显示对应日期的布防时间
     *************************************************/
    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jComboBoxDateActionPerformed
    {// GEN-HEADEREND:event_jComboBoxDateActionPerformed
	if (m_bInitialed) {
	    showAlarmTime();
	}
    }// GEN-LAST:event_jComboBoxDateActionPerformed

    /*************************************************
     * 函数: "确定" 按钮 单击 事件响应函数 函数描述: 保存设置到结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonSaveActionPerformed
    {// GEN-HEADEREND:event_jButtonSaveActionPerformed
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType = 0;
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType |= ((jCheckBoxMonitorAlarm
		.isSelected() ? 1 : 0) << 0);
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType |= ((jCheckBoxAudioAlarm
		.isSelected() ? 1 : 0) << 1);
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType |= ((jCheckBoxCenter
		.isSelected() ? 1 : 0) << 2);
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType |= ((jCheckBoxAlarmout
		.isSelected() ? 1 : 0) << 3);
	m_struPicCfg.struAULost.strVILostHandleType.dwHandleType |= ((jCheckBoxJPEG
		.isSelected() ? 1 : 0) << 4);

	for (int i = 0; i < HCNetSDK.MAX_ALARMOUT_V30; i++) {
	    m_struPicCfg.struAULost.strVILostHandleType.byRelAlarmOut[i] = (byte) (m_traggerAlarmOut[i]
		    .getCheck() ? 1 : 0);
	}
    }// GEN-LAST:event_jButtonSaveActionPerformed

    /*************************************************
     * 函数: 布防时间 "确定" 按钮 单击 事件响应函数 函数描述: 保存布防时间
     *************************************************/
    private void jButtonConfirmActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonConfirmActionPerformed
    {// GEN-HEADEREND:event_jButtonConfirmActionPerformed
	int iWeekDay = jComboBoxDate.getSelectedIndex();
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour1.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin1.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour1.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[0].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin1.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour2.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin2.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour2.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[1].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin2.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour3.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin3.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour3.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[2].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin3.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour4.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin4.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour4.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[3].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin4.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour5.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin5.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour5.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[4].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin5.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour6.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin6.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour6.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[5].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin6.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour7.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin7.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour7.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[6].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin7.getText());

	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStartHour = (byte) Integer
		.parseInt(jTextFieldInBeginHour8.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStartMin = (byte) Integer
		.parseInt(jTextFieldInBeginMin8.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStopHour = (byte) Integer
		.parseInt(jTextFieldInEndHour8.getText());
	m_struPicCfg.struAULost.struAlarmTime[iWeekDay].struAlarmTime[7].byStopMin = (byte) Integer
		.parseInt(jTextFieldInEndMin8.getText());
    }// GEN-LAST:event_jButtonConfirmActionPerformed

    /*************************************************
     * 函数: "退出" 按钮 单击事件响应函数 函数描述: 销毁对话框
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt)// GEN-FIRST:event_jButtonExitActionPerformed
    {// GEN-HEADEREND:event_jButtonExitActionPerformed
	dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonConfirm;
    private JButton jButtonExit;
    private JButton jButtonSave;
    private JCheckBox jCheckBoxAlarmout;
    private JCheckBox jCheckBoxAudioAlarm;
    private JCheckBox jCheckBoxCenter;
    private JCheckBox jCheckBoxJPEG;
    private JCheckBox jCheckBoxMonitorAlarm;
    private JComboBox<Object> jComboBoxDate;
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
    private JPanel jPanel1;
    private JPanel jPanel4;
    private JScrollPane jScrollPane3;
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

    // End of variables declaration//GEN-END:variables

    /******************************************************************************
     * 类: CheckListItemRenderer JCheckBox ListCellRenderer
     ******************************************************************************/
    public class CheckListItemRenderer extends JCheckBox implements
	    ListCellRenderer<Object> {
	private static final long serialVersionUID = -216428048279172393L;

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
