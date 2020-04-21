package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 系统常量类
 * @author: wanlei
 * @date: 2019年12月21日下午2:03:28
 */
public class Constant {
	// common
	public static final String USER_IN_SESSION = "userInSession";
	public static final String OP = "op";
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF-8";
	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String SELECT_YEAR_MAP = "selectYearMap";
	public static final String SEARCH_YEAR = "searchYear";
	public static final String SUCCESS_RESULT = "1";// 成功
	public static final String FAILURE_RESULT = "0";// 失败
	public static final String ERROR_INFO = "errorInfo";// 失败信息
	public static final String AJAX_RESULT = "res";
	public static final String AJAX_RESULT_MSG = "msg";
	public static final String USER_LEADER_ORG = "orgs";
	public static final String LIMIT = "limit";
	public static final String OFFSET = "offset";

	// sysConfig
	public static final String CONFIG_PROPERTIES = "config.properties";
	public static final String MIN_YEAR = "minYear";
	public static final String CURRENT_YEAR = "currentYear";
	public static final String DEFAULT_LOGIN_PWD = "defaultLoginPwd";
	public static final String ENABLE_REGISTER_USER = "enableRegisterUser";
	public static final String ADMIN_PWD_LOGIN_URL = "adminPwdLoginUrl";
	public static final String KOAL_CN_COOKIE = "koalCertCn";
	public static final String KOAL_E_COOKIE = "koalCertE";
	public static final String ENABLE_VOTE = "enableVote";

	// dbConfig
	public static final String DB_PROPERTIES = "db.properties";
	public static final String DB_JDBC_DRIVER = "jdbcdriver";
	public static final String DB_URL = "url";
	public static final String DB_USERNAME = "username";
	public static final String DB_PASSWORD = "password";

	// errorTip
	public static final String TIP_SYSTEM_ERROR = "系统内部错误";
	public static final String TIP_USERNAME_EMPTY = "用户名不能为空";
	public static final String TIP_PASSWORD_EMPTY = "密码不能为空";
	public static final String TIP_USERNAME_NOT_EXIST = "用户名不存在";
	public static final String TIP_PWD_ERROR= "密码不正确";
	public static final String TIP_CERT_USERNAME_EAMIL_EMPTY = "未解析到证书中的用户名和邮箱信息";
	public static final String TIP_CERT_LOGIN_FAILURE = "根据证书中的用户名或邮箱查询数据库，找不到对应的用户信息";
	
	//assessment
	public static final String ASSESSMENT_LIST = "assessmentList";
	public static final String HAS_ASSESSMENT = "hasAssessment";
	public static final String ASSESSMENT_DETAIL = "assessmentDetail";
	public static final String SELF_ASSESSMENT = "selfAssessment";
	public static final String PARAM_ASSESSMENT_YEAR = "assessmentYear";
	
	//report
	public static final String REPORT_LIST = "reportList";
	public static final String HAS_SALE = "hasSale";
	public static final String HAS_TEC = "hasTec";
	public static final String HAS_ADMINISTRATION = "hasAdministration";
	public static final String REPORT = "report";
	public static final String TYPE = "type";
	public static final String ID = "id";
	public static final String USER_ID = "userId";
	public static final String USER = "user";
	public static final String PARAM_USER_NAME = "userName";
	public static final String PARAM_PATHID = "pathid";
	public static final String PARAM_STATUS = "status";
	public static final String PARAM_TYPE = "type";
	public static final String PARAM_REPORT_YEAR = "reportYear";
	
	// vote
	public static final String PRIZE_TYPE_LIST = "prizeTypeList";
	public static final String PRIZE_TYPE_CODE = "prizeTypeCode";
	public static final String VOTE_LIST = "voteList";
	public static final String PARAMS = "params";
	public static final String USER_VOTE_TYPE = "userVoteType";
	public static final String VW_VOTE_RESULT_SUM_LIST = "vwVoteResultSumList";
	public static final String VOTE_RESULT = "voteResult";
	public static final String PARAM_GOLD_WEIGHT = "goldWeight";
	public static final String PARAM_SILVER_WEIGHT = "silverWeight";
	public static final String PARAM_BRONZE_WEIGHT = "bronzeWeight";
	public static final String PARAM_VOTE_YEAR = "voteYear";
	public static final String VOTED_CURRENT_YEAR = "votedCurrentYear";

}
