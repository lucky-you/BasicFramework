package com.zhowin.basicframework.common.api;


import com.google.gson.GsonBuilder;
import com.zhowin.basicframework.BuildConfig;
import com.zhowin.basicframework.common.base.BaseApplication;
import com.zhowin.basicframework.common.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by: Z_B on 2018/8/24.
 * Function: Retrofit请求
 */
public class RetrofitFactory {

    //访问超时
    private static final long TIMEOUT = 30;

    //获得RetrofitService对象
    public synchronized static ApiService getInstance() {
        return retrofitService();
    }

    private static ApiService retrofitService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_SERVER_URL)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()
                ))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initOkHttpClient())
                .build();
        return retrofit.create(ApiService.class);
    }

    /**
     * OkHttpClient
     */
    private static OkHttpClient initOkHttpClient() {
        OkHttpClient mOkHttpClient = null;
        if (mOkHttpClient == null) {
            synchronized (RetrofitFactory.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(
                            new File(BaseApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addNetworkInterceptor(new CacheInterceptor())
//                            .addInterceptor(new EncryptInterceptor())
                            .addInterceptor(LoggerInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 日志拦截器
     */
    private static HttpLoggingInterceptor LoggerInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    /**
     * 添加OkHttp缓存拦截器
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetworkUtils.isConnected()) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }

    public class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody body = request.body();
            String method = request.method();
            if ("POST".equals(method)) {
                Buffer buffer = new Buffer();
                try {
                    request.body().writeTo(buffer);
                    Charset charset = Charset.forName("UTF-8");
                    MediaType contentType = request.body().contentType();
                    if (contentType != null) {
                        charset = contentType.charset(charset);
                    }
                    String params = buffer.readString(charset);
                    System.out.println("request参数: " + params + "\n token:" + request.header("token"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("request  url:" + request.url().toString());
            Response response = chain.proceed(chain.request());
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            System.out.println("request  response:" + content);
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    /**
     * 参数拦截器--》对参数进行加密
     */
//    private static class EncryptInterceptor implements Interceptor {
//
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            RequestBody requestBody = request.body();
//            String password = ApiService.ENCRYPTION_PASSWORD;
//            FormBody.Builder newFormBuilder = new FormBody.Builder();
//            if (requestBody instanceof FormBody) {
//                FormBody formBody = (FormBody) requestBody;
//                for (int i = 0; i < formBody.size(); i++) {
//                    if (TextUtils.equals("param", formBody.name(i))) { //只对param参数做加密
//                        String paramJson = formBody.value(i); //原始的json， 做加密
//                        RNCryptorNative rncryptor = new RNCryptorNative();
//                        String encrypted = new String(rncryptor.encrypt(paramJson, password));
////                        Log.e("xy", "加密前param数据：" + paramJson);
////                        Log.e("xy", "加密后param数据：" + encrypted);
//                        newFormBuilder.add("param", encrypted); //加密之后添加
//                    } else if (TextUtils.equals("token", formBody.name(i))) {//token 不用加密
//                        newFormBuilder.add(formBody.name(i), formBody.value(i));
//                    }
//                }
//            }
//            RequestBody newFormBody = newFormBuilder.build();
//            Request.Builder builder = request.newBuilder();
//            builder.post(newFormBody);
//            return chain.proceed(builder.build());
//        }
//    }
}
