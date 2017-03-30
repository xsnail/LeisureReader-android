package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.CategoryList;

/**
 * Created by xsnail on 2017/3/21.
 */

public interface CityContract {
    interface CityView extends BaseContract.BaseView{
        void showCategoryList(CategoryList categoryList);
    }

    interface CityPresenter<T> extends BaseContract.BasePresenter<T>{
        void getCategoryList();
    }
}
