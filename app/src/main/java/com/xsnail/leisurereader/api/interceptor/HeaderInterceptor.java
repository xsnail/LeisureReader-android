package com.xsnail.leisurereader.api.interceptor;


import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.utils.DeviceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xsnail on 2016/11/24.
 */

public final class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        if(original.url().toString().contains("book/") ||
                original.url().toString().contains("book-list/")||
                original.url().toString().contains("toc/")||
                original.url().toString().contains("post/")||
                original.url().toString().contains("user/")){
            Request request = original.newBuilder()
                    .addHeader("LoginResult-Agent","ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]")
                    .addHeader("X-LoginResult-Agent", "ZhuiShuShenQi/3.40[preload=false;locale=zh_CN;clientidbase=android-nvidia]")
                    .addHeader("X-Device-Id", DeviceUtils.getIMEI(App.getInstance()))
                    .addHeader("Host","api.zhuishushenqi.com")
                    .addHeader("Connection","Keep-Alive")
                    .addHeader("If-None-Match","W/\"2a04-4nguJ+XAaA1yAeFHyxVImg\"")
                    .addHeader("If-Modified-Since","Tue, 02 Aug 2016 03:20:06 UTC")
                    .build();
            return chain.proceed(request);
        }
        return chain.proceed(original);
    }
}
