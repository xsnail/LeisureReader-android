package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.data.bean.CommentList;
import com.xsnail.leisurereader.data.bean.Disscussion;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.ui.fragment.DiscussionDetailFragment;

import butterknife.BindView;

/**
 * Created by xsnail on 2017/3/24.
 */

public class DiscussionDetailActivity extends BaseActivity{
    public static final String DISCUSSION_ID = "";
    private String bookId;

    public static void startActivity(Context context, String id) {
        Intent intent = new Intent(context, DiscussionDetailActivity.class);
        intent.putExtra(DISCUSSION_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("详情");
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
}

    @Override
    public void initDatas() {
        bookId = getIntent().getStringExtra(DISCUSSION_ID);
    }

    @Override
    public void initView() {
        getSupportFragmentManager().beginTransaction().add(R.id.fl_discussion_detail, DiscussionDetailFragment.newInstance(bookId)).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_discussion_detail;
    }
}
