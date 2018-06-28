package com.seecen.exam.day0807;


/**
 * Register业务类
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class RegisterBiz {

	/**
	 * 校验输入的数据是否合法
	 * @param register 数据对象
	 * @return int 1合法  2身份证错误 3 手机号错误 4座机号错误
	 */
	public int checkInput(Register register) {
		boolean idCardHefa = checkId(register.getIdCard());
		if(!idCardHefa) return 2;
		boolean phoneHefa = checkPhone(register.getPhone());
		if(!phoneHefa) return 3;
		boolean telHefa = checkTel(register.getTel());
		if(!telHefa) return 4;
		
		return 1;
	}
	/**
	 * 检查座机号码
	 * @param tel 座机号码
	 * @return 正确返回true，错误返回false
	 */
	private boolean checkTel(String tel) {
		int index = tel.indexOf("-");
		if(index != 4) {
			return false;
		}
		String houmian = tel.substring(index + 1);	
		if(houmian.length() != 8) {
			return false;
		}
		return true;
	}
	/**
	 * 检查手机号码
	 * @param phone 手机号码
	 * @return 正确返回true，错误返回false
	 */
	private boolean checkPhone(String phone) {
		return phone.length() == 11 ? true : false;
	}
	/**
	 * 检查身份证号码
	 * @param idCard 身份证号码
	 * @return 正确返回true，错误返回false
	 */
	private boolean checkId(String idCard) {
		if(idCard.length() == 16 || idCard.length() == 18 ) {
			return true;
		}
		return false;
	}

}
