package com.xn.hk.common.utils.email;

import java.io.Serializable;

/**
 * 
 * @ClassName: Email
 * @Package: com.xn.hk.common.utils.email
 * @Description: 用于发送邮件的实体类
 * @Author: wanlei
 * @Date: 2018年9月12日 上午11:35:10
 */
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 邮件接收者的地址
	 */
	private String[] toAddress;

	/**
	 * 邮件主题
	 */
	private String subject;

	/**
	 * 邮件的文本内容
	 */
	private String content;

	public String[] getToAddress() {
		return toAddress;
	}

	public void setToAddress(String[] toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Email(String[] toAddress, String subject, String content) {
		super();
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
	}

	public Email() {
		super();
	}

}
