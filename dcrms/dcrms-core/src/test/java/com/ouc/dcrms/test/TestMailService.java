package com.ouc.dcrms.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ouc.dcrms.core.email.MailServiceImpl;

/**
 * @author WuPing
 * @version 2017年2月19日 下午7:12:04
 */

public class TestMailService {

    public static void main(String[] args) {
	String sendMailAddr = "xxx@163.com";
	String sendMailPwd = "xxxx";
        List<String> ccs = new ArrayList<String>();          // 抄送人
        
        List<String> recipients = new ArrayList<String>();   // 收件人
        recipients.add("xxxx@qq.com");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String subject = "报警通知";
        String content = sdf.format(new Date()) + " 李村机房 烟感1发生报警！"; 
        
        List<String> attachments = new ArrayList<String>();    // 附件
       
        MailServiceImpl mailServiceImpl = new MailServiceImpl();
        mailServiceImpl.sendMail(sendMailAddr, sendMailPwd, ccs, recipients, subject, content, attachments);
    }

}
