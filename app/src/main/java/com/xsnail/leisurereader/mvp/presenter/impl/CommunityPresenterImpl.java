package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.BookApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.DiscussionList;
import com.xsnail.leisurereader.mvp.contract.CommunityContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.RxUtils;
import com.xsnail.leisurereader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by xsnail on 2017/3/24.
 */

public class CommunityPresenterImpl extends RxPresenterImpl<CommunityContract.CommunityView> implements CommunityContract.CommunityPresenter<CommunityContract.CommunityView> {

    private BookApi bookApi;

    @Inject
    public CommunityPresenterImpl(BookApi bookApi) {
        this.bookApi = bookApi;
    }

    @Override
    public void getBookDisscussionList(String block, String sort, String distillate, final int start, int limit) {
        String key = StringUtils.creatAcacheKey("book-discussion-list", block, "all", sort, "all", start + "", limit + "", distillate);

        Observable<DiscussionList> fromNetWork = bookApi.getBookDisscussionList(block, "all", sort, "all", start + "", limit + "", distillate)
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
                        LogUtils.e("getBookDisscussionList:" + e.toString());
                        mView.showError();
                    }

                    @Override
                    public void onNext(DiscussionList list) {
                        boolean isRefresh = start == 0 ? true : false;
                        mView.showBookDisscussionList(list.posts, isRefresh);
                    }
                });
        addSubscribe(rxSubscription);
    }
}
