
package com.xsnail.leisurereader.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.data.config.Constant;
import com.xsnail.leisurereader.manager.SettingManager;
import com.xsnail.leisurereader.utils.FormatUtils;
import com.xsnail.leisurereader.view.XLHRatingBar;
import com.xsnail.leisurereader.view.recyclerview.adapter.BaseViewHolder;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;

public class BookDetailReviewAdapter extends RecyclerArrayAdapter<HotReview.Reviews> {


    public BookDetailReviewAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<HotReview.Reviews>(parent, R.layout.item_book_detai_hot_review_list) {
            @Override
            public void setData(HotReview.Reviews item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                                .book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvContent, String.valueOf(item.content))
                        .setText(R.id.tvHelpfulYes, String.valueOf(item.helpful.yes));
                holder.setVisible(R.id.tvTime, true);
                XLHRatingBar ratingBar = holder.getView(R.id.rating);
                ratingBar.setCountSelected(item.rating);
            }
        };
    }
}
