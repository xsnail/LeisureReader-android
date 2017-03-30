package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.CommentList;
import com.xsnail.leisurereader.data.bean.Disscussion;
import com.xsnail.leisurereader.mvp.contract.DiscussionDetailContract;
import com.xsnail.leisurereader.utils.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/24.
 */

public class DiscussionDetailPresenterImpl extends RxPresenterImpl<DiscussionDetailContract.DiscussionDetailView> implements DiscussionDetailContract.DiscussionDetailPresenter<DiscussionDetailContract.DiscussionDetailView> {
    private BookApi bookApi;

    @Inject
    public DiscussionDetailPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDisscussionDetail(String id) {
        Subscription rxSubscription = bookApi.getBookDisscussionDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Disscussion>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDisscussionDetail:" + e.toString());
                    }

                    @Override
                    public void onNext(Disscussion disscussion) {
                        mView.showBookDisscussionDetail(disscussion);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getBestComments(String disscussionId) {
        Subscription rxSubscription = bookApi.getBestComments(disscussionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBestComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        mView.showBestComments(list);
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void getBookDisscussionComments(String disscussionId, int start, int limit) {
        Subscription rxSubscription = bookApi.getBookDisscussionComments(disscussionId, start + "", limit + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getBookDisscussionComments:" + e.toString());
                    }

                    @Override
                    public void onNext(CommentList list) {
                        mView.showBookDisscussionComments(list);
                    }
                });
        addSubscribe(rxSubscription);
    }

}
