package com.xsnail.leisurereader.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseRVFragment;
import com.xsnail.leisurereader.common.OnRvItemClickListener;
import com.xsnail.leisurereader.data.bean.CommentList;
import com.xsnail.leisurereader.data.bean.Disscussion;
import com.xsnail.leisurereader.data.config.Constant;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.di.components.DaggerBookCommunityComponent;
import com.xsnail.leisurereader.mvp.contract.DiscussionDetailContract;
import com.xsnail.leisurereader.mvp.presenter.impl.DiscussionDetailPresenterImpl;
import com.xsnail.leisurereader.ui.adapter.BestCommentListAdapter;
import com.xsnail.leisurereader.ui.adapter.CommentListAdapter;
import com.xsnail.leisurereader.utils.FormatUtils;
import com.xsnail.leisurereader.view.BookContentTextView;
import com.xsnail.leisurereader.view.SupportDividerItemDecoration;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;
import com.yuyh.easyadapter.glide.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xsnail on 2017/3/25.
 */

public class DiscussionDetailFragment extends BaseRVFragment<DiscussionDetailPresenterImpl,CommentList.CommentsBean> implements DiscussionDetailContract.DiscussionDetailView,OnRvItemClickListener<CommentList.CommentsBean> {

    public static final String BUNDLE_ID = "index_id";

    private String id;
    private List<CommentList.CommentsBean> mBestCommentList = new ArrayList<>();
    private BestCommentListAdapter mBestCommentListAdapter;

    private HeaderViewHolder headerViewHolder;


    public static DiscussionDetailFragment newInstance(String id) {
        DiscussionDetailFragment fragment = new DiscussionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getBookDisscussionComments(id, start, limit);
    }

    @Override
    public void showBookDisscussionDetail(Disscussion disscussion) {
        Glide.with(mContext)
                .load(Constant.IMG_BASE_URL + disscussion.post.author.avatar)
                .placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(headerViewHolder.ivAvatar);

        headerViewHolder.tvNickName.setText(disscussion.post.author.nickname);
        headerViewHolder.tvTime.setText(FormatUtils.getDescriptionTimeFromDateString(disscussion.post.created));
        headerViewHolder.tvTitle.setText(disscussion.post.title);
        headerViewHolder.tvContent.setText(disscussion.post.content);
        headerViewHolder.tvCommentCount.setText(String.format(mContext.getString(R.string.comment_comment_count), disscussion.post.commentCount));
    }

    @Override
    public void showBestComments(CommentList list) {
        if (list.comments.isEmpty()) {
            gone(headerViewHolder.tvBestComments, headerViewHolder.rvBestComments);
        } else {
            mBestCommentList.addAll(list.comments);
            headerViewHolder.rvBestComments.setHasFixedSize(true);
            headerViewHolder.rvBestComments.setLayoutManager(new LinearLayoutManager(mContext));
            headerViewHolder.rvBestComments.addItemDecoration(new SupportDividerItemDecoration(mContext, LinearLayoutManager.VERTICAL, true));
            mBestCommentListAdapter = new BestCommentListAdapter(mContext, mBestCommentList);
            mBestCommentListAdapter.setOnItemClickListener(this);
            headerViewHolder.rvBestComments.setAdapter(mBestCommentListAdapter);
            visible(headerViewHolder.tvBestComments, headerViewHolder.rvBestComments);
        }
    }

    @Override
    public void showBookDisscussionComments(CommentList list) {
        mAdapter.addAll(list.comments);
        start = start + list.comments.size();
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerBookCommunityComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_easy_recyclerview;
    }

    @Override
    public void initDatas() {
        id = getArguments().getString(BUNDLE_ID);
        mPresenter.getBookDisscussionDetail(id);
        mPresenter.getBestComments(id);
        mPresenter.getBookDisscussionComments(id, start, limit);
    }

    @Override
    public void configViews() {
        initAdapter(CommentListAdapter.class, false, true);

        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_view_book_discussion_detail, parent, false);
                return headerView;
            }

            @Override
            public void onBindView(View headerView) {
                headerViewHolder = new HeaderViewHolder(headerView);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemClick(View view, int position, CommentList.CommentsBean data) {

    }

    static class HeaderViewHolder {
        @BindView(R.id.ivBookCover)
        ImageView ivAvatar;
        @BindView(R.id.tvBookTitle)
        TextView tvNickName;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        BookContentTextView tvContent;
        @BindView(R.id.tvBestComments)
        TextView tvBestComments;
        @BindView(R.id.rvBestComments)
        RecyclerView rvBestComments;
        @BindView(R.id.tvCommentCount)
        TextView tvCommentCount;

        public HeaderViewHolder(View view) {
            ButterKnife.bind(this, view);   //view绑定
        }
    }

}
