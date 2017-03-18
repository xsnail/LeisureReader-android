package com.xsnail.leisurereader;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerAppComponent;
import com.xsnail.leisurereader.di.modules.AppModule;
import com.xsnail.leisurereader.di.modules.BookApiModule;

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

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
