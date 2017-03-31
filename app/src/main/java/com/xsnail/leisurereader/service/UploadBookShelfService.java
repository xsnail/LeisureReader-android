package com.xsnail.leisurereader.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xsnail.leisurereader.api.ApiConfig;
import com.xsnail.leisurereader.api.MyApi;
import com.xsnail.leisurereader.api.interceptor.HeaderInterceptor;
import com.xsnail.leisurereader.data.bean.BookShelfResult;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/28.
 */

public class UploadBookShelfService extends Service {
    private MyApi myApi;
    private OkHttpClient okHttpClient;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(ApiConfig.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .readTimeout(ApiConfig.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor);

        okHttpClient = builder.build();

        myApi = MyApi.getInstance(okHttpClient);


        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 60 * 1000);

    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if(SharedPreferencesUtil.getInstance().getBoolean("isLogin",false)) {
                List<Recommend.RecommendBooks> recommendBooksList = CollectionsManager.getInstance().getCollectionList();
                if (recommendBooksList != null) {
                    BookShelfResult.Book book = new BookShelfResult.Book();
                    book.userName = SharedPreferencesUtil.getInstance().getString("user");
                    List<String> bookIdList = new ArrayList<>();
                    for (Recommend.RecommendBooks recommendBooks : recommendBooksList) {
                        bookIdList.add(recommendBooks._id);
                    }
                    book.bookIdList = bookIdList;
                    Observable<BookShelfResult> observable = myApi.uploadBookShelf(book);
                    observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<BookShelfResult>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    LogUtils.d("upload failed", e.toString());
                                }

                                @Override
                                public void onNext(BookShelfResult bookShelfResult) {
                                    LogUtils.d("upload bookshelf succeed", bookShelfResult.toString());
                                }
                            });
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 上传记录
        return super.onStartCommand(intent, flags, startId);
    }

}
