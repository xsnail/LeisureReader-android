package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;

/**
 * Created by xsnail on 2017/3/25.
 */

public interface LoginContract {
    interface LoginView extends BaseContract.BaseView{
        void loginSucceed();
        void loginFailed(String error);
    }

    interface LoginPresenter<T> extends BaseContract.BasePresenter<T>{
        void login(String username,String password);
    }
}
