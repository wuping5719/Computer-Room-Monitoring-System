package com.ouc.dcrms.video;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

/*****************************************************************************
 * 类 ： JDialogShowString 
 * 类描述 ：设置叠加字符
 ****************************************************************************/
public class JDialogShowString extends JDialog {
    
    private static final long serialVersionUID = 3365064933954145757L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private HCNetSDK.NET_DVR_SHOWSTRING_V30 m_strShowString;// 叠加字符结构体
    private NativeLong lUserID;// 用户ID
    private int iChannelNumber;// 通道号

    /*************************************************
     * 函数: JDialogShowString 
     * 函数描述: 构造函数 Creates new form JDialogShowString
     *************************************************/
    public JDialogShowString(java.awt.Dialog parent, boolean modal,
	    HCNetSDK.NET_DVR_SHOWSTRING_V30 strShowString, NativeLong UserID,
	    int chanNumber) {
	super(parent, modal);
	m_strShowString = strShowString;
	lUserID = UserID;
	iChannelNumber = chanNumber;
	initComponents();
    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jLabel1 = new JLabel();
	jComboBoxShowArea = new JComboBox<Object>();
	jPanel1 = new JPanel();
	jCheckBoxShow = new JCheckBox();
	jLabel2 = new JLabel();
	jTextFieldSrtingX = new JTextField();
	jLabel3 = new JLabel();
	jTextFieldStringY = new JTextField();
	jLabel4 = new JLabel();
	jTextFieldString = new JTextField();
	jButtonSave = new JButton();
	jButtonSetUp = new JButton();
	jButtonExit = new JButton();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("叠加字符");
	getContentPane().setLayout(null);

	jLabel1.setText("显示区域");
	getContentPane().add(jLabel1);
	jLabel1.setBounds(20, 10, 60, 15);

	jComboBoxShowArea.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
	jComboBoxShowArea
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jComboBoxShowAreaActionPerformed(evt);
		    }
		});
	getContentPane().add(jComboBoxShowArea);
	jComboBoxShowArea.setBounds(90, 10, 70, 21);

	jPanel1.setBorder(BorderFactory.createTitledBorder("区域设置"));

	jCheckBoxShow.setText("显示字符");

	jLabel2.setText("X坐标");

	jLabel3.setText("Y坐标");

	jLabel4.setText("叠加字符");

	jButtonSave.setText("保存");
	jButtonSave.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSaveActionPerformed(evt);
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
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								GroupLayout.Alignment.TRAILING,
								jPanel1Layout
									.createSequentialGroup()
									.addContainerGap(
										217,
										Short.MAX_VALUE)
									.addComponent(
										jButtonSave))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addGap(20,
										20,
										20)
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
															.addComponent(
																jLabel2)
															.addGroup(
																jPanel1Layout
																	.createSequentialGroup()
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.RELATED)
																	.addComponent(
																		jLabel4)))
													.addGap(10,
														10,
														10)
													.addGroup(
														jPanel1Layout
															.createParallelGroup(
																GroupLayout.Alignment.LEADING)
															.addGroup(
																jPanel1Layout
																	.createSequentialGroup()
																	.addComponent(
																		jTextFieldSrtingX,
																		GroupLayout.PREFERRED_SIZE,
																		80,
																		GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																		LayoutStyle.ComponentPlacement.UNRELATED)
																	.addComponent(
																		jLabel3)
																	.addGap(12,
																		12,
																		12)
																	.addComponent(
																		jTextFieldStringY,
																		GroupLayout.DEFAULT_SIZE,
																		64,
																		Short.MAX_VALUE))
															.addComponent(
																jTextFieldString,
																GroupLayout.DEFAULT_SIZE,
																196,
																Short.MAX_VALUE)))
											.addComponent(
												jCheckBoxShow))))
					.addContainerGap()));
	jPanel1Layout
		.setVerticalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(jCheckBoxShow)
					.addGap(6, 6, 6)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								jTextFieldStringY,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel2)
							.addComponent(
								jTextFieldSrtingX,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel3))
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addGap(65,
										65,
										65)
									.addComponent(
										jButtonSave))
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addGap(18,
										18,
										18)
									.addGroup(
										jPanel1Layout
											.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
											.addComponent(
												jLabel4)
											.addComponent(
												jTextFieldString,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	getContentPane().add(jPanel1);
	jPanel1.setBounds(10, 40, 300, 180);

	jButtonSetUp.setText("确定");
	jButtonSetUp.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonSetUpActionPerformed(evt);
	    }
	});
	getContentPane().add(jButtonSetUp);
	jButtonSetUp.setBounds(130, 230, 60, 23);

	jButtonExit.setText("退出");
	jButtonExit.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonExitActionPerformed(evt);
	    }
	});
	getContentPane().add(jButtonExit);
	jButtonExit.setBounds(230, 230, 60, 23);

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "保存" 按钮单击相应函数 函数描述: 保存参数到结构体
     *************************************************/
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSaveActionPerformed
	int iAreaNumber = jComboBoxShowArea.getSelectedIndex();
	m_strShowString.struStringInfo[iAreaNumber].wShowString = (short) ((this.jCheckBoxShow
		.isSelected() == true) ? 1 : 0);
	m_strShowString.struStringInfo[iAreaNumber].sString = (jTextFieldString
		.getText() + "\0").getBytes();
	m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftY = (short) Integer
		.parseInt(jTextFieldStringY.getText());
	m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftX = (short) Integer
		.parseInt(jTextFieldSrtingX.getText());
    }// GEN-LAST:event_jButtonSaveActionPerformed

    /*************************************************
     * 函数: "设置" 按钮单击相应函数 函数描述: 设置参数,调用接口向设备发送参数
     *************************************************/
    private void jButtonSetUpActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSetUpActionPerformed
	m_strShowString.write();
	Pointer lpShowString = m_strShowString.getPointer();
	boolean setDVRConfigSuc = hCNetSDK.NET_DVR_SetDVRConfig(lUserID,
		HCNetSDK.NET_DVR_SET_SHOWSTRING_V30, new NativeLong(
			this.iChannelNumber), lpShowString, m_strShowString
			.size());
	m_strShowString.read();
	if (setDVRConfigSuc == false) {
	    JOptionPane.showMessageDialog(this, "设置显示字符参数失败");
	    System.out.print("" + hCNetSDK.NET_DVR_GetLastError());
	} else {
	    JOptionPane.showMessageDialog(this, "设置显示字符参数成功");
	}
    }// GEN-LAST:event_jButtonSetUpActionPerformed

    /*************************************************
     * 函数: "退出" 按钮单击相应函数 函数描述: 销毁窗口
     *************************************************/
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonExitActionPerformed
	this.dispose();
    }// GEN-LAST:event_jButtonExitActionPerformed

    /*************************************************
     * 函数: "显示区域" 选项改变时间相应函数 函数描述: 显示对应区域的参数
     *************************************************/
    private void jComboBoxShowAreaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jComboBoxShowAreaActionPerformed
	showStringConfig();
    }// GEN-LAST:event_jComboBoxShowAreaActionPerformed

    /*************************************************
     * 函数: showStringConfig 函数描述: 显示对应区域的参数
     *************************************************/
    void showStringConfig() {
	int iAreaNumber = jComboBoxShowArea.getSelectedIndex();
	this.jCheckBoxShow
		.setSelected((m_strShowString.struStringInfo[iAreaNumber].wShowString > 0) ? true
			: false);
	this.jTextFieldSrtingX
		.setText(m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftX
			+ "");
	this.jTextFieldStringY
		.setText(m_strShowString.struStringInfo[iAreaNumber].wShowStringTopLeftY
			+ "");
	this.jTextFieldString.setText(new String(
		m_strShowString.struStringInfo[iAreaNumber].sString));
	String[] sName = new String[2];
	sName = new String(m_strShowString.struStringInfo[iAreaNumber].sString)
		.split("\0", 2);
	this.jTextFieldString.setText(sName[0]);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonExit;
    private JButton jButtonSave;
    private JButton jButtonSetUp;
    JCheckBox jCheckBoxShow;
    JComboBox<Object> jComboBoxShowArea;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    JTextField jTextFieldSrtingX;
    JTextField jTextFieldString;
    JTextField jTextFieldStringY;
    // End of variables declaration//GEN-END:variables
}
