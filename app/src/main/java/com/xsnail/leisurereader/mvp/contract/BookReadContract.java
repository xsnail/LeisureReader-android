package com.xsnail.leisurereader.mvp.contract;

import com.xsnail.leisurereader.base.BaseContract;
import com.xsnail.leisurereader.data.bean.BookMixAToc;
import com.xsnail.leisurereader.data.bean.ChapterRead;

import java.util.List;

/**
 * Created by xsnail on 2017/3/23.
 */

public interface BookReadContract {
    interface BookReadView extends BaseContract.BaseView{
        void showBookToc(List<BookMixAToc.mixToc.Chapters> list);

        void showChapterRead(ChapterRead.Chapter data, int chapter);

        void netError(int chapter);//添加网络处理异常接口
    }

    interface BookReadPresenter<T> extends BaseContract.BasePresenter<T>{
        void getBookMixAToc(String bookId, String view);
        void getChapterRead(String url, int chapter);
    }
}
