package com.dixon.bookkeeping.util;

import android.widget.Toast;

import com.dixon.bookkeeping.base.BaseApplication;

public class ToastUtil {

    public static void show(String text) {
        Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_SHORT).show();
    }
}
