package com.xsnail.leisurereader.api;


import com.xsnail.leisurereader.data.bean.BookShelfResult;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.data.bean.RegisterResult;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xsnail on 2016/11/21.
 */

public interface MyApiService {

    /**
     * 登陆
     */
    @POST("/user/login")
    Observable<LoginResult> login(@Query("username") String username, @Query("password") String password);

    /**
     * 注册
     */
    @POST("/user/register")
    Observable<RegisterResult> register(@Query("username") String username, @Query("password") String password);

    /**
     * 从服务器同步书架
     */
    @GET("/book/{userName}/download")
    Observable<BookShelfResult> sync(@Path("userName")String userName);

    @POST("/book/upload")
    Observable<BookShelfResult> uploadBookShelf(@Body BookShelfResult.Book book);
}
