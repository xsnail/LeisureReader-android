package com.xsnail.leisurereader.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.data.config.Constant;
import com.xsnail.leisurereader.manager.CollectionsManager;
import com.xsnail.leisurereader.view.DragGridListener;
import com.xsnail.leisurereader.view.DragGridView;
import com.xsnail.leisurereader.view.glide.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/17.
 */
public class ShelfAdapter extends BaseAdapter implements DragGridListener {
    private Context mContext;
    private List<Recommend.RecommendBooks> mRecommendBooksList;
    private static LayoutInflater inflater = null;
    private int mHidePosition = -1;
    protected List<AsyncTask<Void, Void, Boolean>> myAsyncTasks = new ArrayList<>();
    private int[] firstLocation;
    public ShelfAdapter(Context context, List<Recommend.RecommendBooks> recommendBooksList){
        this.mContext = context;
        this.mRecommendBooksList = recommendBooksList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear(){
        if(mRecommendBooksList != null){
            mRecommendBooksList.clear();
        }
    }

    public void addAll(List<Recommend.RecommendBooks> recommendBooksList){
        if(mRecommendBooksList != null){
            mRecommendBooksList.addAll(recommendBooksList);
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //背景书架的draw需要用到item的高度
        if(mRecommendBooksList.size() < 10){
            return 10;
        }else{
            return mRecommendBooksList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mRecommendBooksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        final ViewHolder viewHolder;
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.shelfitem, null);
            viewHolder = new ViewHolder(contentView);
            contentView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) contentView.getTag();
        }

        if(mRecommendBooksList.size() > position){
            //DragGridView  解决复用问题
            if(position == mHidePosition){
                contentView.setVisibility(View.INVISIBLE);
            }else {
                contentView.setVisibility(View.VISIBLE);
            }
            if (DragGridView.getShowDeleteButton()) {
                viewHolder.deleteItem_IB.setVisibility(View.VISIBLE);
            }else {
                viewHolder.deleteItem_IB.setVisibility(View.INVISIBLE);
            }
            viewHolder.name.setVisibility(View.VISIBLE);
            viewHolder.name.setText(mRecommendBooksList.get(position).title);
            Glide.with(mContext).load(Constant.IMG_BASE_URL + mRecommendBooksList.get(position).cover).placeholder(R.mipmap.cover_default_new) .transform(new GlideRoundTransform(mContext)).into(viewHolder.imageView);

        }else {
            contentView.setVisibility(View.INVISIBLE);
        }
        return contentView;
    }

    static class ViewHolder {
        @BindView(R.id.ib_close)
        ImageButton deleteItem_IB;
        @BindView(R.id.tv_name)
        TextView name;
        @BindView(R.id.iv_book_shelf)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Drag移动时item交换数据,并在数据库中更新交换后的位置数据
     * @param oldPosition
     * @param newPosition
     */
    @Override
    public void reorderItems(int oldPosition, int newPosition) {
    }

    /**
     * 隐藏item
     * @param hidePosition
     */
    @Override
    public void setHideItem(int hidePosition) {
        this.mHidePosition = hidePosition;
        notifyDataSetChanged();
    }

    /**
     * 删除书本
     * @param deletePosition
     */
    @Override
    public void removeItem(int deletePosition) {
        List<Recommend.RecommendBooks> recommendBooksList = new ArrayList<>();
        recommendBooksList.add(mRecommendBooksList.get(deletePosition));
        CollectionsManager.getInstance().removeSome(recommendBooksList,true);
    }

    /**
     * Book打开后位置移动到第一位
     * @param openPosition
     */
    @Override
    public void setItemToFirst(int openPosition) {
    }

    @Override
    public void nitifyDataRefresh() {
        notifyDataSetChanged();
    }

    public void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        myAsyncTasks.add(asyncTask.execute());
    }

}
