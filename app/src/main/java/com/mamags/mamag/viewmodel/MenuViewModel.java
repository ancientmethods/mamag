package com.mamags.mamag.viewmodel;

import android.app.Application;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.MenuRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samer on 30/09/2017.
 * infer its menuview in order to give the view access to the menuview interface
 */

public class MenuViewModel extends BaseViewModel<MenuView> {
    //interface object of rest api
    RestAPI restAPI;

    Disposable menuDisposable;
    Disposable createMenuDisposable;







    public MenuViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
    }

    public void getMenuList(FDSRequest fdsRequest) {
        //this should reload data if old data is still there
        if (menuDisposable != null) {
            menuDisposable.dispose();
        }

        menuDisposable = restAPI.getMenusList(fdsRequest)
                .subscribeOn(Schedulers.computation())
                .delay(3000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuListResponse -> {
                            if (menuListResponse.getMenuList()!=null && menuListResponse.getMenuList().size() > 0){
                                Iview.loadMenuResults(menuListResponse);
                            }
                            else{
                                Iview.showNoDataView();
                            }
                        },
                        throwable -> {Iview.error("error on menu list");Iview.showNoDataView();});

        compositeDisposable.add(menuDisposable);
    }



    public void createMenu(MenuRequest menu) {

        if (createMenuDisposable != null) {
            createMenuDisposable.dispose();
        }
        createMenuDisposable = restAPI.createMenu(menu)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fdSresponse -> Iview.createMenuResponse(fdSresponse),
                        throwable -> Iview.error("Error creating menu"))
        ;


        compositeDisposable.add(createMenuDisposable);
    }



}
