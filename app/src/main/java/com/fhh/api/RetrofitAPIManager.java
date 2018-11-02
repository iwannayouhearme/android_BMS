package com.fhh.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RetrofitAPIManager {
    private com.fhh.api.ApiService mApiService;
    private String version;
    private static RetrofitAPIManager mInstance;
    private static final int CONN_TIMEOUT = 60;//连接超时时间,单位  秒
    private static final int READ_TIMEOUT = 60;//连接超时时间,单位  秒

    private static OkHttpClient genericClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Accept-Charset", "utf-8")
                                .addHeader("Accept-Language", "zh-CN;q=0.5")
//                                .addHeader("Accept-Encoding", "gzip")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .retryOnConnectionFailure(true)//错误重连
                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        return builder.build();
    }

    public static RetrofitAPIManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitAPIManager.class) {
                mInstance = new RetrofitAPIManager();

            }
        }
        return mInstance;
    }

}
