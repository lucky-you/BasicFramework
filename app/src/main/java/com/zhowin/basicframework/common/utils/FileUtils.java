package com.zhowin.basicframework.common.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FileUtils {

    private static String dirName;

    //压缩文件所放的目录
    public static final String COMPRESS_FILE_PATH = ".outImg";
    //下载文件
    public static final String DOWNLAOD_FILE_PATH = "download";
    //缓存文件
    public static final String CACHE_FILE_PATH = ".cache";
    //临时文件
    public static final String TEMP_FILE_PATH = "temp";
    //icon
    public static final String ICON_FILE_PATH = ".icon";
    //崩溃log
    public static final String LOG_FILE_PATH = ".log";


    public static void init(String dirName) {
        FileUtils.dirName = dirName;
    }

    public static String getDirFilePath(String path) {
        if (TextUtils.isEmpty(dirName)) {
            throw new RuntimeException("directory name is not set,please call init()");
        } else {
            return Environment.getExternalStorageDirectory() + File.separator + dirName + File.separator + path;
        }
    }

    public static String getStringDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
    }
    /**
     * 图片裁剪
     *
     * @return
     */
    public static File getCropFile() {
        File mTempDir = new File(getDirFilePath(TEMP_FILE_PATH));
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
        String fileName = "temp_" + System.currentTimeMillis() + ".jpg";
        File cropFile = new File(mTempDir, fileName);
        return cropFile;
    }
    /**
     * 图片压缩后的文件
     *
     * @return
     */
    public static File getImgFile() {
        File file = new File(getDirFilePath(COMPRESS_FILE_PATH), getStringDate() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * 图片裁剪
     *
     * @return
     */
    public static File getTempFile() {
        File mTempDir = new File(getDirFilePath(TEMP_FILE_PATH));
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
        String fileName = "temp_" + getStringDate() + ".jpg";
        return new File(mTempDir, fileName);
    }

    /**
     * log文件
     *
     * @return
     */
    public static File getLogFile() {
        File mTempDir = new File(getDirFilePath(LOG_FILE_PATH));
        if (!mTempDir.exists()) {
            mTempDir.mkdirs();
        }
        return new File(mTempDir, getStringDate() + ".log");
    }

    /**
     * 使用文件通道的方式复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */
    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        if (size < 0) {
            size = 0;
        }
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }

    }
}
