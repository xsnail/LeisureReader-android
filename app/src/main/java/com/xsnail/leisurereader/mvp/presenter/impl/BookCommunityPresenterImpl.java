package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.DiscussionList;
import com.xsnail.leisurereader.mvp.contract.BookCommunityContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.RxUtils;
import com.xsnail.leisurereader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xsnail on 2017/3/30.
 */

public class BookCommunityPresenterImpl extends RxPresenterImpl<BookCommunityContract.BookCommunityView> implements BookCommunityContract.BookCommunityPresenter<BookCommunityContract.BookCommunityView> {
    private BookApi bookApi;

    @Inject
    public BookCommunityPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDetailDiscussionList(String bookId, String sort, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-detail-discussion-list", bookId, sort, start, limit);
        Observable<DiscussionList> fromNetWork = bookApi.getBookDetailDisscussionList(bookId, sort, "normal,vote", start + "", limit + "")
                .compose(RxUtils.<DiscussionList>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtils.rxCreateDiskObservable(key, DiscussionList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscussionList>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailDiscussionList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(DiscussionList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookDetailDiscussionList(list.posts, isRefresh);
                    }
                });
        addSubscribe(rxSubscription);
    }
}
