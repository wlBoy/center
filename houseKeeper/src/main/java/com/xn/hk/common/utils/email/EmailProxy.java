package com.xn.hk.common.utils.email;

import com.xn.hk.common.constant.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: EmailProxy
 * @Package: com.xn.hk.common.utils.email
 * @Description: 用来发email的工具类
 * @Author: wanlei
 * @Date: 2018年9月12日 上午11:37:17
 */
public class EmailProxy {
	private static EmailProxy sender = null;
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmailProxy.class);

	private EmailProxy() {
		super();
	}

	/**
	 * 构造器私有化,单例模式建实例
	 * 
	 * @return
	 */
	public synchronized static EmailProxy getInstance() {
		if (sender == null) {
			sender = new EmailProxy();
		}
		return sender;
	}

	/**
	 * 以文本格式发送邮件
	 * 
	 * @param mailInfo
	 *            待发送的邮件的信息
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void sendTextMail(Email mailInfo) throws MessagingException, IOException {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties prop = getProperties();
		if (Boolean.valueOf(String.valueOf(prop.getProperty(Constant.MAIL_VALIDATE_KEY)))) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(String.valueOf(prop.getProperty(Constant.MAIL_USERNAME_KEY)),
					String.valueOf(prop.getProperty(Constant.MAIL_PASSWORD_KEY).toString()));
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session session = Session.getDefaultInstance(prop, authenticator);
		session.setDebug(false);
		// 根据session创建一个邮件消息
		Message mailMessage = new MimeMessage(session);
		// 创建邮件发送者地址
		Address from = new InternetAddress(String.valueOf(prop.getProperty(Constant.MAIL_FROM_KEY).toString()));
		// 设置邮件消息的发送者
		mailMessage.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		mailMessage.setRecipients(Message.RecipientType.TO, getAddress(mailInfo.getToAddress()));
		// 设置邮件消息的主题
		mailMessage.setSubject(mailInfo.getSubject());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());
		// 设置邮件消息的主要内容
		Multipart mp = new MimeMultipart();
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(new String(getMailContent(mailInfo.getContent()).getBytes()),
				"text/html; charset=utf-8");
		mp.addBodyPart(contentPart);
		mailMessage.setContent(mp);
		// 发送邮件
		Transport.send(mailMessage);
		logger.info("邮件发送成功!");
	}

	/**
	 * 获得邮件服务器的配置信息
	 * 
	 * @throws IOException
	 */
	private static Properties getProperties() throws IOException {
		Properties prop = new Properties();
		// 使用流加载配置文件
		InputStream in = EmailProxy.class.getClassLoader().getResourceAsStream("email.properties");
		prop.load(in);
		// 设置snmp的主机，端口号和是否需要授权验证(这一步一定要有)
		prop.put("mail.smtp.host", String.valueOf(prop.getProperty(Constant.MAIL_HOST_KEY)));
		prop.put("mail.smtp.port", String.valueOf(prop.getProperty(Constant.MAIL_PORT_KEY)));
		prop.put("mail.smtp.auth", String.valueOf(prop.getProperty(Constant.MAIL_VALIDATE_KEY)));
		return prop;
	}

	/**
	 * 创建邮件的接收者地址，并设置到邮件消息中
	 * 
	 * @param toAddress
	 * @return
	 * @throws AddressException
	 */
	private static InternetAddress[] getAddress(String[] toAddress) throws AddressException {
		InternetAddress[] address = new InternetAddress[toAddress.length];
		for (int i = 0; i < toAddress.length; i++) {
			address[i] = new InternetAddress(toAddress[i]);
		}
		return address;
	}

	/**
	 * 获取邮件格式,拼接邮件内容
	 * 
	 * @param content
	 * @return 邮件内容
	 */
	private static String getMailContent(String content) {
		StringBuffer contentBuffer = new StringBuffer("<html>");
		contentBuffer.append("<head>");
		contentBuffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
		contentBuffer.append("<title>HTML邮件</title>");
		contentBuffer.append("</head>");
		contentBuffer.append("<body>");
		contentBuffer.append("<font size='4'>");
		contentBuffer.append("您好！<br><br>&nbsp;&nbsp;&nbsp;&nbsp;");
		contentBuffer.append(content);
		contentBuffer.append("</font>");
		contentBuffer.append("</body>");
		contentBuffer.append("</html>");
		return contentBuffer.toString();
	}

	public static void main(String[] args) {
		// 这个类主要是设置邮件
		Email mailInfo = new Email();
		mailInfo.setToAddress(new String[] { "1354373900@qq.com" });
		mailInfo.setSubject("这是邮箱标题");
		mailInfo.setContent("这是邮箱内容");
		// 这个类主要来发送邮件
		try {
			sendTextMail(mailInfo);
		} catch (MessagingException e) {
			logger.error("邮件发送失败，原因为:" + e.getMessage());
		} catch (IOException e) {
			logger.error("邮件发送失败，原因为:" + e.getMessage());
		}
	}
}
