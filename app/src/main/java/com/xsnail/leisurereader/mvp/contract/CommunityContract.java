package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.DiscussionList;

import java.util.List;

/**
 * Created by xsnail on 2017/3/24.
 */

public interface CommunityContract {
    interface CommunityView extends BaseContract.BaseView{
        void showBookDisscussionList(List<DiscussionList.PostsBean> list, boolean isRefresh);
    }

    interface CommunityPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookDisscussionList(String block, String sort, String distillate, int start, int limit);
    }
}
