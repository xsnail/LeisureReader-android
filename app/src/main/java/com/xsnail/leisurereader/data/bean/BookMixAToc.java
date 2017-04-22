package com.xsnail.leisurereader.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * url: http://api.zhuishushenqi.com/mix-atoc/50c54ad08380e4f81500002a
 */
public class BookMixAToc extends Base {

    public mixToc mixToc;
    public static class mixToc implements Serializable {
        public String _id;
        //书籍id
        public String book;
        //书籍最新更新时间
        public String chaptersUpdated;


        //章节集合
        public List<Chapters> chapters;

        //每一章内容
        public static class Chapters implements Serializable {
            public String title;
            public String link;
            public boolean unreadble;


            public Chapters() {
            }

            public Chapters(String title, String link) {
                this.title = title;
                this.link = link;
            }
        }
    }

}
