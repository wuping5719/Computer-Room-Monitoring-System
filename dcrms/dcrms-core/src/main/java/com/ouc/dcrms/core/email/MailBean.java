package com.ouc.dcrms.core.email;

import java.util.List;

/**
 * @author WuPing
 * @version 2017年2月19日 下午4:50:05
 */

public class MailBean {
    
    private String subject;    // 主题  
    
    private String sendMailAddr;   // 发送邮箱地址

    private String sendMailPwd;  // 发送邮箱密码

    private List<String> recipients;   // 收件人

    private List<String> ccs;   // 抄送人

    private String content;    // 发送内容

    private String character;    // 内容编码

    private String mailHost;     // 发送服务器地址

    private List<String> attachments;   // 附件

    private String isCheckSend;    // 发送是否需要校验 
    
    /**
     * @param subject 主题
     * @param sendMailAddr 发件人
     * @param sendMailPwd 密码
     * @param recipients 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     */
    public MailBean(String subject, String sendMailAddr, String sendMailPwd,
            List<String> recipients, String content, String character,
            String mailHost, String isCheckSend) {
        this.subject = subject;
        this.sendMailAddr = sendMailAddr;
        this.sendMailPwd = sendMailPwd;
        this.recipients = recipients;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.isCheckSend = isCheckSend;
    }
    
    /**
     * @param subject 主题
     * @param sendMailAddr 发件人
     * @param sendMailPwd 密码
     * @param recipients 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     * @param attachments 附件
     */
    public MailBean(String subject, String sendMailAddr, String sendMailPwd,
            List<String> recipients, String content, String character,
            String mailHost, List<String> attachments, String isCheckSend) {
        this.subject = subject;
        this.sendMailAddr = sendMailAddr;
        this.sendMailPwd = sendMailPwd;
        this.recipients = recipients;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.attachments = attachments;
        this.isCheckSend = isCheckSend;
    }
    
    /**
     * @param subject 主题
     * @param sendMailAddr 发件人
     * @param sendMailPwd 密码
     * @param recipients 收件人 
     * @param content 内容
     * @param character  内容字符集
     * @param mailHost SMTP主机
     * @param isCheckSend 是否需要验证
     * @param attachments 附件
     * @param ccs 抄送
     */
    public MailBean(String subject, String sendMailAddr, String sendMailPwd,
            List<String> recipients, String content, String character,
            String mailHost, List<String> attachments, String isCheckSend, List<String> ccs) {
        this.subject = subject;
        this.sendMailAddr = sendMailAddr;
        this.sendMailPwd = sendMailPwd;
        this.recipients = recipients;
        this.content = content;
        this.character = character;
        this.mailHost = mailHost;
        this.attachments = attachments;
        this.isCheckSend = isCheckSend;
        this.ccs = ccs;
    }
    
    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getCharacter() {
	return character;
    }

    public void setCharacter(String character) {
	this.character = character;
    }

    public String getMailHost() {
	return mailHost;
    }

    public void setMailHost(String mailHost) {
	this.mailHost = mailHost;
    }

    public List<String> getAttachments() {
	return attachments;
    }

    public void setAttachments(List<String> attachments) {
	this.attachments = attachments;
    }

    public String getCheckSend() {
	return isCheckSend;
    }

    public void setCheckSend(String isCheckSend) {
	this.isCheckSend = isCheckSend;
    }

    public List<String> getRecipients() {
	return recipients;
    }

    public void setRecipients(List<String> recipients) {
	this.recipients = recipients;
    }

    public List<String> getCcs() {
	return ccs;
    }

    public void setCcs(List<String> ccs) {
	this.ccs = ccs;
    }

    public String getIsCheckSend() {
	return isCheckSend;
    }

    public void setIsCheckSend(String isCheckSend) {
	this.isCheckSend = isCheckSend;
    }

    public String getSendMailAddr() {
	return sendMailAddr;
    }

    public void setSendMailAddr(String sendMailAddr) {
	this.sendMailAddr = sendMailAddr;
    }

    public String getSendMailPwd() {
	return sendMailPwd;
    }

    public void setSendMailPwd(String sendMailPwd) {
	this.sendMailPwd = sendMailPwd;
    }
}
