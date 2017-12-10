package cn.itcast.bos.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import cn.itcast.bos.domain.constant.Constants;

public class MailUtils {
	private static String smtp_host = "smtp.163.com"; // 网易
	private static String username = "18672304510@163.com"; // 邮箱账户
	private static String password = "itcast201707"; // 邮箱授权码

	private static String from = "18672304510@163.com"; // 使用当前账户
	public static String activeUrl = Constants.BOS_FORE_URL+"/customer_activeMail.action";

	public static void sendMail(String subject, String content, String to) throws Exception{
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
//		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException();
//		}
	}

	public static void main(String[] args) throws Exception{
		sendMail("测试邮件", "你好，传智播客", "caiwenwen@itcast.cn");
	}
}
