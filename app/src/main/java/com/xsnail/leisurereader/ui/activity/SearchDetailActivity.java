package com.xsnail.leisurereader.ui.activity;

import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.data.bean.SearchDetail;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerMainComponent;
import com.xsnail.leisurereader.mvp.contract.SearchDetailContract;
import com.xsnail.leisurereader.mvp.presenter.impl.SearchDetailPresenterImpl;

import java.util.List;

/**
 * Created by xsnail on 2017/3/22.
 */

public class SearchDetailActivity extends BaseActivity<SearchDetailPresenterImpl> implements SearchDetailContract.SearchDetailView {



    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
