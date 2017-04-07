package com.xsnail.leisurereader.ui.activity;


import android.os.Bundle;
import android.widget.TextView;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookShelfComponent;
import com.xsnail.leisurereader.mvp.contract.WifiContract;
import com.xsnail.leisurereader.mvp.presenter.impl.WifiPresenterImpl;

import butterknife.BindView;


/**
 * Created by xsnail on 2017/2/14.
 */

public class WifiActivity extends BaseActivity<WifiPresenterImpl> implements WifiContract.WifiView {

    @BindView(R.id.tv_wifi_name)
    TextView mTvWifiName;

    @BindView(R.id.tv_wifi_ip)
    TextView mTvWifiIp;

    private static final int WEB_CONFIG_PORT = 8004;
    private static final int WEB_CONFIG_MAX_PARALLELS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerBookShelfComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }


    @Override
    public void initDatas() {
        presenter.startServer(WEB_CONFIG_PORT,WEB_CONFIG_MAX_PARALLELS);
        presenter.getWifiInfo();
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wifi;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopServer();
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("wifi传书");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void showWifiInfo(String wifiName, String wifiIp) {
        if (wifiName == null){
            wifiName = "未知";
        }
        mTvWifiName.setText(wifiName);
        if(wifiIp == null){
            mTvWifiIp.setText("请连接wifi");
            return;
        }
        mTvWifiIp.setText("http://"+wifiIp+":"+WEB_CONFIG_PORT+"/upload/index.html");
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
