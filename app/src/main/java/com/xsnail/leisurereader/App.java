package com.xsnail.leisurereader;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerAppComponent;
import com.xsnail.leisurereader.di.modules.AppModule;
import com.xsnail.leisurereader.di.modules.BookApiModule;
import com.xsnail.leisurereader.service.UploadBookShelfService;
import com.xsnail.leisurereader.utils.SharedPreferencesUtil;

/**
 * Created by xsnail on 2017/2/14.
 */

public class App extends Application {
    private static App sInstance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initComponent();
        initialize();
        initPrefs();

        startService(new Intent(this, UploadBookShelfService.class));
    }

    public static App getInstance(){
        return sInstance;
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.builder()
                .bookApiModule(new BookApiModule())
                .appModule(new AppModule(sInstance))
                .build();
    }

    private void initialize(){
        Fresco.initialize(sInstance);
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
