package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.ui.fragment.BookCommunityFragment;
import com.xsnail.leisurereader.ui.fragment.BookReviewFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by xsnail on 2017/3/30.
 */

public class BookCommunityActivity extends BaseActivity {
    public static final String INTENT_ID = "bookId";
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_INDEX = "index";

    public static void startActivity(Context context, String bookId, String title, int index) {
        context.startActivity(new Intent(context, BookCommunityActivity.class)
                .putExtra(INTENT_ID, bookId)
                .putExtra(INTENT_TITLE, title)
                .putExtra(INTENT_INDEX, index));
    }

    private String bookId;
    private String title;
    private int index;

    @BindView(R.id.indicatorSubRank)
    SlidingTabLayout mIndicator;

    @BindView(R.id.viewpagerSubRank)
    ViewPager mViewPager;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    private int[] select = new int[]{0, 0};

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_detail_community;
    }

    @Override
    public void initToolBar() {
        bookId = getIntent().getStringExtra(INTENT_ID);
        title = getIntent().getStringExtra(INTENT_TITLE);
        index = getIntent().getIntExtra(INTENT_INDEX, 0);
        mCommonToolbar.setTitle(title);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.bookdetail_community_tabs));

        mTabContents = new ArrayList<>();
        mTabContents.add(BookCommunityFragment.newInstance(bookId));
        mTabContents.add(BookReviewFragment.newInstance(bookId));

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }

    @Override
    public void initView() {
//        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mIndicator.setViewPager(mViewPager, (String[]) mDatas.toArray());
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
