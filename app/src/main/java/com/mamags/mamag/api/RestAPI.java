package com.mamags.mamag.api;

import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Requests.MealRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Requests.MenuRequest;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Responses.MealTypeListResponse;
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


    @POST("getMenuItems")
    Single<MenuListResponse> getMenusList(@Body FDSRequest fdsRequest);

    @POST("processMenuItem")
    Single<FDSresponse> createMenu (@Body MenuRequest menuRequest);

    @POST("getMealTypes")
    Single<MealTypeListResponse> getMealTypes(@Body MealTypeRequest mealTypeRequest);

    @POST("processMealType")
    Single<FDSresponse> createMealType(@Body MealTypeRequest mealTypeRequest);


    @POST("processMealInfo")
    Single<FDSresponse> createMeal(@Body MealRequest mealTypeRequest);
    //
    // to use with tokens later guildwars
   /* @GET("account/finishers")
    Observable<AccountFinishersResponse> getAccountFinishers(@Header("Authorization") String token);
    Observable<AccountMasteriesResponse> getAccountMasteriesToken(@Query("access_token") String token);*/

    //to use with a variable path
  /*  @GET("characters/{id}")
    Observable<CharacterOverviewResponse> getCharacter(@Header("Authorization") String token,
                                                       @Path("id") String id);*/
}
