package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.DiscussionList;

import java.util.List;

/**
 * Created by xsnail on 2017/3/30.
 */

public interface BookCommunityContract {
    interface BookCommunityView extends BaseContract.BaseView{
        void showBookDetailDiscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface BookCommunityPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDetailDiscussionList(String bookId, String sort, int start, int limit);
    }
}
