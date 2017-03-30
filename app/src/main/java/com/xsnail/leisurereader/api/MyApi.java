package com.xsnail.leisurereader.api;


import com.xsnail.leisurereader.data.bean.BookShelfResult;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.data.bean.RegisterResult;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by xsnail on 2017/3/27.
 */

public class MyApi {
    private static MyApi sInstance;

    private MyApiService service;

    private MyApi(OkHttpClient myOkHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.MY_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(myOkHttpClient)
                .build();
        service = retrofit.create(MyApiService.class);
    }

    public static MyApi getInstance(OkHttpClient okHttpClient){
        if(sInstance == null){
            sInstance = new MyApi(okHttpClient);
        }
        return sInstance;
    }

    //登陆
    public Observable<LoginResult> login(String username, String password){
        return service.login(username,password);
    }

    public Observable<RegisterResult> register(String username,String password){
        return service.register(username,password);
    }

    public Observable<BookShelfResult> sync(String userName){
        return service.sync(userName);
    }

    public Observable<BookShelfResult> uploadBookShelf(BookShelfResult.Book book) {
        return service.uploadBookShelf(book);
    }
}
