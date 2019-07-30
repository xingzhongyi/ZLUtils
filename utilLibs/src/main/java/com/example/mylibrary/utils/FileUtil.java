package com.example.mylibrary.utils;

import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mylibrary.application.ApplicationHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 * Created by xingzy on 2016/4/13.
 */
public class FileUtil {
    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 内部存储，私有
     */
    public static String getPrivateFileRootdir() {
        File sd = ApplicationHelper.getInstance().getApplicationContext().getFilesDir();
        return sd.getPath() + "/";
    }

    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 内部存储，私有
     */
    public static String getExternalCacheFileRootdir() {
        File sd = ApplicationHelper.getInstance().getApplicationContext().getExternalCacheDir();
        return sd.getPath() + "/";
    }

    /**
     * 项目文件存储根目录
     *
     * @return 根目录路径 共享存储
     */
    public static String getFileRootdir() {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在

        if (sdCardExist) {
            File sd = Environment.getExternalStorageDirectory();
            path = sd.getPath() + "/" + AppUtil.getPackageName(ApplicationHelper.getInstance().getApplicationContext()) + "/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            return path;
        } else {
            return path;
        }
    }

    /**
     * 下载根目录
     *
     * @return
     */
    public static String getDownLoadDir() {
        String path = null;
        if (null != getFileRootdir()) {
            path = getFileRootdir() + "file/download/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return path;
    }

    /**
     * 日志文件根目录
     *
     * @return
     */
    public static String getLogFileDir() {
        String path = null;
        if (null != getFileRootdir()) {
            path = getFileRootdir() + "logfiles/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return path;
    }

    /**
     * 压缩过的日志文件根目录
     *
     * @return
     */
    public static String getLogZipFileDir() {
        String path = null;
        if (null != getFileRootdir()) {
            path = getFileRootdir() + "logZipfiles/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return path;
    }

    /**
     * 在当前应用根目录下新建文件夹
     *
     * @param name：文件相对路径名
     * @return
     */
    public static String creatFilePath(String name) {
        String path = getFileRootdir();
        if (!StringUtil.isEmpty(name)) {
            path = path + name;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return path;
    }

    /**
     * 获取内部存储大小
     */
    public static void readSystem() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + FormetFileSize(blockSize * blockCount));
        Log.d("", "可用的block数目：:" + availCount + ",可用大小:" + FormetFileSize(availCount * blockSize));
    }

    /**
     * 获取sd卡剩余空间大小
     */
    public static long getSDCardAVailSize() {
        long sdCardSize = 0L;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            Log.d("TAG", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + FormetFileSize(blockSize * blockCount));
            Log.d("TAG", "可用的block数目：:" + availCount + ",剩余空间:" + FormetFileSize(availCount * blockSize));
            sdCardSize = availCount * blockSize;
        }
        return sdCardSize;
    }

    /**
     * 获取sd卡剩余空间大小
     */
    public static long getSDCardSize() {
        long sdCardSize = 0L;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            Log.d("TAG", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + FormetFileSize(blockSize * blockCount));
            Log.d("TAG", "可用的block数目：:" + availCount + ",剩余空间:" + FormetFileSize(availCount * blockSize));
            sdCardSize = blockCount * blockSize;
        }
        return sdCardSize;
    }

    /**
     * 获得目录下文件的大小
     *
     * @param fileDir 文件目录
     * @return 文件的大小
     */
    public static long getFileSize(File fileDir) {
        long size = 0;
        File flist[] = fileDir.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /***
     * 转换文件大小单位(b/kb/mb/gb)
     ***/
    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.0");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        if (fileSizeString.startsWith(".")) {
            fileSizeString = "0" + fileSizeString;
        }
        return fileSizeString;
    }

    /**
     * 判断文件是否存在
     *
     * @param s：文件的绝对路径
     * @return
     */
    public static boolean isExist(String s) {
        File file = new File(s);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath：文件的绝对路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f.getPath());
            }
            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 文件排序
     *
     * @param files
     * @param type：1：文件名，2：修改时间，3：大小
     * @return
     */
    public static File[] sort(File[] files, SORT_TYPE type, ORDER order) {
        switch (type) {
            case FILE_NAME:
                files = sort_name(files, order);
                break;
            case FILE_SIZE:
                files = sort_size(files, order);
                break;
            case lastModified:
                files = sort_time(files, order);
                break;
        }
        return files;
    }

    /**
     * 保存日志信息到本地文件
     *
     * @param dic
     * @param fileName
     * @param msg
     * @return
     */
    public static boolean save(String dic, @NonNull String fileName, String msg) {
        File file = new File(dic, fileName);
        try {
            OutputStream outputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            outputStreamWriter.write(msg + "\n\r");
            outputStreamWriter.flush();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public enum SORT_TYPE {
        FILE_NAME,
        lastModified,
        FILE_SIZE;
    }

    //第一种排序方法 比较文件名 自然排序 按照文件名字符的ASCII码表值进行排序
    public static File[] sort_name(File[] ll, ORDER order) {
        //for循环遍历数组ll中所有元素进行排序
        for (int i = 0; i < ll.length; i++) {
            for (int j = i + 1; j < ll.length; j++) {
                //利用comparTo()方法进行比较 返回值int型 有正数 负数 正数表示前者的ASCII值较大 0表示相等，负数表示前者的ASCII值小
                if (ll[i].compareTo(ll[j]) > 0 ==  ORDER.valueOf(order)) {
                    //交换赋值
                    File fe;
                    fe = ll[i];
                    ll[i] = ll[j];
                    ll[j] = fe;
                }
            }
        }
        return ll;
    }

    //第二中排序方法 按照文件大小排序
    public static File[] sort_size(File[] ll, ORDER order) {
        //for循环遍历数组ll中所有元素进行排序
        for (int i = 0; i < ll.length; i++) {
            for (int j = i + 1; j < ll.length; j++) {
                //length()方法 返回文件的长度 也就是文件的大小 返回值是long型
                if ((ll[i].length() > ll[j].length())==  ORDER.valueOf(order)) {
                    //交换赋值
                    File fe;
                    fe = ll[i];
                    ll[i] = ll[j];
                    ll[j] = fe;
                }
            }
        }
        return ll;
    }

    //第三种排序方法 按最后一次修改的时间
    public static File[] sort_time(File[] ll, ORDER order) {
        //for循环遍历数组ll中所有元素进行排序
        for (int i = 0; i < ll.length; i++) {
            for (int j = i + 1; j < ll.length; j++) {
                //lastModified()方法返回文件最后一次被修改的时间 毫秒 返回值是long型
                if (ll[i].lastModified() > ll[j].lastModified() == ORDER.valueOf(order)) {
                    //交换赋值
                    File fe;
                    fe = ll[i];
                    ll[i] = ll[j];
                    ll[j] = fe;
                }
            }
        }
        return ll;
    }

    public enum ORDER {
        ASC,
        DESC;

        static boolean valueOf(ORDER order) {
            switch (order) {
                case ASC:
                    return true;
                case DESC:
                    return false;
            }
            return false;
        }
    }
}
