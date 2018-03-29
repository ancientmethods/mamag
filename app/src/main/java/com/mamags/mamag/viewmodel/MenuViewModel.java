package com.mamags.mamag.viewmodel;

import android.app.Application;
import android.graphics.Movie;
import android.util.Log;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.MenuView;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Responses.MenuListResponse;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samer on 30/09/2017.
 * infer its menuview in order to give the view access to the menuview interface
 */

public class MenuViewModel extends BaseViewModel<MenuView> {
    //interface object of rest api
    RestAPI restAPI;
    Observable<MenuListResponse> menuListResponseObservable;


    public MenuViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
    }

    public void getMenuList(FDSRequest fdsRequest) {

        menuListResponseObservable = restAPI.getMenusList(fdsRequest)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Iview.showNoDataView());

        Disposable observer = menuListResponseObservable.subscribe(menuListResponse -> {
                    if (menuListResponse.getMenuList()!=null && menuListResponse.getMenuList().size() > 0){
                        Iview.loadMenuResults(menuListResponse);
                    }
                    else{
                        Iview.showNoDataView();
                    }
                },
                throwable -> {Iview.error("error on menu list");Iview.showNoDataView();});


        compositeDisposable.add(observer);

    }
}