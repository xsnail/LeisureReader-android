package com.xsnail.leisurereader.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.di.components.AppComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/14.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;

    @Inject
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        setupComponent(App.getInstance().getAppComponent());
        setViewToPresenter();
        initView();
        initDatas();
    }

    protected abstract void setupComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    protected abstract void setViewToPresenter();

    protected abstract void initDatas();

    protected abstract void initView();

    protected abstract int getLayoutId();
}
