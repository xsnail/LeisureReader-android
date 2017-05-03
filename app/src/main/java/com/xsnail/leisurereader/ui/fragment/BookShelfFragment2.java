package com.xsnail.leisurereader.ui.fragment;


import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseFragment;
import com.xsnail.leisurereader.data.bean.BookMixAToc;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookShelfComponent;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.mvp.contract.BookShelfContract;
import com.xsnail.leisurereader.mvp.presenter.impl.BookShelfPresenterImpl;
import com.xsnail.leisurereader.ui.activity.BookReadActivity;
import com.xsnail.leisurereader.ui.activity.PdfReadActivity;
import com.xsnail.leisurereader.ui.adapter.ShelfAdapter;
import com.xsnail.leisurereader.view.DragGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;

import butterknife.BindView;


/**
 * Created by xsnail on 2017/2/15.
 */

public class BookShelfFragment2 extends BaseFragment implements BookShelfContract.BookShelfView {

    @BindView(R.id.bookShelf)
    DragGridView mDragGridView;

    @BindView(R.id.srl_book_shelf)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ShelfAdapter mAdapter;
    private List<Recommend.RecommendBooks> mRecommendBooksList = new ArrayList<>();
    //点击书本的位置
    private int itemPosition;

    @Inject
    BookShelfPresenterImpl presenter;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookShelfComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bookshelf2;
    }

    @Override
    public void attachView() {
        if(presenter != null){
            presenter.viewToPresenter(this);
        }
    }

    @Override
    protected void detachView() {
        if (presenter != null){
            presenter.detachView();
        }
    }

    @Override
    public void initDatas() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void configViews() {
        mAdapter = new ShelfAdapter(mContext,mRecommendBooksList);
        mDragGridView.setAdapter(mAdapter);
        mDragGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!isTop(mDragGridView)){
                    mSwipeRefreshLayout.setEnabled(false);
                }else{
                    mSwipeRefreshLayout.setEnabled(true);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    presenter.syncBookShelf();
            }
        });


        mDragGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mRecommendBooksList.size() > position) {
                    itemPosition = position;
                    final Recommend.RecommendBooks recommendBooks = mRecommendBooksList.get(itemPosition);
                    String bookName = mRecommendBooksList.get(itemPosition).title;
                    final String path = recommendBooks.path;
                    File file = new File(path);
                    if (!file.exists()){
                        new android.app.AlertDialog.Builder(mContext)
                                .setTitle(mContext.getString(R.string.app_name))
                                .setMessage(path + "文件不存在,是否删除该书本？")
                                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<Recommend.RecommendBooks> recommendBooksList = new ArrayList<>();
                                        recommendBooksList.add(recommendBooks);
                                        CollectionsManager.getInstance().removeSome(recommendBooksList,true);
                                    }
                                }).setCancelable(true).show();
                        return;
                    }

                    if(recommendBooks.path.endsWith(".pdf") && recommendBooks.isFromSD){
                        PdfReadActivity.startActivity(mContext,recommendBooks.path,recommendBooks._id);
                        return;
                    }
                    BookReadActivity.startActivity(mContext, recommendBooks, recommendBooks.isFromSD);
                }
            }
        });
        onRefresh();
    }

//    @OnClick(R.id.fab)
//    public void addBook(){
//        ((MainActivity)mContext).toolbarTitle.setText("书城");
//        ((MainActivity)mContext).mBottomNavigationBar.selectTab(1);
//        ((MainActivity)mContext).showFragment(1);
//    }

    private boolean isTop(GridView gridView){
        View firstView=null;
        if(gridView.getCount()==0){
            return true;
        }
        firstView=gridView.getChildAt(0);
        if(firstView!=null){
            if(gridView.getFirstVisiblePosition()==0&&firstView.getTop()==gridView.getListPaddingTop()){
                return true;
            }
        }else{
            return true;
        }

        return false;
    }

    public void onRefresh() {
        presenter.syncBookShelf();
    }

    public void refreshCollectionList(){
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionListBySort();
        mAdapter.clear();
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showUserBookShelf() {

    }

    @Override
    public void showBookToc(String bookId, List<BookMixAToc.mixToc.Chapters> list) {

    }

    @Override
    public void syncBookShelfCompleted() {
        List<Recommend.RecommendBooks> data = CollectionsManager.getInstance().getCollectionListBySort();
        if(data != null) {
            mAdapter.clear();
            mAdapter.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
        dismissDialog();
    }

    @Override
    public void showError() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void complete() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
