package com.framework.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) throws Exception {
		// 获取密钥生成器
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 初始化密钥生成器
		kgen.init(128, new SecureRandom(password.getBytes()));
		// 生成密钥
		SecretKey secretKey = kgen.generateKey();
		// 获取主要编码格式
		byte[] enCodeFormat = secretKey.getEncoded();

		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("AES");

		byte[] byteContent = content.getBytes("utf-8");
		// 将Cipher初始化为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 加密
		byte[] result = cipher.doFinal(byteContent);

		return result;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) throws Exception {
		// 获取密钥生成器
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 初始化密钥生成器
		kgen.init(128, new SecureRandom(password.getBytes()));
		// 生成密钥
		SecretKey secretKey = kgen.generateKey();
		// 获取主要编码格式
		byte[] enCodeFormat = secretKey.getEncoded();

		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("AES");
		// 将Cipher初始化为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 解密
		byte[] result = cipher.doFinal(content);

		return result;
	}
	
	/**
	 * ALGORITHM 算法 
	 * 可替换为以下任意一种算法，同时key值的size相应改变。
	 * DES          		key size must be equal to 56
	 * DESede(TripleDES) 	key size must be equal to 112 or 168
	 * AES          		key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
	 * Blowfish     		key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
	 * RC2          		key size must be between 40 and 1024 bits
	 * RC4(ARCFOUR) 		key size must be between 40 and 1024 bits
	 * 在Key toKey(byte[] key)方法中使用下述代码
	 * SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);替换
	 * DESKeySpec dks = new DESKeySpec(key);
	 * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
	 * SecretKey secretKey = keyFactory.generateSecret(dks);
	 */
	public static final String ALGORITHM = "AES";

	public static byte[] encrypt(byte[] data, byte[] psd) { // 加密 lys

		byte[] result;

		if (null == data || null == psd) {
			return null;
		}

		if (psd.length != 16) { // AES key 128 bit
			return null;
		}

		try {

			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(psd, "AES");
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			result = aesECB.doFinal(data);

			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} finally {
			// return result;
		}
		return null;

	}

	public static byte[] decrypt(byte[] data, byte[] psd) { // 解密 lys

		if (null == data || null == psd) {
			return null;
		}

		if (psd.length != 16) { // AES key 128 bit
			return null;
		}

		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			SecretKeySpec key = new SecretKeySpec(psd, "AES");
			cipher.init(Cipher.DECRYPT_MODE, key); // 初始化

			try {
				return cipher.doFinal(data);
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 解密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(byte[] content, String password) throws Exception {
		// 获取密钥生成器
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// 初始化密钥生成器
		kgen.init(128, new SecureRandom(password.getBytes()));
		// 生成密钥
		SecretKey secretKey = kgen.generateKey();
		// 获取主要编码格式
		byte[] enCodeFormat = secretKey.getEncoded();

		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		// 将Cipher初始化为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 加密
		byte[] result = cipher.doFinal(content);

		return result;
	}
}
