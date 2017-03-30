package com.xsnail.leisurereader.data.bean;

import java.util.List;

/**
 * Created by xsnail on 2017/3/21.
 */

public class CategoryList extends Base {

    public List<Category> male;

    public List<Category> female;

    public static class Category{
        public String name;
        public int bookCount;
    }

    @Override
    public String toString() {
        return "CategoryList{" +
                "boyList=" + male +
                ", girlList=" + female +
                '}';
    }
}
