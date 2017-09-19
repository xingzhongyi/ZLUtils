package com.example.mylibrary.utils;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.File;

/**
 * Created by Admin on 2016/4/28.
 * xutils中的数据库模块简单封装
 */
public class DBUtils {
    private static DBUtils dbUtils;
    private DbManager dbManager;
    private DBUpdateListener listener;

    private DBUtils() {
    }

    public static DBUtils getInstance() {
        if (null == dbUtils) {
            dbUtils = new DBUtils();
        }
        return dbUtils;
    }

    public DbManager creatDBManger() {
        if (null == dbManager) {
            DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                    // 数据库的名字
                    .setDbName("DB")
                    // 保存到指定路径
                    .setDbDir(new File(FileUtil.getPrivateFileRootdir() + "db"))
                    // 数据库的版本号
                    .setDbVersion(6)
                    // 数据库版本更新监听
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            if (listener != null) {
                                listener.onUpdate(db, oldVersion, newVersion);
                            }
                            LogUtil.e("数据库版本更新了！");
                        }
                    });
            dbManager = x.getDb(daoConfig);
        }
        return dbManager;
    }

    public void setListener(DBUpdateListener listener) {
        this.listener = listener;
    }

    public interface DBUpdateListener {
        void onUpdate(DbManager dbManager, int oldVersion, int newVersion);
    }

}
