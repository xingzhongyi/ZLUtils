package com.purong;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;

import com.printer.bluetooth.android.BluetoothDiscover;
import com.printer.bluetooth.android.PrintGraphics;
import com.printer.bluetooth.android.PrinterType;
import com.printer.bluetooth.android.Table;
import com.printer.bluetooth.android.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class BluetoothPrinter {
    private static final String TAG = "BluetoothPrinter";
    private boolean needVerify = false;
    private BluetoothDevice btDevice;
    private BluetoothSocket btSocket;
    private BluetoothAdapter btAdapt;
    private OutputStream sendStream;
    private InputStream receiveStream;
    private Handler printerHandler;
    public static final int Handler_Connect_Connecting = 100;
    public static final int Handler_Connect_Success = 101;
    public static final int Handler_Connect_Failed = 102;
    public static final int Handler_Connect_Closed = 103;
    public static final int Handler_Message_Error = 104;
    public static final int Handler_Message_Read = 105;
    private String printerName = "";
    private String macAddress = "";
    private String mEncoding;
    private boolean autoReceive = false;
    private boolean isConnected = false;
    private String SDK_Vesion = "2.3";
    private PrinterType currentPrintType;
    private String companyTitle;
    private String companySubTitle;
    private Bitmap companyLogo;
    private List<String> tableData;
    private String tableReg;
    private String[] tableLine;
    private int two_dimensional_param1;
    private int two_dimensional_param2;
    private int two_dimensional_param3;
    private HashMap<Integer, String> unPrintColumnMap;
    private int[] tableColWidth;
    private byte currentBarCodeType;
    private final UUID PRINTER_UUID;
    private final String printerCompanyName;
    private String separator_T9;
    private String separator_T3;
    public static final int COMM_INIT_PRINTER = 0;
    public static final int COMM_WAKE_PRINTER = 1;
    public static final int COMM_PRINT_AND_RETURN_STANDARD = 2;
    public static final int COMM_PRINT_AND_NEWLINE = 3;
    public static final int COMM_PRINT_AND_ENTER = 4;
    public static final int COMM_MOVE_NEXT_TAB_POSITION = 5;
    public static final int COMM_DEF_LINE_SPACING = 6;
    public static final int COMM_PRINT_AND_WAKE_PAPER_BY_LNCH = 0;
    public static final int COMM_PRINT_AND_WAKE_PAPER_BY_LINE = 1;
    public static final int COMM_CLOCKWISE_ROTATE_90 = 4;
    public static final int COMM_ALIGN = 13;
    public static final int COMM_ALIGN_LEFT = 0;
    public static final int COMM_ALIGN_CENTER = 1;
    public static final int COMM_ALIGN_RIGHT = 2;
    public static final int COMM_LINE_HEIGHT = 10;
    public static final int COMM_CHARACTER_RIGHT_MARGIN = 11;
    public static final byte BAR_CODE_TYPE_UPC_A = 0;
    public static final byte BAR_CODE_TYPE_UPC_E = 1;
    public static final byte BAR_CODE_TYPE_JAN13 = 2;
    public static final byte BAR_CODE_TYPE_JAN8 = 3;
    public static final byte BAR_CODE_TYPE_CODE39 = 4;
    public static final byte BAR_CODE_TYPE_ITF = 5;
    public static final byte BAR_CODE_TYPE_CODABAR = 6;
    public static final byte BAR_CODE_TYPE_CODE93 = 72;
    public static final byte BAR_CODE_TYPE_CODE128 = 73;
    public static final byte BAR_CODE_TYPE_PDF417 = 100;
    public static final byte BAR_CODE_TYPE_DATAMATRIX = 101;
    public static final byte BAR_CODE_TYPE_QRCODE = 102;

    public String getEncoding() {
        return this.mEncoding;
    }

    public void setEncoding(String mEncoding) {
        this.mEncoding = mEncoding;
    }

    public void setAutoReceiveData(boolean auto) {
        this.autoReceive = auto;
    }

    public void setHandler(Handler mHandler) {
        this.printerHandler = mHandler;
    }

    public PrinterType getCurrentPrintType() {
        return this.currentPrintType;
    }

    public void setCurrentPrintType(PrinterType currentPrintType) {
        this.currentPrintType = currentPrintType;
    }

    public String getSDK_Vesion() {
        return this.SDK_Vesion;
    }

    public String getCompanyName() {
        return "company";
    }

    public boolean isConnected() {
        return this.isConnected;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public String getFriendName() {
        return this.getPrinterName();
    }

    public String getPrinterName() {
        return this.printerName;
    }

    public BluetoothPrinter(BluetoothDevice device) {
        this.currentPrintType = PrinterType.T9;
        this.PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        this.printerCompanyName = "company";
        this.separator_T9 = "==============================================\n";
        this.separator_T3 = "==============================\n";
        this.btDevice = device;
        if (this.btDevice != null) {
            this.printerName = this.btDevice.getName();
            this.macAddress = this.btDevice.getAddress();
        }

    }

    public BluetoothPrinter(String macAddress, int flag) {
        this.currentPrintType = PrinterType.T9;
        this.PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        this.printerCompanyName = "company";
        this.separator_T9 = "==============================================\n";
        this.separator_T3 = "==============================\n";
        this.btAdapt = BluetoothAdapter.getDefaultAdapter();

        try {
            this.btDevice = this.btAdapt.getRemoteDevice(macAddress.toUpperCase());
        } catch (Exception var4) {
            var4.printStackTrace();
            this.btDevice = null;
        }

        if (this.btDevice != null) {
            this.printerName = this.btDevice.getName();
            macAddress = this.btDevice.getAddress();
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public BluetoothPrinter(String deviceName) {
        this.currentPrintType = PrinterType.T9;
        this.PRINTER_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        this.printerCompanyName = "company";
        this.separator_T9 = "==============================================\n";
        this.separator_T3 = "==============================\n";
        if (deviceName != null && !deviceName.equals("")) {
            this.btDevice = BluetoothDiscover.getBondedPrinterByName(deviceName);
            if (this.btDevice != null) {
                this.printerName = this.btDevice.getName();
                this.macAddress = this.btDevice.getAddress();
            }
        }

    }

    public boolean isPrinterNull() {
        return this.btDevice == null;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int open() {
        this.openConnection();
        return 0;
    }

    public void openConnection() {
        if (this.printerHandler != null) {
            this.printerHandler.obtainMessage(100).sendToTarget();
        }

        (new Thread() {
            public void run() {
                int bytes;
                try {
                    BluetoothPrinter.this.btSocket = BluetoothPrinter.this.btDevice.createRfcommSocketToServiceRecord(BluetoothPrinter.this.PRINTER_UUID);
                    if (BluetoothPrinter.this.btSocket != null) {
                        BluetoothPrinter.this.btSocket.connect();
                        Log.i("BluetoothPrinter", "connect establish, wait encryption verify");
                    }

                    BluetoothPrinter.this.sendStream = BluetoothPrinter.this.btSocket.getOutputStream();
                    BluetoothPrinter.this.receiveStream = BluetoothPrinter.this.btSocket.getInputStream();
                } catch (Exception var5) {
                    Log.i("BluetoothPrinter", "connect failed, wait reflect method or 4.0 method to retry..");
                    var5.printStackTrace();

                    try {
                        bytes = Integer.parseInt(VERSION.SDK);
                        Log.i("BluetoothPrinter", "sdk is.." + bytes);
                        if (bytes >= 14) {
                            BluetoothPrinter.this.btSocket = BluetoothPrinter.this.btDevice.createInsecureRfcommSocketToServiceRecord(BluetoothPrinter.this.PRINTER_UUID);
                            if (BluetoothPrinter.this.btSocket != null) {
                                Log.i("BluetoothPrinter", "4.0 method success, wait connect....");
                                BluetoothPrinter.this.btSocket.connect();
                                Log.i("BluetoothPrinter", "connect establish, wait encryption verify");
                            }
                        } else {
                            Method e = BluetoothPrinter.this.btDevice.getClass().getMethod("createRfcommSocket", Integer.TYPE);
                            BluetoothPrinter.this.btSocket = (BluetoothSocket) e.invoke(BluetoothPrinter.this.btDevice, Integer.valueOf(1));
                            if (BluetoothPrinter.this.btSocket != null) {
                                BluetoothPrinter.this.btSocket.connect();
                                Log.i("BluetoothPrinter", "reflect success, connect establish, wait encryption verify");
                            }
                        }
                    } catch (Exception var4) {
                        Log.i("BluetoothPrinter", "all method connect failed, return.");
                        var4.printStackTrace();
                        BluetoothPrinter.this.closeConnection();
                        if (printerHandler != null) {
                            printerHandler.obtainMessage(102).sendToTarget();
                        }
                        return;
                    }
                }

                if (BluetoothPrinter.this.needVerify) {
                    if (!BluetoothPrinter.this.verifyEncryption()) {
                        Log.i("BluetoothPrinter", "encryption verify failed");
                        BluetoothPrinter.this.closeConnection();
                        return;
                    }

                    Log.i("BluetoothPrinter", "encryption verify success");
                    BluetoothPrinter.this.isConnected = true;
                    if (BluetoothPrinter.this.printerHandler != null) {
                        BluetoothPrinter.this.printerHandler.obtainMessage(101).sendToTarget();
                    }
                } else {
                    BluetoothPrinter.this.isConnected = true;
                    if (BluetoothPrinter.this.printerHandler != null) {
                        BluetoothPrinter.this.printerHandler.obtainMessage(101).sendToTarget();
                    }
                }

                if (BluetoothPrinter.this.autoReceive) {
                    while (true) {
                        try {
                            byte[] buffer;
                            do {
                                do {
                                    bytes = BluetoothPrinter.this.receiveStream.available();
                                } while (bytes <= 0);

                                buffer = new byte[bytes];
                                BluetoothPrinter.this.receiveStream.available();
                                bytes = BluetoothPrinter.this.receiveStream.read(buffer);
                            } while (BluetoothPrinter.this.printerHandler == null);

                            BluetoothPrinter.this.printerHandler.obtainMessage(105, bytes, -1, buffer).sendToTarget();
                        } catch (IOException var6) {
                            if (BluetoothPrinter.this.printerHandler != null) {
                                BluetoothPrinter.this.printerHandler.obtainMessage(104).sendToTarget();
                            }
                            break;
                        }
                    }
                }

            }
        }).start();
    }

    private boolean verifyEncryption() {
        Object tmpData = null;
        boolean receiveLength = false;

        byte[] var10;
        try {
            byte[] mac = new byte[]{(byte) 29, (byte) 31, (byte) 0};
            this.sendStream.write(mac);
            this.sendStream.flush();
            int var11 = this.receiveStream.available();
            int tmpChar = 15;

            while (var11 <= 0 && tmpChar-- > 0) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var8) {
                    var8.printStackTrace();
                }

                var11 = this.receiveStream.available();
                Log.i("BluetoothPrinter", "receiveStream length is : " + var11);
            }

            if (var11 <= 0) {
                return false;
            }

            var10 = new byte[var11];
            this.receiveStream.read(var10);
        } catch (IOException var9) {
            var9.printStackTrace();
            Log.i("BluetoothPrinter", "receive input Stream error!");
            return false;
        }

        String var12 = this.btDevice.getAddress().replaceAll(":", "").toUpperCase();
        char[] var13 = var12.toCharArray();
        int CheckSum = 0;
        Log.i("BluetoothPrinter", "btDevice.getAddress() is : " + this.btDevice.getAddress());

        for (int tmpResult = 0; tmpResult < 12; ++tmpResult) {
            CheckSum += var13[tmpResult] << 8 * (tmpResult % 4);
        }

        Log.i("BluetoothPrinter", "CheckSum is : " + CheckSum);
        CheckSum ^= 1397772884;
        Log.i("BluetoothPrinter", "CheckSum is(after ..) : " + CheckSum);
        byte[] var14 = new byte[4];

        for (int j = 0; j < 4; ++j) {
            var14[j] = (byte) (CheckSum >> (3 - j) * 8);
        }

        Log.i("BluetoothPrinter", "receive tmpData[0] is : " + var10[0] + ", self tmpResult[0] is: " + var14[0]);
        return var10[0] == var14[0];
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int close() {
        this.closeConnection();
        return 0;
    }

    public void closeConnection() {
        try {
            if (this.btSocket != null) {
                this.btSocket.close();
            }

            this.btDevice = null;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        if (this.isConnected) {
            this.isConnected = false;
            if (this.printerHandler != null) {
                this.printerHandler.obtainMessage(103).sendToTarget();
            }
        }

    }

    public byte[] receive() {
        byte[] receiveData = null;

        try {
            int e = this.receiveStream.available();

            for (int testTime = 15; e <= 0 && testTime-- > 0; e = this.receiveStream.available()) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var5) {
                    var5.printStackTrace();
                }
            }

            Log.i("BluetoothPrinter", "receive()-->receiveStream length is : " + e);
            receiveData = new byte[e];
            this.receiveStream.read(receiveData);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return receiveData;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int send(String content) {
        return this.printText(content);
    }

    public int printText(String content) {
        if (content == null) {
            return -1;
        } else {
            try {
                this.sendStream.write(content.getBytes(this.mEncoding != null ? this.mEncoding : "GBK"));
                this.sendStream.flush();
                return 0;
            } catch (Exception var3) {
                var3.printStackTrace();
                return -1;
            }
        }
    }

    public int printImage(String path) {
        if (path != null) {
            this.printByteData(Utils.bitmap2PrinterBytes(BitmapFactory.decodeFile(path), 0));
        }

        return -1;
    }

    public int printImage(String path, int left) {
        if (path != null) {
            this.printByteData(Utils.bitmap2PrinterBytes(BitmapFactory.decodeFile(path), left));
        }

        return -1;
    }

    public int printImage(Bitmap bitmap) {
        return bitmap != null ? this.printByteData(Utils.bitmap2PrinterBytes(bitmap, 0)) : -1;
    }

    public int printImageDot(String path, int multiple, int left) {
        return path != null ? this.printByteData(Utils.bitmap2PrinterBytesDot(BitmapFactory.decodeFile(path), multiple, left)) : -1;
    }

    public int printImageDot(Bitmap bitmap, int multiple, int left) {
        return bitmap != null ? this.printByteData(Utils.bitmap2PrinterBytesDot(bitmap, multiple, left)) : -1;
    }

    public int printImage(Bitmap bitmap, int left) {
        return bitmap != null ? this.printByteData(Utils.bitmap2PrinterBytes(bitmap, left)) : -1;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public int send(byte[] content) {
        return this.printByteData(content);
    }

    public int printByteData(byte[] content) {
        try {
            this.sendStream.write(content);
            this.sendStream.flush();
            return 0;
        } catch (Exception var3) {
            var3.printStackTrace();
            return -1;
        }
    }

    public void init() {
        this.setPrinter(0);
    }

    public boolean setPrinter(int command) {
        byte[] arrayOfByte = null;
        switch (command) {
            case 0:
                arrayOfByte = new byte[]{(byte) 27, (byte) 64};
                break;
            case 1:
                arrayOfByte = new byte[]{(byte) 0};
                break;
            case 2:
                arrayOfByte = new byte[]{(byte) 12};
                break;
            case 3:
                arrayOfByte = new byte[]{(byte) 10};
                break;
            case 4:
                arrayOfByte = new byte[]{(byte) 13};
                break;
            case 5:
                arrayOfByte = new byte[]{(byte) 9};
                break;
            case 6:
                arrayOfByte = new byte[]{(byte) 27, (byte) 50};
        }

        this.printByteData(arrayOfByte);
        return true;
    }

    public boolean setPrinter(int command, int value) {
        byte[] arrayOfByte = new byte[3];
        switch (command) {
            case 0:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 74;
                break;
            case 1:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 100;
                break;
            case 2:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 33;
                break;
            case 3:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 85;
                break;
            case 4:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 86;
                break;
            case 5:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 87;
                break;
            case 6:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 45;
                break;
            case 7:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 43;
                break;
            case 8:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 105;
                break;
            case 9:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 99;
                break;
            case 10:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 51;
                break;
            case 11:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 32;
            case 12:
                arrayOfByte[0] = 28;
                arrayOfByte[1] = 80;
            case 13:
                arrayOfByte[0] = 27;
                arrayOfByte[1] = 97;
                if (value > 2 || value < 0) {
                    return false;
                }
        }

        arrayOfByte[2] = (byte) value;
        this.printByteData(arrayOfByte);
        return true;
    }

    public void setCharacterMultiple(int x, int y) {
        byte[] arrayOfByte = new byte[]{(byte) 29, (byte) 33, (byte) 0};
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            arrayOfByte[2] = (byte) (x * 16 + y);
            this.printByteData(arrayOfByte);
        }

    }

    public  void openCash(){
        byte[] arrayOfByte = new byte[]{27,112,0,50,80};
        Log.d("Cash","dfsadfssdfsadfsd");
        this.printByteData(arrayOfByte);

    }

    public void setLeftMargin(int nL, int nH) {
        byte[] arrayOfByte = new byte[]{(byte) 29, (byte) 76, (byte) nL, (byte) nH};
        this.printByteData(arrayOfByte);
    }

    public void setPrintModel(boolean isBold, boolean isDoubleHeight, boolean isDoubleWidth, boolean isUnderLine) {
        byte[] arrayOfByte = new byte[]{(byte) 27, (byte) 33, (byte) 0};
        int a = 0;
        if (isBold) {
            a += 8;
        }

        if (isDoubleHeight) {
            a += 16;
        }

        if (isDoubleHeight) {
            a += 32;
        }

        if (isDoubleHeight) {
            a += 128;
        }

        arrayOfByte[2] = (byte) a;
        this.printByteData(arrayOfByte);
    }

    public void setBarCode(int param1, int param2, int param3, byte type) {
        if (type != 100 && type != 101 && type != 102) {
            byte[] command = new byte[]{(byte) 29, (byte) 119, (byte) 0};
            if (param1 >= 2 && param1 <= 6) {
                command[2] = (byte) param1;
            } else {
                command[2] = 2;
            }

            this.printByteData(command);
            command[0] = 29;
            command[1] = 104;
            if (param2 >= 1 && param2 <= 255) {
                command[2] = (byte) param2;
            } else {
                command[2] = -94;
            }

            this.printByteData(command);
            command[0] = 29;
            command[1] = 72;
            if (param3 >= 0 && param3 <= 3) {
                command[2] = (byte) param3;
            } else {
                command[2] = 0;
            }

            this.printByteData(command);
        } else {
            this.two_dimensional_param1 = param1;
            this.two_dimensional_param2 = param2;
            this.two_dimensional_param3 = param3;
        }

        this.currentBarCodeType = type;
    }

    public void printBarCode(String content) {
        if (content != null) {
            byte[] realCommand;
            byte[] tempCommand = new byte[1024];
            int index = 0;
            int strLength = content.length();
            int tempLength = strLength;
            byte[] tmpByte = content.getBytes();
            int var17;
            label128:
            switch (this.currentBarCodeType) {
                case 72:
                    realCommand = new byte[strLength + 4];
                    realCommand[0] = 29;
                    realCommand[1] = 107;
                    realCommand[2] = this.currentBarCodeType;
                    realCommand[3] = (byte) strLength;
                    var17 = 0;

                    while (true) {
                        if (var17 >= strLength) {
                            break label128;
                        }

                        realCommand[4 + var17] = tmpByte[var17];
                        ++var17;
                    }
                case 73:
                    char[] var16 = content.toCharArray();
                    boolean var18 = false;
                    boolean j = false;
                    boolean preHasCodeC = false;
                    boolean needCodeC = false;

                    int i;
                    for (i = 0; i < strLength; ++i) {
                        byte a = (byte) var16[i];
                        if (a >= 0 && a <= 31) {
                            if (i == 0 || !var18) {
                                tempCommand[index++] = 123;
                                tempCommand[index++] = 65;
                                var18 = true;
                                j = false;
                                preHasCodeC = false;
                                tempLength += 2;
                            }

                            tempCommand[index++] = a;
                        } else {
                            if (a >= 48 && a <= 57) {
                                if (!preHasCodeC) {
                                    for (int b = 1; b < 9; ++b) {
                                        if (i + b == strLength || !Utils.isNum((byte) var16[i + b])) {
                                            needCodeC = false;
                                            break;
                                        }

                                        if (b == 8) {
                                            needCodeC = true;
                                        }
                                    }
                                }

                                if (needCodeC) {
                                    if (!preHasCodeC) {
                                        tempCommand[index++] = 123;
                                        tempCommand[index++] = 67;
                                        var18 = false;
                                        j = false;
                                        preHasCodeC = true;
                                        tempLength += 2;
                                    }

                                    if (i != strLength - 1) {
                                        byte var22 = (byte) var16[i + 1];
                                        if (Utils.isNum(var22)) {
                                            tempCommand[index++] = (byte) ((a - 48) * 10 + (var22 - 48));
                                            --tempLength;
                                            ++i;
                                            continue;
                                        }
                                    }
                                }
                            }

                            if (!j) {
                                tempCommand[index++] = 123;
                                tempCommand[index++] = 66;
                                var18 = false;
                                j = true;
                                preHasCodeC = false;
                                tempLength += 2;
                            }

                            tempCommand[index++] = a;
                        }
                    }

                    realCommand = new byte[tempLength + 4];
                    realCommand[0] = 29;
                    realCommand[1] = 107;
                    realCommand[2] = this.currentBarCodeType;
                    realCommand[3] = (byte) tempLength;
                    i = 0;

                    while (true) {
                        if (i >= tempLength) {
                            break label128;
                        }

                        realCommand[i + 4] = tempCommand[i];
                        ++i;
                    }
                case 100:
                case 101:
                case 102:
                    String[] sb = content.split(",");
                    realCommand = new byte[sb.length + 10];
                    realCommand[0] = 29;
                    realCommand[1] = 90;
                    realCommand[2] = (byte) (this.currentBarCodeType - 100);
                    realCommand[3] = 27;
                    realCommand[4] = 90;
                    realCommand[5] = (byte) this.two_dimensional_param1;
                    realCommand[6] = (byte) this.two_dimensional_param2;
                    realCommand[7] = (byte) this.two_dimensional_param3;
                    realCommand[8] = (byte) (sb.length % 256);
                    realCommand[9] = (byte) (sb.length / 256);
                    int temp = 0;

                    while (true) {
                        if (temp >= sb.length) {
                            break label128;
                        }

                        realCommand[10 + temp] = (byte) Integer.parseInt(sb[temp]);
                        Log.i("BluetoothPrinter", sb[temp]);
                        ++temp;
                    }
                default:
                    realCommand = new byte[strLength + 4];
                    realCommand[0] = 29;
                    realCommand[1] = 107;
                    realCommand[2] = this.currentBarCodeType;

                    for (var17 = 0; var17 < content.length(); ++var17) {
                        realCommand[3 + var17] = tmpByte[var17];
                    }

                    realCommand[strLength + 3] = 0;
            }

            StringBuffer var19 = new StringBuffer();

            for (int var21 = 0; var21 < realCommand.length; ++var21) {
                String var20 = Integer.toHexString(realCommand[var21] & 255);
                if (var20.length() == 1) {
                    var20 = "0" + var20;
                }

                var19.append(var20 + " ");
            }

            Log.i("BluetoothPrinter", "bar code command is : " + var19.toString());
            this.printByteData(realCommand);
        }
    }

    public void setTitle(String title, String subTitle, Bitmap logo) {
        this.companyTitle = title;
        this.companySubTitle = subTitle;
        if (logo != null) {
            if (logo.getWidth() > 200) {
                this.companyLogo = Utils.compressBitmap(logo, 100);
            } else {
                this.companyLogo = logo;
            }
        } else {
            this.companyLogo = null;
        }

    }

    public void printTitle() {
        if (this.companyTitle != null || this.companySubTitle != null || this.companyLogo != null) {
            PrintGraphics pg = new PrintGraphics();
            pg.init(this.getCurrentPrintType());
            boolean titleTextSize = false;
            float titlePX = 0.0F;
            if (this.companyTitle != null) {
                int var5 = 40;

                for (titlePX = (float) this.companyTitle.length() * pg.getCharacterWidth(var5); titlePX > (float) (pg.getWidth() - (this.companyLogo != null ? this.companyLogo.getWidth() : 0)); titlePX = (float) this.companyTitle.length() * pg.getCharacterWidth(var5--)) {
                }

                pg.setTextSize(var5);
            }

            if (this.companyLogo != null) {
                Log.i("BluetoothPrinter", "companyLogo.getWidth() is :" + this.companyLogo.getWidth() + "companyLogo.getHeight() is : " + this.companyLogo.getHeight());
                if (this.companyTitle != null) {
                    pg.drawImage(((float) (pg.getWidth() - this.companyLogo.getWidth()) - titlePX) / 2.0F, 10.0F, this.companyLogo);
                    pg.drawText(((float) (pg.getWidth() - this.companyLogo.getWidth()) - titlePX) / 2.0F + (float) this.companyLogo.getWidth() + 5.0F, (float) (this.companyLogo.getHeight() + 5), this.companyTitle);
                } else {
                    pg.drawImage((float) ((pg.getWidth() - this.companyLogo.getWidth()) / 2), 0.0F, this.companyLogo);
                }
            } else if (this.companyTitle != null) {
                pg.drawText(((float) pg.getWidth() - titlePX) / 2.0F, 40.0F, this.companyTitle);
            }

            if (this.companySubTitle != null) {
                int subTitleSize = 25;

                for (titlePX = (float) this.companySubTitle.length() * pg.getCharacterWidth(subTitleSize); titlePX > (float) pg.getWidth(); titlePX = (float) this.companySubTitle.length() * pg.getCharacterWidth(subTitleSize--)) {
                }

                pg.setTextSize(subTitleSize);
                pg.drawText(((float) pg.getWidth() - titlePX) / 2.0F, (float) (this.companyLogo != null ? this.companyLogo.getHeight() + 35 : 75), this.companySubTitle);
            }

            this.printImage(pg.getCanvasImage());
        }
    }

    public void printTable(Table table) {
        if (table != null) {
            this.tableReg = table.getRegularExpression();
            this.tableData = table.getTableData();
            this.tableColWidth = table.getColumnWidth();
            if (table.hasSeparator()) {
                if (this.currentPrintType == PrinterType.T9) {
                    this.printText(this.separator_T9);
                } else {
                    this.printText(this.separator_T3);
                }
            }

            for (int m = 0; m < this.tableData.size(); ++m) {
                this.tableLine = this.tableData.get(m).split(this.tableReg);
                this.unPrintColumnMap = null;
                this.printTableLine(this.tableLine);
            }

            if (table.hasSeparator()) {
                if (this.currentPrintType == PrinterType.T9) {
                    this.printText(this.separator_T9);
                } else {
                    this.printText(this.separator_T3);
                }
            }

        }
    }

    private void printTableLine(String[] tableLine) {
        StringBuffer sb = new StringBuffer();
        boolean sub_length = false;
        String[] line = tableLine;

        for (int i = 0; i < line.length; ++i) {
            line[i] = line[i].trim();
            int index = line[i].indexOf("\n");
            if (index != -1) {
                if (this.unPrintColumnMap == null) {
                    this.unPrintColumnMap = new HashMap();
                }

                this.unPrintColumnMap.put(Integer.valueOf(i), line[i].substring(index + 1));
                line[i] = line[i].substring(0, index);
                this.printTableLine(line);
                this.printNewLine(line);
                return;
            }

            int length = Utils.getStringCharacterLength(line[i]);
            int col_length = this.tableColWidth.length;
            int col_width = 8;
            if (i < col_length) {
                col_width = this.tableColWidth[i];
            }

            if (length > col_width) {
                int var11 = Utils.getSubLength(line[i], col_width);
                if (this.unPrintColumnMap == null) {
                    this.unPrintColumnMap = new HashMap();
                }

                this.unPrintColumnMap.put(Integer.valueOf(i), line[i].substring(var11, line[i].length()));
                line[i] = line[i].substring(0, var11);
                this.printTableLine(line);
                this.printNewLine(line);
                return;
            }

            int j;
            if (i == 0) {
                sb.append(line[i]);

                for (j = 0; j < col_width - length; ++j) {
                    sb.append(" ");
                }
            } else {
                for (j = 0; j < col_width - length; ++j) {
                    sb.append(" ");
                }

                sb.append(line[i]);
            }
        }

        this.printText(sb.toString() + "\n");
    }

    private void printNewLine(String[] oldLine) {
        if (this.unPrintColumnMap != null && this.unPrintColumnMap.size() > 0) {
            String[] newLine = new String[oldLine.length];
            Iterator iterator = this.unPrintColumnMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry i = (Entry) iterator.next();
                Integer key = (Integer) i.getKey();
                String value = (String) i.getValue();
                if (key.intValue() < oldLine.length) {
                    newLine[key.intValue()] = value;
                }
            }

            this.unPrintColumnMap = null;

            for (int var7 = 0; var7 < newLine.length; ++var7) {
                if (newLine[var7] == null || newLine[var7] == "") {
                    newLine[var7] = " ";
                }
            }

            this.printTableLine(newLine);
        }

    }
}

