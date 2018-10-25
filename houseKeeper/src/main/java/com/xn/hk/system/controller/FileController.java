package com.xn.hk.system.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xn.hk.common.constant.Constant;
import com.xn.hk.common.constant.FileType;
import com.xn.hk.common.constant.View;
import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.file.FileUtil;
import com.xn.hk.common.utils.log.LogHelper;
import com.xn.hk.common.utils.log.LogType;
import com.xn.hk.common.utils.page.BasePage;
import com.xn.hk.common.utils.string.DateFormatUtil;
import com.xn.hk.common.utils.string.StringUtil;
import com.xn.hk.system.dao.AdminLogDao;
import com.xn.hk.system.model.FileEntity;
import com.xn.hk.system.model.User;
import com.xn.hk.system.service.FileService;
import com.xn.hk.system.service.UserService;

/**
 * 
 * @Title: FileController
 * @Package: com.xn.hk.system.controller
 * @Description: 处理文件的控制层
 * @Author: wanlei
 * @Date: 2018年1月23日 下午4:00:22
 */
@Controller
@RequestMapping(value = "/system/file")
public class FileController {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	/**
	 * 注入service层
	 */
	@Autowired
	private FileService fileService;
	@Autowired
	private UserService userService;
	@Autowired
	private AdminLogDao adminLogDao;

