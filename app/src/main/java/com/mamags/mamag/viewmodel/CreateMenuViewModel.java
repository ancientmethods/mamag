package com.mamags.mamag.viewmodel;

import android.app.Application;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.ICreateMenu;
import com.mamags.mamag.model.MenuRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Samer on 10/11/2017.
 */

public class CreateMenuViewModel extends BaseViewModel<ICreateMenu> {
    //interface object of rest api
    RestAPI restAPI;
    Disposable createMenuDisposable;

    public CreateMenuViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
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
