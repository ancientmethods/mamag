package com.mamags.mamag.viewmodel;

import android.app.Application;
import android.util.Log;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.model.FDSresponse;
import com.mamags.mamag.model.Menu;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by samer on 30/09/2017.
 * infer its menuview in order to give the view access to the menuview interface
 */

public class MenuViewModel extends BaseViewModel<MenuView> {
    //interface object of rest api
    RestAPI restAPI;

    Disposable menuDisposable;

    public MenuViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
    }

    public void getMenuList() {
        //this should reload data if old data is still there
        if(menuDisposable!=null){
            menuDisposable.dispose();
        }

        menuDisposable = restAPI.searchTweets("paramter")
                                .delay(3000, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(menusList -> Iview.loadMenuResults(menusList),
                                           throwable -> Iview.error("error on menu list"));

        compositeDisposable.add(menuDisposable);
    }


    public void createMenu(Menu menu) {
       Call<FDSresponse> fdSresponse= restAPI.createMenu(menu);

        fdSresponse.enqueue(new Callback<FDSresponse>() {

            @Override
            public void onResponse(Call<FDSresponse> call, Response<FDSresponse> response) {
               FDSresponse response1 = response.body();
                Log.d("mamag",response1.getErrorMessage());
                Log.d("mamag",String.valueOf(response.code()));


            }

            @Override
            public void onFailure(Call<FDSresponse> call, Throwable t) {

            }
        });


    }
}
