package com.xn.hk.common.utils.cfg;

/**
 * 
 * @ClassName: CfgConstant
 * @Package: com.xn.hk.common.utils.cfg
 * @Description: 系统配置的key常量类(该常量类的value值与配置文件systemCfg.ini中的key一一对应)
 * @Author: wanlei
 * @Date: 2018年10月8日 下午7:21:06
 */
public class CfgConstant {
	/**
	 * 系统配置文件名(原始的)
	 */
	public final static String ORIGINAL_SYSTEM_CFG_INI = "originalSystemCfg.ini";
	/**
	 * 系统配置文件名(实时更新的)
	 */
	public final static String REALTIME_SYSTEM_CFG_INI = "realTimeSystemCfg.ini";
	/*----------------------------------系统配置---------------------------------*/
	/**
	 * 用户默认密码
	 */
	public final static String DEFAULT_USER_PWD = "defaultUserPwd";
	/**
	 * 登录密码加密
	 */
	public final static String USER_PWD_KEY = SystemCfg.getInstance().loadCfg().getProperty("userPwdKey");
	/**
	 * 是否启用验证码登录
	 */
	public final static String ENABLE_VERIFY_CODE_LOGIN = "enableVerifyCodeLogin";
	/**
	 * 验证码长度
	 */
	public final static String VERIFY_CODE_LENGTH = "verifyCodeLength";
	/**
	 * 用户头像存放位置
	 */
	public final static String USER_PHOTO_PATH = "userPhotoPath";

	/*----------------------------------邮件服务器---------------------------------*/
	/**
	 * 是否启用邮箱发送
	 */
	public final static String ENABLE_MAIL = "enableMail";
	/**
	 * 是否邮箱授权验证
	 */
	public final static String MAIL_VALIDATE = "mailValidate";
	/**
	 * 邮箱主机
	 */
	public final static String MAIL_HOST = "mailHost";
	/**
	 * 邮箱端口号
	 */
	public final static String MAIL_PORT = "mailPort";
	/**
	 * 邮件发送者的地址
	 */
	public final static String MAIL_FROM = "mailFrom";
	/**
	 * 授权邮箱
	 */
	public final static String MAIL_USERNAME = "mailUserName";
	/**
	 * 邮箱授权密码(如果开启了客户端授权码，则密码填授权码密码，没有开启，则账号登录密码)的key
	 */
	public final static String MAIL_PASSWORD = "mailPassword";
	/*----------------------------------LDAP服务器---------------------------------*/
	/**
	 * 开启用户自动推送
	 */
	public final static String START_AUTO_PUSH = "startAutoPush";
	/**
	 * 域用户名
	 */
	public final static String SECURITY_PRINCIPAL = "securityPrincipal";
	/**
	 * 域根节点
	 */
	public final static String BASE_DN = "baseDn";
	/**
	 * keyStore文件位置
	 */
	public final static String KEY_STORE = "keyStore";
	/**
	 * SSL域密码
	 */
	public final static String SSL_PASSWORD = "sslPassword";
	/**
	 * AD管理员域密码
	 */
	public final static String AD_PASSWORD = "adPassword";
	/**
	 * 域服务器 URL
	 */
	public final static String LDAP_URL = "ldapUrl";
	/**
	 * 使用加密通道
	 */
	public final static String USE_SSL = "useSsl";
	/**
	 * 开启推送密码
	 */
	public final static String PUSH_PASSWORD = "pushPassword";
	/**
	 * 用户不能更改密码
	 */
	public final static String PASSWORD_CANT_CHANGE = "passwdCantChange";
	/*----------------------------------snmp监控--------------------------------*/
	/**
	 * 是否启用snmp监控
	 */
	public final static String ENABLE_MONITOR = "enableMonitor";
	/**
	 * 是否监控处理器
	 */
	public final static String MONITOR_CPU = "monitorCpu";
	/**
	 * 是否监控硬盘
	 */
	public final static String MONITOR_DISK = "monitorDisk";
	/**
	 * 是否监控网络流量
	 */
	public final static String MONITOR_NETWORK_FLOW = "monitorNetworkFlow";
	/**
	 * 是否监控设备服务时间
	 */
	public final static String MONITOR_SERVICE_TIME = "monitorServiceTime";
	/**
	 * 是否监控内存
	 */
	public final static String MONITOR_MEMORY = "monitorMemory";
	/**
	 * 监听设备IP
	 */
	public final static String MONITOR_IP = "monitorIp";
	/*----------------------------------发送sms---------------------------------*/
	/**
	 * SDK服务器地址(中国网建SMS短信通，固定的)
	 */
	public final static String SMS_URL = "smsUrl";
	/**
	 * 用户名(中国网建SMS短信通分配的登录名)
	 */
	public final static String SMS_USERNAME = "smsUserName";
	/**
	 * 接口安全密钥(中国网建SMS短信通分配的接口安全密钥)
	 */
	public final static String SMS_PASSWORD = "smsPassword";
	/**
	 * 接受短信的号码
	 */
	public final static String SMS_MOBILE = "smsMobile";
	/**
	 * 是否启用sms短信
	 */
	public final static String ENABLE_SMS = "enableSms";
	/**
	 * 短信编码
	 */
	public final static String SMS_CHARACTER_CODING = "smsCharacterCoding";

	/*-------------------------------------------------------------------*/
}
