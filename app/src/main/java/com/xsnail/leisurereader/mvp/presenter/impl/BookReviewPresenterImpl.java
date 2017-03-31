package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.mvp.contract.BookReviewContract;
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

public class BookReviewPresenterImpl extends RxPresenterImpl<BookReviewContract.BookReviewView> implements BookReviewContract.BookReviewPresenter<BookReviewContract.BookReviewView> {

    private BookApi bookApi;

    @Inject
    public BookReviewPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    public void getBookDetailReviewList(String bookId, String sort, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-detail-review-list", bookId, sort, start, limit);
        Observable<HotReview> fromNetWork = bookApi.getBookDetailReviewList(bookId, sort, start + "", limit + "")
                .compose(RxUtils.<HotReview>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtils.rxCreateDiskObservable(key, HotReview.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDetailReviewList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(HotReview list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookDetailReviewList(list.reviews, isRefresh);
                    }
                });
        addSubscribe(rxSubscription);
    }

}
