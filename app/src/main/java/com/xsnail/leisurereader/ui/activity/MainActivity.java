package com.xsnail.leisurereader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.ui.fragment.BookCityFragment;
import com.xsnail.leisurereader.ui.fragment.BookShelfFragment;
import com.xsnail.leisurereader.ui.fragment.CommunityFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    private BookShelfFragment bookShelfFragment;
    private BookCityFragment bookCityFragment;
    private CommunityFragment communityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    protected void setViewToPresenter() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initView() {
        initNavigationView();
        initSearchView();
        initBottomMenu();
    }

    private void initSearchView() {

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
//        User user= UserIntermediate.instance.getUser(context);
        TextView username=(TextView) headerLayout.findViewById(R.id.info_username);
        TextView phone=(TextView) headerLayout.findViewById(R.id.info_phone);
        SimpleDraweeView avatar=(SimpleDraweeView) headerLayout.findViewById(R.id.info_avatar);
        username.setText("xsnail");
        phone.setText("测试");
        avatar.setImageURI("http://112.74.178.151/static/userImages/myxsnail.png");
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
    protected int getLayoutId() {
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
                break;
            case R.id.scan_local:
                break;
            case R.id.wifi_transfer:
                Intent intent = new Intent(MainActivity.this,WifiActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                break;
            case R.id.login_out:
                break;
            case R.id.about_me:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
