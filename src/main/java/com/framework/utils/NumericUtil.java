package com.framework.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 数字工具类
 */
public class NumericUtil {

	private static final DecimalFormat decimalFormat = new DecimalFormat();

	/**
	 * 浮点数取两位格式化
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatDecimal(double decimal) {
		return formatDecimal(decimal, "#.##");
	}

	/**
	 * 浮点数取两位格式化
	 * 
	 * @param decimal
	 * @return
	 */
	public static String formatDecimal(Object decimal) {
		return formatDecimal(decimal, "#.##");
	}

	/**
	 * 浮点数格式化
	 * 
	 * @param decimal
	 * @param pattern
	 * @return
	 */
	public static String formatDecimal(double decimal, String pattern) {
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(decimal);
	}

	/**
	 * 浮点数格式化
	 * 
	 * @param decimal
	 * @param pattern
	 * @return
	 */
	public static String formatDecimal(Object decimal, String pattern) {
		decimalFormat.applyPattern(pattern);
		return decimalFormat.format(decimal);
	}

	/**
	 * 进行加法运算，默认保留2位小数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @return
	 */
	public static double add(double d1, double d2) {
		return add(d1, d2, 2);
	}

	/**
	 * 进行加法运算，并指定小数保留位数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @param scale
	 *            保留位数
	 * @return
	 */
	public static double add(double d1, double d2, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(d1);

		BigDecimal b2 = BigDecimal.valueOf(d2);

		return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 进行减法运算，默认保留2位小数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @return
	 */
	public static double subtract(double d1, double d2) {
		return subtract(d1, d2, 2);
	}

	/**
	 * 进行减法运算，并指定小数保留位数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @param scale
	 *            保留位数
	 * @return
	 */
	public static double subtract(double d1, double d2, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(d1);

		BigDecimal b2 = BigDecimal.valueOf(d2);

		return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 进行乘法运算，默认保留2位小数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @return
	 */
	public static double multiply(double d1, double d2) {
		return multiply(d1, d2, 2);
	}

	/**
	 * 进行乘法运算，并指定小数保留位数
	 * 
	 * @param d1
	 *            浮点数
	 * @param d2
	 *            浮点数
	 * @param scale
	 *            保留位数
	 * @return
	 */
	public static double multiply(double d1, double d2, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(d1);

		BigDecimal b2 = BigDecimal.valueOf(d2);

		return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 进行除法运算，默认保留2位小数
	 * 
	 * @param d1
	 *            被除数
	 * @param d2
	 *            除数
	 * @return
	 */
	public static double divide(double d1, double d2) {
		return divide(d1, d2, 2);
	}

	/**
	 * 进行除法运算，并指定小数保留位数
	 * 
	 * @param d1
	 *            被除数
	 * @param d2
	 *            除数
	 * @param scale
	 *            保留位数
	 * @return
	 */
	public static double divide(double d1, double d2, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(d1);

		BigDecimal b2 = BigDecimal.valueOf(d2);

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 进行四舍五入操作，默认保留2未小数
	 * 
	 * @param d
	 *            浮点数
	 * @return
	 */
	public static double round(double d) {
		return round(d, 2);
	}

	/**
	 * 进行四舍五入操作
	 * 
	 * @param d
	 *            浮点数
	 * @param scale
	 *            保留位数
	 * @return
	 */
	public static double round(double d, int scale) {
		BigDecimal b1 = BigDecimal.valueOf(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将单个字节转换成16进制字符串
	 * 
	 * @param buf
	 *            单个字节
	 * @return
	 */
	public static String parseByte2HexString(byte buf) {
		String hex = Integer.toHexString(buf & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase();
	}

	/**
	 * 将字节数组转换成16进制字符串，每个16进制中间包含空格
	 * 
	 * @param buf
	 *            字节数组
	 * @return
	 */
	public static String parseByte2HexStringWithBlank(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (byte b : buf) {
			String hex = parseByte2HexString(b);
			sb.append(hex.toUpperCase());
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 将字节数组转换成16进制字符串
	 * 
	 * @param buf
	 *            字节数组
	 * @return
	 */
	public static String parseByte2HexString(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (byte b : buf) {
			String hex = parseByte2HexString(b);
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static List<String> parseByte2HexAddress(byte[] buf) {
		List<String> list = new ArrayList<String>();
		int i = 1;
		byte[] address = new byte[6];
		for (byte b : buf) {
			if (b != 0x0A) {
				address[address.length-i] = b;
				i++;
			}else{
				list.add(NumericUtil.parseByte2HexString(address));
				i = 1;
			}
		}
		return list;
	}
	
	/**
	 * 将16进制字符串转换为字节数组
	 * 
	 * @param hexStr
	 *            16进制字符串
	 * @return
	 */
	public static byte[] parseHexString2ByteArray(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将16进制字符串转换为单个字节
	 * 
	 * @param hexStr
	 *            16进制字符串
	 * @return
	 */
	public static Byte parseHexString2Byte(String hexStr) {
		int length = hexStr.length();
		if (length < 1 || length > 2)
			return null;
		if (length==1)
			hexStr = "0"+hexStr;
		int high = Integer.parseInt(hexStr.substring(0, 1), 16);
		int low = Integer.parseInt(hexStr.substring(1, 2), 16);
		byte b = (byte) (high * 16 + low);
		return b;
	}

	/**
	 * 将字符串解析成Integer类型，如果无法解析则直接返回null值的Integer类型
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回Integer对象
	 */
	public static Integer valueOfInteger(String s) {
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 将字符串解析成int，如果无法解析则直接返回0
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回int基本数据
	 */
	public static int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 将字符串解析成Double类型，如果无法解析则直接返回null值的Double类型
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回Double对象
	 */
	public static Double valueOfDouble(String s) {
		try {
			return Double.valueOf(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 将字符串解析成double，如果无法解析则直接返回0.0d
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回double基本数据
	 */
	public static double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return 0.0d;
		}
	}

	/**
	 * 将字符串解析成Float类型，如果无法解析则直接返回null值的Float类型
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回Float对象
	 */
	public static Float valueOfFloat(String s) {
		try {
			return Float.valueOf(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * 将字符串解析成float，如果无法解析则直接返回0.0f
	 * 
	 * @param s
	 *            要解析的字符串
	 * @return 返回float基本数据
	 */
	public static float parseFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}

	/**
	 * 将int数据转换成16进制的byte数组
	 * 
	 * @param data
	 *            要转换的int数据
	 * @param byteLength
	 *            byte数组长度
	 * @return
	 */
	public static byte[] intToByteArray(int data, int byteLength) {
		String format;
		if (data > 0 && byteLength > 0) {
			if (byteLength < 10) {
				format = "%0" + byteLength + "x";
			} else {
				format = "%" + byteLength + "x";
			}
			String s = String.format(format, data);
			return s.getBytes();
		}
		return null;
	}

	/**
	 * 将int数据变成4字节byte数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] intToByteArray(int num) {
		byte[] result = new byte[4];
		result[0] = (byte) (num >>> 24);// 取最高8位放到0下标
		result[1] = (byte) (num >>> 16);// 取次高8为放到1下标
		result[2] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[3] = (byte) (num); // 取最低8位放到3下标
		return result;
	}
	
	/**
	 * 将long数据变成n字节byte数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] longToByteArray(long num, int length) {
		if (length > 8) {
			length = 8;
		} else if (length <= 0) {
			length = 1;
		}
		byte[] result = new byte[length];
		for (int i = 1; i <= length; i++) {
			result[i - 1] = (byte) (num >>> 8 * (length - i));
		}
		return result;
	}
	
	/**
	 * 将int数据变成2字节byte数组
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] intToTwoByteArray(int num) {
		byte[] result = new byte[2];
		result[0] = (byte) (num >>> 8); // 取次低8位放到2下标
		result[1] = (byte) (num); // 取最低8位放到3下标
		return result;
	}

	/**
	 * 16进制8字节数组变为int
	 * 
	 * @param data
	 */
	public static int eightByteArrayToInt(byte[] data) {
		String s = null;
		if (data != null) {
			try {
				s = new String(data, "ascii");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Integer length = Integer.parseInt(s, 16);
			return length;
		}
		return 0;
	}

	/**
	 * 将byte数组转换为int数据
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {
		int value = 0;
		// 由高位到低位
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}
	
	/**
	 * 将byte数组转换为int数据
	 * 
	 * @param bytes
	 * @return
	 */
	public static long byteArrayToLong(byte[] bytes) {
		long value = 0;
		// 由高位到低位
		for (int i = 0; i < bytes.length; i++) {
			int shift = (bytes.length - 1 - i) * 8;
			value += (bytes[i] & 0x000000FF) << shift;// 往高位游
		}
		return value;
	}

	/*public static void main(String[] args) {
		byte[] b = str2Bcd("0059");
		System.out.println(Integer.parseInt(bcd2Str(b)));
	}*/

	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
	}
	
	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static String bcd2Str(byte b) {
		String res = "" + ((b & 0xf0) >>> 4) + (b & 0x0f);
		return res.substring(0, 1).equalsIgnoreCase("0") ? res.substring(1) : res;
	}
	
	/**
	 * @功能: BCD码转为10进制串(阿拉伯数据)
	 * @参数: BCD码
	 * @结果: 10进制串
	 */
	public static int bcd2Int(byte b) {
		String res = "" + ((b & 0xf0) >>> 4) + (b & 0x0f);
		if (res.substring(0, 1).equalsIgnoreCase("0")) {
			res = res.substring(1);
		}
		return Integer.parseInt(res);
	}

	/**
	 * @功能: 10进制串转为BCD码
	 * @参数: 10进制串
	 * @结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	} 
	
	/**
	 * 判断基本数据类型的封装类型是否为null或者0
	 * 
	 * @param number
	 * @return
	 * @author LEVEL
	 * @date 2016年7月24日 下午12:41:27
	 */
	public static boolean isEmpty(Number number)
	{
		return number == null || number.doubleValue() == 0.0d;
	}
	
	/**
	 * 判断基本数据类型的封装类型是否不为null且不为0
	 * 
	 * @param number
	 * @return
	 * @author LEVEL
	 * @date 2016年7月24日 下午12:42:30
	 */
	public static boolean isNotEmpty(Number number)
	{
		return !isEmpty(number);
	}
}