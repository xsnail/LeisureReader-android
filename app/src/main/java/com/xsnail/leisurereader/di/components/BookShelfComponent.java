package com.xsnail.leisurereader.di.components;

import com.xsnail.leisurereader.ui.activity.BookReadActivity;
import com.xsnail.leisurereader.ui.activity.WifiActivity;
import com.xsnail.leisurereader.ui.fragment.BookShelfFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookShelfComponent {
    WifiActivity inject(WifiActivity wifiActivity);

    BookShelfFragment inject(BookShelfFragment bookShelfFragment);

    BookReadActivity inject(BookReadActivity bookReadActivity);

}
