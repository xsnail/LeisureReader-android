package com.xsnail.leisurereader.di.components;

import com.xsnail.leisurereader.ui.activity.LoginActivity;
import com.xsnail.leisurereader.ui.activity.MainActivity;
import com.xsnail.leisurereader.ui.activity.RegisterActivity;
import com.xsnail.leisurereader.ui.activity.SearchDetailActivity;

import dagger.Component;

/**
 * Created by xsnail on 2017/3/22.
 */

@Component(dependencies = AppComponent.class)
public interface MainComponent {
    MainActivity inject(MainActivity mainActivity);

    SearchDetailActivity inject(SearchDetailActivity searchDetailActivity);

    LoginActivity inject(LoginActivity loginActivity);

    RegisterActivity inject(RegisterActivity registerActivity);
}
