package com.example.mylibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/7/1.
 */
public class BitmapUtil {
    /**
     * 缩放Bitmap图片
     * 尺寸压缩
     **/
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 通过降低图片的质量来压缩图片
     *
     * @param bitmap  要压缩的图片位图对象
     * @param maxSize 压缩后图片大小的最大值,单位KB
     * @return 压缩后的图片位图对象
     */
    public static byte[] compressBitmap(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 50;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        Log.e("BitmapUtil", "图片压缩前大小：" + baos.toByteArray().length + "byte");
        while (baos.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            Log.e("BitmapUtil", "质量压缩到原来的" + quality + "%时大小为："
                    + baos.toByteArray().length + "byte");
        }
        bitmap.recycle();
        bitmap = null;
        return baos.toByteArray();
    }

    public static Bitmap zoomBitmap(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();
        try {
//        options.inJustDecodeBounds=true;
//        Bitmap bitmap=BitmapFactory.decodeFile(path);
            float x = bitmap.getWidth() / 480f;
            float y = bitmap.getHeight() / 720f;
            if (x > 1 && y > 1) {
                if (x > y) {
                    options.inSampleSize = Math.round(y);
                } else {
                    options.inSampleSize = Math.round(x);
                }
                options.inJustDecodeBounds = false;
                bitmap = null;
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } else {
                return bitmap;
            }
        } catch (Exception e) {
            Log.e("图片压缩错误:", e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    data = null;
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
