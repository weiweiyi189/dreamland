package com.example.dreamland.ui.util;

import javax.mail.Session;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;



public class SendmailUtil {
    private String PROTOCOL = "smtp";
    private String HOST = "smtp.163.com";
    private String PORT = "25";
    private String IS_AUTH = "true";
    private String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private String from = "chenyu99jia@163.com";
    //授权码
    private String authenticCode = "DIPXSTQYXAIIWRHN";
    // 收件人
    private String to;
    // 初始化连接邮件服务器的会话信息
    private Properties props;

    public SendmailUtil(String toEmail) {
        to = toEmail;
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);   // 邮件发送协议
        props.setProperty("mail.smtp.host", HOST);   // SMTP邮件服务器
        props.setProperty("mail.smtp.port", PORT);   // SMTP邮件服务器默认端口
        props.setProperty("mail.smtp.auth", IS_AUTH);   // 是否要求身份认证
        props.setProperty("mail.debug", IS_ENABLED_DEBUG_MOD);   // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    }

    /**
     * 发送简单的邮件
     *
     * @param text
     * @throws Exception
     */
    public void sendTextEmail(String text) throws Exception {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props);
        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(from));
        // 设置邮件主题
        message.setSubject("您的一个老朋友想对您说");
        // 设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置纯文本内容为邮件正文
        message.setText(text);
        // 保存并生成最终的邮件内容
        message.saveChanges();
        // 获得Transport实例对象
        Transport transport = session.getTransport();
        // 打开连接
        transport.connect(from, authenticCode);    //邮箱账户，授权码
        // 将message对象传递给transport对象，将邮件发送出去
        transport.sendMessage(message, message.getAllRecipients());    //未执行成功
        // 关闭连接
        transport.close();
    }
}
