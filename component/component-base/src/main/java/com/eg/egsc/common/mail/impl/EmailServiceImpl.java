/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.mail.impl;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eg.egsc.common.exception.CommonException;
import com.eg.egsc.common.mail.EmailService;

/**
 * Email Service
 * 
 * @author douguoqiang
 * @since 2018年1月15日
 */
@Component
public class EmailServiceImpl implements EmailService {
	protected final Logger logger = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Value("${mail.smtp.host:mail.hdsc.com}")
	private String mailHost;
	@Value("${mail.smtp.sender:}")
	private String sender;
	@Value("${mail.smtp.port:25}")
	private String port;

	@Override
	public void sendEmail(String receiver, String subject, String content) {
		sendEmail(receiver, subject, content, null);
	}

	@Override
	public void sendEmail(String receiver, String subject, String content,
			String attachmentLocation) {
		try {
			sendEmailNoSSL(mailHost, port, sender, null, receiver, subject,
					content, attachmentLocation);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new CommonException(null, e.getMessage());
		}

	}

	@Override
	public void sendEmail(final String mailHost, final int port,
			final String sender, final String senderPwd, String receiver,
			String subject, String content, String attachmentLocation) {
		MimeMessage message = null;
		Session session = null;
		Transport transport = null;

		Properties props = new Properties();
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");

		props.put("mail.smtp.auth.mechanisms", "NTLM");
		props.put("mail.smtp.auth.ntlm.domain", "hdsc");

		session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, senderPwd);
			}
		});
		session.setDebug(true);// 开启后有调试信息
		message = new MimeMessage(session);
		try {
			// 发件人
			message.setFrom(new InternetAddress(sender));
			// 收件人
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					receiver));
			// 邮件主题
			message.setSubject(subject);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 添加邮件正文
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// 添加附件的内容
			if (attachmentLocation != null) {
				File file = new File(attachmentLocation);
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// MimeUtility.encodeWord可以避免文件名乱码
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(file
						.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			transport = session.getTransport("smtps");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailHost, port, sender, senderPwd);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			logger.info("send success!");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new CommonException(null, "发送邮件给：" + sender + "失败");
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					logger.error(e.getMessage());
				}
			}
		}

	}

	@Override
	public void sendEmailNoSSL(final String mailHost, final String port,
			final String sender, final String senderPwd, String receiver,
			String subject, String content, String attachmentLocation)
			throws Exception {
		logger.info("send email to " + receiver + " with no SSL");

		Properties props = System.getProperties();
		props.setProperty("mail.smtp.host", mailHost); // 指定SMTP服务器
		props.setProperty("mail.smtp.auth", "false"); // 指定是否需要SMTP验证
		props.setProperty("mail.smtp.port", port); // 指定端口

		Session mailSession = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, senderPwd);
			}
		}); // 获得一个默认会话session

		mailSession.setDebug(true);// 是否在控制台显示debug信息
		Message message = new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(sender));// 发件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				receiver));// 收件人
		message.setSubject(subject);// 邮件主题

		// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
		Multipart multipart = new MimeMultipart();

		// 添加邮件正文
		BodyPart contentPart = new MimeBodyPart();
		contentPart.setContent(content, "text/html;charset=UTF-8");
		multipart.addBodyPart(contentPart);

		// 添加附件的内容
		if (attachmentLocation != null) {
			File file = new File(attachmentLocation);
			BodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			attachmentBodyPart.setDataHandler(new DataHandler(source));

			// MimeUtility.encodeWord可以避免文件名乱码
			attachmentBodyPart.setFileName(MimeUtility.encodeWord(file
					.getName()));
			multipart.addBodyPart(attachmentBodyPart);
		}

		// 将multipart对象放到message中
		message.setContent(multipart);

		// message.setText(title);//邮件内容
		message.saveChanges();
		Transport.send(message);
		logger.info("Send email to " + receiver + " success.");
	}
}
