package com.pr.m1card;

import java.io.File;
import java.math.BigInteger;

import android.util.Log;

public class AndroidSDK {

	public native int openSerialPort(String serialPort, int baud,
			int nDataBits, int nStopBits, int nParity);

	public native boolean closeSerialPort(int fd);

	public native int serialWrite(String buf, int fd, int flag, int size);

	public native int setBaudrate(int fd, int baud);

	public native int setSerialPortParam(int fd, int dataBits, int StopBits,
			int parity);

	public native int getInputSerialPortBytes(int fd);

	public native int clearSerialPortBytes(int fd);

	public native int sendSerialPortByte(int fd, String str, int size);

	public native String receiveSerialPortByte(int fd, int size);

	public native int sendSerialPortString(int fd, String str, int flag,
			int size);

	public native String ReceiveSerialPortString(int fd);

	static {
		System.loadLibrary("XY101ThreeCun");
	}

	private int fd = -1, count = 0;
	int res = 0;

	public int CMD = -1;
	public int STRING = 1;

	private int open(String port, int intBaud, int nDataBits, int nStopBits,
			int nParity) {
		File file = new File(port);

		if (!file.exists()) {
			return -1;
		}

		fd = openSerialPort(port, intBaud, nDataBits, nStopBits, nParity);
		Log.e("open fd===", " " + fd);
		if (fd < 0 || fd > 254) {
			return -1;
		} else {

			return fd;
		}
	}

	private boolean close() {
		closeSerialPort(fd);
		return true;
	}

	private int write(String str, int flag) {

		// 打印中文字
		if (str.length() != str.getBytes().length) {
			Log.d("INFO", "chinese--------------------");
			res = serialWrite(str, fd, 0, 0);
			return res;
		} else if (flag == STRING) {

			Log.d("INFO", "STRING-------------------------");
			int len = str.getBytes().length;
			res = serialWrite(str, fd, STRING, len);
			return res;
		} else if (flag == CMD) {
			Log.d("INFO", "CMD-------------------------");
			res = serialWrite(str, fd, CMD, str.length());
			return res;
		}
		return 0;
	}

	private int setBaud(int hander, int baud) {
		return setBaudrate(hander, baud);
	}

	private int setSerialPortParamater(int hander, int dataBits, int StopBits,
			int parity) {
		return setSerialPortParam(hander, dataBits, StopBits, parity);
	}

	private int getInputSerialPortByte(int hander) {
		return getInputSerialPortBytes(hander);
	}

	private int clearSerialPortByte(int hander) {
		return clearSerialPortBytes(hander);
	}

	private int sendSerialPortBytes(int fd, byte[] buffer, int size) {
		String str = MyString.bytesToHexString(buffer);
		return sendSerialPortByte(fd, str, str.length());

	}

	private int sendSerialPortStrings(int fd, String str) {
		// 打印中文字
		if (str.length() != str.getBytes().length) {
			res = serialWrite(str, fd, 0, 0);
			return res;
		} else {
			int len = str.getBytes().length;
			res = serialWrite(str, fd, STRING, len);
			return res;
		}

	}

	private String ReceiveSerialPortStrings(int fd) {
		String str = ReceiveSerialPortString(fd);

		if (str == null || str.isEmpty()) {
			return "null";
		} else
			return str;
	}

	/**
	 * 不满2位的整型，补足两位。
	 * 
	 * @param v
	 * @return
	 */
	private String formateInputValue(int v) {
		if (Integer.toString(v).length() == 1) {
			return String.format("%02d", v);
		} else {
			return Integer.toString(v);
		}
	}

	/**
	 * 不满2位的字符串，补足两位。
	 * 
	 * @param v
	 * @return
	 */
	private String formateInputValue(String v) {
		if (v.length() == 1) {
			return "0" + v;
		} else {
			return v;
		}
	}

