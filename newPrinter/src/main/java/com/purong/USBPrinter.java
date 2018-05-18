package com.purong;

/**
 * Created by Administrator on 2017-05-12.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.os.Handler;
import android.util.Log;

import com.printer.sdk.api.PrinterType;
import com.printer.sdk.api.Table;
import com.printer.sdk.api.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class USBPrinter {
    private Handler mHandler;
    private UsbManager mUsbManager;
    private UsbDevice mUsbDevice;
    UsbDeviceConnection connection = null;
    UsbInterface usbInterface = null;
    UsbEndpoint inEndpoint = null;
    UsbEndpoint outEndpoint = null;
    private boolean isOldUSB = false;
    private static final String TAG = "USBPrinter";
    public static final int Handler_Get_Device_List_Found = 100;
    public static final int Handler_Get_Device_List_Completed = 101;
    public static final int Handler_Get_Device_List_No_Device = 102;
    public static final int Handler_Get_Device_List_Error = 103;
    public static final int Handler_New_Device_Found = 104;
    public static final int Handler_Data_Read = 105;
    public static final int Handler_Connect_Success = 106;
    public static final int Handler_Connect_Failed = 107;
    private String companyTitle;
    private String companySubTitle;
    private Bitmap companyLogo;
    private List<String> tableData;
    private String tableReg;
    private String[] tableLine;
    private String mEncoding;
    private String printerName;
    private boolean autoReceive = false;
    private boolean isConnected = false;
    private boolean hasFoundDevice = false;
    private String SDK_Vesion = "1.0";
    private PrinterType currentPrintType;
    private int two_dimensional_param1;
    private int two_dimensional_param2;
    private int two_dimensional_param3;
    private HashMap<Integer, String> unPrintColumnMap;
    private int[] tableColWidth;
    private byte currentBarCodeType;
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

    public USBPrinter(Context mContext) {
        this.currentPrintType = PrinterType.T9;
        this.printerCompanyName = "company";
        this.separator_T9 = "==============================================\n";
        this.separator_T3 = "==============================\n";
        this.mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public void setEncoding(String mEncoding) {
        this.mEncoding = mEncoding;
    }

    public void setAutoReceiveData(boolean auto, Handler handler) {
        this.mHandler = handler;
        this.autoReceive = auto;
    }

    public byte getCurrentBarCodeType() {
        return this.currentBarCodeType;
    }

    public void setCurrentBarCodeType(byte currentBarCodeType) {
        this.currentBarCodeType = currentBarCodeType;
    }

    public void setCurrentPrintType(PrinterType currentPrintType) {
        this.currentPrintType = currentPrintType;
    }

    public PrinterType getCurrentPrintType() {
        return this.currentPrintType;
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

    public String getPrinterName() {
        return this.printerName;
    }

    public void getDeviceList(Handler handler) {
        this.mHandler = handler;
        (new DeviceListThread()).start();
    }

    public boolean openConnection(UsbDevice device) {
        this.isConnected = false;
        this.mUsbDevice = device;
        this.isOldUSB = 1659 == device.getVendorId() && 8965 == device.getProductId();
        this.printerName = device.getDeviceName();

        try {
            if (this.mUsbManager.hasPermission(this.mUsbDevice)) {
                this.usbInterface = device.getInterface(0);

                for (int e = 0; e < this.usbInterface.getEndpointCount(); ++e) {
                    UsbEndpoint ep = this.usbInterface.getEndpoint(e);
                    if (ep.getType() == 2) {
                        if (ep.getDirection() == 0) {
                            this.outEndpoint = ep;
                        } else {
                            this.inEndpoint = ep;
                        }
                    }
                }

                this.connection = this.mUsbManager.openDevice(this.mUsbDevice);
                if (this.connection != null) {
                    this.isConnected = this.connection.claimInterface(this.usbInterface, true);
                }
            }

            if (this.isConnected && this.autoReceive && !this.isOldUSB) {
                (new readThread()).start();
            }
        } catch (Exception var4) {
            this.isConnected = false;
        }

        return this.isConnected;
    }

    public void closeConnection() {
        if (this.isConnected && this.connection != null) {
            this.connection.releaseInterface(this.usbInterface);
            this.connection.close();
            this.connection = null;
            this.isConnected = false;
        }

    }

    public UsbDevice getCurrentDevice() {
        return this.mUsbDevice;
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
                byte[] e = content.getBytes(this.mEncoding != null ? this.mEncoding : "GBK");
                return this.connection.bulkTransfer(this.outEndpoint, e, e.length, 3000);
            } catch (UnsupportedEncodingException var3) {
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

    public int printByteData(byte[] content, int timeout) {
        return this.connection.bulkTransfer(this.outEndpoint, content, content.length, timeout);
    }

    public int printByteData(byte[] content) {
        return this.connection.bulkTransfer(this.outEndpoint, content, content.length, 3000);
    }

    public void init() {
        this.setPrinter(0);
    }

    public boolean setPrinter(int command) {
        byte[] arrayOfByte = null;
        switch (command) {
            case 0:
                arrayOfByte = new byte[]{27, 64};
                break;
            case 1:
                arrayOfByte = new byte[]{0};
                break;
            case 2:
                arrayOfByte = new byte[]{12};
                break;
            case 3:
                arrayOfByte = new byte[]{10};
                break;
            case 4:
                arrayOfByte = new byte[]{13};
                break;
            case 5:
                arrayOfByte = new byte[]{9};
                break;
            case 6:
                arrayOfByte = new byte[]{27, 50};
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
        byte[] arrayOfByte = new byte[]{29, 33, 0};
        if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
            arrayOfByte[2] = (byte) (x * 16 + y);
            this.printByteData(arrayOfByte);
        }

    }

    public void openCash() {
        byte[] arrayOfByte = new byte[]{27, 112, 0, 50, 80};
        this.printByteData(arrayOfByte);

    }

    public void cutHalfPaper() {
        if (1305 == mUsbDevice.getVendorId() && 8211 == mUsbDevice.getProductId()) {
            byte[] arrayOfByte = new byte[]{27, 109};
            this.printByteData(arrayOfByte);
        }
    }

    public void cutTotalPaper() {
        if (1305 == mUsbDevice.getVendorId() && 8211 == mUsbDevice.getProductId()) {
            byte[] arrayOfByte = new byte[]{27, 105};
            this.printByteData(arrayOfByte);
        }
    }

    public void setLeftMargin(int nL, int nH) {
        byte[] arrayOfByte = new byte[]{29, 76, (byte) nL, (byte) nH};
        this.printByteData(arrayOfByte);
    }

    public void setPrintModel(boolean isBold, boolean isDoubleHeight, boolean isDoubleWidth, boolean isUnderLine) {
        byte[] arrayOfByte = new byte[]{27, 33, 0};
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
            byte[] command = new byte[]{29, 119, 0};
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
            byte[] realCommand = Utils.getBarCodeText(this.currentBarCodeType, this.currentPrintType, content, this.two_dimensional_param1, this.two_dimensional_param2, this.two_dimensional_param3);
            StringBuffer sb = new StringBuffer();

            for (int j = 0; j < realCommand.length; ++j) {
                String temp = Integer.toHexString(realCommand[j] & 255);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }

                sb.append(temp + " ");
            }

            Log.i("USBPrinter", "bar code command is : " + sb.toString());
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
            this.printImage(Utils.getTitleImage(this.currentPrintType, this.companyTitle, this.companyLogo, this.companySubTitle));
        }
    }

    public void printTable(Table table) {
        if (table != null) {
            this.printText(Utils.getTableText(table, this.currentPrintType));
        }
    }

    class DeviceListThread extends Thread {
        DeviceListThread() {
        }

        public void run() {
            super.run();

            try {
                Iterator var2 = USBPrinter.this.mUsbManager.getDeviceList().values().iterator();

                while (var2.hasNext()) {
                    UsbDevice e = (UsbDevice) var2.next();
                    Log.d("USBPrinter", "vid:" + e.getVendorId() + "   pid:" + e.getProductId() + "   " + e.getDeviceName());
//                    if ((1193 == e.getVendorId() && 22352 == e.getProductId())
//                            ||(5455 == e.getVendorId() && 5455 == e.getProductId())
//                            ||(26728 == e.getVendorId() && 512 == e.getProductId())) {
//                        if (USBPrinter.this.mHandler != null) {
//                            USBPrinter.this.mHandler.obtainMessage(100, e).sendToTarget();
//                        }
//
//                        USBPrinter.this.hasFoundDevice = true;
//                    }
                    if (1193 == e.getVendorId()
                            || 5455 == e.getVendorId()
                            || 34918 == e.getVendorId()
                            || 1137 == e.getVendorId()
                            || 6790 == e.getVendorId()
                            || 1305 == e.getVendorId()
                            || 26728 == e.getVendorId()) {
                        if (USBPrinter.this.mHandler != null) {
                            USBPrinter.this.mHandler.obtainMessage(100, e).sendToTarget();
                        }

                        USBPrinter.this.hasFoundDevice = true;
                    }
                }

                if (USBPrinter.this.mHandler != null) {
                    USBPrinter.this.mHandler.obtainMessage(USBPrinter.this.hasFoundDevice ? 101 : 102).sendToTarget();
                }
            } catch (Exception var3) {
                var3.printStackTrace();
                if (USBPrinter.this.mHandler != null) {
                    USBPrinter.this.mHandler.obtainMessage(103, var3).sendToTarget();
                }
            }

        }
    }

    class readThread extends Thread {
        readThread() {
        }

        public void run() {
            byte[] retData = null;
            int inMax = USBPrinter.this.inEndpoint.getMaxPacketSize();
            ByteBuffer byteBuffer = ByteBuffer.allocate(inMax);
            UsbRequest usbRequest = new UsbRequest();
            usbRequest.initialize(USBPrinter.this.connection, USBPrinter.this.inEndpoint);
            usbRequest.queue(byteBuffer, inMax);
            if (USBPrinter.this.connection.requestWait() == usbRequest) {
                retData = byteBuffer.array();
            }

            if (USBPrinter.this.mHandler != null && retData != null) {
                USBPrinter.this.mHandler.obtainMessage(105, retData.length, retData.length, retData).sendToTarget();
            }

        }
    }

    public static void openCash(USBPrinter mPrinter) {
        mPrinter.init();
        byte[] arrayOfByte = new byte[]{27, 115, (byte) 0, (byte) 50, (byte) 80};
        Log.d("Cash", "dfsadfssdfsadfsd");
        mPrinter.printByteData(arrayOfByte);

    }
}
