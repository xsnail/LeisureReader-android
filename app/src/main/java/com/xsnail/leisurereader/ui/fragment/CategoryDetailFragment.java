package com.xsnail.leisurereader.ui.fragment;

import android.os.Bundle;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.data.bean.BooksByCats;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCityComponent;
import com.xsnail.leisurereader.mvp.contract.CategoryContract;
import com.xsnail.leisurereader.mvp.presenter.impl.CategoryDetailPresenterImpl;
import com.xsnail.leisurereader.ui.activity.BookDetailActivity;
import com.xsnail.leisurereader.ui.adapter.CategoryDetailAdapter;

/**
 * Created by xsnail on 2017/3/21.
 */

public class
CategoryDetailFragment extends BaseRVFragment<CategoryDetailPresenterImpl,BooksByCats.BooksBean> implements CategoryContract.CategoryDetailView{
    public final static String BUNDLE_MAJOR = "major";
    public final static String BUNDLE_MINOR = "minor";
    public final static String BUNDLE_GENDER = "gender";
    public final static String BUNDLE_TYPE = "type";

    private String major = "";
    private String minor = "";
    private String gender = "";
    private String type = "";

    public static CategoryDetailFragment newInstance(String major, String minor, String gender, String type) {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MAJOR, major);
        bundle.putString(BUNDLE_GENDER, gender);
        bundle.putString(BUNDLE_MINOR, minor);
        bundle.putString(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookCityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        major = getArguments().getString(BUNDLE_MAJOR);
        gender = getArguments().getString(BUNDLE_GENDER);
        minor = getArguments().getString(BUNDLE_MINOR);
        type = getArguments().getString(BUNDLE_TYPE);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getCategoryList(gender, major, minor, this.type, 0, limit);
    }

    @Override
    public void configViews() {
        initAdapter(CategoryDetailAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void onItemClick(int position) {
        BooksByCats.BooksBean data = mAdapter.getItem(position);
        BookDetailActivity.startActivity(mContext, data._id);
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void onLoadMore() {
        mPresenter.getCategoryList(gender, major, minor, this.type, start, limit);
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void showCategoryList(BooksByCats data, boolean isRefresh) {
        if (isRefresh) {
            start = 0;
            mAdapter.clear();
        }
        mAdapter.addAll(data.books);
        start += data.books.size();
    }
}
