package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;

/**
 * Created by xsnail on 2017/4/8.
 */

public class DisclaimerActivity extends BaseActivity {

    public static void startActivity(Context context){
        context.startActivity(new Intent(context,DisclaimerActivity.class));
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("免责声明");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_disclaimer;
    }
}
