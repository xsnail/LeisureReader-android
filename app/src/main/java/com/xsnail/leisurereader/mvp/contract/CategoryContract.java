package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.BooksByCats;

/**
 * Created by xsnail on 2017/3/21.
 */

public interface CategoryContract {
    interface CategoryDetailView extends BaseContract.BaseView{
        void showCategoryList(BooksByCats data, boolean isRefresh);
    }

    interface CategoryDetailPresenter<T> extends BaseContract.BasePresenter<T>{
        void getCategoryList(String gender, String major, String minor, String type, int start, int limit);
    }
}
