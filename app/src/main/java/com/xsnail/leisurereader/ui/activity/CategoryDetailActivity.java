package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.ui.fragment.CategoryDetailFragment;
import com.xsnail.leisurereader.view.RVPIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;

/**
 * Created by xsnail on 2017/3/21.
 */

public class CategoryDetailActivity extends BaseActivity{

    public static final String INTENT_CATE_NAME = "name";
    public static final String INTENT_GENDER = "gender";

    private String cate = "";
    private String gender = "";

    private List<String> mDatas;
    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;

    @BindView(R.id.indicatorSub)
    RVPIndicator mIndicator;
    @BindView(R.id.viewpagerSub)
    ViewPager mViewPager;

    public static void startActivity(Context context, String name, String gender) {
        Intent intent = new Intent(context, CategoryDetailActivity.class);
        intent.putExtra(INTENT_CATE_NAME, name);
        intent.putExtra(INTENT_GENDER, gender);
        context.startActivity(intent);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }


    @Override
    public void initToolBar() {
        cate = getIntent().getStringExtra(INTENT_CATE_NAME);
        gender = getIntent().getStringExtra(INTENT_GENDER);
        mCommonToolbar.setTitle(cate);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
        mDatas = Arrays.asList(getResources().getStringArray(R.array.sub_tabs));

        mTabContents = new ArrayList<>();
        mTabContents.add(CategoryDetailFragment.newInstance(cate, "", gender, "new"));
        mTabContents.add(CategoryDetailFragment.newInstance(cate, "", gender, "hot"));
        mTabContents.add(CategoryDetailFragment.newInstance(cate, "", gender, "reputation"));
        mTabContents.add(CategoryDetailFragment.newInstance(cate, "", gender, "over"));

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
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mIndicator.setViewPager(mViewPager, 0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_category_detail;
    }


}
