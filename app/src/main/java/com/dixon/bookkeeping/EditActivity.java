package com.dixon.bookkeeping;

import androidx.annotation.NonNull;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dixon.bookkeeping.base.BaseActivity;
import com.dixon.bookkeeping.util.BackupUtil;
import com.dixon.bookkeeping.util.DialogUtil;
import com.dixon.bookkeeping.util.ToastUtil;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

public class EditActivity extends BaseActivity implements View.OnClickListener {

    private static final int BACKUP_IN = 0x1;
    private static final int BACKUP_OUT = 0x2;

    private LinearLayout mBackupIn, mBackupOut;
    private TextView mBackupHave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();
    }

    private void initView() {
        mBackupIn.setOnClickListener(this);
        mBackupOut.setOnClickListener(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mBackupIn = findViewById(R.id.llBackupIn);
        mBackupOut = findViewById(R.id.llBackupOut);
        mBackupHave = findViewById(R.id.tvBackupHave);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llBackupIn:
                if (askReadWritePermission(BACKUP_IN)) {
                    backupIn();
                }
                break;
            case R.id.llBackupOut:
                if (askReadWritePermission(BACKUP_OUT)) {
                    backupOut();
                }
                break;
        }
    }

    private boolean canBackup() {
        return BackupUtil.isBackupExists();
    }

    private void backupIn() {
        if (canBackup()) {
            showBackupInDialog();
        } else {
            ToastUtil.show("本地未找到可导入数据！");
        }
    }

    private void backupOut() {
        if (canBackup()) {
            showBackupOutDialog();
        } else {
            doBackupOut();
        }
    }

    private void showBackupInDialog() {
        DialogUtil.showAskDialog("警告",
                "是否导入本地备份数据？此操作会覆盖现有数据！\n导入后将重启 App。",
                "导入",
                "关闭",
                new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        BackupUtil.loadFromBackup();
                        // 暂时：导入完毕，重启 APP。
                        android.os.Process.killProcess(android.os.Process.myPid());
                        //这种情况会重启俩次 系统会帮忙重启一次 真是见了鬼了
//                    RestartAPPTool.restartAPP(EditActivity.this);
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                });
    }

    private void showBackupOutDialog() {
        DialogUtil.showAskDialog("警告",
                "本地已有备份，导出将覆盖本地备份！",
                "导出",
                "关闭",
                new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        doBackupOut();
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                });
    }

    private void doBackupOut() {
        if (BackupUtil.startBackup()) {
            ToastUtil.show("备份成功");
        } else {
            ToastUtil.show("备份失败");
        }
    }

    private boolean askReadWritePermission(int reason) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //没权限 请求权限 先返回false
                    this.requestPermissions(permissions, reason);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case BACKUP_IN:
                    backupIn();
                    break;
                case BACKUP_OUT:
                    backupOut();
                    break;
            }
        } else {
            ToastUtil.show("权限未获取");
        }
    }
}
