package com.xn.hk.common.constant;

import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: View
 * @Package: com.xn.hk.common.constant
 * @Description: 系统中所有重定向Action的常量类
 * @Author: wanlei
 * @Date: 2018年8月23日 上午10:32:05
 */
public class View {
	/*----------------------------------系统管理---------------------------------*/
	public static final ModelAndView USER_REDITRCT_ACTION = new ModelAndView("redirect:showAllUser.do");// 重定向分页所有用户的Action
	public static final ModelAndView USER_REDITRCT_LOGIN_VIEW = new ModelAndView("redirect:tologin.do");// 重定向到登录页面
	public static final ModelAndView USER_REDITRCT_HOME_VIEW = new ModelAndView("redirect:tohome.do");// 重定向到后台首页页面
	public static final ModelAndView USER_REDITRCT_UPDATE_PWD_VIEW = new ModelAndView("redirect:toUpdatePwd.do");// 重定向到后台修改密码页面
	public static final ModelAndView ROLE_REDITRCT_ACTION = new ModelAndView("redirect:showAllRole.do");// 重定向分页所有角色的Action
	public static final ModelAndView MODULE_REDITRCT_ACTION = new ModelAndView("redirect:showAllModule.do");// 重定向分页所有模块的Action
	public static final ModelAndView CHANNEL_REDITRCT_ACTION = new ModelAndView("redirect:showAllChannelData.do");// 重定向分页所有渠道的Action
	public static final ModelAndView FILE_REDITRCT_ACTION = new ModelAndView("redirect:showAllFile.do");// 重定向分页所有文件的Action
	/*----------------------------------账务管理---------------------------------*/
	public static final ModelAndView PERSONAL_ACCOUNT_REDITRCT_ACTION = new ModelAndView(
			"redirect:showPersonalAccount.do");// 重定向分页所有账务的Action
	public static final ModelAndView ACCOUNT_TYPE_REDITRCT_ACTION = new ModelAndView("redirect:showPersonalType.do");// 重定向分页所有账务类别的Action
	/*----------------------------------考试管理---------------------------------*/
	public static final ModelAndView CLIENT_PAPER_REDITRCT_ACTION = new ModelAndView("redirect:showAllClientPaper.do");// 重定向分页所有前台试卷的Action
	public static final ModelAndView PAPER_REDITRCT_ACTION = new ModelAndView("redirect:showAllPaper.do");// 重定向分页所有试卷的Action
	public static final ModelAndView QUESTION_REDITRCT_ACTION = new ModelAndView("redirect:showAllQuestion.do");// 重定向分页所有题目的Action
	public static final ModelAndView QUESTION_TYPE_REDITRCT_ACTION = new ModelAndView("redirect:showAllType.do");// 重定向分页所有题型的Action
	public static final ModelAndView SCORE_REDITRCT_ACTION = new ModelAndView("redirect:showAllScore.do");// 重定向分页所有分数的Action
}
