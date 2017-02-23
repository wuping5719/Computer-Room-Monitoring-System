package com.ouc.dcrms.video;

import com.sun.jna.NativeLong;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/*****************************************************************************
 * 类 ： JDialogUpGrade 
 * 类描述 ：设备升级
 ****************************************************************************/
public class JDialogUpGrade extends JDialog {

    private static final long serialVersionUID = -4211666645546972533L;

    static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
    private NativeLong m_lUserID;// 用户ID
    private NativeLong m_lUpgradeHandle;// 文件升级句柄
    Timer timer;// 定时器

    /*************************************************
     * 函数: JDialogUpGrade 
     * 函数描述: 构造函数 Creates new form JDialogUpGrade
     *************************************************/
    public JDialogUpGrade(java.awt.Frame parent, boolean modal,
	    NativeLong lUserID) {
	super(parent, modal);
	m_lUserID = lUserID;
	m_lUpgradeHandle = new NativeLong(-1);
	initComponents();
	jProgressBarUpgrade.setMinimum(0);
	jProgressBarUpgrade.setMaximum(100);
    }

    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	jPanel1 = new JPanel();
	jLabel1 = new JLabel();
	jComboBoxNetEnvir = new JComboBox<Object>();
	jButtonSetNetEnvir = new JButton();
	jLabel2 = new JLabel();
	jTextFieldFileDir = new JTextField();
	jButtonBrowse = new JButton();
	jLabel3 = new JLabel();
	jLabelUpgradeState = new JLabel();
	jButtonUpgrade = new JButton();
	jButtonExit = new JButton();
	jProgressBarUpgrade = new JProgressBar();

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("设备升级");
	getContentPane().setLayout(null);

	jPanel1.setBorder(BorderFactory.createTitledBorder(""));

	jLabel1.setText("网络环境");

	jComboBoxNetEnvir.setModel(new DefaultComboBoxModel<Object>(
		new String[] { "LAN", "WAN" }));

