package com.bbgu.zmz.community.util;
import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    public static String myEmailAccount = "721791701@qq.com";
    public static String myEmailPassword = "azizsjwhglarbcgg";
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static String receiveMailAccount;

    public static Result sendActiveMail(String receiveMailAccount, String mailActiveCode, int mailCode){
        //RegRespObj regRespObj = new RegRespObj();
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        //session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log


        try{
            // 3. 创建一封邮件
            MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount, mailActiveCode,mailCode);

            // 4. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            transport.connect(myEmailAccount, myEmailPassword);

            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 7. 关闭连接
            transport.close();
        }catch (Exception e){
            return new Result().error(MsgEnum.EMAIL_INCORRECT);
        }
        return new Result().ok(MsgEnum.OK);
    }


    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session 和服务器交互的会话
     * @param sendMail 发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String mailActiveCode,int mailCode) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        if(!mailActiveCode.equals("code")){
            message.setFrom(new InternetAddress(sendMail, "Code社区账户激活邮件", "UTF-8"));
        }else{
            message.setFrom(new InternetAddress(sendMail, "Code社区找回用户密码", "UTF-8"));
        }

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMailAccount, "UTF-8"));

        // 4. Subject: 邮件主题
        if(!mailActiveCode.equals("code")){
        // 5. Content: 邮件正文（可以使用html标签）
            message.setSubject("用户激活", "UTF-8");
            String activeUrl="http://101.200.47.40/user/activemail/"+mailActiveCode;
            message.setContent("尊敬的用户，您好！我是Code社区站长听风，请点击激活链接完成邮箱激活，激活链接有效期只有五分钟。联立登录的账号初始密码为123456，请及时修改！<a href=\""+activeUrl+"\" target=\"_blank\">"+activeUrl+"</a>", "text/html;charset=UTF-8");
        }else{
            message.setSubject("找回用户密码", "UTF-8");
            message.setContent("尊敬的用户，您好！本次验证码为："+ mailCode, "text/html;charset=UTF-8");
        }
        //message.setContent("尊敬的用户，您好！我是Code社区站长听风，我正在进行邮箱激活测试，打扰了！", "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }


}
