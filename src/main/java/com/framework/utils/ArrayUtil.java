package com.framework.utils;

import java.util.Arrays;

/**
 * 数组工具类
 */
public class ArrayUtil {
	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static byte[] reverse(final byte[] array) {
		byte temp;
		int length = array.length;
		byte[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static short[] reverse(final short[] array) {
		short temp;
		int length = array.length;
		short[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static int[] reverse(final int[] array) {
		int temp;
		int length = array.length;
		int[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static long[] reverse(final long[] array) {
		long temp;
		int length = array.length;
		long[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static float[] reverse(final float[] array) {
		float temp;
		int length = array.length;
		float[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static double[] reverse(final double[] array) {
		double temp;
		int length = array.length;
		double[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static char[] reverse(final char[] array) {
		char temp;
		int length = array.length;
		char[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 考虑时间复杂度和空间复杂度的反转
	 * 
	 * @param <T>
	 *
	 * @param array
	 *            需要翻转的数组
	 * @return
	 */
	public static <T> T[] reverse(final T[] array) {
		T temp;
		int length = array.length;
		T[] res = Arrays.copyOf(array, length);
		for (int i = 0; i < length / 2; i++) {
			temp = res[i];
			res[i] = res[length - 1 - i];
			res[length - 1 - i] = temp;
		}
		return res;
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(byte[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(short[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (short b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(int[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(long[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (long b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(float[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (float b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(double[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (double b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(boolean[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (boolean b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(char[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (char b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 将数组值转换成字符串显示
	 * 
	 * @param data
	 * @return
	 */
	public static String toString(String[] data) {
		if (data == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String b : data) {
			sb.append(b).append(",");
		}
		sb.replace(sb.length() - 1, sb.length(), "]");
		return sb.toString();
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(byte[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(short[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(int[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(long[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(float[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(double[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isNull(char[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static <T> boolean isNull(T[] array) {
		return !isNotNull(array);
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(byte[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(short[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(int[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(long[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(float[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(double[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static boolean isNotNull(char[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 数组是否不为空
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T> boolean isNotNull(T[] array) {
		return array != null && array.length > 0;
	}

	/**
	 * 根据长度拷贝数组
	 * 
	 * @param original
	 *            源数组
	 * @param from
	 *            索引开始位置
	 * @param length
	 *            长度
	 * @return 返回截取的数组
	 */
	public static byte[] copyOfLength(byte[] original, int from, int length) {
		return Arrays.copyOfRange(original, from, from + length);
	}
}