	jButtonSetNetEnvir.setText("设置");
	jButtonSetNetEnvir
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			jButtonSetNetEnvirActionPerformed(evt);
		    }
		});

	jLabel2.setText("升级文件");

	jTextFieldFileDir.setText("c:\\digicap");

	jButtonBrowse.setText("浏览");
	jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonBrowseActionPerformed(evt);
	    }
	});

	jLabel3.setText("状态");

	jButtonUpgrade.setText("升级");
	jButtonUpgrade.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		jButtonUpgradeActionPerformed(evt);
	    }
	});

	jButtonExit.setText("退出");

	GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addGap(27, 27, 27)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.TRAILING)
							.addComponent(
								jProgressBarUpgrade,
								GroupLayout.Alignment.LEADING,
								GroupLayout.DEFAULT_SIZE,
								320,
								Short.MAX_VALUE)
							.addGroup(
								GroupLayout.Alignment.LEADING,
								jPanel1Layout
									.createParallelGroup(
										GroupLayout.Alignment.LEADING,
										false)
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jButtonUpgrade,
												GroupLayout.PREFERRED_SIZE,
												70,
												GroupLayout.PREFERRED_SIZE)
											.addGap(33,
												33,
												33)
											.addComponent(
												jButtonExit,
												GroupLayout.PREFERRED_SIZE,
												70,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jLabel3,
												GroupLayout.PREFERRED_SIZE,
												30,
												GroupLayout.PREFERRED_SIZE)
											.addGap(18,
												18,
												18)
											.addComponent(
												jLabelUpgradeState,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
									.addComponent(
										jButtonBrowse,
										GroupLayout.PREFERRED_SIZE,
										60,
										GroupLayout.PREFERRED_SIZE)
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jLabel2,
												GroupLayout.PREFERRED_SIZE,
												60,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jTextFieldFileDir,
												GroupLayout.PREFERRED_SIZE,
												256,
												GroupLayout.PREFERRED_SIZE))
									.addGroup(
										jPanel1Layout
											.createSequentialGroup()
											.addComponent(
												jLabel1,
												GroupLayout.PREFERRED_SIZE,
												70,
												GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
												LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(
												jComboBoxNetEnvir,
												GroupLayout.PREFERRED_SIZE,
												70,
												GroupLayout.PREFERRED_SIZE)
											.addGap(31,
												31,
												31)
											.addComponent(
												jButtonSetNetEnvir,
												GroupLayout.PREFERRED_SIZE,
												60,
												GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(47, Short.MAX_VALUE)));
	jPanel1Layout
		.setVerticalGroup(jPanel1Layout
			.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addGap(21, 21, 21)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel1)
							.addComponent(
								jComboBoxNetEnvir,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
							.addComponent(
								jButtonSetNetEnvir))
					.addGap(18, 18, 18)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel2)
							.addComponent(
								jTextFieldFileDir,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jButtonBrowse)
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.TRAILING)
							.addComponent(jLabel3)
							.addComponent(
								jLabelUpgradeState,
								GroupLayout.PREFERRED_SIZE,
								20,
								GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(jProgressBarUpgrade,
						GroupLayout.PREFERRED_SIZE, 15,
						GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED,
						GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								GroupLayout.Alignment.BASELINE)
							.addComponent(
								jButtonUpgrade)
							.addComponent(
								jButtonExit))
					.addContainerGap()));

	getContentPane().add(jPanel1);
	jPanel1.setBounds(10, 10, 410, 210);

	pack();
    }// </editor-fold>//GEN-END:initComponents

    /*************************************************
     * 函数: "浏览" 按钮单击相应函数 函数描述: 选择升级文件
     *************************************************/
    private void jButtonBrowseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonBrowseActionPerformed
	JFileChooser JFileChooser1 = new JFileChooser("c:/digicap");// 启动一个文件选择器
	if (JFileChooser.APPROVE_OPTION == JFileChooser1.showOpenDialog(this))// 如果文件选择完毕
	{
	    openFile(JFileChooser1.getSelectedFile().getPath());// 作为将来的接口
	    String filepath = JFileChooser1.getSelectedFile().getPath();// 获取被选择文件的路径
	    jTextFieldFileDir.setText(filepath);// 输出文件路径
	}
    }// GEN-LAST:event_jButtonBrowseActionPerformed

    /*************************************************
     * 函数: "设置" 按钮单击相应函数 函数描述: 设置网络环境
     *************************************************/
    private void jButtonSetNetEnvirActionPerformed(
	    java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSetNetEnvirActionPerformed
	if (!hCNetSDK.NET_DVR_SetNetworkEnvironment(jComboBoxNetEnvir
		.getSelectedIndex())) {
	    JOptionPane.showMessageDialog(this, "设置网络环境失败");
	    return;
	}
    }// GEN-LAST:event_jButtonSetNetEnvirActionPerformed

    /*************************************************
     * 函数: "升级" 按钮单击相应函数 函数描述: 调用接口,开始升级
     *************************************************/
    private void jButtonUpgradeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonUpgradeActionPerformed
	File fileUpgrade = new File(jTextFieldFileDir.getText());
	if (fileUpgrade.canRead() == false) {
	    JOptionPane.showMessageDialog(this, "无效文件");
	    return;
	}
	if (fileUpgrade.length() == 0) {
	    JOptionPane.showMessageDialog(this, "文件为空");
	    return;
	}
	m_lUpgradeHandle = hCNetSDK.NET_DVR_Upgrade(m_lUserID,
		jTextFieldFileDir.getText());
	if (m_lUpgradeHandle.intValue() < 0) {
	    JOptionPane.showMessageDialog(this, "升级失败");
	} else {
	    timer = new Timer();// 新建定时器
	    timer.schedule(new MyTask(), 0, 500);// 0秒后开始响应函数
	}
    }// GEN-LAST:event_jButtonUpgradeActionPerformed

    /*************************************************
     * 类: MyTask 类描述: 定时器响应函数
     *************************************************/
    class MyTask extends TimerTask {// 定时器函数 相当于c语言中的onTimer();

	@Override
	public void run() {
	    int UpgradeStatic = hCNetSDK
		    .NET_DVR_GetUpgradeState(m_lUpgradeHandle);
	    int iPos = hCNetSDK.NET_DVR_GetUpgradeProgress(m_lUpgradeHandle);

	    if (iPos > 0) {
		jProgressBarUpgrade.setValue(iPos);
	    }
	    if (UpgradeStatic == 2) {
		jLabelUpgradeState.setText("状态：正在升级设备，请等待......");
	    } else {
		switch (UpgradeStatic) {
		case -1:
		    jLabelUpgradeState.setText("升级失败");
		    break;
		case 1:
		    jLabelUpgradeState.setText("状态：升级设备成功");
		    jProgressBarUpgrade.setValue(100);
		    break;
		case 3:
		    jLabelUpgradeState.setText("状态：升级设备失败");
		    break;
		case 4:
		    jLabelUpgradeState.setText("状态：网络断开,状态未知");
		    break;
		case 5:
		    jLabelUpgradeState.setText("状态：升级文件语言版本不匹配");
		    break;
		default:
		    break;
		}
		if (hCNetSDK.NET_DVR_CloseUpgradeHandle(m_lUpgradeHandle) == true) {
		    System.out.println("NET_DVR_CloseUpgradeHandle");
		}
		m_lUpgradeHandle = new NativeLong(-1);
		timer.cancel();// 使用这个方法退出任务
	    }
	}
    }

    void openFile(String fileName) {
	try {
	    File file = new File(fileName);
	    int size = (int) file.length();
	    int chars_read = 0;
	    FileReader in = new FileReader(file);
	    char[] data = new char[size];
	    while (in.ready()) {
		chars_read += in.read(data, chars_read, size - chars_read);
		// read(目标数组、文件起始位置、文件结束位置)
		// 返回读入的数据量
	    }
	    in.close();
	} catch (IOException e) {
	    System.out.println(e.toString());
	}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton jButtonBrowse;
    private JButton jButtonExit;
    private JButton jButtonSetNetEnvir;
    private JButton jButtonUpgrade;
    private JComboBox<Object> jComboBoxNetEnvir;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabelUpgradeState;
    private JPanel jPanel1;
    private JProgressBar jProgressBarUpgrade;
    private JTextField jTextFieldFileDir;
    // End of variables declaration//GEN-END:variables
}