	/**
	 * 分页显示所有的文件
	 * 
	 * @param log
	 *            日志对象
	 * @param pages
	 *            分页对象
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/showAllFile.do")
	public ModelAndView showAllFile(FileEntity file, BasePage<FileEntity> pages) {
		ModelAndView mv = new ModelAndView("system/showAllFile");
		// 封装查询条件
		pages.setBean(file);
		List<FileEntity> files = fileService.pageList(pages);
		// 将list封装到分页对象中
		pages.setList(files);
		mv.addObject(Constant.PAGES, pages);
		// 查询所有的用户
		List<User> users = userService.findAll();
		mv.addObject(Constant.USERS, users);
		return mv;
	}

	/**
	 * 上传文件并保存文件至数据库中
	 * 
	 * @param file
	 *            文件实体
	 * @param session
	 *            session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/uploadFile.do")
	public ModelAndView uploadFile(MultipartFile uploadFile, String remark, HttpSession session) {
		// 上传文件非空判断
		if (uploadFile.isEmpty()) {
			logger.info("上传文件为空!");
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("上传文件为空!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
		// 得到原始上传的文件名
		String originalFileName = uploadFile.getOriginalFilename();
		// 截取文件后缀,判断系统是否支持该文件类型,这里注意要用lastIndexOf查找.，因为文件名中可能含有.
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		FileType type = FileType.getFileTypeBySuffix(suffix);
		if (type == null) {
			logger.error("不支持{}文件类型!", suffix);
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("不支持" + suffix + "文件类型!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
		// 构建新的文件名(UUID_老文件名)
		String newFileName = StringUtil.genUUIDString() + "_" + originalFileName;
		// 得到配置文件中的文件存储位置(以yyyyMMdd目录分开存储)
		String filePath = SystemCfg.loadCfg().getProperty(SystemCfg.FILE_PATH) + File.separator
				+ String.valueOf(DateFormatUtil.getNumberDay()) + File.separator + newFileName;
		File localFile = new File(filePath);
		// 目录不存在，则创建目录
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		try {
			// 调用springmvc中自带的上传方法
			uploadFile.transferTo(localFile);
		} catch (Exception e) {
			logger.error("上传文件失败，原因为:", e);
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("上传文件失败!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
		// 封装文件实体类，调用service方法入库
		FileEntity fileEntity = new FileEntity();
		User user = (User) session.getAttribute(Constant.SESSION_USER);
		fileEntity.setFileId(StringUtil.genUUIDString());// 生成UUID的文件ID
		fileEntity.setFileName(originalFileName);
		fileEntity.setFileType(type.getTypeId());
		fileEntity.setUploadBy(user.getUserId());
		fileEntity.setUploadByName(user.getUserName());
		fileEntity.setFilePath(filePath);// 文件存储位置即下载路径
		fileEntity.setUploadTime(DateFormatUtil.formatDateTime());
		fileEntity.setUpdateTime(DateFormatUtil.formatDateTime());
		fileEntity.setCurday(DateFormatUtil.getNumberDay());
		fileEntity.setRemark(remark);
		int result = fileService.insert(session, "上传文件", LogType.FILE_LOG.getType(), fileEntity);
		if (result == Constant.ZERO_VALUE) {
			logger.error("添加{}文件失败!", originalFileName);
		} else {
			logger.info("添加{}文件成功!", originalFileName);
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("上传文件成功!", Constant.SUCCESS_TIP));
		}
		return View.FILE_REDITRCT_ACTION;
	}

	/**
	 * 处理上传文件大小超过springmvc中配置的最大文件大小时，抛出的异常
	 * 
	 * @param ex
	 *            异常信息
	 * @param session
	 * @return ModelAndView
	 */
	@ExceptionHandler
	public ModelAndView handleException(Exception ex, HttpSession session) {
		// 处理文件上传过大异常
		if (ex instanceof MaxUploadSizeExceededException) {
			long maxSize = ((MaxUploadSizeExceededException) ex).getMaxUploadSize();
			String fileSize = maxSize / (1024 * 1024) + "M";
			logger.error("上传文件大小超过{},异常信息为:{}", fileSize, ex);
			session.setAttribute(Constant.TIP_MSG,
					StringUtil.genTipMsg("上传文件大小超过" + fileSize + ",请更换文件!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
		return null;
	}

	/**
	 * 修改文件备注信息
	 * 
	 * @param file
	 *            文件实体
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/update.do")
	public ModelAndView updateFile(FileEntity file, HttpSession session) {
		int result = fileService.update(session, "修改文件", LogType.FILE_LOG.getType(), file);
		if (result == Constant.ZERO_VALUE) {
			logger.error("修改{}文件失败!", file.getFileName());
		} else {
			logger.info("修改{}文件成功!", file.getFileName());
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("修改文件成功!", Constant.SUCCESS_TIP));
		}
		return View.FILE_REDITRCT_ACTION;
	}

	/**
	 * 根据ID数组删除一个或多个文件
	 * 
	 * @param fileIds
	 *            文件ID数组
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/delete.do")
	public ModelAndView deleteFile(String[] fileIds, HttpSession session) {
		// 先删除本地文件
		for (String fileId : fileIds) {
			FileEntity file = fileService.findById(fileId);
			FileUtil.deleteFile(file.getFilePath());
		}
		// 再删除数据库记录
		int result = fileService.batchDelete(session, "删除文件", LogType.FILE_LOG.getType(), fileIds);
		if (result == Constant.ZERO_VALUE) {
			logger.error("删除失败,该数组不存在!");
		} else {
			logger.info("删除文件成功!");
			session.setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("删除文件成功!", Constant.SUCCESS_TIP));
		}
		return View.FILE_REDITRCT_ACTION;
	}

	/**
	 * 文件下载 注意重定向不能与下载文件同时用一个response，会报错
	 * 
	 * @param fileId
	 *            文件ID
	 * @param request
	 *            request请求
	 * @param response
	 *            response响应
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/downloadFile.do")
	public ModelAndView downloadFile(String fileId, HttpServletRequest request, HttpServletResponse response) {
		String downloadFileName = "";// 下载文件名
		FileEntity fileEntity = fileService.findById(fileId);
		if (fileEntity == null) {
			request.getSession().setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("下载文件不存在!", Constant.ERROR_TIP));
			logger.error("下载文件ID不存在，加载文件信息失败!");
			return View.FILE_REDITRCT_ACTION;
		}
		// 获取要下载的原始文件名
		String orginFileName = fileEntity.getFileName();
		// 判断是否为IE11浏览器
		Boolean isIE11 = request.getHeader("User-Agent").indexOf("like Gecko") > 0;
		try {
			// 兼容IE和非IE,下载文件名中文乱码问题
			if (request.getHeader("user-agent").toLowerCase().contains("msie") || isIE11) {
				// IE
				downloadFileName = URLEncoder.encode(orginFileName, Constant.UTF8);
			} else {
				// 非IE
				downloadFileName = new String(orginFileName.getBytes(Constant.UTF8), Constant.ISO_8859_1);
			}
		} catch (Exception e) {
			logger.error("构建下载文件名失败，原因为:" + e);
		}
		// 获取数据库中存的下载路径
		String filePath = fileEntity.getFilePath();
		File file = new File(filePath);
		if (file.exists()) {
			BufferedInputStream bis = null;
			OutputStream os = null;
			try {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.setContentLength(FileUtil.getFileSize(filePath));// 设置下载文件大小
				response.addHeader("Content-Disposition", "attachment;fileName=" + downloadFileName);// 设置下载文件名
				byte[] buffer = new byte[1024];
				bis = new BufferedInputStream(new FileInputStream(file), 1024 * 1024);
				os = response.getOutputStream();
				int len = 0;
				while ((len = bis.read(buffer)) != -1) {
					os.write(buffer, 0, len);
				}
				os.flush();
			} catch (Exception e) {
				logger.error("下载{}文件失败!错误原因:{}", orginFileName, e);
				return null;
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						logger.error("关闭流失败，原因为:" + e);
					}
				}
				if (os != null) {
					try {
						os.flush();
						os.close();
					} catch (IOException e) {
						logger.error("关闭流失败，原因为:" + e);
					}
				}
			}
			logger.info("下载{}文件成功!", orginFileName);
			// 记录日志
			LogHelper.getInstance().saveLog(adminLogDao, request.getSession(), "下载文件", true, LogType.FILE_LOG.getType(),
					fileEntity);
			return null;
		} else {
			logger.error("路径{}下找不到下载文件:{}!", filePath, orginFileName);
			// 记录日志
			LogHelper.getInstance().saveLog(adminLogDao, request.getSession(), "下载文件", false,
					LogType.FILE_LOG.getType(), fileEntity);
			request.getSession().setAttribute(Constant.TIP_MSG, StringUtil.genTipMsg("路径下找不到该文件!", Constant.ERROR_TIP));
			return View.FILE_REDITRCT_ACTION;
		}
	}
}
