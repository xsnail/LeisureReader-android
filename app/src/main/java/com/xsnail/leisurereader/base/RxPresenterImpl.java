package com.xsnail.leisurereader.base;

/**
 * Created by xsnail on 2017/2/15.
 */

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenterImpl<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe(){
        if(mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if(mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void viewToPresenter(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        unSubscribe();
    }
}

