package com.xsnail.leisurereader.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseFragment;
import com.xsnail.leisurereader.common.OnRvItemClickListener;
import com.xsnail.leisurereader.data.bean.CategoryList;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCityComponent;
import com.xsnail.leisurereader.mvp.contract.CityContract;
import com.xsnail.leisurereader.mvp.presenter.impl.BookCityPresenterImpl;
import com.xsnail.leisurereader.ui.activity.CategoryDetailActivity;
import com.xsnail.leisurereader.ui.adapter.BookCityAdapter;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.view.SupportGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by xsnail on 2017/2/15.
 */

public class BookCityFragment extends BaseFragment implements CityContract.CityView {

    @BindView(R.id.recyclerview_city_boy)
    RecyclerView mRecyclerViewBoy;

    @BindView(R.id.recyclerview_city_girl)
    RecyclerView mRecyclerViewGirl;

    @Inject
    BookCityPresenterImpl mBookCityPresenter;

    private BookCityAdapter mBookCityBoyAdapter;
    private BookCityAdapter mBookCityGirlAdapter;
    private List<CategoryList.Category> mBoyCategoryList = new ArrayList<>();
    private List<CategoryList.Category> mGirlCategoryList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected void detachView() {
        if(mBookCityPresenter != null){
            mBookCityPresenter.detachView();
        }
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
        return R.layout.fragment_bookcity;
    }

    @Override
    public void attachView() {
        if (mBookCityPresenter != null){
            mBookCityPresenter.viewToPresenter(this);
        }
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void configViews() {
        mRecyclerViewBoy.setHasFixedSize(true);
        mRecyclerViewBoy.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerViewBoy.addItemDecoration(new SupportGridItemDecoration(mContext));
        mRecyclerViewGirl.setHasFixedSize(true);
        mRecyclerViewGirl.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerViewGirl.addItemDecoration(new SupportGridItemDecoration(mContext));
        mBookCityBoyAdapter = new BookCityAdapter(mContext,mBoyCategoryList,new ClickListener("male"));
        mBookCityGirlAdapter = new BookCityAdapter(mContext,mGirlCategoryList,new ClickListener("female"));
        mRecyclerViewBoy.setAdapter(mBookCityBoyAdapter);
        mRecyclerViewGirl.setAdapter(mBookCityGirlAdapter);

        mBookCityPresenter.getCategoryList();
    }

    @Override
    public void showCategoryList(CategoryList categoryList) {
        mBoyCategoryList.clear();
        mGirlCategoryList.clear();
        mBoyCategoryList.addAll(categoryList.male);
        mGirlCategoryList.addAll(categoryList.female);
        for(CategoryList.Category category : mBoyCategoryList){
            LogUtils.d(category.name);
        }
        mBookCityBoyAdapter.notifyDataSetChanged();
        mBookCityGirlAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    class ClickListener implements OnRvItemClickListener<CategoryList.Category> {

        private String gender;

        public ClickListener(String gender) {
            this.gender = gender;
        }

        @Override
        public void onItemClick(View view, int position, CategoryList.Category data) {
            CategoryDetailActivity.startActivity(mContext, data.name, gender);
            LogUtils.d("test",data.name);
        }
    }
}
