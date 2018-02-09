/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.mail;

/**
 * 邮件相关服务
 * 
 * @author gaoyanlong
 * @since 2017年12月22日
 */
public interface EmailService {
	/**
	 * 发送邮件服务，使用系统默认发件人
	 * 
	 * @param receiver 收件人
	 * @param subject 主题
	 * @param content 内容
	 * @param attachmentLocation 附件位置
	 */
	void sendEmail(String receiver, String subject, String content,
			String attachmentLocation);

	/**
	 * 发送邮件服务
	 * 
	 * @param mailHost  邮箱服务器
	 * @param port 端口号
	 * @param sender 发件人
	 * @param senderPwd 发件人密码
	 * @param receiver  收件人
	 * @param subject  主题
	 * @param content  内容
	 * @param attachmentLocation  附件位置
	 */
	void sendEmail(String mailHost, int port, String sender, String senderPwd,
			String receiver, String subject, String content,
			String attachmentLocation);

	/**
	 * 发送邮件服务 - No SSL
	 * 
	 * @param mailHost
	 * @param port
	 * @param sender
	 * @param senderPwd
	 * @param receiver
	 * @param subject
	 * @param content
	 * @param attachmentLocation
	 * @throws Exception
	 */
	void sendEmailNoSSL(String mailHost, String port, String sender,
			String senderPwd, String receiver, String subject, String content,
			String attachmentLocation) throws Exception;

	/**
	 *  Name sendEmail
	 * @param receiver
	 * @param subject
	 * @param content
	 *            void
	 */
	void sendEmail(String receiver, String subject, String content);
}
