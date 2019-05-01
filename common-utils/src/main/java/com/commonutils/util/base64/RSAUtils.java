package com.commonutils.util.base64;

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
 * RSA���ܹ���
 */
public class RSAUtils {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	public static final String PADDING="RSA/None/PKCS1Padding";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final int KEY_SIZE = 1024;  //  ��Կ����1024�������117�ֽ�����

	static{
		Security.addProvider(new BouncyCastleProvider());
	}
	/**
	 * ��˽Կ����Ϣ��������ǩ��
	 *
	 * @param data
	 *            ��������
	 * @param privateKey
	 *            ˽Կ
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// ������base64�����˽Կ
		byte[] keyBytes = decryptBASE64(privateKey);

		// ����PKCS8EncodedKeySpec����
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM ָ���ļ����㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// ȡ˽Կ�׶���
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// ��˽Կ����Ϣ��������ǩ��
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	/**
	 * BASE64����
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64Util.encodeBase64String(key);
	}

	/**
	 * BASE64����
	 *
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64Util.decodeBase64(key.getBytes());
	}

	/**
	 * У������ǩ��
	 *
	 * @param data
	 *            ��������
	 * @param publicKey
	 *            ��Կ
	 * @param sign
	 *            ����ǩ��
	 *
	 * @return У��ɹ�����true ʧ�ܷ���false
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {

		// ������base64����Ĺ�Կ
		byte[] keyBytes = decryptBASE64(publicKey);

		// ����X509EncodedKeySpec����
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM ָ���ļ����㷨
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// ȡ��Կ�׶���
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// ��֤ǩ���Ƿ�����
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * ����<br>
	 * ��˽Կ����
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// ����Կ����
		byte[] keyBytes = decryptBASE64(key);

		// ȡ��˽Կ
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// �����ݽ���
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * ����<br>
	 * �ù�Կ����
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// ����Կ����
		byte[] keyBytes = decryptBASE64(key);

		// ȡ�ù�Կ
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// �����ݽ���
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * ����<br>
	 * �ù�Կ����
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// �Թ�Կ����
		byte[] keyBytes = decryptBASE64(key);

		// ȡ�ù�Կ
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// �����ݼ���
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * ����<br>
	 * ��˽Կ����
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// ����Կ����
		byte[] keyBytes = decryptBASE64(key);

		// ȡ��˽Կ
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// �����ݼ���
		Cipher cipher = Cipher.getInstance(PADDING ,  BouncyCastleProvider.PROVIDER_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * ȡ��˽Կ(BASE64����)
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
	 * ȡ�ù�Կ(BASE64����)
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
	 * �ж��ַ����Ƿ񱻹�Կ����
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
	 * ��ʼ����Կ
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM , BouncyCastleProvider.PROVIDER_NAME);
		keyPairGen.initialize(KEY_SIZE , new SecureRandom());

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// ��Կ
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// ˽Կ
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
		System.out.println("��Կ��" + pubKey);
		System.out.println("˽Կ��" + privateKey);

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
		System.out.println("���ĳ��ȣ�" + text.length());
		byte[] eb = RSAUtils.encryptByPublicKey(text.getBytes(), pubKey);
		String encStr = RSAUtils.encryptBASE64(eb).replace("\r\n", "");
		System.out.println(encStr);
		
		byte[] encryBytes = RSAUtils.decryptBASE64(encStr);
		byte[] decryBytes = RSAUtils.decryptByPrivateKey(encryBytes, privateKey);
		System.out.println(new String(decryBytes));
		//System.out.println( new String( RSAUtils.decryptByPublicKey(eb, pubKey)));
	}
}
