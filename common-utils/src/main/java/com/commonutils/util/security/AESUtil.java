package com.commonutils.util.security;

import com.commonutils.util.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES加密工具
 */
public class AESUtil {
    /**
     *
     * @param EncryptRule 秘钥
     * @param Content 加密内容
     * @return base64密文
     */
    public static String Encrypt(String EncryptRule,String Content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator Keygen=KeyGenerator.getInstance("AES");

            //2.根据Key初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom Random = SecureRandom.getInstance("SHA1PRNG");
            Random.setSeed(EncryptRule.getBytes());
            Keygen.init(128, Random);

            //3.产生原始对称密钥
            SecretKey Original_Key=Keygen.generateKey();

            //4.获得原始对称密钥的字节数组
            byte [] Raw=Original_Key.getEncoded();

            //5.根据字节数组生成AES密钥
            SecretKey Key=new SecretKeySpec(Raw, "AES");

            //6.根据指定算法AES自成密码器
            Cipher CipherInstance=Cipher.getInstance("AES");

            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            CipherInstance.init(Cipher.ENCRYPT_MODE, Key);

            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] Byte_Encode=Content.getBytes("utf-8");

            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] Byte_AES=CipherInstance.doFinal(Byte_Encode);

            //10.将加密后的数据转换为字符串
            String AES_encode= Base64.encodeBase64URLSafeString(Byte_AES);

            //11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param DecryptRule 秘钥
     * @param Content   Encrypt方法生成的密文
     * @return 原文
     */
    public static String Decrypt(String DecryptRule,String Content) {
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator Keygen=KeyGenerator.getInstance("AES");

            //2.根据DecryptRule规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom Random = SecureRandom.getInstance("SHA1PRNG");
            Random.setSeed(DecryptRule.getBytes());
            Keygen.init(128, Random);

            //3.产生原始对称密钥
            SecretKey Original_Key=Keygen.generateKey();

            //4.获得原始对称密钥的字节数组
            byte [] Raw=Original_Key.getEncoded();

            //5.根据字节数组生成AES密钥
            SecretKey Key=new SecretKeySpec(Raw, "AES");

            //6.根据指定算法AES自成密码器
            Cipher CipherInstance=Cipher.getInstance("AES");

            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            CipherInstance.init(Cipher.DECRYPT_MODE, Key);

            //8.将加密并解码后的内容解码成字节数组
            byte [] Byte_Content= Base64.decodeBase64(Content);

			/*
			 * 解密
			 */
            byte [] Byte_Decode=CipherInstance.doFinal(Byte_Content);
            String AES_decode=new String(Byte_Decode,"utf-8");

            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.element("UserName","18516094389");
        json.element("Password","123456");
        json.element("CellPhone","18516094389");
        json.element("NickName","18516094389");
//        String content = "{\"UserName\":\"18516094389\",\"Password\":\"123456\"}";
        String content = json.toString();
        String password = "S2V5QnlYWFdGcm9tQm9XdVl1bg==";
        System.out.println("加密之前：" + content);

        // 加密
        String encrypt = Encrypt(password,content);
        System.out.println("加密后的内容：" + encrypt);

        // 解密
        String decrypt = Decrypt(password,encrypt);
        System.out.println("解密后的内容：" + decrypt);
    }
}
