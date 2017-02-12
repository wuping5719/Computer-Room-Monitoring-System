package com.ouc.dcrms.collect.panel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author WuPing
 * @version 2016年12月22日 下午10:49:48
 */

public class CollecterPanel extends JFrame implements ActionListener {

    private static final long serialVersionUID = -2486615101188505883L;

    // 定义组件
    private JPanel jp1, jp2;// 面板
    private JLabel jlb; // 标签
    private JButton jb1, jb2; // 按钮

    public static void main(String[] args) {
	@SuppressWarnings("unused")
	CollecterPanel win = new CollecterPanel();
    }

    // 构造函数
    public CollecterPanel() {
	// 创建面板
	jp1 = new JPanel();
	jp2 = new JPanel();

	// 创建标签
	jlb = new JLabel("分布式机房监控数据采集子系统");
	// 创建按钮
	jb1 = new JButton("开始采集");
	jb2 = new JButton("结束采集");
	
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        
	// 设置布局管理
	this.setLayout(new GridLayout(2, 1)); // 网格式布局

	// 加入各个组件
	jp1.add(jlb);

	jp2.add(jb1);
	jp2.add(jb2);

	// 加入到JFrame
	this.add(jp1);
	this.add(jp2);

	// 设置窗体
	this.setTitle("数据采集子系统");// 窗体标签
	this.setSize(300, 100);// 窗体大小
	this.setLocationRelativeTo(null); // 在屏幕中间显示(居中显示)
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 退出关闭JFrame
	this.setVisible(true);// 显示窗体

	// 锁定窗体
	this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand() == "开始采集") {
	    JOptionPane.showMessageDialog(null, "开始采集！", "提示消息",
		    JOptionPane.WARNING_MESSAGE);
	} else if (e.getActionCommand() == "结束采集") {
	    JOptionPane.showMessageDialog(null, "结束采集！", "提示消息",
		    JOptionPane.WARNING_MESSAGE);
	}
    }

}
