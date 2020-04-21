package com.kl.ar.common.constant;

/**
 * 
 * @package:com.kl.ar.common.constant
 * @Description: 系统视图常量类
 * @author: wanlei
 * @date: 2019年12月21日下午2:16:24
 */
public class View {
	public static final String LOGIN_JSP = "/WEB-INF/view/login.jsp";
	// common
	public static final String SESSION_JSP = "/WEB-INF/view/common/session.jsp";
	public static final String ERROR_JSP = "/WEB-INF/view/common/error.jsp";
	// report
	public static final String INDEX_JSP = "/WEB-INF/view/report/index.jsp";
	public static final String REPORT_JSP = "/WEB-INF/view/report/report.jsp";
	public static final String REPORT_DETAIL_JSP = "/WEB-INF/view/report/reportDetail.jsp";
	public static final String REPORT_DETAIL_LIST_JSP = "/WEB-INF/view/report/reportDetailList.jsp";
	public static final String REPORT_LIST_JSP = "/WEB-INF/view/report/reportList.jsp";
	public static final String EXPORT_ALL_JSP = "/WEB-INF/view/report/exportAll.jsp";
	// selfAssessment
	public static final String MY_VALUE_LIST_JSP = "/WEB-INF/view/selfAssessment/myValueList.jsp";
	public static final String MY_VALUE_JSP = "/WEB-INF/view/selfAssessment/myValue.jsp";
	public static final String ASSESSMENT_LIST_JSP = "/WEB-INF/view/selfAssessment/assessmentList.jsp";
	public static final String DEPT_MY_VALUE_JSP = "/WEB-INF/view/selfAssessment/deptMyValue.jsp";
	public static final String DETAIL_MY_VALUE_JSP = "/WEB-INF/view/selfAssessment/detailMyValue.jsp";
	// vote
	public static final String ANNUAL_VOTE_JSP = "/WEB-INF/view/vote/annualVote.jsp";
	public static final String VOTE_DETAIL_JSP = "/WEB-INF/view/vote/voteDetail.jsp";
	public static final String VOTE_LIST_JSP = "/WEB-INF/view/vote/voteList.jsp";
	public static final String SUM_VOTE_BY_PRIZE_JSP = "/WEB-INF/view/vote/sumVoteByPrize.jsp";
	public static final String SUM_VOTE_BY_WEIGHT_JSP = "/WEB-INF/view/vote/sumVoteByWeight.jsp";
	// action
	public static final String RS_TOINDEX_ACTION = "/rs?op=toIndex";
	public static final String PWDLOGIN_TOLOGIN_ACTION = "/pwdLogin?op=toLogin";
	public static final String PWDLOGIN_TOSESSION_ACTION = "/pwdLogin?op=toSession";

}
