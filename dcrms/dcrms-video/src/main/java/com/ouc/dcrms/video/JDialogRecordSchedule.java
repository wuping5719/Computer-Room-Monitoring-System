package com.ouc.dcrms.video;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ：JDialogRecordSchedule 
 * 类描述 ：定时录像设置
 ****************************************************************************/
public class JDialogRecordSchedule extends JDialog {
    private static final long serialVersionUID = 7078353798103797110L;

    private HCNetSDK.NET_DVR_RECORD_V30 m_struRemoteRecCfg;// 录像参数

    /*************************************************
     * 函数: JDialogRecordSchedule 
     * 函数描述: 构造函数 Creates new form JDialogRecordSchedule
     *************************************************/
    public JDialogRecordSchedule(java.awt.Dialog parent, boolean modal,
	    HCNetSDK.NET_DVR_RECORD_V30 struRemoteRecCfg) {
	super(parent, modal);
	m_struRemoteRecCfg = struRemoteRecCfg;
	initComponents();
    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jDialog1 = new JDialog();
	jLabel1 = new JLabel();
	jComboBoxWeekDay = new JComboBox<Object>();
	jCheckBoxAllDay = new JCheckBox();
	jLabel2 = new JLabel();
	jComboBoxRecordType = new JComboBox<Object>();
	jPanel1 = new JPanel();
	jLabel3 = new JLabel();
	jLabel4 = new JLabel();
	jLabel5 = new JLabel();
	jLabel6 = new JLabel();
	jTextFieldEndHour1 = new JTextField();
	jTextFieldBeginMinute1 = new JTextField();
	jLabel7 = new JLabel();
	jLabel8 = new JLabel();
	jTextFieldBeginHour1 = new JTextField();
	jLabel9 = new JLabel();
	jTextFieldEndMinute1 = new JTextField();
	jLabel10 = new JLabel();
	jLabel11 = new JLabel();
	jComboBoxRecordType1 = new JComboBox<Object>();
	jLabel12 = new JLabel();
	jTextFieldBeginHour2 = new JTextField();
	jLabel13 = new JLabel();
	jTextFieldBeginMinute2 = new JTextField();
	jLabel14 = new JLabel();
	jLabel15 = new JLabel();
	jTextFieldEndHour2 = new JTextField();
	jLabel16 = new JLabel();
	jTextFieldEndMinute2 = new JTextField();
	jLabel17 = new JLabel();
	jComboBoxRecordType2 = new JComboBox<Object>();
	jLabel18 = new JLabel();
	jTextFieldBeginHour3 = new JTextField();
	jLabel19 = new JLabel();
	jTextFieldBeginMinute3 = new JTextField();
	jLabel20 = new JLabel();
	jLabel21 = new JLabel();
	jTextFieldEndHour3 = new JTextField();
	jLabel22 = new JLabel();
	jTextFieldEndMinute3 = new JTextField();
	jLabel23 = new JLabel();
	jComboBoxRecordType3 = new JComboBox<Object>();
	jLabel24 = new JLabel();
	jTextFieldBeginHour4 = new JTextField();
	jLabel25 = new JLabel();
	jTextFieldBeginMinute4 = new JTextField();
	jLabel26 = new JLabel();
	jLabel27 = new JLabel();
	jTextFieldEndHour4 = new JTextField();
	jLabel28 = new JLabel();
	jTextFieldEndMinute4 = new JTextField();
	jLabel29 = new JLabel();
	jComboBoxRecordType4 = new JComboBox<Object>();
	jLabel30 = new JLabel();
	jTextFieldBeginHour5 = new JTextField();
	jLabel31 = new JLabel();
	jTextFieldBeginMinute5 = new JTextField();
	jLabel32 = new JLabel();
	jLabel33 = new JLabel();
	jTextFieldEndHour5 = new JTextField();
	jLabel34 = new JLabel();
	jTextFieldEndMinute5 = new JTextField();
	jLabel35 = new JLabel();
	jComboBoxRecordType5 = new JComboBox<Object>();
	jLabel36 = new JLabel();
	jTextFieldBeginHour6 = new JTextField();
	jLabel37 = new JLabel();
	jTextFieldBeginMinute6 = new JTextField();
	jLabel38 = new JLabel();
	jLabel39 = new JLabel();
	jTextFieldEndHour6 = new JTextField();
	jLabel40 = new JLabel();
	jTextFieldEndMinute6 = new JTextField();
	jLabel41 = new JLabel();
	jComboBoxRecordType6 = new JComboBox<Object>();
	jLabel42 = new JLabel();
	jTextFieldBeginHour7 = new JTextField();
	jLabel43 = new JLabel();
	jTextFieldBeginMinute7 = new JTextField();
	jLabel44 = new JLabel();
	jLabel45 = new JLabel();
	jTextFieldEndHour7 = new JTextField();
	jLabel46 = new JLabel();
	jTextFieldEndMinute7 = new JTextField();
	jLabel47 = new JLabel();
	jComboBoxRecordType7 = new JComboBox<Object>();
	jLabel48 = new JLabel();
	jTextFieldBeginHour8 = new JTextField();
	jLabel49 = new JLabel();
	jTextFieldBeginMinute8 = new JTextField();
	jLabel50 = new JLabel();
	jLabel51 = new JLabel();
	jTextFieldEndHour8 = new JTextField();
	jLabel52 = new JLabel();
	jTextFieldEndMinute8 = new JTextField();
	jLabel53 = new JLabel();
	jComboBoxRecordType8 = new JComboBox<Object>();
	jButtonSave = new JButton();
	jButtonExit = new JButton();

	GroupLayout jDialog1Layout = new GroupLayout(jDialog1.getContentPane());
	jDialog1.getContentPane().setLayout(jDialog1Layout);
	jDialog1Layout.setHorizontalGroup(jDialog1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
	jDialog1Layout.setVerticalGroup(jDialog1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("录像时间");
	getContentPane().setLayout(null);

	jLabel1.setText("日期");
	getContentPane().add(jLabel1);
	jLabel1.setBounds(20, 10, 40, 15);

	jComboBoxWeekDay
		.setModel(new DefaultComboBoxModel<Object>(new String[] {
			"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天" }));
	jComboBoxWeekDay.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jComboBoxWeekDayActionPerformed(evt);
	    }
	});
	getContentPane().add(jComboBoxWeekDay);
	jComboBoxWeekDay.setBounds(70, 10, 70, 21);

