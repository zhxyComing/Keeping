package com.dixon.bookkeeping.base;

import android.app.Application;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.util.List;

public class BaseApplication extends Application implements AppStateTracker.AppStateChangeListener {

    private static BaseApplication sApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        AppStateTracker.track(this, this);
    }

    protected boolean isFirstIn() {
        SharedPreferences shared = getSharedPreferences("is_first_in", MODE_PRIVATE);
        boolean isFirstIn = shared.getBoolean("first_in", true);
        SharedPreferences.Editor editor = shared.edit();
        if (isFirstIn) {
            //第一次进入跳转
            editor.putBoolean("first_in", false);
            editor.apply();
        }
        return isFirstIn;
    }

    public static BaseApplication getApplication() {
        return sApplication;
    }

    @Override
    public void appTurnIntoForeground() {
        List<WeakReference<AppStateTracker.AppStateChangeListener>> list = AppStateRegister.getRegisterTrackers();
        for (WeakReference<AppStateTracker.AppStateChangeListener> reference : list) {
            AppStateTracker.AppStateChangeListener listener = reference.get();
            if (listener != null) {
                listener.appTurnIntoForeground();
            }
        }
    }

    @Override
    public void appTurnIntoBackGround() {
        List<WeakReference<AppStateTracker.AppStateChangeListener>> list = AppStateRegister.getRegisterTrackers();
        for (WeakReference<AppStateTracker.AppStateChangeListener> reference : list) {
            AppStateTracker.AppStateChangeListener listener = reference.get();
            if (listener != null) {
                listener.appTurnIntoBackGround();
            }
        }
    }
}
