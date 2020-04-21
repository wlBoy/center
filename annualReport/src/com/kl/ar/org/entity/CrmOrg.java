package com.kl.ar.org.entity;

import java.util.Date;

/**
 * 
 * @package:com.kl.ar.org.entity
 * @Description: crm机构实体类
 * @author: wanlei
 * @date: 2019年12月21日下午3:02:49
 */
public class CrmOrg {
	// 数据库表名
	public static String TABLE = "crm_org";
	private String ORGID;
	private String sCode;
	private String sName;
	private String idEmployee;
	private String sMemo;
	private String Created;
	private Date Updated;
	private String UpdatedBy;
	private String pathid;
	private String idparent;
	private String bfail;
	private String orgtype;
	private String sName2;
	private String pathid2;
	private String babstract;
	private String org_id;

	public String getORGID() {
		return ORGID;
	}

	public void setORGID(String oRGID) {
		ORGID = oRGID;
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

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public String getsMemo() {
		return sMemo;
	}

	public void setsMemo(String sMemo) {
		this.sMemo = sMemo;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

	public Date getUpdated() {
		return Updated;
	}

	public void setUpdated(Date updated) {
		Updated = updated;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getPathid() {
		return pathid;
	}

	public void setPathid(String pathid) {
		this.pathid = pathid;
	}

	public String getIdparent() {
		return idparent;
	}

	public void setIdparent(String idparent) {
		this.idparent = idparent;
	}

	public String getBfail() {
		return bfail;
	}

	public void setBfail(String bfail) {
		this.bfail = bfail;
	}

	public String getOrgtype() {
		return orgtype;
	}

	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}

	public String getsName2() {
		return sName2;
	}

	public void setsName2(String sName2) {
		this.sName2 = sName2;
	}

	public String getPathid2() {
		return pathid2;
	}

	public void setPathid2(String pathid2) {
		this.pathid2 = pathid2;
	}

	public String getBabstract() {
		return babstract;
	}

	public void setBabstract(String babstract) {
		this.babstract = babstract;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	@Override
	public String toString() {
		return "CrmOrg [ORGID=" + ORGID + ", sCode=" + sCode + ", sName=" + sName + ", idEmployee=" + idEmployee
				+ ", sMemo=" + sMemo + ", Created=" + Created + ", Updated=" + Updated + ", UpdatedBy=" + UpdatedBy
				+ ", pathid=" + pathid + ", idparent=" + idparent + ", bfail=" + bfail + ", orgtype=" + orgtype
				+ ", sName2=" + sName2 + ", pathid2=" + pathid2 + ", babstract=" + babstract + ", org_id=" + org_id
				+ "]";
	}

}
