package com.xsnail.leisurereader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.data.bean.Recommend;
import com.xsnail.leisurereader.data.config.Constant;
import com.xsnail.leisurereader.manager.SettingManager;
import com.xsnail.leisurereader.utils.FileUtils;
import com.xsnail.leisurereader.utils.FormatUtils;
import com.xsnail.leisurereader.view.recyclerview.adapter.BaseViewHolder;
import com.xsnail.leisurereader.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.text.NumberFormat;


public class RecommendAdapter extends RecyclerArrayAdapter<Recommend.RecommendBooks> {

    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<Recommend.RecommendBooks>(parent, R.layout.item_recommend_list) {
            @Override
            public void setData(final Recommend.RecommendBooks item) {
                super.setData(item);
                String latelyUpdate = "";
                if (!TextUtils.isEmpty(FormatUtils.getDescriptionTimeFromDateString(item.updated))) {
                    latelyUpdate = FormatUtils.getDescriptionTimeFromDateString(item.updated) + ":";
                }

                holder.setText(R.id.tvRecommendTitle, item.title)
                        .setText(R.id.tvLatelyUpdate, latelyUpdate)
                        .setText(R.id.tvRecommendShort, item.lastChapter)
                        .setVisible(R.id.ivTopLabel, item.isTop)
                        .setVisible(R.id.ivUnReadDot, FormatUtils.formatZhuiShuDateString(item.updated)
                                .compareTo(item.recentReadingTime) > 0);

                if (item.path != null && item.path.endsWith(Constant.SUFFIX_PDF)) {
                    holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_pdf);
                } else if (item.isFromSD) {
                    holder.setImageResource(R.id.ivRecommendCover, R.drawable.ic_shelf_txt);
                    long fileLen = FileUtils.getChapterFile(item._id, 1).length();
                    if (fileLen > 10) {
                        double progress = ((double) SettingManager.getInstance().getReadProgress(item._id)[2]) / fileLen;
                        NumberFormat fmt = NumberFormat.getPercentInstance();
                        fmt.setMaximumFractionDigits(2);
                        holder.setText(R.id.tvRecommendShort, "当前阅读进度：" + fmt.format(progress));
                    }
                } else if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.cover,
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivRecommendCover, R.drawable.cover_default);
                }
            }
        };
    }

}
