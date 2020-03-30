package com.dixon.bookkeeping.manager;

import android.app.Application;
import android.util.Log;

import com.dixon.bookkeeping.db.DaoMaster;
import com.dixon.bookkeeping.db.DaoSession;

import org.greenrobot.greendao.database.Database;

public class Dao {

    private static DaoSession mSession;

    public static void init(Application context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "keeping_detail") {
            @Override
            public void onUpgrade(Database db, int oldVersion, int newVersion) {
                //greenDao 升级默认会删除原库 应该如下升级
                //1.bean添加对应属性
                //2.本函数添加升级代码
                //3.build.gradle版本+1
                int currentVersion = oldVersion;
                if (oldVersion == 3) {
                    //修改表结构信息
                    db.execSQL("ALTER TABLE " + "DETAIL_ITEM_BEAN" + " ADD tag VARCHAR DEFAULT '';");
                    currentVersion++;
                }
                if (currentVersion != newVersion) {
                    super.onUpgrade(db, oldVersion, newVersion);
                }
            }
        };
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        mSession = master.newSession();
    }

    public static DaoSession getDaoSession() {
        return mSession;
    }
}
