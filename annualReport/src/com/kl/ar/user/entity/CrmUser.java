package com.kl.ar.user.entity;
/**
 * 
 * @package:com.kl.ar.user.entity
 * @Description: crm用户实体类 
 * @author: wanlei
 * @date: 2019年12月21日下午3:22:05
 */
public class CrmUser {
	// 数据库表名
	public static String TABLE = "crm_user";

	private String USERID;
	private String sCode;
	private String sName;
	private String idDep;
	private String idScheme;
	private String sPhoto;
	private String sMemo;
	private String lLoginUser;
	private String sIDCard;
	private String dBirthday;
	private String sAddress;
	private String sPostcode;
	private String emTerritory;
	private String sTel;
	private String sTelHome;
	private String sMobile;
	private String sMail;
	private String sMailPersonal;
	private String sIM;
	private String Created;
	private String CreatedBy;
	private String Updated;
	private String UpdatedBy;
	private String bAdmin;
	private String emSex;
	private String emStatus;
	private String sPhoto_title;
	private String stid;
	private String issales;
	private String location;
	private String dirtelphone;
	private String indate;
	private String outdate;
	private String needfilllog;
	private String bmanager;
	private String bcheck;
	private String idorg;
	private String position;
	private String buref;
	private String certcn;
	private String idorgperf;
	private String handtodep;
	private String handtoemp;
	private String role;
	private String partTime;

	// 用于展示的信息
	public String depName;

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getIdDep() {
		return idDep;
	}

	public void setIdDep(String idDep) {
		this.idDep = idDep;
	}

	public String getIdScheme() {
		return idScheme;
	}

	public void setIdScheme(String idScheme) {
		this.idScheme = idScheme;
	}

	public String getsPhoto() {
		return sPhoto;
	}

	public void setsPhoto(String sPhoto) {
		this.sPhoto = sPhoto;
	}

	public String getsMemo() {
		return sMemo;
	}

	public void setsMemo(String sMemo) {
		this.sMemo = sMemo;
	}

	public String getlLoginUser() {
		return lLoginUser;
	}

	public void setlLoginUser(String lLoginUser) {
		this.lLoginUser = lLoginUser;
	}

	public String getsIDCard() {
		return sIDCard;
	}

	public void setsIDCard(String sIDCard) {
		this.sIDCard = sIDCard;
	}

	public String getdBirthday() {
		return dBirthday;
	}

	public void setdBirthday(String dBirthday) {
		this.dBirthday = dBirthday;
	}

	public String getsAddress() {
		return sAddress;
	}

	public void setsAddress(String sAddress) {
		this.sAddress = sAddress;
	}

	public String getsPostcode() {
		return sPostcode;
	}

	public void setsPostcode(String sPostcode) {
		this.sPostcode = sPostcode;
	}

	public String getEmTerritory() {
		return emTerritory;
	}

	public void setEmTerritory(String emTerritory) {
		this.emTerritory = emTerritory;
	}

	public String getsTel() {
		return sTel;
	}

	public void setsTel(String sTel) {
		this.sTel = sTel;
	}

	public String getsTelHome() {
		return sTelHome;
	}

	public void setsTelHome(String sTelHome) {
		this.sTelHome = sTelHome;
	}

