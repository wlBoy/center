package com.xn.hk.common.utils.jwt;

import java.security.PrivateKey;
import java.security.spec.ECParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.EcJwkGenerator;
import org.jose4j.jwk.EllipticCurveJsonWebKey;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.OctJwkGenerator;
import org.jose4j.jwk.OctetSequenceJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jwk.Use;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.keys.resolvers.HttpsJwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xn.hk.common.model.Pair;
import com.xn.hk.common.utils.json.FastjsonUtil;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * @author wanlei
 * @description jose4j工具类
 * @date 2020/12/22 15:43
 **/
public class Jose4jUtil {
	/**
	 * 记录日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(Jose4jUtil.class);
	/**
	 * 长度 至少 1024, 建议 2048
	 *
	 * @since 1.1.0
	 */
	private static final int DEFAULT_KEY_SIZE = 2048;

	private Jose4jUtil() {

	}

	/**
	 * 使用指定的jsonWebKey生成JWT字符串，使用私钥签名
	 * 
	 * @param issuer
	 *            颁发者
	 * @param audience
	 *            客户端ID
	 * @param subject
	 *            用户唯一标识
	 * @param expireTime
	 *            过期时间，单位：分钟
	 * @param claimMap
	 *            断言MAP
	 * @param jsonWebKey
	 *            jsonWebKey
	 * @return
	 */
	public static String buildJwt(String issuer, String audience, long expireTime, String subject,
			Map<String, Object> claimMap, JsonWebKey jsonWebKey) {
		String algorithm = jsonWebKey.getAlgorithm();
		Pair<String, PrivateKey> privateKeyPair = getPrivateKeyAndTypeByAlgorithm(jsonWebKey, algorithm);
		if (privateKeyPair == null || privateKeyPair.getValue() == null) {
			logger.warn("根据jsonWebKey中的算法获取对应的私钥失败");
			return null;
		}
		try {
			// 构建jwt断言MAP
			JwtClaims claims = getJwtClaims(issuer, audience, subject, expireTime, claimMap);
			// 构建jws
			JsonWebSignature jws = new JsonWebSignature();
			jws.setPayload(claims.toJson());
			jws.setKeyIdHeaderValue(jsonWebKey.getKeyId());
			jws.setAlgorithmHeaderValue(jsonWebKey.getAlgorithm());
			// set private key
			jws.setKey(privateKeyPair.getValue());
			return jws.getCompactSerialization();
		} catch (Exception e) {
			logger.error("生成JWT失败，原因为:{}", e);
			return null;
		}
	}

	/**
	 * 在jwksJson字符串随机抽取一个jsonWebKey生成JWT字符串，使用私钥签名
	 *
	 * @param issuer
	 *            颁发者
	 * @param audience
	 *            客户端ID
	 * @param subject
	 *            用户唯一标识
	 * @param expireTime
	 *            过期时间，单位：分钟
	 * @param claimMap
	 *            断言MAP
	 * @param jsonWebKeySet
	 *            jsonWebKeySet对象
	 * @return
	 */
	public static String buildRandomJwt(String issuer, String audience, long expireTime, String subject,
			Map<String, Object> claimMap, JsonWebKeySet jsonWebKeySet) {
		// 从JsonWebKeySet中随机获取一个JsonWebKey
		List<JsonWebKey> jsonWebKeyList = jsonWebKeySet.getJsonWebKeys();
		int randomIndex = (int) Math.round(Math.random() * (jsonWebKeyList.size() - 1));
		JsonWebKey jsonWebKey = jsonWebKeyList.get(randomIndex);
		return buildJwt(issuer, audience, expireTime, subject, claimMap, jsonWebKey);
	}

	/**
	 * 在jwksJson字符串随机抽取一个jsonWebKey生成JWT字符串，使用私钥签名
	 *
	 * @param issuer
	 *            颁发者
	 * @param audience
	 *            客户端ID
	 * @param subject
	 *            用户唯一标识
	 * @param expireTime
	 *            过期时间，单位：分钟
	 * @param claimMap
	 *            断言MAP
	 * @param jwksJson
	 *            jwksJson字符串
	 * @return
	 * @throws JoseException
	 */
	public static String buildRandomJwt(String issuer, String audience, long expireTime, String subject,
			Map<String, Object> claimMap, String jwksJson) throws JoseException {
		JsonWebKeySet jsonWebKeySet = new JsonWebKeySet(jwksJson);
		return buildRandomJwt(issuer, audience, expireTime, subject, claimMap, jsonWebKeySet);
	}

