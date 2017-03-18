package com.xsnail.leisurereader.di.components;

import com.xsnail.leisurereader.ui.activity.WifiActivity;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface BookShelfComponent {
    WifiActivity inject(WifiActivity wifiActivity);
}
