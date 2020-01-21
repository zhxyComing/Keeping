package com.dixon.bookkeeping.util;

import android.text.InputType;
import android.view.View;

import com.dixon.bookkeeping.IGetActivity;
import com.dixon.bookkeeping.R;
import com.dixon.bookkeeping.base.BaseApplication;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.MDEditDialog;
import com.wevey.selector.dialog.MDSelectionDialog;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.List;

public class DialogUtil {

    public static void showDescDialog(String title, String desc) {
        new NormalAlertDialog.Builder(((IGetActivity) BaseApplication.getApplication()).getTopActivity())
                .setHeight(0.23f)  //屏幕高度*0.23
                .setWidth(0.65f)  //屏幕宽度*0.65
                .setTitleVisible(true).setTitleText(title)
                .setTitleTextColor(R.color.colorTextContentBlueLight)
                .setContentText(desc)
                .setContentTextColor(R.color.colorTextTitleBlack)
                .setTitleTextSize(18)
                .setContentTextSize(16)
                .setSingleMode(true).setSingleButtonText("关闭")
                .setSingleButtonTextColor(R.color.colorSymbolRed)
                .setCanceledOnTouchOutside(true)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    public static void showAskDialog(String title,
                                     String content,
                                     String leftBtnDesc,
                                     String rightBtnDesc,
                                     DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog> listener) {
        new NormalAlertDialog.Builder(((IGetActivity) BaseApplication.getApplication()).getTopActivity())
                .setTitleVisible(false)
                .setTitleText(title)
                .setTitleTextColor(R.color.black_light)
                .setTitleTextSize(20)
                .setContentText(content)
                .setContentTextSize(18)
                .setButtonTextSize(14)
                .setContentTextColor(R.color.colorTextContentBlack)
                .setLeftButtonText(leftBtnDesc)
                .setLeftButtonTextColor(R.color.colorSymbolRed)
                .setRightButtonText(rightBtnDesc)
                .setRightButtonTextColor(R.color.black_light)
                .setOnclickListener(listener)
                .build()
                .show();
    }

    public static void showEditDialog(String title,
                                      String content,
                                      String leftBtnDesc,
                                      String rightBtnDesc,
                                      DialogInterface.OnLeftAndRightClickListener<MDEditDialog> listener) {
        new MDEditDialog.Builder(((IGetActivity) BaseApplication.getApplication()).getTopActivity())
                .setTitleVisible(true)
                .setTitleText(title)
                .setTitleTextSize(20)
                .setTitleTextColor(R.color.black_light)
                .setContentText(content)
                .setContentTextSize(18)
                .setMaxLength(14)
                .setHintText("14位字符")
                .setMaxLines(1)
                .setContentTextColor(R.color.colorTextContentBlack)
                .setButtonTextSize(14)
                .setLeftButtonTextColor(R.color.colorTextContentGray)
                .setLeftButtonText(leftBtnDesc)
                .setRightButtonTextColor(R.color.colorTextContentBlack)
                .setRightButtonText(rightBtnDesc)
                .setLineColor(R.color.colorTextContentGray)
                .setInputTpye(InputType.TYPE_CLASS_TEXT)
                .setOnclickListener(listener)
                .setMinHeight(0.3f)
                .setWidth(0.8f)
                .build()
                .show();
    }

    public static void showListSelectDialog(List<String> data,
                                            DialogInterface.OnItemClickListener<MDSelectionDialog> listener) {
        new MDSelectionDialog.Builder(((IGetActivity) BaseApplication.getApplication()).getTopActivity())
                .setCanceledOnTouchOutside(true)
                .setItemTextColor(R.color.black_light)
                .setItemHeight(50)
                .setItemWidth(0.8f)  //屏幕宽度*0.8
                .setItemTextSize(16)
                .setCanceledOnTouchOutside(true)
                .setOnItemListener(listener)
                .build()
                .setDatas(data)
                .show();
    }
}