	/**
	 * 根据jsonWebKey中的算法获取对应的私钥和key类型
	 *
	 * @param jsonWebKey
	 * @param algorithm
	 * @return
	 */
	private static Pair<String, PrivateKey> getPrivateKeyAndTypeByAlgorithm(JsonWebKey jsonWebKey, String algorithm) {
		Pair<String, PrivateKey> keyPair = null;
		if (getAllRsaAlg().contains(algorithm)) {
			keyPair = new Pair<>(RsaJsonWebKey.KEY_TYPE,
					jsonWebKey == null ? null : ((RsaJsonWebKey) jsonWebKey).getPrivateKey());
		} else if (getAllEcAlg().contains(algorithm)) {
			keyPair = new Pair<>(EllipticCurveJsonWebKey.KEY_TYPE,
					jsonWebKey == null ? null : ((EllipticCurveJsonWebKey) jsonWebKey).getPrivateKey());
		}
		return keyPair;
	}

	/**
	 * 构建标准的ID_TOKEN格式的JWT断言MAP
	 *
	 * @return
	 */
	public static Map<String, Object> buildIdTokenClaims(String userName) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("auth_time", new Date());// 非必须。EU完成认证的时间
		resultMap.put("nonce", StringUtil.genUUIDString());// 非必须。RP发送请求的时候提供的随机字符串，用来减缓重放攻击，也可以来关联ID
															// Token和RP本身的Session信息
		resultMap.put("name", userName);
		return resultMap;
	}

	/**
	 * 解析jwt字符串，使用公钥验签， https://bitbucket.org/b_c/jose4j/wiki/JWT%20Examples
	 *
	 * @param jwtStr
	 *            jwt字符串
	 * @param issuer
	 *            颁发者
	 * @param audience
	 *            客户端ID
	 * @param jsonWebKey
	 *            指定的jsonWebKey
	 * @return
	 */
	public static Map<String, Object> parseJwt(String jwtStr, String issuer, String audience, JsonWebKey jsonWebKey) {
		try {
			// 解析idToken, 验签
			JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() // the JWT must have an
																							// expiration time
					.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account
														// for clock skew
					.setRequireSubject() // the JWT must have a subject claim
					.setExpectedIssuer(issuer) // whom the JWT needs to have been issued by
					.setExpectedAudience(audience) // to whom the JWT is intended for
					// 公钥
					.setVerificationKey(jsonWebKey.getKey()) // verify the signature with the public key
					.setJwsAlgorithmConstraints( // only allow the expected signature algorithm(s) in the given context
							ConstraintType.PERMIT, jsonWebKey.getAlgorithm())
					.build(); // create the JwtConsumer instance
			final JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtStr);
			return jwtClaims.getClaimsMap();
		} catch (Exception e) {
			logger.error("使用指定KEY中的公钥解析JWT失败，原因为:{}", e);
			return null;
		}
	}

	/**
	 * 使用jwks.json字符串解析JWT
	 *
	 * @param jwtStr
	 * @param issuer
	 * @param audience
	 * @param jwksJson
	 * @return
	 */
	public static Map<String, Object> parseJwtByJwksJson(String jwtStr, String issuer, String audience,
			String jwksJson) {
		try {
			JwksVerificationKeyResolver jwksResolver = new JwksVerificationKeyResolver(
					new JsonWebKeySet(jwksJson).getJsonWebKeys());
			return buildJwtConsumerByResolver(jwtStr, issuer, audience, jwksResolver);
		} catch (Exception e) {
			logger.error("使用jwks.json字符串解析JWT失败，原因为:{}", e);
			return null;
		}
	}

	/**
	 * 使用jwksUrl地址解析JWT
	 *
	 * @param jwtStr
	 * @param issuer
	 * @param audience
	 * @param jwksUrl
	 * @return
	 */
	public static Map<String, Object> parseJwtByJwksUrl(String jwtStr, String issuer, String audience, String jwksUrl) {
		try {
			HttpsJwksVerificationKeyResolver httpsJwksKeyResolver = new HttpsJwksVerificationKeyResolver(
					new HttpsJwks(jwksUrl));
			return buildJwtConsumerByResolver(jwtStr, issuer, audience, httpsJwksKeyResolver);
		} catch (Exception e) {
			logger.error("使用jwksUrl地址解析JWT失败，原因为:{}", e);
			return null;
		}
	}

	/**
	 * 使用VerificationKeyResolver去解析JWT
	 *
	 * @param jwtStr
	 * @param issuer
	 * @param audience
	 * @param verificationKeyResolver
	 * @return
	 * @throws InvalidJwtException
	 */
	private static Map<String, Object> buildJwtConsumerByResolver(String jwtStr, String issuer, String audience,
			VerificationKeyResolver verificationKeyResolver) throws InvalidJwtException {
		// 解析idToken, 验签
		JwtConsumer jwtConsumer = new JwtConsumerBuilder().setRequireExpirationTime() // the JWT must have an
				.setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account
				// for clock skew
				.setRequireSubject() // the JWT must have a subject claim
				.setExpectedIssuer(issuer) // whom the JWT needs to have been issued by
				.setExpectedAudience(audience) // to whom the JWT is intended for
				.setVerificationKeyResolver(verificationKeyResolver) // Set the VerificationKeyResolver to use to
				// select the
				// key for JWS signature/MAC verification.
				.build(); // create the JwtConsumer instance
		final JwtClaims jwtClaims = jwtConsumer.processToClaims(jwtStr);
		return jwtClaims.getClaimsMap();
	}

	/**
	 * 构建jwtClaims
	 *
	 * @param issuer
	 *            颁发者
	 * @param audience
	 *            客户端ID
	 * @param subject
	 *            用户唯一标识
	 * @param expireTime
	 *            过期时间，单位：分钟
	 * @param claimMap
	 *            断言MAP
	 * @return
	 * @throws InvalidJwtException
	 */
	private static JwtClaims getJwtClaims(String issuer, String audience, String subject, long expireTime,
			Map<String, Object> claimMap) throws InvalidJwtException {
		JwtClaims claims = JwtClaims.parse(FastjsonUtil.mapToJson(claimMap));
		claims.setIssuer(issuer); // who creates the token and signs it
		claims.setAudience(audience); // to whom the token is intended to be sent
		claims.setExpirationTimeMinutesInTheFuture(expireTime); // time when the token will expire (10 minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
		claims.setSubject(subject); // the subject/principal is whom the token is about
		return claims;
	}

	/**
	 * 生成RSA算法的jwk，默认使用AlgorithmIdentifiers.RSA_USING_SHA256
	 *
	 * @return
	 * @throws JoseException
	 */
	public static RsaJsonWebKey genRsaJwks() throws JoseException {
		return genRsaJwks(AlgorithmIdentifiers.RSA_USING_SHA256);
	}

	/**
	 * 生成RSA算法的jwk，支持的算法有:AlgorithmIdentifiers.RSA_USING_SHA256,AlgorithmIdentifiers.RSA_USING_SHA384,AlgorithmIdentifiers.RSA_USING_SHA512
	 *
	 * @return
	 * @throws JoseException
	 */
	public static RsaJsonWebKey genRsaJwks(String algorithm) throws JoseException {
		RsaJsonWebKey jwk = RsaJwkGenerator.generateJwk(DEFAULT_KEY_SIZE);
		// sig or enc
		jwk.setUse(Use.SIGNATURE);
		jwk.setKeyId(StringUtil.genUUIDString());
		jwk.setAlgorithm(algorithm);
		return jwk;
	}

	/**
	 * 生成EC算法的jwk，默认使用AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256
	 *
	 * @return
	 * @throws JoseException
	 */
	public static EllipticCurveJsonWebKey genEcJwks() throws JoseException {
		return genEcJwks(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);
	}

	/**
	 * 生成EC算法的jwk，支持的算法有:AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256,AlgorithmIdentifiers.ECDSA_USING_P384_CURVE_AND_SHA384,AlgorithmIdentifiers.ECDSA_USING_P521_CURVE_AND_SHA512
	 *
	 * @return
	 * @throws JoseException
	 */
	public static EllipticCurveJsonWebKey genEcJwks(String algorithm) throws JoseException {
		ECParameterSpec ecParameterSpec = EllipticCurves.P256;
		if (AlgorithmIdentifiers.ECDSA_USING_P384_CURVE_AND_SHA384.equals(algorithm)) {
			ecParameterSpec = EllipticCurves.P384;
		} else if (AlgorithmIdentifiers.ECDSA_USING_P521_CURVE_AND_SHA512.equals(algorithm)) {
			ecParameterSpec = EllipticCurves.P521;
		}
		EllipticCurveJsonWebKey jwk = EcJwkGenerator.generateJwk(ecParameterSpec);
		// sig or enc
		jwk.setUse(Use.SIGNATURE);
		jwk.setKeyId(StringUtil.genUUIDString());
		jwk.setAlgorithm(algorithm);
		return jwk;
	}

	/**
	 * 生成OCT算法的jwk，默认使用AlgorithmIdentifiers.HMAC_SHA256
	 * 
	 * @return
	 * @throws JoseException
	 */
	public static OctetSequenceJsonWebKey genOctJwks() throws JoseException {
		return genOctJwks(AlgorithmIdentifiers.HMAC_SHA256);
	}

	/**
	 * 生成OCT算法的jwk，支持的算法有:AlgorithmIdentifiers.HMAC_SHA256,AlgorithmIdentifiers.HMAC_SHA384,AlgorithmIdentifiers.HMAC_SHA512
	 *
	 * @return
	 * @throws JoseException
	 */
	public static OctetSequenceJsonWebKey genOctJwks(String algorithm) throws JoseException {
		OctetSequenceJsonWebKey jwk = OctJwkGenerator.generateJwk(DEFAULT_KEY_SIZE);
		// sig or enc
		jwk.setUse(Use.ENCRYPTION);
		jwk.setKeyId(StringUtil.genUUIDString());
		jwk.setAlgorithm(algorithm);
		return jwk;
	}

	/**
	 * 生成RSA，EC支持的所有算法的jwks.json字符串
	 *
	 * @return
	 */
	public static String genAllAlgJwksJson() throws JoseException {
		List<JsonWebKey> jwkList = new ArrayList<>();
		for (String rsaAlg : getAllRsaAlg()) {
			jwkList.add(genRsaJwks(rsaAlg));
		}
		for (String ecAlg : getAllEcAlg()) {
			jwkList.add(genEcJwks(ecAlg));
		}
		return genJwksJson(jwkList);
	}

	/**
	 * 获取支持的所有签名算法
	 *
	 * @return
	 */
	public static List<String> getTokenAllSignAlg() {
		List<String> resultList = getAllRsaAlg();
		resultList.addAll(getAllEcAlg());
		return resultList;
	}

	/**
	 * 获取支持的所有签名RSA算法
	 *
	 * @return
	 */
	public static List<String> getAllRsaAlg() {
		return Arrays.asList(AlgorithmIdentifiers.RSA_USING_SHA256, AlgorithmIdentifiers.RSA_USING_SHA384,
				AlgorithmIdentifiers.RSA_USING_SHA512);
	}

	/**
	 * 获取支持的所有签名EC算法
	 *
	 * @return
	 */
	public static List<String> getAllEcAlg() {
		return Arrays.asList(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256,
				AlgorithmIdentifiers.ECDSA_USING_P384_CURVE_AND_SHA384,
				AlgorithmIdentifiers.ECDSA_USING_P521_CURVE_AND_SHA512);
	}

	/**
	 * 生成jwks.json字符串
	 * 
	 * @param jwk
	 * @return
	 */
	public static String genJwksJson(List<JsonWebKey> jwkList) {
		return new JsonWebKeySet(jwkList).toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}

	/**
	 * 生成jwks.json字符串
	 * 
	 * @param jwk
	 * @return
	 */
	public static String genJwksJson(JsonWebKey... jwkArray) {
		return new JsonWebKeySet(jwkArray).toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws JoseException
	 */
	public static void main(String[] args) throws JoseException {
		String issuer = "http://www.issuer.com";
		String audience = "123456789";
		long expireTime = 10;
		String subject = "1";
		Map<String, Object> claimMap = buildIdTokenClaims("张三");
		RsaJsonWebKey rsaJsonWebKey = genRsaJwks();
		EllipticCurveJsonWebKey ellipticCurveJsonWebKey = genEcJwks();
		// OctetSequenceJsonWebKey octetSequenceJsonWebKey = genOctJwks();
		List<JsonWebKey> jsonWebKeys = new ArrayList<>();
		jsonWebKeys.add(rsaJsonWebKey);
		jsonWebKeys.add(ellipticCurveJsonWebKey);
		// jsonWebKeys.add(octetSequenceJsonWebKey);
		String jwksJson = genJwksJson(jsonWebKeys);
		System.out.println("生成各算法的jwks.json为:" + jwksJson);
		// =======================上面是生成jwks.json文件，下面是生成jwt和解析jwt的代码============================//
		// 生成随机的jwt并解析
		String randomJwt = buildRandomJwt(issuer, audience, expireTime, subject, claimMap, jwksJson);
		System.out.println("随机生成jwt为:" + randomJwt);
		Map<String, Object> randomMap = parseJwtByJwksJson(randomJwt, issuer, audience, jwksJson);
		System.out.println("解析随机生成的jwt为:" + randomMap);
		String idToken = "eyJraWQiOiJhdXRobi1hcGkta2V5aWQiLCJhbGciOiJSUzI1NiIsImprdSI6Imh0dHA6Ly9zc28uYTEua2lkYWFzLmNvbS9wdWJsaWMvandrcyJ9.eyJhdXRoX3RpbWUiOjE2MTIxNjQ0MDE5ODMsIm5hbWUiOiLmm7nno4rvvIjorqTor4HkuK3lv4PliKvliKDpmaTvvIkiLCJwaG9uZV9udW1iZXIiOiIxODYxNjM0NTQ5MyIsIm5vbmNlIjoiODNlNDQzMTljYzQ4MmQ2M2I2Mjk5ZjM4ZTJlNDZjMmYiLCJpc3MiOiJodHRwOi8vc3NvLmExLmtpZGFhcy5jb20iLCJhdWQiOiI0NDE0MzQ4Mzk4NDE2NjkxMiIsImV4cCI6MTYxMjE3MTYwMiwianRpIjoiYnVrd2tNcW1ERHhhYTAxQTdNblpuZyIsImlhdCI6MTYxMjE2NDQwMiwic3ViIjoiMTMifQ.nfjiZgRITt-JeqGydetV6DrOu2Lg9wi99rppl0SEyenlgCd8wiAZWbNgqXOUs_6_31k7KjBZ8XMzRE-n-hPEF7n0MulVAZjN0qFsAMXcaOa-La2Sidgp9ICgjVJTZxL8WBByvzB4eauBVkt8YFJcrrlfcZ82Q8YlrDe7_-6-WSnIsUxPl-zBvzvkO71B8bXfhkNWPPwAV9hOSwKuf1RZHaGJlXphffV-ycGPMkXO-CxW16Ttca47rNX_NKp2NA4tZA3kX8NbP8uSzT2ePcwhxY-nmThmJbFA4qb9WzyiU1WNb42EHQO8o82tnc4I6t_Ac7Nsd_h0GtoUH-0IEdoUZQ";
		Map<String, Object> httpsMap = parseJwtByJwksUrl(idToken, "http://sso.a1.kidaas.com", "44143483984166912",
				"http://10.0.248.38:8088/v5/public/jwks");
		System.out.println("使用jwksUrl解析jwt为:" + httpsMap);
		// 生成指定算法的jwt并解析
		String rsaJwt = buildJwt(issuer, audience, expireTime, subject, claimMap, rsaJsonWebKey);
		String ecJwt = buildJwt(issuer, audience, expireTime, subject, claimMap, ellipticCurveJsonWebKey);
		System.out.println("生成RSA算法的jwt为:" + rsaJwt);
		System.out.println("生成EC算法的jwt为:" + ecJwt);
		Map<String, Object> rsaMap = parseJwt(rsaJwt, issuer, audience, rsaJsonWebKey);
		Map<String, Object> ecMap = parseJwt(ecJwt, issuer, audience, ellipticCurveJsonWebKey);
		System.out.println("解析RSA算法的jwt为:" + rsaMap);
		System.out.println("解析EC算法的jwt为:" + ecMap);
	}

}
