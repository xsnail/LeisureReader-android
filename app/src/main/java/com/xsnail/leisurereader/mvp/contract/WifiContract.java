package com.xsnail.leisurereader.mvp.contract;

import android.content.Context;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.wifi.SimpleHttpServer;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface WifiContract{
    interface WifiView extends BaseContract.BaseView{
        void showWifiInfo(String wifiName,String wifiIp);
    }

    interface WifiPresenter<T> extends BaseContract.BasePresenter<T>{
        void startServer(int port,int maxParallels);
        void getWifiInfo();
        void stopServer();
    }
}
