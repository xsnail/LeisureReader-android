package com.xsnail.leisurereader.ui.fragment;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.data.bean.BookMixAToc;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.data.support.RefreshBookShelfEvent;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookShelfComponent;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.manager.EventManager;
import com.xsnail.leisurereader.mvp.contract.BookShelfContract;
import com.xsnail.leisurereader.mvp.presenter.impl.BookShelfPresenterImpl;
import com.xsnail.leisurereader.ui.activity.BookReadActivity;
import com.xsnail.leisurereader.ui.activity.MainActivity;
import com.xsnail.leisurereader.ui.adapter.RecommendAdapter;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsnail on 2017/2/15.
 */

public class BookShelfFragment extends BaseRVFragment<BookShelfPresenterImpl,Recommend.RecommendBooks> implements RecyclerArrayAdapter.OnItemLongClickListener,BookShelfContract.BookShelfView {

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookShelfComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void configViews() {
        initAdapter(RecommendAdapter.class, true, false);
        mAdapter.setOnItemLongClickListener(this);
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.foot_view_shelf, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                headerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBook();
                    }
                });
            }
        });
        mRecyclerView.getEmptyView().findViewById(R.id.btnToAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });
        onRefresh();
    }

    private void addBook(){
        ((MainActivity)mContext).toolbarTitle.setText("书城");
        ((MainActivity)mContext).mBottomNavigationBar.selectTab(1);
        ((MainActivity)mContext).showFragment(1);
    }

    @Override
    public void onItemClick(int position) {
        BookReadActivity.startActivity(mContext, mAdapter.getItem(position), mAdapter.getItem(position).isFromSD);
    }

    @Override
    public boolean onItemLongClick(int position) {
        showLongClickDialog(position);
        return false;
    }

    /**
     * 显示长按对话框
     *
     * @param position
     */
    private void showLongClickDialog(final int position) {
        final boolean isTop = CollectionsManager.getInstance().isTop(mAdapter.getItem(position)._id);
        String[] items;
        DialogInterface.OnClickListener listener;
        if (mAdapter.getItem(position).isFromSD) {
            items = getResources().getStringArray(R.array.recommend_item_long_click_choice_local);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(mAdapter.getItem(position)._id, !isTop);
                            EventManager.refreshCollectionList();
                            break;
                        case 1:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(mAdapter.getItem(position));
                            showDeleteCacheDialog(removeList);
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        } else {
            items = getResources().getStringArray(R.array.recommend_item_long_click_choice);
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //置顶、取消置顶
                            CollectionsManager.getInstance().top(mAdapter.getItem(position)._id, !isTop);
                            break;
//                        case 1:
//                            //缓存全本
//                            if (mAdapter.getItem(position).isFromSD) {
//                                mRecyclerView.showTipViewAndDelayClose("本地文件不支持该选项哦");
//                            } else {
//                                showDialog();
//                                mPresenter.getTocList(mAdapter.getItem(position)._id);
//                            }
//                            break;
                        case 1:
                            //删除
                            List<Recommend.RecommendBooks> removeList = new ArrayList<>();
                            removeList.add(mAdapter.getItem(position));
                            showDeleteCacheDialog(removeList);
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                }
            };
        }
        if (isTop) items[0] = getString(R.string.cancle_top);
        new AlertDialog.Builder(mContext)
                .setTitle(mAdapter.getItem(position).title)
                .setItems(items, listener)
                .setNegativeButton(null, null)
                .create().show();
    }

    /**
     * 显示删除本地缓存对话框
     *
     * @param removeList
     */
    private void showDeleteCacheDialog(final List<Recommend.RecommendBooks> removeList) {
        final boolean selected[] = {true};
        new AlertDialog.Builder(mContext)
                .setTitle(mContext
                        .getString(R.string.remove_selected_book))
                .setMultiChoiceItems(new String[]{mContext
                        .getString(R.string.delete_local_cache)}, selected,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selected[0] = isChecked;
                            }
                        })
                .setPositiveButton(mContext
                        .getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        new AsyncTask<String, String, String>() {
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                showDialog();
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                CollectionsManager.getInstance().removeSome(removeList, selected[0]);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                mRecyclerView.showTipViewAndDelayClose("成功移除书籍");
                                for (Recommend.RecommendBooks bean : removeList) {
                                    mAdapter.remove(bean);
                                }

                                hideDialog();
                            }
                        }.execute();

                    }
                })
                .setNegativeButton(mContext
                        .getString(R.string.cancel), null)
                .create().show();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.syncBookShelf();
    }

    public void refreshCollectionList(){
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionListBySort();
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setRefreshing(false);
    }

    //TODO
    @Override
    public void showUserBookShelf() {

    }

    @Override
    public void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list) {

    }

    @Override
    public void syncBookShelfCompleted() {
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionListBySort();
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setRefreshing(false);
        dismissDialog();
    }

    @Override
    public void showError() {
        mRecyclerView.setRefreshing(false);
    }

    @Override
    public void complete() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
