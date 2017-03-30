package com.xsnail.leisurereader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.data.bean.SearchDetail;
import com.xsnail.leisurereader.data.support.RefreshBookShelfEvent;
import com.xsnail.leisurereader.data.support.RefreshUserEvent;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCityComponent;
import com.xsnail.leisurereader.di.components.DaggerMainComponent;
import com.xsnail.leisurereader.manager.EventManager;
import com.xsnail.leisurereader.mvp.contract.MainContract;
import com.xsnail.leisurereader.mvp.presenter.impl.MainPresenterImpl;
import com.xsnail.leisurereader.ui.fragment.BookCityFragment;
import com.xsnail.leisurereader.ui.fragment.BookShelfFragment;
import com.xsnail.leisurereader.ui.fragment.CommunityFragment;
import com.xsnail.leisurereader.utils.LogUtils;
import com.xsnail.leisurereader.utils.SharedPreferencesUtil;
import com.xsnail.leisurereader.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenterImpl> implements NavigationView.OnNavigationItemSelectedListener,MainContract.MainView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar_title)
    public TextView toolbarTitle;
    @BindView(R.id.bottom_navigation_bar)
    public BottomNavigationBar mBottomNavigationBar;

    private BookShelfFragment bookShelfFragment;
    private BookCityFragment bookCityFragment;
    private CommunityFragment communityFragment;
    private TextView mUserName;
    private TextView mUserMotto;
    private SimpleDraweeView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        EventBus.getDefault().register(mContext);
    }

    @Override
    public void initView() {
        initNavigationView();
        initSearchView(searchView);
        initBottomMenu();
    }

    public void initSearchView(MaterialSearchView searchView) {
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
//        searchView.setSuggestions(mContext.getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Snackbar.make(findViewById(R.id.toolbar_container), "Query: " + query, Snackbar.LENGTH_LONG)
//                        .show();
                presenter.getSearchResultList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if(newText != null && !newText.isEmpty()) {
                    presenter.getQuerySuggestion(newText);
                }
                return false;
            }
        });
    }


    private void initNavigationView(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        View headerLayout= navigationView.inflateHeaderView(R.layout.main_slide);
        navigationView.setNavigationItemSelectedListener(this);
        initHeader(headerLayout);
    }

    public void initHeader(View headerLayout) {
//        LoginResult user= UserIntermediate.instance.getUser(context);
        mUserName = (TextView) headerLayout.findViewById(R.id.info_username);
        mUserMotto = (TextView) headerLayout.findViewById(R.id.info_phone);
        mAvatar=(SimpleDraweeView) headerLayout.findViewById(R.id.info_avatar);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SharedPreferencesUtil.getInstance().getBoolean("isLogin",false)) {
                    LoginActivity.startActivity(mContext);
                }
            }
        });
        initUserInfo();
    }

    private void initUserInfo() {
        if(SharedPreferencesUtil.getInstance().getBoolean("isLogin",false)){
            mUserName.setText(SharedPreferencesUtil.getInstance().getString("user"));
            mUserMotto.setText("欢迎您,"+SharedPreferencesUtil.getInstance().getString("user"));
            mAvatar.setImageResource(R.drawable.xsnail);
        }else {
            mUserName.setText("xsnail");
            mUserMotto.setText("面朝大海,春暖花开");
            mAvatar.setImageResource(R.drawable.default_avatar);
        }
    }

    public void initBottomMenu() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.drawable.tag_shelf, R.string.tag_shelf).setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.tag_books, R.string.tag_books).setActiveColorResource(R.color.black))
                .addItem(new BottomNavigationItem(R.drawable.tag_community, R.string.tag_community).setActiveColorResource(R.color.black))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        toolbarTitle.setText(R.string.tag_shelf);
                        showFragment(0);
                        break;
                    case 1:
                        toolbarTitle.setText(R.string.tag_books);
                        showFragment(1);
                        break;
                    case 2:
                        toolbarTitle.setText( R.string.tag_community);
                        showFragment(2);
                        break;
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        toolbarTitle.setText(R.string.tag_shelf);
        showFragment(0);
    }

    public void showFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0 : if (bookShelfFragment != null) {
                ft.show(bookShelfFragment);
            } else {
                bookShelfFragment = new BookShelfFragment();
                ft.add(R.id.frame_layout, bookShelfFragment);
            }
                break;
            case 1 : if (bookCityFragment != null) {
                ft.show(bookCityFragment);
            } else {
                bookCityFragment = new BookCityFragment();
                ft.add(R.id.frame_layout, bookCityFragment);
            }
                break;
            case 2 : if (communityFragment != null) {
                ft.show(communityFragment);
            } else {
                communityFragment = new CommunityFragment();
                ft.add(R.id.frame_layout, communityFragment);
            }
                break;
        }
        ft.commit();
    }

    public void hideAllFragment(FragmentTransaction ft) {
        if (bookShelfFragment != null) {
            ft.hide(bookShelfFragment);
        }
        if (bookCityFragment != null) {
            ft.hide(bookCityFragment);
        }
        if (communityFragment != null) {
            ft.hide(communityFragment);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        jump(id);
        return false;
    }

    private void jump(int id) {
        switch (id){
            case R.id.sync_shelf:
                if(SharedPreferencesUtil.getInstance().getBoolean("isLogin",false)){
                    presenter.syncBookShelf(SharedPreferencesUtil.getInstance().getString("user"));
                }else{
                    ToastUtils.showToast("请先登陆");
                }
                break;
            case R.id.scan_local:
                ScanLocalBookActivity.startActivity(mContext);
                break;
            case R.id.wifi_transfer:
                Intent intent = new Intent(MainActivity.this,WifiActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                SettingActivity.startActivity(mContext);
                break;
            case R.id.login_out:
                loginout();
                break;
            case R.id.about_me:
                break;
            default:
                break;
        }
    }

    private void loginout() {
        if(SharedPreferencesUtil.getInstance().getBoolean("isLogin",false)) {
            SharedPreferencesUtil.getInstance().putBoolean("isLogin", false);
            SharedPreferencesUtil.getInstance().putString("user", "");
            ToastUtils.showToast("退出登录成功");
        }else{
            ToastUtils.showToast("当前未登陆");
        }
        EventManager.refreshUser();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showAutoCompleteList(List<String> list) {
        String[] array = new String[list.size()];
        list.toArray(array);
        for(String s : array) {
            LogUtils.d("auto-complete", s);
        }
        searchView.setSuggestions(array);
    }

    @Override
    public void showSearchResultList(List<SearchDetail.SearchBooks> list) {
        SearchDetail.SearchBooks searchBooks = list.get(0);
        BookDetailActivity.startActivity(mContext,searchBooks._id);
    }

    @Override
    public void showSysncSucceed() {
        ToastUtils.showToast("同步成功");
    }

    @Override
    public void showSyncFailed(String error) {
        ToastUtils.showToast(error);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshUser(RefreshUserEvent event){
        initUserInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void RefreshBookShelf(RefreshBookShelfEvent event){
        if(bookShelfFragment != null){
            bookShelfFragment.onRefresh();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mContext);
    }
}
