package com.xn.hk.dataDic.service;

import com.xn.hk.common.service.BaseService;
import com.xn.hk.dataDic.model.DataDicTerm;

/**
 * 
 * @ClassName: DataDicTermService
 * @Package: com.xn.hk.data.dic.service
 * @Description: 数据字典项的service层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:50:16
 */
public interface DataDicTermService extends BaseService<DataDicTerm> {
	/**
	 * 根据数据字典代码查找该数据字典项个数
	 * 
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 返回数据字典项个数
	 */
	public int findCountByDataDicCode(String dataDicCode);

	/**
	 * 根据数据字典项代码和所属数据字典代码查找该数据字典项
	 * 
	 * @param dataDicTermCode
	 *            数据字典项代码
	 * @param dataDicCode
	 *            数据字典代码
	 * @return 数据字典项
	 */
	public DataDicTerm findTermCodeAndDataDicCode(String dataDicTermCode, String dataDicCode);

}
