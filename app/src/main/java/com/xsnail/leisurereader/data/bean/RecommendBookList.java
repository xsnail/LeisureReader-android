package com.xsnail.leisurereader.data.bean;

import java.util.List;

/**
 * 推荐书单
 * http://api.zhuishushenqi.com/book-list/50c54ad08380e4f81500002a/recommend
 */
public class RecommendBookList extends Base {

    public List<RecommendBook> booklists;

    public static class RecommendBook {
        public String id;
        public String title;
        public String author;
        public String desc;
        public int bookCount;
        public String cover;
        public int collectorCount;
    }
}
