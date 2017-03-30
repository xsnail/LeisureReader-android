
package com.xsnail.leisurereader.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.view.recyclerview.EasyRecyclerView;
import com.xsnail.leisurereader.view.recyclerview.adapter.OnLoadMoreListener;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.xsnail.leisurereader.view.recyclerview.swipe.OnRefreshListener;

import java.lang.reflect.Constructor;

import javax.inject.Inject;

import butterknife.BindView;


public abstract class BaseRVFragment<T1 extends BaseContract.BasePresenter, T2> extends BaseFragment implements OnLoadMoreListener, OnRefreshListener, RecyclerArrayAdapter.OnItemClickListener {

    @Inject
    protected T1 mPresenter;

    @BindView(R.id.recyclerview)
    protected EasyRecyclerView mRecyclerView;
    protected RecyclerArrayAdapter<T2> mAdapter;

    protected int start = 0;
    protected int limit = 20;

    /**
     * [此方法不可再重写]
     */
    @Override
    public void attachView() {
        if (mPresenter != null)
            mPresenter.viewToPresenter(this);
    }

    public void detachView(){
        if (mPresenter != null){
            mPresenter.detachView();
        }
    }

    protected void initAdapter(boolean refreshable, boolean loadmoreable) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setItemDecoration(ContextCompat.getColor(mContext, R.color.common_divider_narrow), 1, 0, 0);
            mRecyclerView.setAdapterWithProgress(mAdapter);
        }

        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(this);
            mAdapter.setError(R.layout.common_error_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.resumeMore();

                }
            });
            if (loadmoreable) {
                mAdapter.setMore(R.layout.common_more_view, this);
                mAdapter.setNoMore(R.layout.common_nomore_view);
            }
            if (refreshable && mRecyclerView != null) {
                mRecyclerView.setRefreshListener(this);
            }
        }
    }

    protected void initAdapter(Class<? extends RecyclerArrayAdapter<T2>> clazz, boolean refreshable, boolean loadmoreable) {
        mAdapter = (RecyclerArrayAdapter<T2>) createInstance(clazz);
        initAdapter(refreshable, loadmoreable);
    }

    public Object createInstance(Class<?> cls) {
        Object obj;
        try {
            Constructor c1 = cls.getDeclaredConstructor(Context.class);
            c1.setAccessible(true);
            obj = c1.newInstance(mContext);
        } catch (Exception e) {
            obj = null;
        }
        return obj;
    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onRefresh() {
        mRecyclerView.setRefreshing(true);
    }

    protected void loaddingError() {
        if (mAdapter.getCount() < 1) { // 说明缓存也没有加载，那就显示errorview，如果有缓存，即使刷新失败也不显示error
            mAdapter.clear();
        }
        mAdapter.pauseMore();
        mRecyclerView.setRefreshing(false);
        mRecyclerView.showTipViewAndDelayClose("似乎没有网络哦");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
