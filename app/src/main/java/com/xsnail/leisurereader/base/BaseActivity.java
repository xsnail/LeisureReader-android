package com.xsnail.leisurereader.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.xsnail.leisurereader.App;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.view.CustomDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/14.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AppCompatActivity {

    private Unbinder unbinder;
    public Toolbar mCommonToolbar;
    protected Context mContext;
    private CustomDialog dialog;

    @Inject
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        unbinder = ButterKnife.bind(this);

        setupComponent(App.getInstance().getAppComponent());
        mCommonToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (mCommonToolbar != null) {
            initToolBar();
            setSupportActionBar(mCommonToolbar);
            mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        setViewToPresenter();
        initDatas();
        initView();
    }

    protected abstract void setupComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachView();
        unbinder.unbind();
    }

    public abstract void initToolBar();

    public void setViewToPresenter(){
        if(presenter != null){
            presenter.viewToPresenter(this);
        }
    }

    public abstract void initDatas();

    public abstract void initView();

    public abstract int getLayoutId();

    public void detachView(){
        if(presenter != null){
            presenter.detachView();
        }
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    protected void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    protected void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }
}
