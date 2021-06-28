package com.hong.pubu;


import android.util.Log;


import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 网络数据请求工具类
 */

public class RetrofitManager {
    /**定义retrofit*/
    private static Retrofit mRetrofit;
    /**初始化retrofit*/
    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            /**定义网络请求超时或等待时间*/
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.writeTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(15, TimeUnit.SECONDS);
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.addInterceptor(new LoggingInterceptor());
            /**retrofit实体初始胡*/
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }
    /**
     * Log拦截
     */
    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //发送时截取数据
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            Log.e("TAG",String.format("发送请求:%s", request.url()));
            //接收时截取数据
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d("TAG",String.format(Locale.CHINA, "接收响应:%s%n请求时间:%.1fms%n报文信息:%s返回JSON:%s",
                    response.request().url(),
                    (t2 - t1) / 1e6d,
                    response.headers(),
                    responseBody.string()));
            return response;
        }
    }
}
