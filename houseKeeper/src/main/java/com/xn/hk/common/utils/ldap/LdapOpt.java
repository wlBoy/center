package com.xn.hk.common.utils.ldap;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.utils.cfg.SystemCfg;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: LdapOpt
 * @Package:com.xn.hk.common.utils.ldap
 * @Description: LDAP基本操作类
 * @Author: wanl
 * @Date: 2018年9月20日 上午11:16:20
 */
public class LdapOpt {
	private static final Logger logger = LoggerFactory.getLogger(LdapOpt.class);
	// 加密通道端口
	private static final String SSL_LDAP_URL_PORT = "636";
	// 非加密通道端口
	private static final String WITHOUT_SSL_LDAP_URL_PORT = "389";
	// 根节点
	public static String BASE_DN = SystemCfg.loadCfg().getProperty(SystemCfg.BASE_DN);
	// 不需要密码
	public final static int UF_PASSWD_NOTREQD = 0x0020;
	// 用户不能更改密码。可以读取此标志，但不能直接设置它。(不能设置)
	public final static int UF_PASSWD_CANT_CHANGE = 0x0040;
	// 这是表示典型用户的默认帐户类型
	public final static int UF_NORMAL_ACCOUNT = 0x0200;
	// 用户的密码已过期(Windows 2000/Windows Server 2003)
	public final static int UF_PASSWORD_EXPIRED = 0x800000;
	// 禁用用户帐户
	public final static int UF_ACCOUNTDISABLE = 0x0002;

