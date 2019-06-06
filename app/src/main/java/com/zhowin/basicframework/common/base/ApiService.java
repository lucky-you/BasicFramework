package com.zhowin.basicframework.common.base;


import com.zhowin.basicframework.BuildConfig;

/**
 * 网络请求
 */
public interface ApiService {


    //baseUrl
//    String API_SERVER_URL = "https://miyue.nacy.cc/";  //-->线下
    String API_SERVER_URL = BuildConfig.API_HOST; //-->线上
    String TOKEN = "token";
    String PARAM = "param";
    String HEADER_URL = "api/v1";

    String ENCRYPTION_PASSWORD = "xiayun2018";//参数加密密码



}
