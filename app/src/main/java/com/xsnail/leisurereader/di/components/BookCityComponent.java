package com.xsnail.leisurereader.di.components;

import com.xsnail.leisurereader.ui.activity.BookDetailActivity;
import com.xsnail.leisurereader.ui.fragment.BookCityFragment;
import com.xsnail.leisurereader.ui.fragment.CategoryDetailFragment;

import dagger.Component;

/**
 * Created by xsnail on 2017/3/21.
 */

@Component(dependencies = AppComponent.class)
public interface BookCityComponent {
    BookCityFragment inject(BookCityFragment bookCityFragment);
    CategoryDetailFragment inject(CategoryDetailFragment categoryDetailFragment);
    BookDetailActivity inject(BookDetailActivity bookDetailActivity);
}
