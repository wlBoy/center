package com.xn.hk.common.constant;

/**
 * 
 * @Title: Contant
 * @Package: com.xn.hk.common.constant
 * @Description: 常量类
 * @Author: wanlei
 * @Date: 2018年2月7日 上午11:51:20
 */
public class Constant {
	/*----------------------------------common---------------------------------*/
	/**
	 * 影响条数0
	 */
	public final static int ZERO_VALUE = 0;
	/**
	 * 提示信息key
	 */
	public final static String TIP_KEY = "msg";
	/**
	 * 提示信息-成功状态key
	 */
	public final static String SUCCESS_TIP_KEY = "success";
	/**
	 * 提示信息-错误状态key
	 */
	public final static String ERROR_TIP_KEY = "error";
	/**
	 * 分页信息的key
	 */
	public final static String PAGE_KEY = "pages";
	/**
	 * 类别value
	 */
	public final static String TYPES_KEY = "types";

	/*----------------------------------用户管理---------------------------------*/
	/**
	 * session中已登录用户key
	 */
	public final static String SESSION_USER_KEY = "user";
	/**
	 * cookie中用户名key
	 */
	public final static String USERNAME_KEY = "userName";
	/**
	 * cookie中用户密码key
	 */
	public final static String USERPWD_KEY = "userPwd";
	/**
	 * 所有用户列表key
	 */
	public final static String USER_KEY = "users";
	/**
	 * 用户IDkey
	 */
	public final static String USERID_KEY = "userId";
	/**
	 * 用户头像上传的本地硬盘路径
	 */
	public final static String USER_FACE_PATH = "D:/houseKeeper/faces";
	/**
	 * 登录密码key
	 */
	public final static String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";
	/**
	 * 验证码key
	 */
	public final static String VERIFY_CODE_KEY = "verifyCodeValue";

	/*----------------------------------角色管理---------------------------------*/
	/**
	 * 所有角色的key
	 */
	public final static String ROlE_KEY = "roles";
	/*----------------------------------模块管理---------------------------------*/
	/**
	 * 所有一级模块列表key
	 */
	public final static String ONE_MODULES_KEY = "oneModules";
	/**
	 * 所有二级模块列表key
	 */
	public final static String TWO_MODULES_KEY = "twoModules";
	/**
	 * 所有一级模块列表大小key
	 */
	public final static String ONE_MODULES_SIZE = "oneModulesSize";
	/**
	 * 所有二级模块列表大小key
	 */
	public final static String TWO_MODULES_SIZE = "oneModulesSize";
	/**
	 * 一级模块value
	 */
	public final static int ONE_MODULES_VALUE = 1;
	/**
	 * 二级模块value
	 */
	public final static int TWO_MODULES_VALUE = 2;
	/*----------------------------------账务管理---------------------------------*/
	/**
	 * 账务父类别-支出
	 */
	public final static String COMEOUT_VALUE = "支出";
	/**
	 * 账务父类别-收入
	 */
	public final static String COMEIN_VALUE = "收入";
	/**
	 * 账务父类别为收入的子类别列表key
	 */
	public final static String COMEIN_TYPES_KEY = "comeInTypes";
	/**
	 * 父类别为支出的子类别列表key
	 */
	public final static String COMEOUT_TYPES_KEY = "comeOutTypes";
	/*----------------------------------考试管理---------------------------------*/
	/**
	 * 题目列表value
	 */
	public final static String QLIST_VALUES = "qlist";
	/**
	 * 用户题目答案列表value
	 */
	public final static String ULIST_VALUES = "ulist";
	/**
	 * 分数value
	 */
	public final static String SCORE_VALUES = "score";
	/**
	 * 试卷key
	 */
	public final static String PAPER_KEY = "paper";
	/**
	 * 试卷IDkey
	 */
	public final static String PAPERID_KEY = "paperId";
	/**
	 * 试卷正确答案key
	 */
	public final static String ANSWER_KEY = "answer";
	/*----------------------------------邮件服务器的key---------------------------------*/
	/**
	 * 是否邮箱授权验证的key
	 */
	public final static String MAIL_VALIDATE_KEY = "mailValidate";
	/**
	 * 邮箱主机的key
	 */
	public final static String MAIL_HOST_KEY = "mailHost";
	/**
	 * 邮箱端口号的key
	 */
	public final static String MAIL_PORT_KEY = "mailPort";
	/**
	 * 邮件发送者的地址的key
	 */
	public final static String MAIL_FROM_KEY = "mailFrom";
	/**
	 * 授权邮箱的key
	 */
	public final static String MAIL_USERNAME_KEY = "mailUserName";
	/**
	 * 邮箱授权密码(如果开启了客户端授权码，则密码填授权码密码，没有开启，则账号登录密码)的key
	 */
	public final static String MAIL_PASSWORD_KEY = "mailPassword";

