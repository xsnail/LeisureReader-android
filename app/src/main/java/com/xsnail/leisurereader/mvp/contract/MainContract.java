package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.SearchDetail;

import java.util.List;

/**
 * Created by xsnail on 2017/3/22.
 */

public interface MainContract {
    interface MainView extends BaseContract.BaseView{
        void showAutoCompleteList(List<String> list);
        void showSearchResultList(List<SearchDetail.SearchBooks> list);
        void showSysncSucceed(String error);
        void showSyncFailed(String error);
    }

    interface MainPresenter<T> extends BaseContract.BasePresenter<T>{
        void getQuerySuggestion(String query);
        void getSearchResultList(String query);
        void syncBookShelf(String userName);
    }
}
