package com.mamags.mamag.viewmodel;

import android.app.Application;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.model.Requests.MealRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Requests.MenuRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Samer on 10/11/2017.
 */

public class CreateMenuViewModel extends BaseViewModel {
    //interface object of rest api
    RestAPI restAPI;
    Disposable createMenuDisposable;
    Disposable createMealTypeDisposable;
    Disposable createMealDisposable;

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
                .subscribe(fdSresponse -> Iview.processStandardResponse(fdSresponse,true),
                        throwable -> Iview.error("Error creating menu"))
        ;


        compositeDisposable.add(createMenuDisposable);
    }

    public void createMealTypeDisposable(MealTypeRequest mealTypeRequest) {

        if (createMealTypeDisposable != null) {
            createMealTypeDisposable.dispose();
        }
        createMealTypeDisposable = restAPI.createMealType(mealTypeRequest)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fdSresponse -> Iview.processStandardResponse(fdSresponse,true),
                        throwable -> Iview.error("Error creating meal type "))
        ;


        compositeDisposable.add(createMealTypeDisposable);
    }

    public void createMealDisposable(MealRequest mealRequest) {

        if (createMealDisposable != null) {
            createMealDisposable.dispose();
        }
        createMealDisposable = restAPI.createMeal(mealRequest)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fdSresponse -> Iview.processStandardResponse(fdSresponse,true),
                        throwable -> Iview.error("Error creating meal "))
        ;


        compositeDisposable.add(createMealDisposable);
    }
}
