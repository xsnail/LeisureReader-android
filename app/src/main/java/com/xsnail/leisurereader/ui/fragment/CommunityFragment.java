package com.xsnail.leisurereader.ui.fragment;


import android.util.Log;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.data.bean.DiscussionList;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCommunityComponent;
import com.xsnail.leisurereader.mvp.contract.CommunityContract;
import com.xsnail.leisurereader.mvp.presenter.impl.CommunityPresenterImpl;
import com.xsnail.leisurereader.ui.activity.DiscussionDetailActivity;
import com.xsnail.leisurereader.ui.adapter.BookDiscussionAdapter;

import java.util.List;


/**
 * Created by xsnail on 2017/2/15.
 */

public class CommunityFragment extends BaseRVFragment<CommunityPresenterImpl,DiscussionList.PostsBean> implements CommunityContract.CommunityView {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookCommunityComponent.builder()
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

    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDisscussionList("ramble", "updated", "", 0, limit);
    }

    @Override
    public void showBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void onItemClick(int position) {
        DiscussionList.PostsBean postsBean = mAdapter.getItem(position);
        DiscussionDetailActivity.startActivity(mContext,postsBean._id);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBookDisscussionList("ramble", "created", "", 0, limit);
    }
}
