package com.xsnail.leisurereader.data.bean;

import java.util.List;

/**
 * 热门书评
 * http://api.zhuishushenqi.com/post/review/best-by-book?book=50c54ad08380e4f81500002a
 */
public class HotReview extends Base {

    public List<Reviews> reviews;

    public static class Reviews {
        public String _id;
        public int rating;
        public String content;
        public String title;

        public Author author;

        public Helpful helpful;
        public int likeCount;
        public String state;
        public String updated;
        public String created;
        public int commentCount;

        public static class Author {
            public String _id;
            public String avatar;
            public String nickname;
            public String type;
            public int lv;
            public String gender;
        }

        public static class Helpful {
            public int yes;
            public int total;
            public int no;
        }
    }
}
