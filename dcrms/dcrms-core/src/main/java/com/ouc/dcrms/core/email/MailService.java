package com.ouc.dcrms.core.email;

import java.util.List;

/**
 * @author WuPing
 * @version 2017年2月19日 下午6:46:40
 */

public interface MailService {
    /**
     * 发送邮件
     * @Eclosing_Method  : sendMail
     * @param sendMailAddr 发送人邮箱
     * @param sendMailPwd 发送人密码
     * @param ccs 抄送人
     * @param recipients 收件人
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param attachments 附件
     */
    public void sendMail(String sendMailAddr, String sendMailPwd, List<String> ccs, 
            List<String> recipients, String subject, String content, List<String> attachments);
}
