package com.ouc.dcrms.video;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import java.awt.Frame;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ：JDialogCheckTime 
 * 类描述 ：校时
 ****************************************************************************/
public class JDialogCheckTime extends JDialog {
    private static final long serialVersionUID = -5380001977380491613L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserId;// 用户ID

    /*************************************************
     * 函数: JDialogCheckTime 
     * 函数描述: 构造函数 Creates new form JDialogCheckTime
     *************************************************/
    public JDialogCheckTime(Frame parent, boolean modal,
	    NativeLong userId) {
	super(parent, modal);
	initComponents();
	m_lUserId = userId;
	Date today = new Date();
	Calendar now = Calendar.getInstance();// 日历对象 //得到当前日期
	now.setTime(today); // 设置进去
	jTextFieldYear.setText(now.get(Calendar.YEAR) + "");
	jTextFieldMonth.setText((now.get(Calendar.MONTH) + 1) + "");
	jTextFieldDay.setText(now.get(Calendar.DATE) + "");
	jTextFieldHour.setText(now.get(Calendar.HOUR_OF_DAY) + "");
	jTextFieldMinute.setText(now.get(Calendar.MINUTE) + "");
	jTextFieldSecond.setText(now.get(Calendar.SECOND) + "");

    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jPanel1 = new JPanel();
	jToggleButtonExit = new JToggleButton();
	jTextFieldDay = new JTextField();
	jToggleButtonCheckTime = new JToggleButton();
	jLabel2 = new JLabel();
	jTextFieldHour = new JTextField();
	jLabel3 = new JLabel();
	jTextFieldMonth = new JTextField();
	jLabel6 = new JLabel();
	jTextFieldYear = new JTextField();
	jLabel1 = new JLabel();
	jTextFieldMinute = new JTextField();
	jLabel4 = new JLabel();
	jTextFieldSecond = new JTextField();
	jLabel5 = new JLabel();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("校时");

	jPanel1.setBorder(BorderFactory.createTitledBorder(""));

	jToggleButtonExit.setText("退出");
	jToggleButtonExit
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jToggleButtonExitActionPerformed(evt);
		    }
		});

	jToggleButtonCheckTime.setText("校时");
	jToggleButtonCheckTime
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jToggleButtonCheckTimeActionPerformed(evt);
		    }
		});

	jLabel2.setText("月");

	jLabel3.setText("日");

	jLabel6.setText("秒");

	jLabel1.setText("年");

	jLabel4.setText("时");

	jLabel5.setText("分");

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				GroupLayout.Alignment.TRAILING,
				jPanel1Layout
					.createSequentialGroup()
					.addGap(17, 17, 17)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createParallelGroup(
										GroupLayout.Alignment.TRAILING)
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jTextFieldHour,
												GroupLayout.PREFERRED_SIZE,
												40,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel4,
												GroupLayout.PREFERRED_SIZE,
												27,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jTextFieldYear,
												GroupLayout.PREFERRED_SIZE,
												40,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.UNRELATED)
											.addComponent(
												jLabel1,
												GroupLayout.PREFERRED_SIZE,
												27,
												GroupLayout.PREFERRED_SIZE)))
							.addComponent(
								jToggleButtonCheckTime,
								0, 0,
								Short.MAX_VALUE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldMonth,
										GroupLayout.PREFERRED_SIZE,
										40,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel2,
										GroupLayout.PREFERRED_SIZE,
										27,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jTextFieldDay,
										GroupLayout.PREFERRED_SIZE,
										40,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel3,
										GroupLayout.PREFERRED_SIZE,
										27,
										GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jTextFieldMinute,
										GroupLayout.PREFERRED_SIZE,
										40,
										GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(
										jPanel1Layout
											.createParallelGroup(
												GroupLayout.Alignment.TRAILING,
												false)
											.addComponent(
												jToggleButtonExit,
												GroupLayout.Alignment.LEADING,
												0,
												0,
												Short.MAX_VALUE)
											.addGroup(
												GroupLayout.Alignment.LEADING,
												jPanel1Layout
													.createSequentialGroup()
													.addComponent(
														jLabel5,
														GroupLayout.PREFERRED_SIZE,
														27,
														GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
													.addComponent(
														jTextFieldSecond,
														GroupLayout.PREFERRED_SIZE,
														40,
														GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(
										LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										jLabel6,
										GroupLayout.PREFERRED_SIZE,
										27,
										GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()));
	jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		jPanel1Layout
			.createSequentialGroup()
			.addGap(20, 20, 20)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jTextFieldYear,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel1)
					.addComponent(jTextFieldMonth,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel2)
					.addComponent(jTextFieldDay,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel3))
			.addPreferredGap(
				LayoutStyle.ComponentPlacement.UNRELATED)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jTextFieldHour,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel4)
					.addComponent(jTextFieldMinute,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel5)
					.addComponent(jTextFieldSecond,
						GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
					.addComponent(jLabel6))
			.addGap(29, 29, 29)
			.addGroup(
				jPanel1Layout
					.createParallelGroup(
						GroupLayout.Alignment.BASELINE)
					.addComponent(jToggleButtonExit)
					.addComponent(jToggleButtonCheckTime))
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));

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
		layout.createSequentialGroup()
			.addContainerGap()
			.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE,
				GroupLayout.DEFAULT_SIZE,
				GroupLayout.PREFERRED_SIZE)
			.addContainerGap(GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE)));

	getAccessibleContext().setAccessibleName(null);

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: " 退出" 按钮响应函数 函数描述: 销毁对话框
     *************************************************/
    private void jToggleButtonExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleButtonExitActionPerformed
	this.dispose();
    }// GEN-LAST:event_jToggleButtonExitActionPerformed

    /*************************************************
     * 函数: "校时" 按钮响应函数 函数描述: 调用接口校时
     *************************************************/
    private void jToggleButtonCheckTimeActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jToggleButtonCheckTimeActionPerformed
	HCNetSDK.NET_DVR_TIME strCurTime = new HCNetSDK.NET_DVR_TIME();
	strCurTime.dwYear = Integer.parseInt(jTextFieldYear.getText());
	strCurTime.dwMonth = Integer.parseInt(jTextFieldMonth.getText());
	strCurTime.dwDay = Integer.parseInt(jTextFieldDay.getText());
	strCurTime.dwHour = Integer.parseInt(jTextFieldHour.getText());
	strCurTime.dwMinute = Integer.parseInt(jTextFieldMinute.getText());
	strCurTime.dwSecond = Integer.parseInt(jTextFieldSecond.getText());
	strCurTime.write();
	Pointer lpPicConfig = strCurTime.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(m_lUserId,
		HCNetSDK.NET_DVR_SET_TIMECFG, new NativeLong(0), lpPicConfig,
		strCurTime.size());
	strCurTime.read();
	if (setDVRConfigSuc != true) {
	    JOptionPane.showMessageDialog(this, "校时失败");
	    return;
	} else {
	    JOptionPane.showMessageDialog(this, "校时成功");
	    return;
	}

    }// GEN-LAST:event_jToggleButtonCheckTimeActionPerformed
     // Variables declaration - do not modify//GEN-BEGIN:variables

    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JTextField jTextFieldDay;
    private JTextField jTextFieldHour;
    private JTextField jTextFieldMinute;
    private JTextField jTextFieldMonth;
    private JTextField jTextFieldSecond;
    private JTextField jTextFieldYear;
    private JToggleButton jToggleButtonCheckTime;
    private JToggleButton jToggleButtonExit;
    // End of variables declaration//GEN-END:variables
}
