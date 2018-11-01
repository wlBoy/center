package com.xn.hk.common.utils.ftp;

import java.io.Serializable;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * 
 * @ClassName: Sftp
 * @Package: com.xn.hk.common.utils.ftp
 * @Description: SFTP服务器实体类，依赖jar包com.jcraft.jsch
 * @Author: wanlei
 * @Date: 2018年11月1日 上午11:34:46
 */
public class Sftp implements Serializable {
	private static final long serialVersionUID = 1L;
	private Session session;// 会话
	private Channel channel;// 连接通道
	private ChannelSftp sftp;// sftp操作类

	public Sftp() {
		super();
	}

	public Sftp(Session session, Channel channel, ChannelSftp sftp) {
		super();
		this.session = session;
		this.channel = channel;
		this.sftp = sftp;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelSftp getSftp() {
		return sftp;
	}

	public void setSftp(ChannelSftp sftp) {
		this.sftp = sftp;
	}

	@Override
	public String toString() {
		return "Sftp [session=" + session + ", channel=" + channel + ", sftp=" + sftp + "]";
	}

}