	/**
	 * 从配置文件中读取AD域配置信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public static LdapContext getConn() throws NamingException {
		// 登录名
		String SECURITY_PRINCIPAL = SystemCfg.loadCfg().getProperty(SystemCfg.SECURITY_PRINCIPAL);
		// keyStore的存储位置
		String KEYSTORE = SystemCfg.loadCfg().getProperty(SystemCfg.KEY_STORE);
		// SSL密码
		String SSL_PASSWORD = SystemCfg.loadCfg().getProperty(SystemCfg.SSL_PASSWORD);
		// AD域管理员密码
		String AD_PASSWORD = SystemCfg.loadCfg().getProperty(SystemCfg.AD_PASSWORD);
		// LDAP_URL
		String LDAP_URL = SystemCfg.loadCfg().getProperty(SystemCfg.LDAP_URL);
		String port = null;
		if (Boolean.valueOf(SystemCfg.loadCfg().getProperty(SystemCfg.USE_SSL))) {
			port = SSL_LDAP_URL_PORT;
		} else {
			port = WITHOUT_SSL_LDAP_URL_PORT;
		}
		if (StringUtil.isEmpty(KEYSTORE)) {
			return getConnection(KEYSTORE, SSL_PASSWORD, SECURITY_PRINCIPAL, AD_PASSWORD, LDAP_URL, port);
		} else {
			return getConnection(KEYSTORE, SSL_PASSWORD, SECURITY_PRINCIPAL, AD_PASSWORD, LDAP_URL);
		}
	}

	/**
	 * 获取连接通道，如果是636端口就返回加密通道，否则返回普通通道
	 * 
	 * @param keyStore
	 *            keyStore文件的存储位置
	 * @param sslPassWord
	 *            ssl密码
	 * @param adPassWord
	 *            ad域管理员密码
	 * @param ldapUrl
	 *            ad域服务器地址
	 * @param port
	 *            AD域端口号
	 * @return 连接通道
	 * @throws NamingException
	 */
	public static LdapContext getConnection(String keyStore, String sslPassWord, String userName, String adPassWord,
			String ldapUrl, String port) throws NamingException {
		Properties env = new Properties();
		if (SSL_LDAP_URL_PORT.equals(port)) {
			// 使用SSL加密连接LDAP服务器，当推送用户密码时，必须使用SSL连接
			env.put(Context.SECURITY_PROTOCOL, "ssl");
			System.setProperty("javax.net.ssl.trustStore", keyStore);
			if (!StringUtil.isEmpty(sslPassWord)) {
				System.setProperty("javax.net.ssl.trustStorePassword", sslPassWord);
			}
		}
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.setProperty(Context.REFERRAL, "ignore");
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, adPassWord);
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put("java.naming.referral", "follow");
		return new InitialLdapContext(env, null);
	}

	/**
	 * 获取连接通道，默认返回普通通道
	 * 
	 * @param keyStore
	 *            keyStore文件的存储位置
	 * @param sslPassWord
	 *            ssl密码
	 * @param adPassWord
	 *            ad域管理员密码
	 * @param ldapUrl
	 *            ad域服务器地址
	 * @return 连接通道
	 * @throws NamingException
	 */
	public static LdapContext getConnection(String keyStore, String sslPassWord, String userName, String adPassWord,
			String ldapUrl) throws NamingException {
		return getConnection(keyStore, sslPassWord, userName, adPassWord, ldapUrl, WITHOUT_SSL_LDAP_URL_PORT);
	}

	/**
	 * 向域服务器推送用户
	 * 
	 * @param userOU
	 *            添加用户所属机构
	 * @param userName
	 *            用户名称
	 * @param passWord
	 *            密码
	 * @param attrs
	 *            包装好的用户对象
	 * @param type
	 *            对象类型
	 * @return
	 * @throws Exception
	 */
	public static boolean addUser(String userOU, String userName, String passWord, Attributes attrs, String type,
			boolean accountDisable) throws Exception {
		LdapContext ctx = null;
		String userDN = "";
		try {
			if (!"organizationalUnit".equals(type)) {
				userDN = "CN=" + userName + ",";
			} else {
				userDN = "OU=" + userName + ",";
			}
			ctx = getConn();
			// 如果机构不存在就新增
			String[] userOuArray = userOU.split(",");
			String userOu = "";
			for (int temp = userOuArray.length - 1; temp >= 0; temp--) {
				userOu = "OU=" + userOuArray[temp] + "," + userOu;
				if (temp == 0)
					userDN = userDN + userOu + BASE_DN;
				String ouDn = userOu + BASE_DN;
				try {
					ctx.getAttributes(ouDn);
				} catch (NameNotFoundException e) {
					// 如果不存在就新增
					addOU(ouDn);
				}
			}
			try {
				if ("organizationalUnit".equals(type)) {
					attrs.put("objectClass", "organizationalUnit");
					// 创建对象
					ctx.createSubcontext(userDN, attrs);
					return true;
				}
				// required attributes
				int userAccountControl = UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED;
				if (Boolean.valueOf(SystemCfg.loadCfg().getProperty(SystemCfg.PASSWORD_CANT_CHANGE))) {
					userAccountControl += UF_PASSWD_CANT_CHANGE;
				}
				if (accountDisable) {
					userAccountControl += UF_ACCOUNTDISABLE;
				}
				attrs.put("userAccountControl", Integer.toString(userAccountControl));
				attrs.put("objectClass", type);
				attrs.put("sAMAccountName", userName);
				ctx.createSubcontext(userDN, attrs);
			} catch (NameAlreadyBoundException e) {
				if ("organizationalUnit".equals(type)) {
					logger.error("The organizational unit already exists");
					return false;
				} else {
					String oldDN = searchEntity("cn", userName, type);
					changeOU(oldDN, userOu);
					modifyUser(attrs, userName, type);
					logger.error("User already exists, has been moved into the organization unit");
				}
			}
			if (Boolean.valueOf(SystemCfg.loadCfg().getProperty(SystemCfg.PUSH_PASSWORD)) && passWord != null) {
				ModificationItem[] mods = new ModificationItem[2];
				// format the password
				String newQuotedPassword = "\"" + passWord + "\"";
				byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
				mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("unicodePwd", newUnicodePassword));
				int userAccountControl = UF_NORMAL_ACCOUNT + UF_PASSWD_NOTREQD + UF_PASSWORD_EXPIRED;
				if (Boolean.valueOf(SystemCfg.loadCfg().getProperty(SystemCfg.PASSWORD_CANT_CHANGE))) {
					userAccountControl += UF_PASSWD_CANT_CHANGE;
				}
				if (accountDisable) {
					userAccountControl += UF_ACCOUNTDISABLE;
				}
				mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
						new BasicAttribute("userAccountControl", Integer.toString(userAccountControl)));
				// save the password
				ctx.modifyAttributes(userDN, mods);
				mods = null;
			}
			return true;
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
	}

	/**
	 * 添加一个新的组织机构
	 * 
	 * @param OUDN
	 *            组织OU的DN
	 * @return 添加成功返回true，否则返回false
	 * @throws NamingException
	 *             连接异常
	 */
	public static boolean addOU(String OUDN) throws NamingException {
		LdapContext ctx = null;
		try {
			ctx = getConn();
			Attributes attrs = new BasicAttributes(true);
			attrs.put("objectClass", "organizationalUnit");
			ctx.createSubcontext(OUDN, attrs);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
		return false;
	}

	/**
	 * 重命名DN
	 * 
	 * @param oldDN
	 * @param newDN
	 * @return
	 * @throws NamingException
	 */
	public static boolean renameOU(String oldDN, String newDN) throws NamingException {
		LdapContext ctx = null;
		try {
			ctx = getConn();
			ctx.rename(oldDN, newDN);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
		return false;
	}

	/**
	 * 改变组织机构信息
	 * 
	 * @param olduserDN
	 *            老机构的DN
	 * @param newOU
	 *            新机构的DN
	 * @return 修改成功返回true，否则返回false
	 * @throws NamingException
	 *             连接异常
	 */
	public static boolean changeOU(String olduserDN, String newOU) throws NamingException {
		LdapContext ctx = null;
		try {
			ctx = getConn();
			String[] user = olduserDN.split(",");
			String newDN = user[0] + "," + newOU + user[user.length - 2] + "," + user[user.length - 1];
			ctx.rename(olduserDN, newDN);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
		return false;
	}

	/**
	 * 更新用户组信息
	 * 
	 * @param attrs
	 *            组属性
	 * @param userDN
	 *            成员DN
	 * @param op
	 *            操作类型 分别为del、rep、add
	 * @param groupName
	 *            组名 cn=name,cn/ou=xxx
	 */
	public static void modifygroup(Attributes attrs, String op, String groupDN) {
		LdapContext ctx = null;
		try {
			ctx = getConn();
			if ("del".equals(op))
				ctx.modifyAttributes(groupDN, DirContext.REMOVE_ATTRIBUTE, attrs);
			else if ("rep".equals(op))
				ctx.modifyAttributes(groupDN, DirContext.REPLACE_ATTRIBUTE, attrs);
			else if ("add".equals(op))
				ctx.modifyAttributes(groupDN, DirContext.ADD_ATTRIBUTE, attrs);
		} catch (NamingException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				ctx = null;
			}
		}
	}

	/**
	 * 查找对象
	 * 
	 * @param cond
	 *            查询字段
	 * @param condvalue
	 *            对应值
	 * @param entityType
	 *            类型
	 * @return
	 * @throws NamingException
	 */
	public static String searchEntity(String cond, String condvalue, String entityType) throws NamingException {
		LdapContext ctx = null;
		String OUName = "";
		try {
			ctx = getConn();
			SearchControls searchCtls = new SearchControls();
			searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String searchFilter = null;
			if ("user".equals(entityType)) {
				searchFilter = "(&(objectCategory=person)(objectClass=user)(" + cond + "=" + condvalue + "))";
			} else if ("group".equals(entityType)) {
				searchFilter = "(&(objectCategory=group)(objectClass=group)(" + cond + "=" + condvalue + "))";
			} else {
				String[] OUList = condvalue.split(",");
				condvalue = "";
				for (int i = OUList.length - 1; i >= 0; i--) {
					condvalue = "OU=" + OUList[i] + "," + condvalue;
					OUName = OUList[0];
				}
				searchFilter = "(&(objectCategory=organizationalUnit)(objectclass=organizationalUnit)(OU=" + OUName
						+ "))";
			}
			String returnedAtts[] = { "member" };
			searchCtls.setReturningAttributes(returnedAtts);
			NamingEnumeration<SearchResult> answer = ctx.search(BASE_DN, searchFilter, searchCtls);
			SearchResult sr = null;
			while (answer.hasMoreElements()) {
				sr = (SearchResult) answer.next();
				if ("group".equals(entityType)) {
					if (sr.getAttributes().get("member") != null) {
						return sr.getAttributes().get("member").toString();
					}
				}
				return sr.getName() + "," + BASE_DN;
			}
		} catch (NullPointerException e) {
			logger.error("Entity not exist");
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
		return null;
	}

	/**
	 * 用户查重
	 * 
	 * @param entityName
	 *            对象的名称
	 * @param entityType
	 *            对象类型
	 * @return
	 * @throws NamingException
	 */
	public static String userDuplicateCheck(String entityName, String entityType) throws NamingException {
		String result = null;
		LdapContext ctx = null;
		try {
			String DN = searchEntity("cn", entityName, entityType);
			ctx = getConn();
			if (DN != null) {
				ctx.destroySubcontext(DN);
			} else {
				logger.error("delete Entity not exist");
				return result;
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (ctx != null) {
				ctx.close();
				ctx = null;
			}
		}
		return result;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param attrs
	 * @param entityName
	 * @param entityType
	 * @return
	 */
	public static boolean modifyUser(Attributes attrs, String entityName, String entityType) throws NamingException {
		LdapContext ctx = null;
		try {
			String userDN = searchEntity("cn", entityName, entityType);
			ctx = getConn();
			ctx.modifyAttributes(userDN, DirContext.REPLACE_ATTRIBUTE, attrs);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
				ctx = null;
			}
		}
		return false;
	}

	/**
	 * 删除对象
	 * 
	 * @param CN
	 *            用户CN
	 * @return
	 * @throws Exception
	 */
	public static boolean delEntity(String CN) {
		LdapContext ctx = null;
		try {
			ctx = getConn();
			if (CN != null) {
				ctx.destroySubcontext(CN);
			} else {
				logger.error("delete Entity not exist");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					logger.error(e.getMessage());
				}
				ctx = null;
			}
		}
		return false;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// String str = FindWebRoot.getWebRoot();
		// System.out.println(str);
		Attributes attrs = new BasicAttributes(true);
		attrs.put("Title", "工程师");
		attrs.put("Department", "应用");
		attrs.put("Company", "格尔");
		// attrs.put("objectClass","user");
		// attrs.put("userPrincipalName",userName + "@adserv.com");
		// attrs.put("Title","工程师");
		// attrs.put("Department","应用");
		// attrs.put("Company","格尔");
		// attrs.put("givenName","Albert");
		// attrs.put("SN","Einstein2");
		// attrs.put("displayName","Albert Einstein");
		// attrs.put("description","Research Scientist");
		// attrs.put("mail","relativity@adtest.com");
		// attrs.put("telephoneNumber","999 123 4567");
		// // 用户为user，OU为organizationalUnit
		// // 新增用户
		// 机构路径 用户名 密码 attrs 'users'
		/*
		 * if (addUser("上海", "test1", "123", attrs, "user", true)) {
		 * System.out.println("添加成功！"); }
		 */
		// // 新增OU
		// if (addUser("格尔,闸北,上海", "测试", "123", attrs, "organizationalUnit")) {
		// System.out.println("添加成功！");
		// }
		// // 删除OU测试
		// if (delEntity("测试,格尔,闸北,上海", "organizationalUnit")) {
		// System.out.println("删除成功！");
		// }
		// // 删除用户测试
		// if (delEntity("test123", "user")) {
		// System.out.println("删除成功！");
		// }
		// // 修改测试
		// if (modifyUser(attrs, "test123", "user")) {
		// System.out.println("更新成功！");
		// }
		try {
			getConnection("D:\\development\\jdk1.8\\jdk\\jre\\lib\\security\\cacerts", "",
					"CN=Administrator,CN=Users,DC=koal,DC=wanl,DC=com", "wanlei123.", "192.168.229.131");
			System.out.println("AD域ssl身份认证成功");
		} catch (Exception e) {
			System.out.println("AD域ssl身份认证失败");
			e.printStackTrace();
		}
	}

}
