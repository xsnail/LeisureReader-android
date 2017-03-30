package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.CommentList;
import com.xsnail.leisurereader.data.bean.Disscussion;

/**
 * Created by xsnail on 2017/3/24.
 */

public interface DiscussionDetailContract {
    interface DiscussionDetailView extends BaseContract.BaseView{
        void showBookDisscussionDetail(Disscussion disscussion);

        void showBestComments(CommentList list);

        void showBookDisscussionComments(CommentList list);
    }

    interface DiscussionDetailPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDisscussionDetail(String id);

        void getBestComments(String disscussionId);

        void getBookDisscussionComments(String disscussionId, int start, int limit);
    }
}
