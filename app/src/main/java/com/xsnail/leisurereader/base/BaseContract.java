package com.xsnail.leisurereader.base;

/**
 * Created by Administrator on 2017/2/14.
 */

public interface BaseContract {
    interface BaseView{
        void showError();
        void complete();
    }

    interface BasePresenter<T>{
        void viewToPresenter(T view);
        void detachView();
    }
}
