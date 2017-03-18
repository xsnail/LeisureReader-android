package com.xsnail.leisurereader.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xsnail on 2016/11/21.
 */

public class BookApi {
    private static BookApi sInstance;

    private BookApiService service;

    private BookApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getInstance(OkHttpClient okHttpClient){
        if(sInstance == null){
            sInstance = new BookApi(okHttpClient);
        }
        return sInstance;
    }


}
