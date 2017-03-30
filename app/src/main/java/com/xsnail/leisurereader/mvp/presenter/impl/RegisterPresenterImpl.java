package com.xsnail.leisurereader.mvp.presenter.impl;

import com.xsnail.leisurereader.api.MyApi;
import com.xsnail.leisurereader.base.RxPresenterImpl;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.data.bean.RegisterResult;
import com.xsnail.leisurereader.mvp.contract.RegisterContract;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.SharedPreferencesUtil;
import com.xsnail.leisurereader.utils.StringUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xsnail on 2017/3/28.
 */

public class RegisterPresenterImpl extends RxPresenterImpl<RegisterContract.RegisterView> implements RegisterContract.RegisterPresenter<RegisterContract.RegisterView> {
    private MyApi myApi;

    @Inject
    public RegisterPresenterImpl(MyApi myApi) {
        this.myApi = myApi;
    }

    @Override
    public void register(String username, String password) {
//        String key = StringUtils.creatAcacheKey("user-register");
//        Observable<LoginResult> fromNetWork = myApi.login(username,password)
//                .compose(RxUtils.<LoginResult>rxCacheBeanHelper(key));
        Observable<RegisterResult> observable = myApi.register(username, password);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterResult>() {
                    @Override
                    public void onNext(RegisterResult data) {
                        if (data != null && mView != null && data.ok == true) {
                            LogUtils.d(data.toString());
                            mView.registerSucceed();
                        } else if (data != null && mView != null && data.ok == false) {
                            mView.registerFail(data.error);
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
                        mView.registerFail(e.toString());
                    }
                });
    }

}
