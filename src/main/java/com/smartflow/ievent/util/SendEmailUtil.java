package com.smartflow.ievent.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.hibernate.result.Outputs;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class SendEmailUtil {

    public static void send() throws Exception{
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");//设置QQ邮件服务器
        properties.setProperty("mail.transport.protocol", "smtp");//邮件发送协议
        //properties.setProperty("mail.smtp.auth", "true");//需要验证用户名密码


        //关于QQ邮箱，还要设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.ssl.socketFactory", sf);

        //使用Java Mail发送邮件
        //1.创建定义整个应用程序所需的环境信息的session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication("2239387608@qq.com", "授权码");
            }
        });

        //开启Session的debug模式，可以查看程序发送mail的运行状态
        session.setDebug(true);

        //2.通过session得到transport对象
        Transport ts = session.getTransport();

        //3.使用邮箱的用户名和授权码连上邮件服务器
        ts.connect("smtp.qq.com", "2239387608@qq.com", "授权码");

        //4.创建邮件

        //创建邮件对象
        MimeMessage message = new MimeMessage(session);

        //指明邮件的发件人
        message.setFrom(new InternetAddress("2239387608@qq.com"));

        //TO：指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2239387608@qq.com"));
        //TO：增加收件人（可选）
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("2239387608@qq.com"));
        //CC：抄送（可选）
        message.addRecipient(Message.RecipientType.CC, new InternetAddress("2239387608@qq.com"));
        //BCC：密送（可选）
        message.addRecipient(Message.RecipientType.BCC, new InternetAddress("2239387608@qq.com"));

        //邮件的标题
        message.setSubject("邮件主题");
        //邮件正文
        message.setContent("邮件正文", "text/html;charset=UTF-8");
        //设置显示的发件时间
        message.setSentDate(new Date());

        //5.发送邮件
//        ts.sendMessage(message, message.getAllRecipients());
//
//        ts.close();
        //保存前面的设置
        message.saveChanges();
              //将该邮件保存到本地
        OutputStream os = new FileOutputStream("myEmail.eml");
        message.writeTo(os);
        os.flush();
        os.close();
    }

    public static void main(String[] args) {
        try {
            send();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
