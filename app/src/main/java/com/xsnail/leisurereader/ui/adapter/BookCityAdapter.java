package com.xsnail.leisurereader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.common.OnRvItemClickListener;
import com.xsnail.leisurereader.data.bean.CategoryList;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * Created by xsnail on 2017/3/21.
 */

public class BookCityAdapter extends EasyRVAdapter<CategoryList.Category> {

    private OnRvItemClickListener itemClickListener;

    public BookCityAdapter(Context context, List<CategoryList.Category> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_category_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final CategoryList.Category item) {
        holder.setText(R.id.tvName, item.name)
                .setText(R.id.tvBookCount, String.format(mContext.getString(R.string
                        .category_book_count), item.bookCount));

        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }
}
