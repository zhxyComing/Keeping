package com.dixon.bookkeeping.manager;

import android.app.Application;

import com.dixon.bookkeeping.db.DaoMaster;
import com.dixon.bookkeeping.db.DaoSession;

import org.greenrobot.greendao.database.Database;

public class Dao {

    private static DaoSession mSession;

    public static void init(Application context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "keeping_detail");
        Database db = helper.getWritableDb();
        DaoMaster master = new DaoMaster(db);
        mSession = master.newSession();
    }

    public static DaoSession getDaoSession() {
        return mSession;
    }
}
