package com.xsnail.leisurereader.mvp.presenter.impl;

import android.util.Log;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.BookDetail;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.data.bean.RecommendBookList;
import com.xsnail.leisurereader.mvp.contract.BookDetailContract;
import com.xsnail.leisurereader.utils.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/22.
 */

public class BookDetailPresenterImpl extends RxPresenterImpl<BookDetailContract.BookDetailView> implements BookDetailContract.BookDetailPresenter<BookDetailContract.BookDetailView> {
    private BookApi bookApi;

    private static final String TAG = "BookDetailPresenter";

    @Inject
    public BookDetailPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    public void getBookDetail(String bookId) {
        Subscription rxSubscription = bookApi.getBookDetail(bookId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetail>() {
                    @Override
                    public void onNext(BookDetail data) {
                        if (data != null && mView != null) {
                            mView.showBookDetail(data);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getHotReview(String book) {
        Subscription rxSubscription = bookApi.getHotReview(book).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotReview>() {
                    @Override
                    public void onNext(HotReview data) {
                        List<HotReview.Reviews> list = data.reviews;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showHotReview(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getRecommendBookList(String bookId, String limit) {
        Subscription rxSubscription = bookApi.getRecommendBookList(bookId, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendBookList>() {
                    @Override
                    public void onNext(RecommendBookList data) {
//                        LogUtils.i(data.booklists);
                        List<RecommendBookList.RecommendBook> list = data.booklists;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showRecommendBookList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("+++" + e.toString());
                    }
                });
        addSubscribe(rxSubscription);
    }

}
