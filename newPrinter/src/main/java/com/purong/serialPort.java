package com.purong;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import android.util.Log;


public class serialPort {
    private int fd = -1, count = 0;

    private String strPort = "/dev/ttyACM2";
    public int CMD = 0;
    public int STRING = 1;

    private String pageWidth="small";//默认是58mm
    private String brand="PD";//默认是湃达打印机

    public String getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(String pageWidth) {
        this.pageWidth = pageWidth;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean Open(String port, int intBaud) {
        strPort = port;
        File file = new File(strPort);
        if (!file.exists()) {
            return false;
        }

        fd = openSerialPort(strPort, intBaud);
        if (fd < 0) {
            return false;
        } else {
            return true;
        }
    }


    public boolean Close() {
        closeSerialPort(fd);
        return true;
    }

    public int Write(String str, int flag) {
        int res = 0;

        if (flag == -2) {
            res = serailWrite(str, fd, -2, 0);
            return res;
        }


        if (flag == CMD) {
            Log.d("gaohua", "2-------------------------");
            res = serailWrite(str, fd, -1, str.length());
            return res;
        }


        if (str.length() != str.getBytes().length) {
            Log.d("gaohua", "chinese--------------------");
            res = serailWrite(str, fd, 0, 0);
            return res;
        }

        Log.d("gaohua", "3-------------------------");

        int len = str.getBytes().length;
        res = serailWrite(str, fd, 1, len);
        return res;
    }


    public native int serailWrite(String buf, int fd, int flag, int size);

    public native int serialRead(int fd);

    public native int openSerialPort(String serialPort, int baud);

    public native boolean closeSerialPort(int fd);

    static {
        System.loadLibrary("mySerial");
    }

    public void init() {
        exeShell("chmod 666 /dev");
        exeShell("chmod 666 /dev/ttyUSB1");
    }

    public void exeShell(String cmd) {

        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            p.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                Log.i("exeShell", line);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