	jCheckBoxAllDay.setText("全天");
	jCheckBoxAllDay.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jCheckBoxAllDayActionPerformed(evt);
	    }
	});
	getContentPane().add(jCheckBoxAllDay);
	jCheckBoxAllDay.setBounds(160, 10, 70, 20);

	jLabel2.setText("录像类型");
	getContentPane().add(jLabel2);
	jLabel2.setBounds(240, 10, 70, 15);

	jComboBoxRecordType.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	getContentPane().add(jComboBoxRecordType);
	jComboBoxRecordType.setBounds(360, 10, 90, 21);

	jPanel1.setBorder(BorderFactory.createTitledBorder(""));
	jPanel1.setLayout(null);

	jLabel3.setText("起始时间");
	jPanel1.add(jLabel3);
	jLabel3.setBounds(70, 10, 60, 15);

	jLabel4.setText("终止时间");
	jPanel1.add(jLabel4);
	jLabel4.setBounds(220, 10, 60, 15);

	jLabel5.setText("录像类型");
	jPanel1.add(jLabel5);
	jLabel5.setBounds(350, 10, 70, 15);

	jLabel6.setText("时间段1");
	jPanel1.add(jLabel6);
	jLabel6.setBounds(10, 40, 50, 15);
	jPanel1.add(jTextFieldEndHour1);
	jTextFieldEndHour1.setBounds(220, 40, 30, 21);
	jPanel1.add(jTextFieldBeginMinute1);
	jTextFieldBeginMinute1.setBounds(120, 40, 30, 21);

	jLabel7.setText("时");
	jPanel1.add(jLabel7);
	jLabel7.setBounds(100, 40, 20, 15);

	jLabel8.setText("分");
	jPanel1.add(jLabel8);
	jLabel8.setBounds(320, 40, 20, 15);
	jPanel1.add(jTextFieldBeginHour1);
	jTextFieldBeginHour1.setBounds(60, 40, 30, 20);

	jLabel9.setText("时");
	jPanel1.add(jLabel9);
	jLabel9.setBounds(260, 40, 20, 15);
	jPanel1.add(jTextFieldEndMinute1);
	jTextFieldEndMinute1.setBounds(280, 40, 30, 21);

	jLabel10.setText("分");
	jPanel1.add(jLabel10);
	jLabel10.setBounds(160, 40, 20, 15);

	jLabel11.setText("到");
	jPanel1.add(jLabel11);
	jLabel11.setBounds(190, 40, 30, 15);

	jComboBoxRecordType1.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType1);
	jComboBoxRecordType1.setBounds(350, 40, 90, 21);

	jLabel12.setText("时间段2");
	jPanel1.add(jLabel12);
	jLabel12.setBounds(10, 70, 50, 15);
	jPanel1.add(jTextFieldBeginHour2);
	jTextFieldBeginHour2.setBounds(60, 70, 30, 20);

	jLabel13.setText("时");
	jPanel1.add(jLabel13);
	jLabel13.setBounds(100, 70, 20, 15);
	jPanel1.add(jTextFieldBeginMinute2);
	jTextFieldBeginMinute2.setBounds(120, 70, 30, 21);

	jLabel14.setText("分");
	jPanel1.add(jLabel14);
	jLabel14.setBounds(160, 70, 20, 15);

	jLabel15.setText("到");
	jPanel1.add(jLabel15);
	jLabel15.setBounds(190, 70, 20, 15);
	jPanel1.add(jTextFieldEndHour2);
	jTextFieldEndHour2.setBounds(220, 70, 30, 21);

	jLabel16.setText("时");
	jPanel1.add(jLabel16);
	jLabel16.setBounds(260, 70, 20, 15);
	jPanel1.add(jTextFieldEndMinute2);
	jTextFieldEndMinute2.setBounds(280, 70, 30, 21);

	jLabel17.setText("分");
	jPanel1.add(jLabel17);
	jLabel17.setBounds(320, 70, 20, 15);

	jComboBoxRecordType2.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType2);
	jComboBoxRecordType2.setBounds(350, 70, 90, 21);

	jLabel18.setText("时间段3");
	jPanel1.add(jLabel18);
	jLabel18.setBounds(10, 100, 50, 15);
	jPanel1.add(jTextFieldBeginHour3);
	jTextFieldBeginHour3.setBounds(60, 100, 30, 20);

	jLabel19.setText("时");
	jPanel1.add(jLabel19);
	jLabel19.setBounds(100, 100, 20, 15);
	jPanel1.add(jTextFieldBeginMinute3);
	jTextFieldBeginMinute3.setBounds(120, 100, 30, 21);

	jLabel20.setText("分");
	jPanel1.add(jLabel20);
	jLabel20.setBounds(160, 100, 20, 15);

	jLabel21.setText("到");
	jPanel1.add(jLabel21);
	jLabel21.setBounds(190, 100, 20, 15);
	jPanel1.add(jTextFieldEndHour3);
	jTextFieldEndHour3.setBounds(220, 100, 30, 21);

	jLabel22.setText("时");
	jPanel1.add(jLabel22);
	jLabel22.setBounds(260, 100, 20, 15);
	jPanel1.add(jTextFieldEndMinute3);
	jTextFieldEndMinute3.setBounds(280, 100, 30, 21);

	jLabel23.setText("分");
	jPanel1.add(jLabel23);
	jLabel23.setBounds(320, 100, 20, 15);

	jComboBoxRecordType3.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType3);
	jComboBoxRecordType3.setBounds(350, 100, 90, 21);

	jLabel24.setText("时间段4");
	jPanel1.add(jLabel24);
	jLabel24.setBounds(10, 130, 50, 15);
	jPanel1.add(jTextFieldBeginHour4);
	jTextFieldBeginHour4.setBounds(60, 130, 30, 20);

	jLabel25.setText("时");
	jPanel1.add(jLabel25);
	jLabel25.setBounds(100, 130, 20, 15);
	jPanel1.add(jTextFieldBeginMinute4);
	jTextFieldBeginMinute4.setBounds(120, 130, 30, 21);

	jLabel26.setText("分");
	jPanel1.add(jLabel26);
	jLabel26.setBounds(160, 130, 20, 15);

	jLabel27.setText("到");
	jPanel1.add(jLabel27);
	jLabel27.setBounds(190, 130, 20, 15);
	jPanel1.add(jTextFieldEndHour4);
	jTextFieldEndHour4.setBounds(220, 130, 30, 21);

	jLabel28.setText("时");
	jPanel1.add(jLabel28);
	jLabel28.setBounds(260, 130, 20, 15);
	jPanel1.add(jTextFieldEndMinute4);
	jTextFieldEndMinute4.setBounds(280, 130, 30, 21);

	jLabel29.setText("分");
	jPanel1.add(jLabel29);
	jLabel29.setBounds(320, 130, 20, 15);

	jComboBoxRecordType4.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType4);
	jComboBoxRecordType4.setBounds(350, 130, 90, 21);

	jLabel30.setText("时间段5");
	jPanel1.add(jLabel30);
	jLabel30.setBounds(10, 160, 50, 15);
	jPanel1.add(jTextFieldBeginHour5);
	jTextFieldBeginHour5.setBounds(60, 160, 30, 20);

	jLabel31.setText("时");
	jPanel1.add(jLabel31);
	jLabel31.setBounds(100, 160, 20, 15);
	jPanel1.add(jTextFieldBeginMinute5);
	jTextFieldBeginMinute5.setBounds(120, 160, 30, 21);

	jLabel32.setText("分");
	jPanel1.add(jLabel32);
	jLabel32.setBounds(160, 160, 20, 15);

	jLabel33.setText("到");
	jPanel1.add(jLabel33);
	jLabel33.setBounds(190, 160, 20, 15);
	jPanel1.add(jTextFieldEndHour5);
	jTextFieldEndHour5.setBounds(220, 160, 30, 21);

	jLabel34.setText("时");
	jPanel1.add(jLabel34);
	jLabel34.setBounds(260, 160, 20, 15);
	jPanel1.add(jTextFieldEndMinute5);
	jTextFieldEndMinute5.setBounds(280, 160, 30, 21);

	jLabel35.setText("分");
	jPanel1.add(jLabel35);
	jLabel35.setBounds(320, 160, 20, 15);

	jComboBoxRecordType5.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType5);
	jComboBoxRecordType5.setBounds(350, 160, 90, 21);

	jLabel36.setText("时间段6");
	jPanel1.add(jLabel36);
	jLabel36.setBounds(10, 190, 50, 15);
	jPanel1.add(jTextFieldBeginHour6);
	jTextFieldBeginHour6.setBounds(60, 190, 30, 20);

	jLabel37.setText("时");
	jPanel1.add(jLabel37);
	jLabel37.setBounds(100, 190, 20, 15);
	jPanel1.add(jTextFieldBeginMinute6);
	jTextFieldBeginMinute6.setBounds(120, 190, 30, 21);

	jLabel38.setText("分");
	jPanel1.add(jLabel38);
	jLabel38.setBounds(160, 190, 20, 15);

	jLabel39.setText("到");
	jPanel1.add(jLabel39);
	jLabel39.setBounds(190, 190, 20, 15);
	jPanel1.add(jTextFieldEndHour6);
	jTextFieldEndHour6.setBounds(220, 190, 30, 21);

	jLabel40.setText("时");
	jPanel1.add(jLabel40);
	jLabel40.setBounds(260, 190, 20, 15);
	jPanel1.add(jTextFieldEndMinute6);
	jTextFieldEndMinute6.setBounds(280, 190, 30, 21);

	jLabel41.setText("分");
	jPanel1.add(jLabel41);
	jLabel41.setBounds(320, 190, 20, 15);

	jComboBoxRecordType6.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType6);
	jComboBoxRecordType6.setBounds(350, 190, 90, 21);

	jLabel42.setText("时间段7");
	jPanel1.add(jLabel42);
	jLabel42.setBounds(10, 220, 50, 15);
	jPanel1.add(jTextFieldBeginHour7);
	jTextFieldBeginHour7.setBounds(60, 220, 30, 20);

	jLabel43.setText("时");
	jPanel1.add(jLabel43);
	jLabel43.setBounds(100, 220, 20, 15);
	jPanel1.add(jTextFieldBeginMinute7);
	jTextFieldBeginMinute7.setBounds(120, 220, 30, 21);

	jLabel44.setText("分");
	jPanel1.add(jLabel44);
	jLabel44.setBounds(160, 220, 20, 15);

	jLabel45.setText("到");
	jPanel1.add(jLabel45);
	jLabel45.setBounds(190, 220, 20, 15);
	jPanel1.add(jTextFieldEndHour7);
	jTextFieldEndHour7.setBounds(220, 220, 30, 21);

	jLabel46.setText("时");
	jPanel1.add(jLabel46);
	jLabel46.setBounds(260, 220, 20, 15);
	jPanel1.add(jTextFieldEndMinute7);
	jTextFieldEndMinute7.setBounds(280, 220, 30, 21);

	jLabel47.setText("分");
	jPanel1.add(jLabel47);
	jLabel47.setBounds(320, 220, 20, 15);

	jComboBoxRecordType7.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType7);
	jComboBoxRecordType7.setBounds(350, 220, 90, 21);

	jLabel48.setText("时间段8");
	jPanel1.add(jLabel48);
	jLabel48.setBounds(10, 250, 50, 15);
	jPanel1.add(jTextFieldBeginHour8);
	jTextFieldBeginHour8.setBounds(60, 250, 30, 20);

	jLabel49.setText("时");
	jPanel1.add(jLabel49);
	jLabel49.setBounds(100, 250, 20, 15);
	jPanel1.add(jTextFieldBeginMinute8);
	jTextFieldBeginMinute8.setBounds(120, 250, 30, 21);

	jLabel50.setText("分");
	jPanel1.add(jLabel50);
	jLabel50.setBounds(160, 250, 20, 15);

	jLabel51.setText("到");
	jPanel1.add(jLabel51);
	jLabel51.setBounds(190, 250, 30, 15);
	jPanel1.add(jTextFieldEndHour8);
	jTextFieldEndHour8.setBounds(220, 250, 30, 21);

	jLabel52.setText("时");
	jPanel1.add(jLabel52);
	jLabel52.setBounds(260, 250, 20, 15);
	jPanel1.add(jTextFieldEndMinute8);
	jTextFieldEndMinute8.setBounds(280, 250, 30, 21);

	jLabel53.setText("分");
	jPanel1.add(jLabel53);
	jLabel53.setBounds(320, 250, 20, 15);

	jComboBoxRecordType8.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "定时录像", "移动侦测", "报警录像", "动测|报警", "动测&报警",
			"命令触发", "智能录像" }));
	jPanel1.add(jComboBoxRecordType8);
	jComboBoxRecordType8.setBounds(350, 250, 90, 21);

	jButtonSave.setText("保存");
	jButtonSave.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSaveActionPerformed(evt);
	    }
	});
	jPanel1.add(jButtonSave);
	jButtonSave.setBounds(10, 280, 70, 23);

	getContentPane().add(jPanel1);
	jPanel1.setBounds(10, 40, 460, 310);

	jButtonExit.setText("退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});
	getContentPane().add(jButtonExit);
	jButtonExit.setBounds(20, 360, 70, 23);

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: 日期 组合框选项改变响应函数 函数描述: 显示对应日期的参数
     *************************************************/
    private void jComboBoxWeekDayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxWeekDayActionPerformed
	showRecordSchedule();
	setEnable();
    }// GEN-LAST:event_jComboBoxWeekDayActionPerformed

    /*************************************************
     * 函数: "全天" 单选按钮状态改变响应函数 函数描述: 设置控件的 enable 属性
     *************************************************/
    private void jCheckBoxAllDayActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jCheckBoxAllDayActionPerformed
	setEnable();
    }// GEN-LAST:event_jCheckBoxAllDayActionPerformed

    /*************************************************
     * 函数: "保存" 按钮单击响应函数 函数描述: 保存设置至结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSaveActionPerformed
	int iDay = jComboBoxWeekDay.getSelectedIndex();
	if (jCheckBoxAllDay.isSelected() == true) {
	    m_struRemoteRecCfg.struRecAllDay[iDay].wAllDayRecord = 1;
	    m_struRemoteRecCfg.struRecAllDay[iDay].byRecordType = (byte) this.jComboBoxRecordType
		    .getSelectedIndex();
	} else {
	    m_struRemoteRecCfg.struRecAllDay[iDay].wAllDayRecord = 0;
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour1.getText());

	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour2.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour3.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour4.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour5.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour6.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour7.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStartHour = (byte) Integer
		    .parseInt(jTextFieldBeginHour8.getText());

	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute1.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute2.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute3.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute4.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute5.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute6.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute7.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStartMin = (byte) Integer
		    .parseInt(jTextFieldBeginMinute8.getText());

	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour1.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour2.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour3.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour4.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour5.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour6.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour7.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStopHour = (byte) Integer
		    .parseInt(jTextFieldEndHour8.getText());

	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute1.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute2.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute3.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute4.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute5.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute6.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute7.getText());
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStopMin = (byte) Integer
		    .parseInt(jTextFieldEndMinute8.getText());

	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].byRecordType = (byte) jComboBoxRecordType1
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].byRecordType = (byte) jComboBoxRecordType2
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].byRecordType = (byte) jComboBoxRecordType3
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].byRecordType = (byte) jComboBoxRecordType4
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].byRecordType = (byte) jComboBoxRecordType5
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].byRecordType = (byte) jComboBoxRecordType6
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].byRecordType = (byte) jComboBoxRecordType7
		    .getSelectedIndex();
	    m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].byRecordType = (byte) jComboBoxRecordType8
		    .getSelectedIndex();
	}
    }// GEN-LAST:event_jButtonSaveActionPerformed

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExitActionPerformed
	this.dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
     * 函数: showRecordSchedule 函数描述: 显示录像时间参数
     *************************************************/
    void showRecordSchedule() {
	int iDay = this.jComboBoxWeekDay.getSelectedIndex();
	if (m_struRemoteRecCfg.struRecAllDay[iDay].wAllDayRecord == 0)// 不全天录像
	{
	    this.jCheckBoxAllDay.setSelected(false);
	    this.jComboBoxRecordType
		    .setSelectedIndex(m_struRemoteRecCfg.struRecAllDay[iDay].byRecordType);
	} else {
	    this.jCheckBoxAllDay.setSelected(true);
	    this.jComboBoxRecordType
		    .setSelectedIndex(m_struRemoteRecCfg.struRecAllDay[iDay].byRecordType);
	}
	this.jTextFieldBeginHour1
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour2
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour3
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour4
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour5
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour6
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour7
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStartHour
			+ "");
	this.jTextFieldBeginHour8
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStartHour
			+ "");

	this.jTextFieldBeginMinute1
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute2
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute3
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute4
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute5
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute6
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute7
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStartMin
			+ "");
	this.jTextFieldBeginMinute8
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStartMin
			+ "");

	this.jTextFieldEndHour1
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour2
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour3
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour4
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour5
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour6
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour7
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStopHour
			+ "");
	this.jTextFieldEndHour8
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStopHour
			+ "");

	this.jTextFieldEndMinute1
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute2
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute3
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute4
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute5
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute6
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute7
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].struRecordTime.byStopMin
			+ "");
	this.jTextFieldEndMinute8
		.setText(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].struRecordTime.byStopMin
			+ "");

	this.jComboBoxRecordType1
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[0].byRecordType);
	this.jComboBoxRecordType2
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[1].byRecordType);
	this.jComboBoxRecordType3
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[2].byRecordType);
	this.jComboBoxRecordType4
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[3].byRecordType);
	this.jComboBoxRecordType5
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[4].byRecordType);
	this.jComboBoxRecordType6
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[5].byRecordType);
	this.jComboBoxRecordType7
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[6].byRecordType);
	this.jComboBoxRecordType8
		.setSelectedIndex(m_struRemoteRecCfg.struRecordSched[iDay].struRecordSched[7].byRecordType);
    }

    /*************************************************
     * 函数: setEnable 函数描述: 设置控件 enable 属性
     *************************************************/
    private void setEnable() {
	if (jCheckBoxAllDay.isSelected()) {
	    this.jTextFieldBeginHour1.setEnabled(false);
	    this.jTextFieldBeginHour2.setEnabled(false);
	    this.jTextFieldBeginHour3.setEnabled(false);
	    this.jTextFieldBeginHour4.setEnabled(false);
	    this.jTextFieldBeginHour5.setEnabled(false);
	    this.jTextFieldBeginHour6.setEnabled(false);
	    this.jTextFieldBeginHour7.setEnabled(false);
	    this.jTextFieldBeginHour8.setEnabled(false);

	    this.jTextFieldBeginMinute1.setEnabled(false);
	    this.jTextFieldBeginMinute2.setEnabled(false);
	    this.jTextFieldBeginMinute3.setEnabled(false);
	    this.jTextFieldBeginMinute4.setEnabled(false);
	    this.jTextFieldBeginMinute5.setEnabled(false);
	    this.jTextFieldBeginMinute6.setEnabled(false);
	    this.jTextFieldBeginMinute7.setEnabled(false);
	    this.jTextFieldBeginMinute8.setEnabled(false);

	    this.jTextFieldEndHour1.setEnabled(false);
	    this.jTextFieldEndHour2.setEnabled(false);
	    this.jTextFieldEndHour3.setEnabled(false);
	    this.jTextFieldEndHour4.setEnabled(false);
	    this.jTextFieldEndHour5.setEnabled(false);
	    this.jTextFieldEndHour6.setEnabled(false);
	    this.jTextFieldEndHour7.setEnabled(false);
	    this.jTextFieldEndHour8.setEnabled(false);

	    this.jTextFieldEndMinute1.setEnabled(false);
	    this.jTextFieldEndMinute2.setEnabled(false);
	    this.jTextFieldEndMinute3.setEnabled(false);
	    this.jTextFieldEndMinute4.setEnabled(false);
	    this.jTextFieldEndMinute5.setEnabled(false);
	    this.jTextFieldEndMinute6.setEnabled(false);
	    this.jTextFieldEndMinute7.setEnabled(false);
	    this.jTextFieldEndMinute8.setEnabled(false);

	    this.jComboBoxRecordType1.setEnabled(false);
	    this.jComboBoxRecordType2.setEnabled(false);
	    this.jComboBoxRecordType3.setEnabled(false);
	    this.jComboBoxRecordType4.setEnabled(false);
	    this.jComboBoxRecordType5.setEnabled(false);
	    this.jComboBoxRecordType6.setEnabled(false);
	    this.jComboBoxRecordType7.setEnabled(false);
	    this.jComboBoxRecordType8.setEnabled(false);

	    this.jComboBoxRecordType.setEnabled(true);
	} else {
	    this.jTextFieldBeginHour1.setEnabled(true);
	    this.jTextFieldBeginHour2.setEnabled(true);
	    this.jTextFieldBeginHour3.setEnabled(true);
	    this.jTextFieldBeginHour4.setEnabled(true);
	    this.jTextFieldBeginHour5.setEnabled(true);
	    this.jTextFieldBeginHour6.setEnabled(true);
	    this.jTextFieldBeginHour7.setEnabled(true);
	    this.jTextFieldBeginHour8.setEnabled(true);

	    this.jTextFieldBeginMinute1.setEnabled(true);
	    this.jTextFieldBeginMinute2.setEnabled(true);
	    this.jTextFieldBeginMinute3.setEnabled(true);
	    this.jTextFieldBeginMinute4.setEnabled(true);
	    this.jTextFieldBeginMinute5.setEnabled(true);
	    this.jTextFieldBeginMinute6.setEnabled(true);
	    this.jTextFieldBeginMinute7.setEnabled(true);
	    this.jTextFieldBeginMinute8.setEnabled(true);

	    this.jTextFieldEndHour1.setEnabled(true);
	    this.jTextFieldEndHour2.setEnabled(true);
	    this.jTextFieldEndHour3.setEnabled(true);
	    this.jTextFieldEndHour4.setEnabled(true);
	    this.jTextFieldEndHour5.setEnabled(true);
	    this.jTextFieldEndHour6.setEnabled(true);
	    this.jTextFieldEndHour7.setEnabled(true);
	    this.jTextFieldEndHour8.setEnabled(true);

	    this.jTextFieldEndMinute1.setEnabled(true);
	    this.jTextFieldEndMinute2.setEnabled(true);
	    this.jTextFieldEndMinute3.setEnabled(true);
	    this.jTextFieldEndMinute4.setEnabled(true);
	    this.jTextFieldEndMinute5.setEnabled(true);
	    this.jTextFieldEndMinute6.setEnabled(true);
	    this.jTextFieldEndMinute7.setEnabled(true);
	    this.jTextFieldEndMinute8.setEnabled(true);

	    this.jComboBoxRecordType1.setEnabled(true);
	    this.jComboBoxRecordType2.setEnabled(true);
	    this.jComboBoxRecordType3.setEnabled(true);
	    this.jComboBoxRecordType4.setEnabled(true);
	    this.jComboBoxRecordType5.setEnabled(true);
	    this.jComboBoxRecordType6.setEnabled(true);
	    this.jComboBoxRecordType7.setEnabled(true);
	    this.jComboBoxRecordType8.setEnabled(true);

	    this.jComboBoxRecordType.setEnabled(false);
	}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonExit;
    private JButton jButtonSave;
    JCheckBox jCheckBoxAllDay;
    JComboBox<Object> jComboBoxRecordType;
    JComboBox<Object> jComboBoxRecordType1;
    JComboBox<Object> jComboBoxRecordType2;
    JComboBox<Object> jComboBoxRecordType3;
    JComboBox<Object> jComboBoxRecordType4;
    JComboBox<Object> jComboBoxRecordType5;
    JComboBox<Object> jComboBoxRecordType6;
    JComboBox<Object> jComboBoxRecordType7;
    JComboBox<Object> jComboBoxRecordType8;
    JComboBox<Object> jComboBoxWeekDay;
    private JDialog jDialog1;
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
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    JTextField jTextFieldBeginHour1;
    JTextField jTextFieldBeginHour2;
    JTextField jTextFieldBeginHour3;
    JTextField jTextFieldBeginHour4;
    JTextField jTextFieldBeginHour5;
    JTextField jTextFieldBeginHour6;
    JTextField jTextFieldBeginHour7;
    JTextField jTextFieldBeginHour8;
    JTextField jTextFieldBeginMinute1;
    JTextField jTextFieldBeginMinute2;
    JTextField jTextFieldBeginMinute3;
    JTextField jTextFieldBeginMinute4;
    JTextField jTextFieldBeginMinute5;
    JTextField jTextFieldBeginMinute6;
    JTextField jTextFieldBeginMinute7;
    JTextField jTextFieldBeginMinute8;
    JTextField jTextFieldEndHour1;
    JTextField jTextFieldEndHour2;
    JTextField jTextFieldEndHour3;
    JTextField jTextFieldEndHour4;
    JTextField jTextFieldEndHour5;
    JTextField jTextFieldEndHour6;
    JTextField jTextFieldEndHour7;
    JTextField jTextFieldEndHour8;
    JTextField jTextFieldEndMinute1;
    JTextField jTextFieldEndMinute2;
    JTextField jTextFieldEndMinute3;
    JTextField jTextFieldEndMinute4;
    JTextField jTextFieldEndMinute5;
    JTextField jTextFieldEndMinute6;
    JTextField jTextFieldEndMinute7;
    JTextField jTextFieldEndMinute8;
    // End of variables declaration//GEN-END:variables
}
