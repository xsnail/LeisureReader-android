package com.xsnail.leisurereader.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xsnail on 2016/11/24.
 */
@Module
public class AppModule {
    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }
}
