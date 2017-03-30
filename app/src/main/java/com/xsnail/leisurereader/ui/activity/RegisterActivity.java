package com.xsnail.leisurereader.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerMainComponent;
import com.xsnail.leisurereader.mvp.contract.LoginContract;
import com.xsnail.leisurereader.mvp.contract.RegisterContract;
import com.xsnail.leisurereader.mvp.presenter.impl.LoginPresenterImpl;
import com.xsnail.leisurereader.mvp.presenter.impl.RegisterPresenterImpl;
import com.xsnail.leisurereader.utils.ToastUtils;

import butterknife.BindView;

/**
 * Created by xsnail on 2017/3/25.
 */
public class RegisterActivity extends BaseActivity<RegisterPresenterImpl> implements RegisterContract.RegisterView {

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_psw) EditText _pswText;
    @BindView(R.id.input_psw_again) EditText _pswAgainText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }


    @Override
    protected void setupComponent(AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initView() {
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    presenter.register(_nameText.getText().toString(),_pswText.getText().toString());
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }


    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _pswText.getText().toString();
        String passwordAgain = _pswAgainText.getText().toString();

        if (name.isEmpty() || name.length() < 4 || name.length() > 10) {
            _nameText.setError("用户名必须在4到10字符");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 12) {
            _pswText.setError("密码必须在8到12字符");
            _pswAgainText.setError("密码必须在8到12字符");
            valid = false;
        }

        if(!password.equals(passwordAgain)){
            _pswText.setError("密码必须一致");
            _pswAgainText.setError("密码必须一致");
            valid = false;
        }

        return valid;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }


    @Override
    public void registerSucceed() {
        ToastUtils.showToast("注册成功");
        finish();
    }

    @Override
    public void registerFail(String error) {
        ToastUtils.showToast(error);
    }
}