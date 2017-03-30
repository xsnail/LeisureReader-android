package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.BookDetail;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.data.bean.RecommendBookList;

import java.util.List;

/**
 * Created by xsnail on 2017/3/22.
 */

public interface BookDetailContract {
    interface BookDetailView extends BaseContract.BaseView{
        void showBookDetail(BookDetail data);
        void showHotReview(List<HotReview.Reviews> list);
        void showRecommendBookList(List<RecommendBookList.RecommendBook> list);
    }

    interface BookDetailPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDetail(String bookId);
        void getHotReview(String book);
        void getRecommendBookList(String bookId, String limit);
    }
}
