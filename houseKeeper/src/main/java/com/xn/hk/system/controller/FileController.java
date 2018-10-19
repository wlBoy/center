package com.xn.hk.system.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
