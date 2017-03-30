package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.MyApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.manager.EventManager;
import com.xsnail.leisurereader.mvp.contract.LoginContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.RxUtils;
import com.xsnail.leisurereader.utils.SharedPreferencesUtil;
import com.xsnail.leisurereader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/25.
 */

public class LoginPresenterImpl extends RxPresenterImpl<LoginContract.LoginView> implements LoginContract.LoginPresenter<LoginContract.LoginView> {
    private MyApi myApi;

    @Inject
    public LoginPresenterImpl(MyApi myApi) {
        this.myApi = myApi;
    }

    @Override
    public void login(String username, String password) {
//        String key = StringUtils.creatAcacheKey("user-login");
//        Observable<LoginResult> fromNetWork = myApi.login(username,password)
//                .compose(RxUtils.<LoginResult>rxCacheBeanHelper(key));
        Observable<LoginResult> observable = myApi.login(username,password);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResult>() {
                    @Override
                    public void onNext(LoginResult data) {
                        if (data != null && mView != null && data.ok == true) {
                            LogUtils.d(data.toString());
                            SharedPreferencesUtil.getInstance().putBoolean("isLogin",true);
                            SharedPreferencesUtil.getInstance().putString("user",data.data.userName);
                            mView.loginSucceed();
                        }else if(data != null && mView != null && data.ok == false){
                            mView.loginFailed(data.error);
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
                        mView.loginFailed(e.toString());
                    }
                });
    }
}
