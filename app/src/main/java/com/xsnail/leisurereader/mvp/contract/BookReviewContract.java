package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.HotReview;

import java.util.List;

/**
 * Created by xsnail on 2017/3/30.
 */

public interface BookReviewContract {
    interface BookReviewView extends BaseContract.BaseView{
        void showBookDetailReviewList(List<HotReview.Reviews> list, boolean isRefresh);
    }

    interface BookReviewPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDetailReviewList(String bookId, String sort, int start, int limit);
    }
}
