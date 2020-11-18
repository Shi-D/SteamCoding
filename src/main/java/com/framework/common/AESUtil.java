package com.framework.common;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil
{
	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) throws Exception
	{
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
	public static byte[] decrypt(byte[] content, String password) throws Exception
	{
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
}
