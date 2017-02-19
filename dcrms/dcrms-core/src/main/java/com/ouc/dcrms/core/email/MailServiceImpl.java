package com.ouc.dcrms.core.email;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author WuPing
 * @version 2017年2月19日 下午6:49:23
 */

public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(String sendMailAddr, String sendMailPwd,
	    List<String> ccs, List<String> recipients, String subject,
	    String content, List<String> attachments) {
	Properties properties = new Properties();
	properties.setProperty("mail.smtp.auth", "true"); // 服务器需要认证
	properties.setProperty("mail.transport.protocol", "smtp"); // 声明发送邮件使用的端口

	Session session = Session.getInstance(properties);
	session.setDebug(true); // 同意在当前线程的控制台打印与服务器对话信息

	URLName urlName = new URLName(sendMailAddr);
	PasswordAuthentication pa = new PasswordAuthentication(sendMailAddr,
		sendMailPwd);
	session.setPasswordAuthentication(urlName, pa);

	MimeMessage mimeMessage = new MimeMessage(session); // 构建发送的信息

	try {
	    // 发件人
	    mimeMessage.setFrom(new InternetAddress(sendMailAddr));
	    // 收件人
	    mimeMessage.setRecipients(Message.RecipientType.TO, getAllRecipients(recipients));
	    // 发件日期
	    mimeMessage.setSentDate(new Date());
	    // 抄送人
	    mimeMessage.setRecipients(Message.RecipientType.CC, getAllCCs(ccs));
	    // 主题与字符编码
	    mimeMessage.setSubject(subject, "UTF-8");
	    // 邮件内容
	    mimeMessage.setText(content);
	    
	    // 设置邮件体格式
	    MimeBodyPart mbp = new MimeBodyPart();
	    // bp.setContent("<meta http-equiv=Content-Type content=text/html; charset="+getCharacter()+">"
	    //   + content, "text/html;charset="+getCharacter());

	    mbp.setText(content);

	    MimeMultipart multipart = new MimeMultipart();
	    multipart.addBodyPart(mbp);

	    // 添加附件
	    addBodyPartByMimeMultipart(multipart, attachments);
	    Transport transport = session.getTransport();
	    transport.connect("smtp.163.com", 25, sendMailAddr, sendMailPwd); // 连接发件人使用发件的服务器
	    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients()); // 接收邮件
	    transport.close();
	    
	} catch (MessagingException e) {
	    e.printStackTrace();
	}
    }

    // 获得所有收件人的邮箱地址
    private Address[] getAllRecipients(List<String> recipients)
	    throws ArrayIndexOutOfBoundsException, AddressException {
	return valueOfAddress(recipients);
    }

    // 获取所有抄送人的邮箱地址
    private Address[] getAllCCs(List<String> ccs)
	    throws ArrayIndexOutOfBoundsException, AddressException {
	if (null == ccs || ccs.size() == 0)
	    return new Address[0];
	else
	    return valueOfAddress(ccs);
    }

    private Address[] valueOfAddress(List<String> list)
	    throws ArrayIndexOutOfBoundsException, AddressException {
	Address[] address = new Address[0];
	if (null == list || list.size() == 0)
	    throw new ArrayIndexOutOfBoundsException("List is null");
	address = new Address[list.size()];
	for (int i = 0; i < list.size(); ++i) {
	    address[i] = InternetAddress.parse(list.get(i))[0];
	}
	return address;
    }

    // 添加所有附件
    private void addBodyPartByMimeMultipart(MimeMultipart mm,
	    List<String> filePaths) throws MessagingException,
	    ArrayIndexOutOfBoundsException, NullPointerException {

	if (null == filePaths || filePaths.size() == 0)
	    return;
	if (null == mm)
	    throw new NullPointerException("MimeMultipart is null");
	for (int i = 0; i < filePaths.size(); i++) {
	    MimeBodyPart bp = new MimeBodyPart();
	    FileDataSource fds = new FileDataSource(filePaths.get(i));
	    DataHandler dh = new DataHandler(fds);
	    bp.setDisposition(Part.ATTACHMENT);
	    bp.setFileName(fds.getName());
	    bp.setDataHandler(dh);
	    mm.addBodyPart(bp);
	}
    }
}
