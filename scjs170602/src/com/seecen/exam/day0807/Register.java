package com.seecen.exam.day0807;

/**
 * Register实体类(javabean)
 * @scjs170602
 * @author 【万磊】
 * @2017年8月7日
 */
public class Register {
	private String idCard;// 身份证
	private String phone; // 手机号
	private String tel; // 座机号

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Register(String idCard, String phone, String tel) {
		super();
		this.idCard = idCard;
		this.phone = phone;
		this.tel = tel;
	}
	public Register(){
		super();
	}
}
