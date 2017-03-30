package com.xsnail.leisurereader.mvp.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.BookMixAToc;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.data.bean.UserBookShelf;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.mvp.contract.BookShelfContract;
import com.xsnail.leisurereader.utils.ACache;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/23.
 */

public class BookShelfPresenterImpl extends RxPresenterImpl<BookShelfContract.BookShelfView> implements BookShelfContract.BookShelfPresenter<BookShelfContract.BookShelfView> {
    private BookApi bookApi;
    private Context mContext;

    private static final String TAG = "BookShelfPresenterImpl";

    @Inject
    public BookShelfPresenterImpl(Context context, BookApi bookApi) {
        this.mContext = context;
        this.bookApi = bookApi;
    }

    public void getUserBookShelf(LoginResult loginResult) {
        Subscription rxSubscription = bookApi.getUserBookShelf(loginResult).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBookShelf>() {
                    @Override
                    public void onNext(UserBookShelf data) {
                        if (data != null && mView != null) {
                            mView.showUserBookShelf();
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

    public void getTocList(final String bookId) {
        bookApi.getBookMixAToc(bookId, "chapters").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc>() {
                    @Override
                    public void onNext(BookMixAToc data) {
                        ACache.get(mContext).put(bookId + "bookToc", data);
                        List<BookMixAToc.mixToc.Chapters> list = data.mixToc.chapters;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showBookToc(bookId, list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.showError();
                    }
                });
    }

    @Override
    public void syncBookShelf() {
        List<Recommend.RecommendBooks> list = CollectionsManager.getInstance().getCollectionList();
        List<Observable<BookMixAToc.mixToc>> observables = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (Recommend.RecommendBooks bean : list) {
                if (!bean.isFromSD) {
                    Observable<BookMixAToc.mixToc> fromNetWork = bookApi.getBookMixAToc(bean._id, "chapters")
                            .map(new Func1<BookMixAToc, BookMixAToc.mixToc>() {
                                @Override
                                public BookMixAToc.mixToc call(BookMixAToc data) {
                                    return data.mixToc;
                                }
                            })
//                    .compose(RxUtil.<BookMixAToc.mixToc>rxCacheListHelper(
//                            StringUtils.creatAcacheKey("book-toc", bean._id, "chapters")))
                            ;
                    observables.add(fromNetWork);
                }
            }
        } else {
            ToastUtils.showSingleToast("书架空空如也...");
            mView.syncBookShelfCompleted();
            return;
        }

        Subscription rxSubscription = Observable.mergeDelayError(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookMixAToc.mixToc>() {
                    @Override
                    public void onNext(BookMixAToc.mixToc data) {
                        String lastChapter = data.chapters.get(data.chapters.size() - 1).title;
                        CollectionsManager.getInstance().setLastChapterAndLatelyUpdate(data.book, lastChapter, data.chaptersUpdated);
                    }

                    @Override
                    public void onCompleted() {
                        mView.syncBookShelfCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: " + e);
                        mView.showError();
                    }
                });
        addSubscribe(rxSubscription);
    }
}
