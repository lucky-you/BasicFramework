package com.zhowin.basicframework.common.api;


import com.zhowin.basicframework.BuildConfig;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 网络请求
 */
public interface ApiService {


//    String API_SERVER_URL = "https://miyue.nacy.cc/";  //-->线下
    String API_SERVER_URL = BuildConfig.API_HOST; //-->线上
    String TOKEN = "token";
    String PARAM = "param";
    String HEADER_URL = "api/v1";

    /**
     * 下载文件
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);


}
