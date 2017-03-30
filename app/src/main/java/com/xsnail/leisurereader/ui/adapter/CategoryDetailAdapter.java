package com.xsnail.leisurereader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.data.bean.BooksByCats;
import com.xsnail.leisurereader.data.config.Constant;
import com.xsnail.leisurereader.manager.SettingManager;
import com.xsnail.leisurereader.view.recyclerview.adapter.BaseViewHolder;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;


public class CategoryDetailAdapter extends RecyclerArrayAdapter<BooksByCats.BooksBean> {

    public CategoryDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BooksByCats.BooksBean>(parent, R.layout.item_sub_category_list) {
            @Override
            public void setData(BooksByCats.BooksBean item) {
                super.setData(item);
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivSubCateCover, Constant.IMG_BASE_URL + item.cover,
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivSubCateCover, R.drawable.cover_default);
                }

                holder.setText(R.id.tvSubCateTitle, item.title)
                        .setText(R.id.tvSubCateAuthor, (item.author == null ? "未知" : item.author) + " | " + (item.majorCate == null ? "未知" : item.majorCate))
                        .setText(R.id.tvSubCateShort, item.shortIntro)
                        .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg),
                                item.latelyFollower,
                                TextUtils.isEmpty(item.retentionRatio) ? "0" : item.retentionRatio));
            }
        };
    }
}
