package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.CategoryList;
import com.xsnail.leisurereader.mvp.contract.CityContract;
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

public class BookCityPresenterImpl extends RxPresenterImpl<CityContract.CityView> implements CityContract.CityPresenter<CityContract.CityView>{

    private BookApi bookApi;

    @Inject
    public BookCityPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getCategoryList() {
        String key = StringUtils.creatAcacheKey("book-category-list");
        Observable<CategoryList> fromNetWork = bookApi.getCategoryList()
                .compose(RxUtils.<CategoryList>rxCacheBeanHelper(key));

        //依次检查disk、network
        Subscription rxSubscription = Observable.concat(RxUtils.rxCreateDiskObservable(key, CategoryList.class), fromNetWork)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryList>() {
                    @Override
                    public void onNext(CategoryList data) {
                        if (data != null && mView != null) {
                            LogUtils.d(data.toString());
                            mView.showCategoryList(data);
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
                        mView.complete();
                    }
                });
        addSubscribe(rxSubscription);
    }
}
