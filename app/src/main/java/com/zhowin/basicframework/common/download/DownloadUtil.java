package com.zhowin.basicframework.common.download;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.zhowin.basicframework.R;
import com.zhowin.basicframework.common.base.ApiService;
import com.zhowin.basicframework.common.base.BaseApplication;
import com.zhowin.basicframework.common.retrofit.RetrofitFactory;
import com.zhowin.basicframework.common.utils.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 下载的工具类
 */
public class DownloadUtil {

    private static final String TAG = "xy";
    public static final String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/" + BaseApplication.getInstance().getString(R.string.app_name);
    //视频下载相关
    protected ApiService mApiService;
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mVideoPath; //下载到本地的视频路径


    public DownloadUtil() {
        if (mApiService == null) {
            mApiService = RetrofitFactory.getInstance();
        }
    }

    /**
     * 下载文件
     *
     * @param url              链接
     * @param downloadListener 监听回调
     */
    public void downloadFile(String url, final DownloadStatusListener downloadListener) {
        //通过Url得到保存到本地的文件名
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i);
                mVideoPath = PATH_CHALLENGE_VIDEO + name;
            }
        }
        if (TextUtils.isEmpty(mVideoPath)) {
            Log.e(TAG, "downloadVideo: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mVideoPath);
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
            if (mApiService == null) {
                Log.e(TAG, "downloadVideo: 下载接口为空了");
                return;
            }
            mCall = mApiService.downloadFile(url);
            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                    //下载文件放在子线程
                    mThread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //保存到本地
                            writeFileToDisk(response, mFile, downloadListener);
                        }
                    };
                    mThread.start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    downloadListener.onFailure("网络错误!");
                }
            });
        } else {
            //已经存在了，不再重复下载
            downloadListener.onFinish(mVideoPath);
        }
    }

    /**
     * 保存到本地
     */
    private void writeFileToDisk(Response<ResponseBody> response, File file, DownloadStatusListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;
        if (response.body() == null) {
            downloadListener.onFailure("资源错误!");
            return;
        }
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e(TAG, "当前进度:" + currentLength);
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onFinish(mVideoPath);
                }
            }
        } catch (FileNotFoundException e) {
            downloadListener.onFailure("未找到文件!");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onFailure("IO错误!");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public interface DownloadStatusListener {
        void onStart();

        void onProgress(int currentLength);

        void onFinish(String localPath);

        void onFailure(String errorInfo);
    }

}
