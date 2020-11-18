package com.framework.utils;

import org.apache.mina.util.Base64;

/**
 * Base64Util加密解密工具类
 */
public class Base64Util {
	/**
	 * 使用Base64加密
	 * 
	 * @param plainCode
	 *            明文
	 * @return 密文
	 */
	public static byte[] encode(byte[] plainCode) {
		return Base64.encodeBase64(plainCode);
	}

	/**
	 * 使用Base64加密
	 * 
	 * @param plainCode
	 *            明文
	 * @return 密文
	 */
	public static String encodeToString(byte[] plainCode) {
		byte[] encode = Base64.encodeBase64(plainCode);
		return new String(encode);
	}

	/**
	 * 使用Base64解密
	 * 
	 * @param cryptograph
	 *            密文
	 * @return 明文
	 */
	public static byte[] decode(byte[] cryptograph) {
		return Base64.decodeBase64(cryptograph);
	}

	/**
	 * 使用Base64解密
	 * 
	 * @param cryptograph
	 *            密文
	 * @return 明文
	 */
	public static byte[] decode(String cryptograph) {
		return decode(cryptograph.getBytes());
	}

	/**
	 * 使用Base64解密
	 * 
	 * @param cryptograph
	 *            密文
	 * @return 明文
	 */
	public static String decodeToString(String cryptograph) {
		return new String(decode(cryptograph));
	}
}
