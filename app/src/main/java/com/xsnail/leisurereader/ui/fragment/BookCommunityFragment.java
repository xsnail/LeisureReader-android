
package com.xsnail.leisurereader.ui.fragment;

import android.os.Bundle;


import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.data.bean.DiscussionList;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCommunityComponent;
import com.xsnail.leisurereader.mvp.contract.BookCommunityContract;
import com.xsnail.leisurereader.mvp.contract.CommunityContract;
import com.xsnail.leisurereader.mvp.presenter.impl.BookCommunityPresenterImpl;
import com.xsnail.leisurereader.mvp.presenter.impl.CommunityPresenterImpl;
import com.xsnail.leisurereader.ui.activity.DiscussionDetailActivity;
import com.xsnail.leisurereader.ui.adapter.BookDiscussionAdapter;

import java.util.List;

/**
 * 书籍详情 讨论列表Fragment
 *
 */
public class BookCommunityFragment extends BaseRVFragment<BookCommunityPresenterImpl, DiscussionList.PostsBean> implements BookCommunityContract.BookCommunityView {

    public final static String BUNDLE_ID = "bookId";

    public static BookCommunityFragment newInstance(String id) {
        BookCommunityFragment fragment = new BookCommunityFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String bookId;
    private String sort = "updated";


    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }

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
        bookId = getArguments().getString(BUNDLE_ID);
    }

    @Override
    public void configViews() {
        initAdapter(BookDiscussionAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void onItemClick(int position) {
        DiscussionList.PostsBean data = mAdapter.getItem(position);
        DiscussionDetailActivity.startActivity(mContext,data._id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDetailDiscussionList(bookId, sort, 0, limit);
    }

    @Override
    public void onLoadMore() {
        mPresenter.getBookDetailDiscussionList(bookId, sort, start, limit);
    }

    @Override
    public void showBookDetailDiscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
            start = 0;
        }
        mAdapter.addAll(list);
        start = start + list.size();
    }
}