	/**
	 * 设置输入值得区间范围
	 * 
	 * @param v
	 * @return
	 */
	private int setInputValueScope(int v, int minValue, int maxValue) {
		if (v < minValue) {
			return minValue;
		} else if (v > maxValue) {
			return maxValue;
		} else {
			return v;
		}
	}

	/**
	 * 打印内容输出
	 * 
	 * @param content
	 *            可打印的格式化内容
	 * @return 实际打印内容的长度
	 */
	public int PrintWrite(String content) {
		write(content, STRING);
		return content.length();
	}

	/**
	 * 打开串口设备
	 * 
	 * @param name
	 *            串口设备路径
	 * @param nBaudrate
	 *            指定波特率，一般为115200
	 * @param nDataBits
	 *            设置数据位 8
	 * @param nStopBits
	 *            设置停止位 1
	 * @param nParity
	 *            设置检验位 0
	 * @return -1表示失败，成功返回设备句柄
	 */
	public int OpenUart(String name, int nBaudrate, int nDataBits,
			int nStopBits, int nParity) {
		fd = open(name, nBaudrate, nDataBits, nStopBits, nParity);
		return fd;
	}

	/**
	 * 关闭串口设备
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄。
	 * @return 大于0，表示成功，否则失败
	 */
	public int CloseUart(int hSerial) {
		boolean closeSerialPort = closeSerialPort(hSerial);
		if (closeSerialPort) {
			return 1;
		} else
			return 0;
	}

	/**
	 * 设置串口波特率
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @param nBaudrate
	 *            指定波特率
	 * @return 等于0，表示成功，否则失败
	 */
	public int SetUartBaudrate(int hSerial, int nBaudrate) {

		return setBaudrate(hSerial, nBaudrate);
	}

	/**
	 * 设置串口参数
	 * 
	 * @param hander
	 *            OpenUart成功时，返回的设备句柄
	 * @param dataBits
	 *            设置数据位
	 * @param StopBits
	 *            设置停止位
	 * @param parity
	 *            设置校验位
	 * @return 等于0，表示成功，否则失败
	 */
	public int SetSerialPortParam(int hSerial, int nDataBits, int nStopBits,
			int nParity) {
		return setSerialPortParamater(hSerial, nDataBits, nStopBits, nParity);
	}

	/**
	 * 获取串口接收缓冲区字节数
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @return 接收缓冲区字节数
	 */
	public int GetUartInputCount(int hSerial) {
		return getInputSerialPortByte(hSerial);
	}

	/**
	 * 清除终端串口接受缓冲区数据
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @return 等于0，表示成功，否则失败
	 */
	public int ClearUartInputCount(int hSerial) {
		return clearSerialPortByte(hSerial);
	}

	/**
	 * 发送串口数据
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @param buffer
	 *            发送数据缓冲区
	 * @param size
	 *            发送数据长度
	 * @return 大于0，表示成功，否则失败
	 */
	public int SendByteUart(int hSerial, byte[] buffer, int size) {

		return sendSerialPortBytes(hSerial, buffer, size);
	}

	/**
	 * 接收串口数据
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @param buffer
	 *            接收缓冲区
	 * @param size
	 *            接收缓冲区长度(请保证缓冲区大小足够大)
	 * @return 大于0，表示成功，否则失败
	 */
	public int RecvByteUart(int hSerial, byte[] buffer, int size) {

		String res = receiveSerialPortByte(hSerial, size);
		if (res.isEmpty() || res.contains("faield")) {
			return -1;
		}
		// Log.i("######","#####res:"+res);
		byte[] byteTmp = MyString.hexStringToBytes(res);
		for (int i = 0; i < res.length() / 2; i++)
			buffer[i] = byteTmp[i];

		return res.length() / 2;
	}

	/**
	 * 发送串口数据（字符串类型）
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @param str
	 *            发送数据
	 * @return 大于0，表示成功，否则失败
	 */
	public int SendUart(int hSerial, String str) {

		return sendSerialPortStrings(fd, str);
	}

