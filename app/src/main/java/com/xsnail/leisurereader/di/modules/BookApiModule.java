package com.xsnail.leisurereader.di.modules;


import com.xsnail.leisurereader.api.ApiConfig;
import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.api.interceptor.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by xsnail on 2016/11/24.
 */

@Module
public class BookApiModule {
    @Provides
    public OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(ApiConfig.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor);
        return builder.build();
    }
    @Provides
    protected BookApi provideBookService(OkHttpClient client){
        return BookApi.getInstance(client);
    }


}