	/*----------------------------------LDAP服务器的key---------------------------------*/
	/**
	 * 开启用户自动推送的key
	 */
	public final static String START_AUTO_PUSH_KEY = "startAutoPush";
	/**
	 * 域用户名的key
	 */
	public final static String SECURITY_PRINCIPAL_KEY = "securityPrincipal";
	/**
	 * 域根节点的key
	 */
	public final static String BASE_DN_KEY = "baseDn";
	/**
	 * keyStore文件位置的key
	 */
	public final static String KEY_STORE_KEY = "keyStore";
	/**
	 * SSL域密码的key
	 */
	public final static String SSL_PASSWORD_KEY = "sslPassword";
	/**
	 * AD管理员域密码的key
	 */
	public final static String AD_PASSWORD_KEY = "adPassword";
	/**
	 * 域服务器 URL的key
	 */
	public final static String LDAP_URL_KEY = "ldapUrl";
	/**
	 * 使用加密通道的key
	 */
	public final static String USE_SSL_KEY = "useSsl";
	/**
	 * 开启推送密码的key
	 */
	public final static String PUSH_PASSWORD_KEY = "pushPassword";
	/**
	 * 用户不能更改密码的key
	 */
	public final static String PASSWORD_CANT_CHANGE_KEY = "passwdCantChange";
	/*----------------------------------snmp的key---------------------------------*/
	/**
	 * 是否监控处理器的key
	 */
	public final static String MONITOR_CPU = "monitorCpu";
	/**
	 * 是否监控硬盘的key
	 */
	public final static String MONITOR_DISK = "monitorDisk";
	/**
	 * 是否监控网络流量的key
	 */
	public final static String MONITOR_NETWORK_FLOW = "monitorNetworkFlow";
	/**
	 * 是否监控设备服务时间的key
	 */
	public final static String MONITOR_SERVICE_TIME = "monitorServiceTime";
	/**
	 * 是否监控内存的key
	 */
	public final static String MONITOR_MEMORY = "monitorMemory";
	/**
	 * 监听设备IP的key
	 */
	public final static String MONITOR_IP = "monitorIp";
	/*----------------------------------sms的key---------------------------------*/
	/**
	 * SDK服务器地址(中国网建SMS短信通，固定的)的key
	 */
	public final static String SMS_URL = "smsUrl";
	/**
	 * 用户名(中国网建SMS短信通分配的登录名)的key
	 */
	public final static String SMS_USERNAME = "smsUserName";
	/**
	 * 接口安全密钥(中国网建SMS短信通分配的接口安全密钥)的key
	 */
	public final static String SMS_PASSWORD = "smsPassword";
	/**
	 * 接受短信的号码的key
	 */
	public final static String SMS_MOBILE = "smsMobile";
	/**
	 * 是否启用sms短信的key
	 */
	public final static String ENABLE_SMS = "enableSms";

	/*-------------------------------------------------------------------*/
}
