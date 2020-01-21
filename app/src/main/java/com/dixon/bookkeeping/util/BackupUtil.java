package com.dixon.bookkeeping.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 备份 即把数据库文件拷贝到sd卡根目录keepingcache文件夹下
 */
public class BackupUtil {

    private static final String INSIDE_CACHE_PATH = "data/data/com.dixon.bookkeeping/databases";
    private static final String OUTSIDE_CACHE_PATH = "/storage/emulated/0/keepingcache";

    public static boolean startBackup() {
        File file = new File(INSIDE_CACHE_PATH);
        if (file.exists()) {
            try {
                File dest = new File(OUTSIDE_CACHE_PATH);
                copyFolder(file.getAbsolutePath(), dest.getAbsolutePath());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean loadFromBackup() {
        File src = new File(OUTSIDE_CACHE_PATH);
        if (!src.exists()) {
            return false;
        }
        File dest = new File(INSIDE_CACHE_PATH);
        try {
            copyFolder(src.getAbsolutePath(), dest.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean isBackupExists() {

        return new File(OUTSIDE_CACHE_PATH).exists();
    }

    // 复制文件夹
    public static void copyFolder(String sourceDir, String targetDir)
            throws IOException {
        // 新建目标目录
        (new File(targetDir)).mkdirs();
        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new
                        File(new File(targetDir).getAbsolutePath()
                        + File.separator + file[i].getName());
                copyFileUsingStream(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyFolder(dir1, dir2);
            }
        }
    }


    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }

    private static String getSdPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }
}
