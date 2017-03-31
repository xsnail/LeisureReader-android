package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.api.MyApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.AutoComplete;
import com.xsnail.leisurereader.data.bean.BookDetail;
import com.xsnail.leisurereader.data.bean.BookShelfResult;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.data.bean.RegisterResult;
import com.xsnail.leisurereader.data.bean.SearchDetail;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.manager.EventManager;
import com.xsnail.leisurereader.mvp.contract.MainContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/22.
 */

public class MainPresenterImpl extends RxPresenterImpl<MainContract.MainView> implements MainContract.MainPresenter<MainContract.MainView> {

    private BookApi bookApi;
    private MyApi myApi;

    @Inject
    public MainPresenterImpl(BookApi bookApi,MyApi myApi) {
        this.bookApi = bookApi;
        this.myApi = myApi;
    }

    @Override
    public void getSearchResultList(String query) {
        Subscription rxSubscription = bookApi.getSearchResult(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchDetail>() {
                    @Override
                    public void onNext(SearchDetail bean) {
                        List<SearchDetail.SearchBooks> list = bean.books;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showSearchResultList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
        addSubscribe(rxSubscription);
    }

    @Override
    public void syncBookShelf(String userName) {
        Observable<BookShelfResult> observable = myApi.sync(userName);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookShelfResult>() {
                    @Override
                    public void onNext(BookShelfResult data) {
                        if (data != null && mView != null && data.ok == true) {
                            LogUtils.d(data.toString());
                            if(data.data.bookIdList != null && data.data.bookIdList.size() > 0) {
                                List<Recommend.RecommendBooks> recommendBookses = CollectionsManager.getInstance().getCollectionList();
                                CollectionsManager.getInstance().removeSome(recommendBookses,true);
                                for (String bookId : data.data.bookIdList) {
                                    Observable<BookDetail> observable1 = bookApi.getBookDetail(bookId);
                                    observable1.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<BookDetail>() {
                                                @Override
                                                public void onCompleted() {

                                                }

                                                @Override
                                                public void onError(Throwable e) {

                                                }

                                                @Override
                                                public void onNext(BookDetail bookDetail) {
                                                    Recommend.RecommendBooks recommendBooks = new Recommend.RecommendBooks();
                                                    recommendBooks.title = bookDetail.title;
                                                    recommendBooks._id = bookDetail._id;
                                                    recommendBooks.cover = bookDetail.cover;
                                                    recommendBooks.lastChapter = bookDetail.lastChapter;
                                                    recommendBooks.updated = bookDetail.updated;
                                                    CollectionsManager.getInstance().add(recommendBooks);
                                                }
                                            });
                                }
                            }

//                            mView.showSysncSucceed();
                        } else if (data != null && mView != null && data.ok == false) {
                            mView.showSyncFailed("同步失败");
                        }
                    }

                    @Override
                    public void onCompleted() {
                        LogUtils.d("complete");
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(e.toString());
                        mView.showSyncFailed(e.toString());
                    }
                });
    }

    @Override
    public void getQuerySuggestion(String query) {
        Subscription rxSubscription = bookApi.getAutoComplete(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoComplete>() {
                    @Override
                    public void onNext(AutoComplete autoComplete) {
                        LogUtils.e("getAutoCompleteList" + autoComplete.keywords);
                        List<String> list = autoComplete.keywords;
                        if (list != null && !list.isEmpty() && mView != null) {
                            mView.showAutoCompleteList(list);
                        }
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.toString());
                    }
                });
        addSubscribe(rxSubscription);
    }


}