	/**
	 * 接收串口数据（字符串类型）
	 * 
	 * @param hSerial
	 *            OpenUart成功时，返回的设备句柄
	 * @return 失败返回null，成功返回接收到的内容
	 */
	public String RecvUart(int hSerial) {

		return ReceiveSerialPortStrings(hSerial);
	}

	/**
	 * 该挃令完成查询、防冲突、选卡功能，执行此挃令后就可以进行卡片认证等后续操作
	 * 
	 * @return
	 */
	public String getCardNum() {
		write("400700010000000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer));
		return MyString.bytesToHexString(buffer).trim();

	}

	/**
	 * 获取模块版本号
	 * 
	 * @return
	 */
	public String getVersion() {
		write("4000000000000D", CMD);
		byte[] buffer = new byte[24];
		RecvByteUart(fd, buffer, 24);
		String temp = MyString.bytesToHexString(buffer).substring(10, 31);
		return MyString.stringToCharToString(temp);
	}

	/**
	 * 关闭模块
	 * 
	 * @return 4141->关闭成功
	 */
	public String closeModule() {
		write("4001000000000D", CMD);
		byte[] buffer = new byte[8];
		RecvByteUart(fd, buffer, 8);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 14));
		return MyString.bytesToHexString(buffer).substring(10, 14);
	}

	/**
	 * 重启模块
	 * 
	 * @return 4242->关闭成功
	 */
	public String moduleWarmReset() {
		write("4002000000000D", CMD);
		byte[] buffer = new byte[8];
		RecvByteUart(fd, buffer, 8);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 14));
		return MyString.bytesToHexString(buffer).substring(10, 14);
	}

	/**
	 * 射频重启
	 * 
	 * @return 4343->关闭成功
	 */
	public String rfReset() {
		write("400300010100000D", CMD);
		byte[] buffer = new byte[8];
		RecvByteUart(fd, buffer, 8);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 14));
		return MyString.bytesToHexString(buffer).substring(10, 14);
	}

	/**
	 * 检查射频范围内是否有卡存在
	 * 
	 * @return 0400->有卡
	 */
	public String request() {
		write("400400010100000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 14));
		return MyString.bytesToHexString(buffer).substring(10, 14);
	}

	/**
	 * 可选级别的防冲突，一般 S50 卡使用第一级别防冲突操作就可以。
	 * 
	 * @return 4 字节卡片序列号
	 */
	public String anticoll() {
		write("40050002930000000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 18));
		return MyString.bytesToHexString(buffer).substring(10, 18);
	}

	/**
	 * 选卡
	 * 
	 * @return 选择是否成功：1 字节，一般 S50 卡 0x08 表示成功
	 */
	public String selectCard() {
		write("40060005931C4FBEA700000D", CMD);
		byte[] buffer = new byte[24];
		RecvByteUart(fd, buffer, 24);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 12));
		return MyString.bytesToHexString(buffer).substring(10, 12);
	}

	/**
	 * 认证，在读取卡号完成后，完成认证操作
	 * 
	 * @return 对扇区进行认证，如果密钥匹配，则返回值为 00
	 */
	public String authentication() {
		write("400800080000ffffffffffff00000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 14));
		return MyString.bytesToHexString(buffer).substring(10, 14);
	}

	/**
	 * 读取数据块。 必须先读卡、认证
	 * 
	 * @return 16 字节数据
	 */
	public String readBlock(String blockNum) {
		write("40090001" + blockNum + "00000D", CMD);
		byte[] buffer = new byte[48];
		RecvByteUart(fd, buffer, 48);
		Log.i("####", MyString.bytesToHexString(buffer).substring(10, 42));
		return MyString.bytesToHexString(buffer).substring(10, 42);
	}

	/**
	 * 写数据块。 必须先读卡、认证
	 * 
	 * @return 16 字节数据
	 */
	public String writeBlock(String blockNum, String writeData) {
		write("40100011" + blockNum + writeData + "00000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer).substring(4, 6));
		return MyString.bytesToHexString(buffer).substring(4, 6);
	}

	/**
	 * 初始化数据块。 必须先读卡、认证
	 * 
	 * @return 16 字节数据
	 */
	public String initBlock(String blockNum, String writeData) {
		write("40100011" + blockNum + "00000000" + "ffffffff"
				+ "0000000000FF00FF" + "00000D", CMD);
		byte[] buffer = new byte[16];
		RecvByteUart(fd, buffer, 16);
		Log.i("####", MyString.bytesToHexString(buffer));
		return MyString.bytesToHexString(buffer);
	}

	/**
	 * 加值。 必须先读卡、认证。
	 * 后期需要加逻辑判断，充值的钱+卡里的钱<2147483647
	 * @return "加值成功"，或"加值失败"
	 */
	public String addBlock(String blockNum, Integer writeData) {
		/*
		 * 1.整形->16进制
		 * 2.十六进制每两位为一组，最后一组放第一位，依次。
		 */
		//Integer->16进制
		if(writeData>=0){
			String str = String.format("%x", writeData);
			//字符串前面补0
			String  result = MyString.reverseString(str, 8);
			
			Log.i("writeData", result);
			
			write("40110007C1"+blockNum +result + blockNum	+ "00000D", CMD);
			byte[] buffer = new byte[16];
			RecvByteUart(fd, buffer, 16);
			Log.i("####", MyString.bytesToHexString(buffer));
			if (MyString.bytesToHexString(buffer).substring(10, 14).equals("5151")){
				return "加值成功";
			}else{
				return "加值失败";
			}
		}else{
			return "加值失败";
		}
	}
	/**
	 * 减值。 必须先读卡、认证。
	 * 后期需加判断，-2147483648 时不能再做减值操作
	 * @return 
	 */
	public String subractBlock(String blockNum, Integer writeData) {
		
		if( writeData>=0){
			String str = String.format("%x", writeData);
			String  result = MyString.reverseString(str, 8);
			Log.i("writeData", result);
			
			write("40110007C0"+ blockNum +result + blockNum	+ "00000D", CMD);
			byte[] buffer = new byte[16];
			RecvByteUart(fd, buffer, 16);
			Log.i("####", MyString.bytesToHexString(buffer));
			if (MyString.bytesToHexString(buffer).substring(10, 14).equals("5151")){
				return "减值成功";
			}else{
				return "减值失败";
			}
		}else{
			return "减值失败";
		}
	}
	
	/**
	 * 数值块传值 ，把blockNum1传入到blockNum2
	 * @return 是否成功
	 */
	public String transmitBlock(String blockNum1, String blockNum2) {
	
			write("40110007C2"+ blockNum1 +"00000000" + blockNum2	+ "00000D", CMD);
			byte[] buffer = new byte[16];
			RecvByteUart(fd, buffer, 16);
			Log.i("####", MyString.bytesToHexString(buffer));
			if (MyString.bytesToHexString(buffer).substring(10, 14).equals("5151")){
				return "传值成功";
			}else{
				return "传值失败";
			}
		
	}
	
	/**
	 * 读出改模块的值，并转化为数字
	 * @param blockNum
	 * @return
	 */
	public String readBlockToNumber(String blockNum){
		write("40090001" + blockNum + "00000D", CMD);
		byte[] buffer = new byte[48];
		RecvByteUart(fd, buffer, 48);
		
		String str =  MyString.bytesToHexString(buffer).substring(10, 18);
		String  result = MyString.reverseString(str, 8);
		Log.i("十六进制", result);
		//把16进制转换成整形
//		BigInteger big = new BigInteger(result,16);
//		//String.valueOf(Integer.parseInt(result, 16));
//		Log.i("十进制", big.toString());
//		return String.valueOf(Integer.parseInt(result, 16));
		if(result.substring(0, 1).equals("f")){
			return "金额不能为负";
		}else{
			String.valueOf(Integer.parseInt(result, 16));
			Log.i("十进制", String.valueOf(Integer.parseInt(result, 16)));
			return String.valueOf(Integer.parseInt(result, 16));
		}
	}

}
