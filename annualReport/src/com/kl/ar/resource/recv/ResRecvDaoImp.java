package com.kl.ar.resource.recv;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.kl.ar.common.db.BizDb;
import com.kl.ar.common.util.DateUtil;
import com.kl.ar.common.util.StringUtil;
import com.kl.ar.org.entity.CrmOrg;
import com.kl.ar.user.entity.CrmUser;

import koal.urm.client.action.IpHolder;
import koal.urm.client.resource.recv.IResRecvDao;

/**
 * 
 * @package:com.kl.ar.resource.recv
 * @Description: 资源接受处理类，从KIAM中同步机构和用户数据
 * @author: wanlei
 * @date: 2019年12月21日下午3:12:07
 */
public class ResRecvDaoImp implements IResRecvDao {
	private Logger logger = Logger.getLogger(ResRecvDaoImp.class);

	@Override
	public void beginTrans() throws Exception {
		logger.info("beginTrans");
	}

	@Override
	public void commit() throws Exception {
		logger.info("commit");
	}

	@Override
	public void rollback() throws Exception {
		logger.info("rollback");
	}

	/**
	 * 保存用户信息
	 */
	@Override
	public void saveUser(Map<String, Object> resourceMap) throws Exception {
		logger.info("发送方ip:" + IpHolder.getIp());
		logger.info("推送时间：" + DateUtil.formatDateTime() + ",saveUser:" + resourceMap);
		Map<String, Object> userMap = getUserMap(resourceMap);
		// 根据用户证书CN和邮箱为组合唯一项查询是否更新还是插入操作
		Map<String, Object> dbUserMap = BizDb.queryOneRow(
				"select * from " + CrmUser.TABLE + " where sMail=? AND certcn=?", userMap.get("sMail"),
				userMap.get("certcn"));
		if (dbUserMap != null) {
			int comp = compareInfo(0, userMap, dbUserMap);
			if (comp < 0) {
				// 更新
				String sql = "update " + CrmUser.TABLE
						+ " set sCode=?,sName=?,idDep=?,sMobile=?,emSex=?,idorg=?,UpdatedBy=? where sMail=? AND certcn=?";
				BizDb.update(sql, userMap.get("sCode"), userMap.get("sName"), userMap.get("idDep"),
						userMap.get("sMobile"), userMap.get("emSex"), userMap.get("idorg"), "kiam",
						userMap.get("sMail"), userMap.get("certcn"));
			}
		} else {
			// 插入
			CrmUser user = new CrmUser();
			user.setUSERID(StringUtil.genUUIDString());
			BeanUtils.populate(user, userMap);
			user.setNeedfilllog("1");
			user.setBmanager("0");
			user.setCreatedBy("kiam");
			BizDb.insert(user, CrmUser.TABLE);
		}
	}

	@Override
	public void deleteUser(Map<String, Object> resourceMap) {

	}

	/**
	 * 解析用户map
	 * 
	 * @param selfOrgMaps
	 */
	private Map<String, Object> getUserMap(Map<String, Object> resourceMap) throws Exception {
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		String userEmial = String.valueOf(resourceMap.get("USER_EMAIL"));
		String userName = String.valueOf(resourceMap.get("USER_NAME"));
		String orgId = String.valueOf(resourceMap.get("ORG_ID"));
		String addTime = String.valueOf(resourceMap.get("ADD_TIME"));
		if (resourceMap.get("USER_MOBILE") != null) {
			String userMobile = String.valueOf(resourceMap.get("USER_MOBILE"));
			userMap.put("sMobile", userMobile);
		}
		if (resourceMap.get("USER_SEX") != null) {
			String userSex = String.valueOf(resourceMap.get("USER_SEX"));
			userMap.put("emSex", userSex == "男" ? "0" : "1");
		}
		userMap.put("sCode", userEmial);
		userMap.put("sName", userName);
		userMap.put("idDep", orgId);
		userMap.put("sMail", userEmial);
		userMap.put("Created", addTime);
		userMap.put("indate", addTime);
		userMap.put("idorg", orgId);
		userMap.put("certcn", userName);
		return userMap;
	}

	/**
	 * 保存机构信息
	 */
	@Override
	public void saveOrg(Map<String, Object> resourceMap) throws Exception {
		logger.info("发送方ip:" + IpHolder.getIp());
		logger.info("推送时间：" + DateUtil.formatDateTime() + ",saveOrg:" + resourceMap);
		Map<String, Object> orgMap = getOrgMap(resourceMap);
		// 根据机构ID查询是否更新还是插入操作
		Map<String, Object> dbOrgMap = BizDb.queryOneRow("select * from " + CrmOrg.TABLE + " where ORGID=?",
				orgMap.get("ORGID"));
		if (dbOrgMap != null) {
			int comp = compareInfo(1, orgMap, dbOrgMap);
			if (comp < 0) {
				// 更新
				String sql = "update " + CrmOrg.TABLE
						+ " set sCode=?,sName=?,Updated=?,pathid=?,idparent=?,sName2=?,pathid2=?,UpdatedBy=? where ORGID=?";
				BizDb.update(sql, orgMap.get("sCode"), orgMap.get("sName"), new Date(), orgMap.get("pathid"),
						orgMap.get("idparent"), orgMap.get("sName2"), orgMap.get("pathid2"), "kiam",
						orgMap.get("ORGID"));
			}
		} else {
			// 插入
			CrmOrg org = new CrmOrg();
			BeanUtils.populate(org, orgMap);
			org.setBfail("0");
			org.setBabstract("0");
			org.setUpdatedBy("kiam");
			BizDb.insert(org, CrmOrg.TABLE);
		}
	}

