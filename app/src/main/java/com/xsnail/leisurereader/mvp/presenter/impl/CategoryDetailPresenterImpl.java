package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.BooksByCats;
import com.xsnail.leisurereader.mvp.contract.CategoryContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.RxUtils;
import com.xsnail.leisurereader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xsnail on 2017/3/21.
 */

public class CategoryDetailPresenterImpl extends RxPresenterImpl<CategoryContract.CategoryDetailView> implements CategoryContract.CategoryDetailPresenter<CategoryContract.CategoryDetailView>{
    private BookApi bookApi;

    @Inject
    public CategoryDetailPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }


    @Override
    public void getCategoryList(String gender, String major, String minor, String type,final int start, int limit) {
        String key = StringUtils.creatAcacheKey("category-list", gender, type, major, minor, start, limit);
        Observable<BooksByCats> fromNetWork = bookApi.getBooksByCats(gender, type, major, minor, start, limit)
                .compose(RxUtils.<BooksByCats>rxCacheListHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtils.rxCreateDiskObservable(key, BooksByCats.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BooksByCats>() {
                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("getCategoryList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(BooksByCats booksByCats) {
                        mView.showCategoryList(booksByCats, start == 0 ? true : false);
                    }
                });
        addSubscribe(rxSubscription);
    }
}
