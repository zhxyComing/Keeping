package com.dixon.bookkeeping;

import android.app.Activity;
import android.os.Bundle;

import com.dixon.bookkeeping.base.BaseApplication;
import com.dixon.bookkeeping.bean.SpecialTipBean;
import com.dixon.bookkeeping.bean.TotalBean;
import com.dixon.bookkeeping.manager.Dao;
import com.dixon.bookkeeping.manager.SpecialTipDao;
import com.dixon.bookkeeping.manager.TotalDao;

public class KeepingApplication extends BaseApplication implements IGetActivity {

    private static Activity sTopActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        Dao.init(this);
        firstInInit();

        initLifecycleCallback();
    }

    //首次进入数据初始化
    private void firstInInit() {
        if (isFirstIn()) {
            TotalDao.instance().insert(new TotalBean(0, 0));
            SpecialTipDao.instance().insert(new SpecialTipBean(""));
        }
    }

    private void initLifecycleCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                sTopActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                //根据生命周期 A.resume > B.destroy 所以不会导致内存泄漏
                sTopActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    @Override
    public Activity getTopActivity() {
        return sTopActivity;
    }
}