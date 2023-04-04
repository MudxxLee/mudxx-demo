package com.mudxx.demo.boot.encypt;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Des3 加密解密类
 * @author laiwen
 */
public class Des3 {

	/**
	 * 数据加密
	 * @param src  解密串
	 * @param key  加密秘钥
	 * @return
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String src, String key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] b = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));

		String ss = new String(Base64.encodeBase64(b));
		ss = ss.replaceAll("\\+", "-");
		ss = ss.replaceAll("/", "_");
		return ss;
	}

	// 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	/**
	 * 解密
	 * @param src   解密串
	 * @param key   解密秘钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptThreeDESECB(String src, String key) throws Exception {
		src = src.replaceAll("-", "+");
		src = src.replaceAll("_", "/");
		byte[] bytesrc = Base64.decodeBase64(src.getBytes(StandardCharsets.UTF_8));
		// --解密的key
		DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);

		// --Chipher对象解密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] retByte = cipher.doFinal(bytesrc);

		return new String(retByte, "UTF-8");
	}
	
	
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++){
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static final String DES3_KEY = "demo:boot:des3:key:001001";

	public static void main(String[] args) throws Exception{
		String st = encryptThreeDESECB("root", DES3_KEY);
		System.out.println(st);
		String std = decryptThreeDESECB(st, DES3_KEY);
		System.out.println(std);

	}
}
