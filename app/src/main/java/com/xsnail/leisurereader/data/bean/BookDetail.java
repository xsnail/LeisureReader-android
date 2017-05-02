package com.xsnail.leisurereader.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 书籍详情
 * http://api.zhuishushenqi.com/book/50c54ad08380e4f81500002a
 */
public class BookDetail implements Serializable {

    public String _id;
    public String author;
    public int banned;
    public String cover;
    public String creater;
    public Object dramaPoint;
    public int followerCount;
    public int gradeCount;
    public boolean isSerial;
    public String lastChapter;
    public int latelyFollower;
    public String longIntro;
    public int postCount; // 社区帖子数
    public int serializeWordCount;
    public String site;
    public String title;
    public Object totalPoint;
    public String type;
    public String updated;
    public Object writingPoint;
    public boolean hasNotice;
    public int tagStuck;
    public int chaptersCount;
    public int tocCount;
    public String tocUpdated;
    public String retentionRatio;
    public boolean hasCmread;
    public String thirdFlagsUpdated;
    public int wordCount;
    public String cat;
    public String majorCate;
    public String minorCate;
    public int reviewCount;
    public int totalFollower;
    public boolean cpOnly;
    public boolean hasCp;
    public boolean _le;
    public List<String> tags;
    public List<String> tocs;
    public List<String> categories;
    public Object gender; // MLGB, 偶尔是String，偶尔是Array
}
