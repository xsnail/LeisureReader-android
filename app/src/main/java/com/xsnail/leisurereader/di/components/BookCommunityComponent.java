package com.xsnail.leisurereader.di.components;

import com.xsnail.leisurereader.ui.fragment.BookCommunityFragment;
import com.xsnail.leisurereader.ui.fragment.BookReviewFragment;
import com.xsnail.leisurereader.ui.fragment.CommunityFragment;
import com.xsnail.leisurereader.ui.fragment.DiscussionDetailFragment;

import dagger.Component;

/**
 * Created by xsnail on 2017/3/22.
 */

@Component(dependencies = AppComponent.class)
public interface BookCommunityComponent {
    CommunityFragment inject(CommunityFragment communityFragment);
    DiscussionDetailFragment inject(DiscussionDetailFragment  discussionDetailFragment);
    BookCommunityFragment inject(BookCommunityFragment bookCommunityFragment);
    BookReviewFragment inject(BookReviewFragment bookReviewFragment);
}
