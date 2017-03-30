package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;

/**
 * Created by xsnail on 2017/3/28.
 */

public interface RegisterContract {
    interface RegisterView extends BaseContract.BaseView{
        void registerSucceed();
        void registerFail(String error);
    }

    interface RegisterPresenter<T> extends BaseContract.BasePresenter<T>{
        void register(String username,String password);
    }
}
