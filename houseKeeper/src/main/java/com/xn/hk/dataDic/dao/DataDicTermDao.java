package com.xn.hk.dataDic.dao;

import org.apache.ibatis.annotations.Param;

import com.xn.hk.common.dao.BaseDao;
import com.xn.hk.dataDic.model.DataDicTerm;

/**
 * 
 * @ClassName: DataDicTermDao
 * @Package: com.xn.hk.dataDic.dao
 * @Description: 数据字典项的dao层
 * @Author: wanlei
 * @Date: 2018年11月7日 下午6:47:30
 */
public interface DataDicTermDao extends BaseDao<DataDicTerm> {
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
	public DataDicTerm findTermCodeAndDataDicCode(@Param(value = "dataDicTermCode") String dataDicTermCode,
			@Param(value = "dataDicCode") String dataDicCode);

}
