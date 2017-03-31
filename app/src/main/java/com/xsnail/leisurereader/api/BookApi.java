package com.xsnail.leisurereader.api;



import com.xsnail.leisurereader.data.bean.AutoComplete;
import com.xsnail.leisurereader.data.bean.BookDetail;
import com.xsnail.leisurereader.data.bean.BookMixAToc;
import com.xsnail.leisurereader.data.bean.BooksByCats;
import com.xsnail.leisurereader.data.bean.CategoryList;
import com.xsnail.leisurereader.data.bean.ChapterRead;
import com.xsnail.leisurereader.data.bean.CommentList;
import com.xsnail.leisurereader.data.bean.DiscussionList;
import com.xsnail.leisurereader.data.bean.Disscussion;
import com.xsnail.leisurereader.data.bean.HotReview;
import com.xsnail.leisurereader.data.bean.LoginResult;
import com.xsnail.leisurereader.data.bean.RecommendBookList;
import com.xsnail.leisurereader.data.bean.SearchDetail;
import com.xsnail.leisurereader.data.bean.UserBookShelf;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by xsnail on 2016/11/21.
 */

public class BookApi {
    private static BookApi sInstance;

    private BookApiService service;

    private BookApi(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getInstance(OkHttpClient okHttpClient){
        if(sInstance == null){
            sInstance = new BookApi(okHttpClient);
        }
        return sInstance;
    }

    public synchronized Observable<CategoryList> getCategoryList() {
        return service.getCategoryList();
    }

    public Observable<BooksByCats> getBooksByCats(String gender, String type, String major, String minor, int start, @Query("limit") int limit) {
        return service.getBooksByCats(gender, type, major, minor, start, limit);
    }

    public Observable<BookDetail> getBookDetail(String bookId) {
        return service.getBookDetail(bookId);
    }

    public Observable<HotReview> getHotReview(String book) {
        return service.getHotReview(book);
    }

    public Observable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return service.getRecommendBookList(bookId, limit);
    }

    //关键字自动补全
    public Observable<AutoComplete> getAutoComplete(String query) {
        return service.autoComplete(query);
    }

    public Observable<SearchDetail> getSearchResult(String query) {
        return service.searchBooks(query);
    }

    public Observable<UserBookShelf> getUserBookShelf(LoginResult loginResult) {
        return service.getUserBookShelf(loginResult);
    }

    public Observable<BookMixAToc> getBookMixAToc(String bookId, String view) {
        return service.getBookMixAToc(bookId, view);
    }

    public synchronized Observable<ChapterRead> getChapterRead(String url) {
        return service.getChapterRead(url);
    }

    public Observable<DiscussionList> getBookDisscussionList(String block, String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookDisscussionList(block, duration, sort, type, start, limit, distillate);
    }


    public Observable<Disscussion> getBookDisscussionDetail(String disscussionId) {
        return service.getBookDisscussionDetail(disscussionId);
    }

    public Observable<CommentList> getBestComments(String disscussionId) {
        return service.getBestComments(disscussionId);
    }

    public Observable<CommentList> getBookDisscussionComments(String disscussionId, String start, String limit) {
        return service.getBookDisscussionComments(disscussionId, start, limit);
    }


    public Observable<DiscussionList> getBookDetailDisscussionList(String book, String sort, String type, String start, String limit) {
        return service.getBookDetailDisscussionList(book, sort, type, start, limit);
    }

    public Observable<HotReview> getBookDetailReviewList(String book, String sort, String start, String limit) {
        return service.getBookDetailReviewList(book, sort, start, limit);
    }
}
