package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.BookMixAToc;

import java.util.List;

/**
 * Created by xsnail on 2017/3/23.
 */

public interface BookShelfContract{
    interface BookShelfView extends BaseContract.BaseView{
        void showUserBookShelf();
        void showBookToc(String bookId,List<BookMixAToc.mixToc.Chapters> list);
        void syncBookShelfCompleted();
    }

    interface BookShelfPresenter<T> extends BaseContract.BasePresenter<T>{
        void syncBookShelf();
    }
}
