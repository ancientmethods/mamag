package com.mamags.mamag.api;

import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.MenuRequest;
import com.mamags.mamag.model.Responses.MenuListResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by samer on 30/09/2017.
 */

public interface RestAPI {

    @GET("search/tweets.json")
    Flowable<List<Menu>> searchTweets(@Query("q") String query);


    @POST("getMenus")
    Single<MenuListResponse> getMenusList();

    @POST("processMenuItem")
    Single<FDSresponse> createMenu (@Body MenuRequest menuRequest);


    //
    // to use with tokens later guildwars
   /* @GET("account/finishers")
    Observable<AccountFinishersResponse> getAccountFinishers(@Header("Authorization") String token);
    Observable<AccountMasteriesResponse> getAccountMasteriesToken(@Query("access_token") String token);*/

}
