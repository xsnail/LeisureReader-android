package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.SearchDetail;

import java.util.List;

/**
 * Created by xsnail on 2017/3/22.
 */

public interface SearchDetailContract{
    interface SearchDetailView extends BaseContract.BaseView{
        void showSearchResultList(List<SearchDetail.SearchBooks> list);
    }

    interface SearchDetailPresenter<T> extends BaseContract.BasePresenter<T>{
        void getSearchResultList(String query);
    }
}


