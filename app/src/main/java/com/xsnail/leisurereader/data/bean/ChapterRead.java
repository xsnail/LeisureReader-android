
package com.xsnail.leisurereader.data.bean;

import java.io.Serializable;

/**
 *http://chapter2.zhuishushenqi.com/chapter/http:%2F%2Fbook.my716.com%2FgetBooks.aspx%3Fmethod=content&bookId=41894&chapterFile=1%2520%25E7%2596%25AF%25E7%258B%2582%25E5%25B9%25B4%25E4%25BB%25A31.txt&chinese
 */
public class ChapterRead extends Base {

    //章节
    public Chapter chapter;

    public static class Chapter implements Serializable{
        //这章的标题
        public String title;
        //这章的内容
        public String body;

        public Chapter(String title, String body) {
            this.title = title;
            this.body = body;
        }

    }
}
