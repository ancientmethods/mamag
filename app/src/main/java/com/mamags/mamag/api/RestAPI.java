package com.mamags.mamag.api;

import com.mamags.mamag.model.Menu;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samer on 30/09/2017.
 */

public interface RestAPI {

    @GET("search/tweets.json")
    Flowable<List<Menu>> searchTweets(@Query("q") String query);
}
