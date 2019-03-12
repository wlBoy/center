package com.xn.hk.common.utils.cert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERVisibleString;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.crmf.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: CommonCertificate
 * @Package: com.xn.hk.common.utils.cert
 * @Description: 通用证书类，通过BC包的类解析证书，并将解析后的证书信息放到此类属性中，方便获取
 * @Author: wanlei
 * @Date: 2019年3月11日 下午5:33:53
 */
/*
 * 此类对应获取项 TBSCertificate ::= SEQUENCE { version [ 0 ] Version DEFAULT v1(0),
 * serialNumber CertificateSerialNumber, signature AlgorithmIdentifier, issuer
 * Name, validity Validity, subject Name, subjectPublicKeyInfo
 * SubjectPublicKeyInfo, issuerUniqueID [ 1 ] IMPLICIT UniqueIdentifier
 * OPTIONAL, subjectUniqueID [ 2 ] IMPLICIT UniqueIdentifier OPTIONAL,
 * extensions [ 3 ] Extensions OPTIONAL }
 */
public class CommonCertificate {
	private static final Logger log = LoggerFactory.getLogger(CommonCertificate.class);

	public static final int KEYUSAGE_digitalSignature = 0;
	public static final int KEYUSAGE_nonRepudiation = 1;
	public static final int KEYUSAGE_keyEncipherment = 2;
	public static final int KEYUSAGE_dataEncipherment = 3;
	public static final int KEYUSAGE_keyAgreement = 4;
	public static final int KEYUSAGE_keyCertSign = 5;
	public static final int KEYUSAGE_cRLSign = 6;
	public static final int KEYUSAGE_encipherOnly = 7;
	public static final int KEYUSAGE_decipherOnly = 8;

	private X509Certificate x509Cert;
	private Certificate cert;

	public X509Certificate getX509Cert() {
		return x509Cert;
	}

	public Certificate getCert() {
		return cert;
	}