	/**
	 * 解析机构map
	 * 
	 * @throws Exception
	 */
	private Map<String, Object> getOrgMap(Map<String, Object> resourceMap) throws Exception {
		HashMap<String, Object> orgMap = new HashMap<String, Object>();
		String orgId = String.valueOf(resourceMap.get("ORG_ID"));
		String orgNo = String.valueOf(resourceMap.get("ORG_NO"));
		String orgName = String.valueOf(resourceMap.get("ORG_NAME"));
		String parentOrgId = String.valueOf(resourceMap.get("PARENT_ORG_ID"));
		String orgFullPath = String.valueOf(resourceMap.get("ORG_FULL_PATH")).replace("/", ".");
		String addTime = String.valueOf(resourceMap.get("ADD_TIME"));
		// 根据父级机构ID查询父级机构信息
		Map<String, Object> parentMap = BizDb.queryOneRow("select * from " + CrmOrg.TABLE + " where ORGID = ?",
				parentOrgId);
		if (parentMap != null) {
			String parentOrgName = String.valueOf(parentMap.get("sName"));
			String parentOrgFullPath = String.valueOf(parentMap.get("pathid"));
			orgMap.put("sName2", parentOrgName);
			orgMap.put("pathid2", parentOrgFullPath);
		}
		orgMap.put("ORGID", orgId);
		orgMap.put("sCode", orgNo);
		orgMap.put("sName", orgName);
		orgMap.put("pathid", orgFullPath);
		orgMap.put("idparent", parentOrgId);
		orgMap.put("Created", addTime);
		orgMap.put("org_id", orgNo);
		return orgMap;
	}

	/**
	 * 比较机构信息
	 * 
	 * @param type
	 *            0代表人员信息，1代表机构信息
	 * @param orgMap
	 * @param selfOrgMap
	 * @return 0不做更新，-1为更新操作
	 */
	private int compareInfo(int type, Map<String, Object> orgMap, Map<String, Object> selfOrgMap) {
		boolean eq = true;
		// 看是否需要更新
		if (type == 0) {
			eq = equalsUser(orgMap, selfOrgMap);
		} else {
			eq = equalsOrg(orgMap, selfOrgMap);
		}
		if (eq) {
			return 0;// 不更新
		} else {
			return -1;// 更新
		}
	}

	/**
	 * 比较用户信息字段
	 * 
	 * @param useMap
	 * @param selfUserMap
	 * @return
	 */
	private boolean equalsUser(Map<String, Object> useMap, Map<String, Object> selfUserMap) {
		return equalsItem(useMap, selfUserMap, "sCode") && equalsItem(useMap, selfUserMap, "sName")
				&& equalsItem(useMap, selfUserMap, "idDep") && equalsItem(useMap, selfUserMap, "sMobile")
				&& equalsItem(useMap, selfUserMap, "emSex") && equalsItem(useMap, selfUserMap, "idorg");
	}

	/**
	 * 比较机构信息字段
	 * 
	 * @param orgMap
	 * @param selfOrgMap
	 * @return
	 */
	private boolean equalsOrg(Map<String, Object> orgMap, Map<String, Object> selfOrgMap) {
		return equalsItem(orgMap, selfOrgMap, "sCode") && equalsItem(orgMap, selfOrgMap, "sName")
				&& equalsItem(orgMap, selfOrgMap, "pathid") && equalsItem(orgMap, selfOrgMap, "idparent")
				&& equalsItem(orgMap, selfOrgMap, "sName2") && equalsItem(orgMap, selfOrgMap, "pathid2");
	}

	/**
	 * 比较机构信息项
	 * 
	 * @param orgMap
	 * @param selfOrgMap
	 * @return
	 */
	private boolean equalsItem(Map<String, Object> dataMap, Map<String, Object> selfDataMap, String key) {
		Object value = dataMap.get(key);
		Object selfValue = selfDataMap.get(key);
		if (null == value && null == selfValue) {
			return true;
		} else if (null != value && null != selfValue && value.equals(selfValue)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deleteOrg(Map<String, Object> map) {

	}

	@Override
	public void deleteAccount(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteApp(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteGroup(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteGroupMember(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteGroupRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteOrgUser(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteResource(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteRoleAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteUserAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void deleteUserRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public List<Map<String, Object>> pull(Map<String, Object> var1) throws Exception {
		return null;
	}

	@Override
	public Map<String, Object> pullOne(String var1) throws Exception {
		return null;
	}

	@Override
	public void saveAccount(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveApp(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveGroup(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveGroupMember(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveGroupRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveOrgUser(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveResource(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveRoleAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveUserAuthority(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveUserRole(Map<String, Object> var1) throws Exception {

	}

	@Override
	public void saveUserRole(List<Map<String, Object>> var1) throws Exception {

	}

	public static void main(String[] args) {
		System.out.println(Integer.parseInt(String.valueOf("200")));
	}
}