	public String getsMobile() {
		return sMobile;
	}

	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}

	public String getsMail() {
		return sMail;
	}

	public void setsMail(String sMail) {
		this.sMail = sMail;
	}

	public String getsMailPersonal() {
		return sMailPersonal;
	}

	public void setsMailPersonal(String sMailPersonal) {
		this.sMailPersonal = sMailPersonal;
	}

	public String getsIM() {
		return sIM;
	}

	public void setsIM(String sIM) {
		this.sIM = sIM;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getUpdated() {
		return Updated;
	}

	public void setUpdated(String updated) {
		Updated = updated;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getbAdmin() {
		return bAdmin;
	}

	public void setbAdmin(String bAdmin) {
		this.bAdmin = bAdmin;
	}

	public String getEmSex() {
		return emSex;
	}

	public void setEmSex(String emSex) {
		this.emSex = emSex;
	}

	public String getEmStatus() {
		return emStatus;
	}

	public void setEmStatus(String emStatus) {
		this.emStatus = emStatus;
	}

	public String getsPhoto_title() {
		return sPhoto_title;
	}

	public void setsPhoto_title(String sPhoto_title) {
		this.sPhoto_title = sPhoto_title;
	}

	public String getStid() {
		return stid;
	}

	public void setStid(String stid) {
		this.stid = stid;
	}

	public String getIssales() {
		return issales;
	}

	public void setIssales(String issales) {
		this.issales = issales;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDirtelphone() {
		return dirtelphone;
	}

	public void setDirtelphone(String dirtelphone) {
		this.dirtelphone = dirtelphone;
	}

	public String getIndate() {
		return indate;
	}

	public void setIndate(String indate) {
		this.indate = indate;
	}

	public String getOutdate() {
		return outdate;
	}

	public void setOutdate(String outdate) {
		this.outdate = outdate;
	}

	public String getNeedfilllog() {
		return needfilllog;
	}

	public void setNeedfilllog(String needfilllog) {
		this.needfilllog = needfilllog;
	}

	public String getBmanager() {
		return bmanager;
	}

	public void setBmanager(String bmanager) {
		this.bmanager = bmanager;
	}

	public String getBcheck() {
		return bcheck;
	}

	public void setBcheck(String bcheck) {
		this.bcheck = bcheck;
	}

	public String getIdorg() {
		return idorg;
	}

	public void setIdorg(String idorg) {
		this.idorg = idorg;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBuref() {
		return buref;
	}

	public void setBuref(String buref) {
		this.buref = buref;
	}

	public String getCertcn() {
		return certcn;
	}

	public void setCertcn(String certcn) {
		this.certcn = certcn;
	}

	public String getIdorgperf() {
		return idorgperf;
	}

	public void setIdorgperf(String idorgperf) {
		this.idorgperf = idorgperf;
	}

	public String getHandtodep() {
		return handtodep;
	}

	public void setHandtodep(String handtodep) {
		this.handtodep = handtodep;
	}

	public String getHandtoemp() {
		return handtoemp;
	}

	public void setHandtoemp(String handtoemp) {
		this.handtoemp = handtoemp;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPartTime() {
		return partTime;
	}

	public void setPartTime(String partTime) {
		this.partTime = partTime;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	@Override
	public String toString() {
		return "CrmUser [USERID=" + USERID + ", sCode=" + sCode + ", sName=" + sName + ", idDep=" + idDep
				+ ", idScheme=" + idScheme + ", sPhoto=" + sPhoto + ", sMemo=" + sMemo + ", lLoginUser=" + lLoginUser
				+ ", sIDCard=" + sIDCard + ", dBirthday=" + dBirthday + ", sAddress=" + sAddress + ", sPostcode="
				+ sPostcode + ", emTerritory=" + emTerritory + ", sTel=" + sTel + ", sTelHome=" + sTelHome
				+ ", sMobile=" + sMobile + ", sMail=" + sMail + ", sMailPersonal=" + sMailPersonal + ", sIM=" + sIM
				+ ", Created=" + Created + ", CreatedBy=" + CreatedBy + ", Updated=" + Updated + ", UpdatedBy="
				+ UpdatedBy + ", bAdmin=" + bAdmin + ", emSex=" + emSex + ", emStatus=" + emStatus + ", sPhoto_title="
				+ sPhoto_title + ", stid=" + stid + ", issales=" + issales + ", location=" + location + ", dirtelphone="
				+ dirtelphone + ", indate=" + indate + ", outdate=" + outdate + ", needfilllog=" + needfilllog
				+ ", bmanager=" + bmanager + ", bcheck=" + bcheck + ", idorg=" + idorg + ", position=" + position
				+ ", buref=" + buref + ", certcn=" + certcn + ", idorgperf=" + idorgperf + ", handtodep=" + handtodep
				+ ", handtoemp=" + handtoemp + ", role=" + role + ", partTime=" + partTime + "]";
	}

}
