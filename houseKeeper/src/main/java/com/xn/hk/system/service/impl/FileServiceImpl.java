package com.xn.hk.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.common.service.impl.BaseServiceImpl;
import com.xn.hk.system.dao.FileDao;
import com.xn.hk.system.model.File;
import com.xn.hk.system.service.FileService;

/**
 * 
 * @Title: FileServiceImpl
 * @Package: com.xn.ad.system.service.impl
 * @Description: 处理文件的service业务逻辑实现层
 * @Author: wanlei
 * @Date: 2017-11-28 下午03:30:15
 */
@Service
public class FileServiceImpl extends BaseServiceImpl<File> implements FileService {
	@Autowired
	private FileDao fileDao;

	/**
	 * 实现父类的方法，指定所用的dao
	 */
	public BaseDao<File> getDao() {
		return fileDao;
	}

}
