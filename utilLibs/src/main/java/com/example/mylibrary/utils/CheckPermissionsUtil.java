package com.example.mylibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2016/7/6.
 */
public class CheckPermissionsUtil {
    private static CheckPermissionsUtil checkPermissionsUtil;
    private Context context;

    private CheckPermissionsUtil(Context context) {
        this.context=context.getApplicationContext();
    }
    public static CheckPermissionsUtil instance(Context context){
        if(null==checkPermissionsUtil){
            checkPermissionsUtil=new CheckPermissionsUtil(context);
        }
        return checkPermissionsUtil;
    }

    /**
     * 判断是否缺少权限
     * @param permission
     * @return
     */
    public boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_DENIED;
    }

    /**
     *判断权限集合
     * @param permissions
     * @return
     */
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }
}