	/**
	 * 根据证书文件构造
	 * 
	 * @param certFile
	 * @throws Exception
	 */
	public CommonCertificate(File certFile) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(certFile);
			byte[] data = new byte[fis.available()];
			fis.read(data);
			init(data);
		} finally {
			if (null != fis) {
				fis.close();
			}
		}
	}

	/**
	 * 根据证书Base64编码构造
	 * 
	 * @param certB64
	 * @throws Exception
	 */
	public CommonCertificate(String certB64) throws Exception {
		init(certB64.getBytes());
	}

	/**
	 * 根据证书二进制数据构造
	 * 
	 * @param certEncode
	 * @throws Exception
	 */
	public CommonCertificate(byte[] certEncode) throws Exception {
		init(certEncode);
	}

	/**
	 * 根据证书二进制数据构造证书
	 * 
	 * @param certEncode
	 * @return
	 * @throws Exception
	 */
	public static X509Certificate getCert(byte[] certEncode) throws Exception {
		X509Certificate cert = null;
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509", "BC");
		ByteArrayInputStream bis = null;
		Exception e = null;
		try {
			bis = new ByteArrayInputStream(certEncode);
			cert = (X509Certificate) certFactory.generateCertificate(bis);
		} catch (Exception ex) {
			e = ex;
			log.error("证书解析失败，" + ex.getMessage());
		} finally {
			if (bis != null) {
				bis.close();
			}
		}

		if (cert == null) {
			try {
				String certStr = "-----BEGIN CERTIFICATE-----\n" + new String(certEncode)
						+ "\n-----END CERTIFICATE-----";
				bis = new ByteArrayInputStream(certStr.getBytes());
				cert = (X509Certificate) certFactory.generateCertificate(bis);
				e = null;
			} catch (Exception ex) {
				log.error("证书解析失败（加证书头尾），" + ex.getMessage());
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
		if (e != null) {
			throw e;
		}
		return cert;
	}

	public static byte[] getOctets(String string) {
		char ac[] = string.toCharArray();
		byte abyte0[] = new byte[ac.length];
		for (int i = 0; i != ac.length; i++)
			abyte0[i] = (byte) ac[i];

		return abyte0;
	}

	/**
	 * 
	 * @param atv
	 * @return
	 * @throws Exception
	 */
	private String getCertItemValue(AttributeTypeAndValue atv) throws Exception {
		ASN1ObjectIdentifier type = atv.getType();
		ASN1Encodable value = atv.getValue();
		String valueStr = value.toString();
		if (value instanceof DERPrintableString) {
			valueStr = new String(((DERPrintableString) value).getOctets(), "gbk");
			log.info("type:DERPrintableString---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		} else if (value instanceof DERT61String) {
			valueStr = new String(((DERT61String) value).getOctets(), "gbk");
			log.info("type:DERT61String---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		} else if (value instanceof DERUTF8String) {
			valueStr = ((DERUTF8String) value).getString();
			log.info("type:DERUTF8String---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		} else if (value instanceof DERIA5String) {
			valueStr = new String(((DERIA5String) value).getOctets(), "gbk");
			log.info("type:DERIA5String---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		} else if (value instanceof DERVisibleString) {
			valueStr = new String(((DERVisibleString) value).getOctets(), "gbk");
			log.info("type:DERVisibleString---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		} else {
			log.info("type:other---" + BCStyle.INSTANCE.oidToDisplayName(type) + "=" + valueStr);
		}

		return valueStr;
	}

	/**
	 * 解析获取DN信息（整个DN信息或DN中单项值）
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getDN(X500Principal x500Principal, ASN1ObjectIdentifier oid) {
		StringBuilder dnSb = new StringBuilder();
		try {
			ASN1InputStream ais = new ASN1InputStream(x500Principal.getEncoded());
			ASN1Sequence seq = (ASN1Sequence) ais.readObject();
			for (int i = 0; i < seq.size(); i++) {
				DLSet sets = (DLSet) seq.getObjectAt(i);
				Enumeration<?> enumer = sets.getObjects();
				while (enumer.hasMoreElements()) {
					DLSequence curSeq = (DLSequence) enumer.nextElement();
					AttributeTypeAndValue atv = AttributeTypeAndValue.getInstance(curSeq);
					ASN1ObjectIdentifier type = atv.getType();
					if (oid.equals(BCStyle.DN_QUALIFIER)) {
						if (!dnSb.toString().equals("")) {
							dnSb.append(", ");
						}
						dnSb.append(BCStyle.INSTANCE.oidToDisplayName(type) + "=" + getCertItemValue(atv));
					} else if (type.equals(oid)) {
						dnSb.append(getCertItemValue(atv));
						break;
					}
				}
			}
		} catch (Exception ex) {
			log.error("解析DN失败：" + ex.getMessage());
		}

		return dnSb.toString();
	}

	/**
	 * 解析获得证书颁发者DN信息（整个DN信息或DN中单项值）
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getIssuerDN(ASN1ObjectIdentifier oid) {
		return getDN(x509Cert.getIssuerX500Principal(), oid);
	}

	/**
	 * 解析获得证书DN信息（整个DN信息或DN中单项值）
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getSubjectDN(ASN1ObjectIdentifier oid) {
		return getDN(x509Cert.getSubjectX500Principal(), oid);
	}

	private void init(byte[] certEncode) throws Exception {
		x509Cert = getCert(certEncode);
		ASN1InputStream ais = new ASN1InputStream(x509Cert.getEncoded());
		ASN1Sequence seq = (ASN1Sequence) ais.readObject();
		cert = Certificate.getInstance(seq);
	}

	public int getVersion() {
		return x509Cert.getVersion();
	}

	public BigInteger getSerialNumber() {
		return cert.getTBSCertificate().getSerialNumber().getValue();
	}

	public AlgorithmIdentifier getSignature() {
		return cert.getTBSCertificate().getSignature();
	}

	/**
	 * 提供机会自由获取证书颁发者主题项值
	 * 
	 * @param oid
	 * @return
	 */
	public String getIssuer(ASN1ObjectIdentifier oid) {
		return getIssuerDN(oid);
	}

	/**
	 * 获取颁发者证书主题项值
	 * 
	 * @return
	 */
	public String getIssuer() {
		return getIssuerDN(BCStyle.DN_QUALIFIER);
	}

	public Time getStartDate() {
		return cert.getTBSCertificate().getStartDate();
	}

	public Time getEndDate() {
		return cert.getTBSCertificate().getEndDate();
	}

	/**
	 * 提供机会自由获取主题项值
	 * 
	 * @param oid
	 * @return
	 */
	public String getSubject(ASN1ObjectIdentifier oid) {
		return getSubjectDN(oid);
	}

	/**
	 * 获取主题项值
	 * 
	 * @return
	 */
	public String getSubject() {
		return getSubjectDN(BCStyle.DN_QUALIFIER);
	}

	public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
		return cert.getTBSCertificate().getSubjectPublicKeyInfo();
	}

	public DERBitString getIssuerUniqueId() {
		return cert.getTBSCertificate().getIssuerUniqueId();
	}

	public DERBitString getSubjectUniqueId() {
		return cert.getTBSCertificate().getSubjectUniqueId();
	}

	public Extensions getExtensions() {
		return cert.getTBSCertificate().getExtensions();
	}

	public String getSubjectCommonName() {
		return getSubjectDN(BCStyle.CN);
	}

	public String getSubjectGiveName() {
		return getSubjectDN(BCStyle.GIVENNAME);
	}

	public String getSubjectEmailAddress() {
		return getSubjectDN(BCStyle.E);
	}

	public String getIssuerCommonName() {
		return getIssuerDN(BCStyle.CN);
	}

	public String getSubjectO() {
		return getSubjectDN(BCStyle.O);
	}

	/**
	 * 
	 * KeyUsage ::= BIT STRING { digitalSignature (0), nonRepudiation (1),
	 * keyEncipherment (2), dataEncipherment (3), keyAgreement (4), keyCertSign (5),
	 * cRLSign (6), encipherOnly (7), decipherOnly (8) }
	 * 
	 * @return
	 */
	public boolean[] getKeyUsages() throws Exception {
		if (x509Cert.getKeyUsage() == null) {
			log.debug("获取不到密钥用法");
			throw new Exception("获取不到密钥用法");
		}

		return x509Cert.getKeyUsage();
	}

	// 国标中规定，0,1只要有一项，即为签名证书，目前暂不改
	public boolean isDigitalSignature() throws Exception {
		return getKeyUsages()[KEYUSAGE_digitalSignature];
	}

	// 国标中规定，2,3,7,8中有一项即为加密证书，目前暂不改
	public boolean isDataEncipherment() throws Exception {
		return getKeyUsages()[KEYUSAGE_dataEncipherment] || getKeyUsages()[KEYUSAGE_keyAgreement];
	}

	/**
	 * 提供机会判断是否包含某种密钥用法
	 * 
	 * @param keyUsage
	 * @return
	 */
	public boolean includeKeyUsage(int keyUsage) throws Exception {
		return getKeyUsages()[keyUsage];
	}

	public boolean isValidate() {
		Time tStart = getStartDate();
		Time tEnd = getEndDate();
		Calendar c = Calendar.getInstance();
		return c.getTime().compareTo(tStart.getDate()) >= 0 && c.getTime().compareTo(tEnd.getDate()) <= 0;
	}

	public String getItemValueFromSubject(String itemKey) {
		String value = null;
		String subject = getSubject();
		if (subject != null && !subject.trim().equals("")) {
			String[] items = subject.trim().split(",");
			for (String item : items) {
				item = item.trim();
				int splitIndex = item.indexOf("=");
				if (splitIndex != -1) {
					String key = item.substring(0, splitIndex).trim();
					if (key.toUpperCase().equals(itemKey.toUpperCase())) {
						value = item.substring(item.indexOf("=") + 1).trim();
						break;
					}
				}
			}
		}

		return value;
	}

	public String toString() {
		return x509Cert.toString();
	}

	/**
	 * 将x509类的证书 DN 反转成公司在用的证书 DN 的顺序。
	 * 
	 * @param dn
	 *            "C=中国,ST=北京市,L=北京市,O=中安网脉,OU=研发部,CN=SH___CA,E=ca@sis.com"
	 * @return klDn "E=ca@sis.com, CN=SH___CA, OU=研发部, O=中安网脉, L=北京市, ST=北京市, C=中国"
	 */
	public static String convertDn(String dn) {
		String klDn = "";
		if (null == dn) {
			return dn;
		}
		String[] temps = dn.split(",");
		for (int i = temps.length - 1; i > 0; --i) {
			String[] item = temps[i].split("=");
			klDn += item[0].trim() + "=" + item[1].trim() + ", ";
		}
		String[] item = temps[0].split("=");
		klDn += item[0].trim() + "=" + item[1].trim();
		return klDn;
	}

}
