package com.xsnail.leisurereader.base;

/**
 * Created by xsnail on 2017/2/14.
 */

public class BasePresenterImpl<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void viewToPresenter(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
