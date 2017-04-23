
package com.xsnail.leisurereader.ui.fragment;

import android.os.Bundle;


import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCommunityComponent;
import com.xsnail.leisurereader.mvp.contract.BookReviewContract;
import com.xsnail.leisurereader.mvp.presenter.impl.BookReviewPresenterImpl;
import com.xsnail.leisurereader.ui.adapter.BookDetailReviewAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 书籍详情 书评列表Fragment
 *
 */
public class BookReviewFragment extends BaseRVFragment<BookReviewPresenterImpl, HotReview.Reviews> implements BookReviewContract.BookReviewView {

    public final static String BUNDLE_ID = "bookId";

    public static BookReviewFragment newInstance(String id) {
        BookReviewFragment fragment = new BookReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String bookId;

    private String sort = "updated";

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
        initAdapter(BookDetailReviewAdapter.class, true, true);
        onRefresh();
    }

    @Override
    public void showBookDetailReviewList(List<HotReview.Reviews> list, boolean isRefresh) {
        if (isRefresh) {
            mAdapter.clear();
        }
        mAdapter.addAll(list);
        if(list != null)
            start = start + list.size();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getBookDetailReviewList(bookId, sort, 0, limit);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookDetailReviewList(bookId, sort, start, limit);
    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showError() {
        loaddingError();
    }

    @Override
    public void complete() {
        mRecyclerView.setRefreshing(false);
    }
}
