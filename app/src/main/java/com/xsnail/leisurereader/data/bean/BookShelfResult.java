package com.xsnail.leisurereader.data.bean;

import java.util.List;

/**
 * Created by xsnail on 2017/3/28.
 */

public class BookShelfResult extends Base {
    public String error;
    public Book data;

    public static class Book{
        public List<String> bookIdList;
        public String userName;
    }

    @Override
    public String toString() {
        return "BookShelfResult{" +
                "error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
