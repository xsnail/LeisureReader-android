package com.xsnail.leisurereader.di.components;

import android.content.Context;
import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.di.modules.AppModule;
import com.xsnail.leisurereader.di.modules.BookApiModule;
import dagger.Component;

/**
 * Created by xsnail on 2016/11/24.
 */
@Component(modules = {AppModule.class,BookApiModule.class})
public interface AppComponent {
    Context getContext();

    BookApi getBookApi();

}
