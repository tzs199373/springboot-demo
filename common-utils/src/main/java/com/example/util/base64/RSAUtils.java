package com.example.util.base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具
 */
public class RSAUtils {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String PADDING="RSA/None/PKCS1Padding";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final int KEY_SIZE = 1024;  //  秘钥长度1024，最长加密117字节明文

	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	/**
	 * 用私钥对信息生成数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	/**
	 * BASE64加密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64Util.encodeBase64String(key);
	}

	/**
	 * BASE64解密
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64Util.decodeBase64(key.getBytes());
	}

	/**
	 * 校验数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 *
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥(BASE64编码)
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥(BASE64编码)
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap)
			throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 判断字符串是否被公钥加密
	 * @param text
	 * @param privateKey
	 * @return
	 */
	public static boolean isTextEncryptByPublicKey(String text , String privateKey){
		boolean bol = true;
		try{
			 byte[] encryBytes = RSAUtils.decryptBASE64(text);
			 RSAUtils.decryptByPrivateKey(encryBytes, privateKey);
		}catch(Exception e){
			bol = false;
		}
		return bol;
	}

	/**
	 * 初始化密钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM , BouncyCastleProvider.PROVIDER_NAME);
		keyPairGen.initialize(KEY_SIZE , new SecureRandom());

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}


	public static void main(String[] args) throws Exception {
		Map<String ,Object> keyMap = RSAUtils.initKey();
		String pubKey = RSAUtils.getPublicKey(keyMap);
		String privateKey = RSAUtils.getPrivateKey(keyMap);
		System.out.println("公钥：" + pubKey);
		System.out.println("私钥：" + privateKey);

		/*privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIQaJdHElc4xDv/19ZDX+c3IksIL"+
				"gadKdoKWqQsYyaeGW6vRNm4jhXikn50BB9zVgJBgvvyGN5tJFqQ5YauiR93nyKUoP/WL51t/oGb5"+
				"Ikpvquhb8nDVBMdOAkrW4zx3bm0iBijAc4wk4a43/mxQs2/NhXOl0jxq+4ZDVxQU9puRAgMBAAEC"+
				"gYAV0O2oVWi6EHmzhXlPDjlNJfqFUk97WGhMS7j8wf9iNmWtm+j7H8xQmWYEu0wC8+gor9mKrFht"+
				"luXxgXUjBQi3b9nFZdRqxAjjRs9LB1ouottklH3eph62VogCmFJMQa3bBfDJcuzpAxCzs8k6XJdH"+
				"ShjgaRXdAmawHUi8xMuwgwJBAPXL8x8CmEUr/Sm6GNsADW6bZ/G+Uykj5OdaEPHqu5yCqs7BwipK"+
				"i/iRhvAPd59xDO8MJofzlNy+t2owCUxC0Z8CQQCJlfsC0H3O4vbOR54FmcLN2efOASf8/+T4L4Oh"+
				"8NR+GqJtGcjeFJDk9HXYN9nMoWAaxezCY60yz2mkTa+V02TPAkBlQ3kONYt2NAFAx6Kl1wexoeTY"+
				"+4hXUqpjhf+lKwfQGyUzuS+9LKkt5waosDagQXsKoP7e08NojkoSPWgXOxARAkAYwOojTH2GDmGX"+
				"JV1x5oMc5BqHfr/CDgwU9v940Ep60bHtbRktk4eqGa1mFE5UOah/yKJbKvHLVXPNQsjXbOwTAkEA"+
				"oubTFsWSyFzmlUDALu35gvZ4m+5wYj2mLrp1bcn39TXUbFWl/z861hUYMb2sX+dUvaf7TSo/89Gp"+
				"Ch3VCZ2QPA==";
		String enText = "TPc7UVtnIjUK8Qj8c0R9oZmLLMEKyE3GUonbCNPllBKyjsDnLcg9Fsfr2HXdC5CcSEXP74OJd+RUpEFNhDkZXWkqWIZhLlyHY0yKj4x+Emn+CGMVfEegUHIu0qJwbQveUMDTNmxE0YNpbehGytrXq9L7jGxcZklrftQyN/4rLLQ=";
		enText =  "Ipssw08xKwA5OtJcRMmRo7skL8IeO8XviSIBxxqfc4xYCAhlZReZJ08HNSB3Mfmh6vC0ZgvWSM1TDBZjDm5ZL0CijV2hoSoMxhGTKgVQcehuD0sb4IkA9ZGf2C8uzaymmHGUdHs7VJBiZhYz5AuU68PIyWyYW+l9Oti0+X2UJYM=";
		byte[] deBytes = RSAUtils.decryptByPrivateKey(decryptBASE64(enText), privateKey);
		System.out.println(new String(deBytes));*/


		String text = "admin";
		System.out.println("明文长度：" + text.length());
		byte[] eb = RSAUtils.encryptByPublicKey(text.getBytes(), pubKey);
		String encStr = RSAUtils.encryptBASE64(eb).replace("\r\n", "");
		System.out.println(encStr);
		
		byte[] encryBytes = RSAUtils.decryptBASE64(encStr);
		byte[] decryBytes = RSAUtils.decryptByPrivateKey(encryBytes, privateKey);
		System.out.println(new String(decryBytes));
		//System.out.println( new String( RSAUtils.decryptByPublicKey(eb, pubKey)));
	}
}
