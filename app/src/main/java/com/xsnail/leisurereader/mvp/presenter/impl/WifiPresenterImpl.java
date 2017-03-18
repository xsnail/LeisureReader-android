package com.xsnail.leisurereader.mvp.presenter.impl;

import android.content.Context;

import com.xsnail.leisurereader.base.BasePresenterImpl;
import com.xsnail.leisurereader.mvp.contract.WifiContract;
import com.xsnail.leisurereader.utils.NetUtils;
import com.xsnail.leisurereader.wifi.ResourceInAssetsHandler;
import com.xsnail.leisurereader.wifi.SimpleHttpServer;
import com.xsnail.leisurereader.wifi.UploadPdfHandler;
import com.xsnail.leisurereader.wifi.UploadTxtHandler;
import com.xsnail.leisurereader.wifi.WebConfiguration;

import javax.inject.Inject;

/**
 * Created by xsnail on 2017/2/14.
 */

public class WifiPresenterImpl extends BasePresenterImpl<WifiContract.WifiView> implements WifiContract.WifiPresenter<WifiContract.WifiView> {

    private SimpleHttpServer server;
    private Context mContext;

    @Inject
    public WifiPresenterImpl(Context context){
        this.mContext = context;
    }

    @Override
    public void startServer(int port, int maxParallels) {
        WebConfiguration webConfig = new WebConfiguration();
        webConfig.setPort(port);
        webConfig.setMaxParallels(maxParallels);
        server = new SimpleHttpServer(webConfig);
        server.registResourceHandler(new ResourceInAssetsHandler(mContext));
        server.registResourceHandler(new UploadTxtHandler());
        server.registResourceHandler(new UploadPdfHandler());
        server.startAsync();
    }

    @Override
    public void getWifiInfo() {
        String wifiName = NetUtils.getWifiName(mContext);
        String wifiIp = NetUtils.getWifiIp(mContext);
        mView.showWifiInfo(wifiName,wifiIp);
    }

    @Override
    public void stopServer() {
        server.stopAsync();
    }
}
