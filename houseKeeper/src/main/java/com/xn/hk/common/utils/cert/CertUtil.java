package com.xn.hk.common.utils.cert;

import java.io.File;
import java.io.FileInputStream;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * 
 * @ClassName: CertUtil
 * @Package: com.xn.hk.common.utils.cert
 * @Description: 获取证书的工具类
 * @Author: wanlei
 * @Date: 2019年3月11日 下午5:37:49
 */
public class CertUtil {
	/** Begin certificate in RFC 1421 encoding */
	private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";

	/** End certificate in RFC 1421 encoding */
	private static final String END_CERT = "-----END CERTIFICATE-----";

	/** The maximum length of lines in printable encoded certificates */
	private static final int CERT_LINE_LENGTH = 64;

	/** Begin certificate signing request */
	private static final String BEGIN_CERT_REQ = "-----BEGIN CERTIFICATE REQUEST-----";

	/** End certificate signing request */
	private static final String END_CERT_REQ = "-----END CERTIFICATE REQUEST-----";

	/** The maximum length of lines in certificate signing requests */
	private static final int CERT_REQ_LINE_LENGTH = 76;
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static CommonCertificate getX509Cert(String cert) throws Exception {
		return new CommonCertificate(cert);
	}

	public static String formatCsr(String csr) {
		// CSR is bounded by a header and footer
		String sCsr = BEGIN_CERT_REQ + "\n";

		// Limit line lengths between header and footer
		for (int iCnt = 0; iCnt < csr.length(); iCnt += CERT_REQ_LINE_LENGTH) {
			int iLineLength;

			if ((iCnt + CERT_REQ_LINE_LENGTH) > csr.length()) {
				iLineLength = (csr.length() - iCnt);
			} else {
				iLineLength = CERT_REQ_LINE_LENGTH;
			}

			sCsr += csr.substring(iCnt, (iCnt + iLineLength)) + "\n";
		}

		// Footer
		sCsr += END_CERT_REQ + "\n";
		return sCsr;
	}

	public static String unFormatCsr(String csr) {
		// CSR is bounded by a header and footer
		String sCsr = csr.replaceAll(BEGIN_CERT_REQ, "").replaceAll(END_CERT_REQ, "").replaceAll("\r", "")
				.replaceAll("\n", "");
		return sCsr;
	}

	public static String formatCert(String csr) {
		// CSR is bounded by a header and footer
		String sCsr = BEGIN_CERT + "\n";

		// Limit line lengths between header and footer
		for (int iCnt = 0; iCnt < csr.length(); iCnt += CERT_LINE_LENGTH) {
			int iLineLength;

			if ((iCnt + CERT_LINE_LENGTH) > csr.length()) {
				iLineLength = (csr.length() - iCnt);
			} else {
				iLineLength = CERT_LINE_LENGTH;
			}

			sCsr += csr.substring(iCnt, (iCnt + iLineLength)) + "\n";
		}

		// Footer
		sCsr += END_CERT + "\n";
		return sCsr;
	}

	public static String unFormatCert(String csr) {
		// CSR is bounded by a header and footer
		String sCsr = csr.replaceAll(BEGIN_CERT, "").replaceAll(END_CERT, "").replaceAll("\r", "").replaceAll("\n", "")
				.replaceAll(" ", "");
		return sCsr;
	}

}
