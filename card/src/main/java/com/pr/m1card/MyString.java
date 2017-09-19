package com.pr.m1card;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class MyString {
	/**
	 * 转换 byte[] to 十六进制字符串.
	 * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串�??
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String hexBytesToString(byte[] src) {
		try {
			return new String(src, "ascii");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return "no data";
	}

	public static int[] hexStringToInt(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		int length = hexString.length() / 2;
		int[] d = new int[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = Integer.parseInt(hexString.substring(pos, pos + 1), 16);
		}
		return d;
	}

	/**
	 * 转化字符串为十六进制编码
	 * 
	 * @param s
	 * @return
	 */
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 转化十六进制编码为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * string先转成char再转成String, 第一个string是byte转换过来的，其实是16进制的String显示。
	 * 
	 * @param s
	 * @return
	 */
	public static String stringToCharToString(String s) {

		StringBuilder sb = new StringBuilder();
		char[] c = new char[20];
		for (int i = 0; i < s.length() / 2; i++) {
			// string->char 两个两个转换
			c[i] = (char) Integer.parseInt(s.substring(0 + i * 2, 2 + i * 2),
					16);

			sb.append(c[i]);
			// Log.i("########",sb.toString());
		}
		return sb.toString();
	}

	/**
	 * 按指定长度的格式化输出，前面补0
	 * 
	 * @param str
	 *            ： 需要格式化的字符串
	 * @param length
	 *            ： 指定的长度
	 * @return 格式化后的字符串
	 */
	public static String formatString(String str, Integer length) {
		
		String plate =String.format("%0"+ length+ "d", 0);
		// 字符串前面补0
		String result = plate.substring(0, length - str.length()) + str;

		return result;
	}
	
	/**
	 * 12345678 - > 78563412
	 * 
	 * @param str
	 *            ： 需要格式化的字符串
	 * @param length
	 *            ： 指定的长度
	 * @return 反转后的字符串
	 */
	public static String reverseString(String str, Integer length) {
		
		StringBuilder sb = new StringBuilder();
		String[] arr = new String[length/2];
		for(int i=0;i<length/2;i++){
			arr[i] = formatString(str,length).substring(length-2*(i+1), length-2*i);
			sb.append(arr[i]);
		}
		//System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	
}
