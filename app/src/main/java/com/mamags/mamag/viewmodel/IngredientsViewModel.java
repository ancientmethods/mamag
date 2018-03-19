package com.mamags.mamag.viewmodel;

import android.app.Application;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.IngredientsView;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.Requests.IngredientsRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samer on 19/03/2018.
 */

public class IngredientsViewModel extends BaseViewModel<IngredientsView> {

    //interface object of rest api
    RestAPI restAPI;

    Disposable ingredientDisposable;


    public IngredientsViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
    }

    public void getIngredientsList(IngredientsRequest ingredientsRequest) {
        //this should reload data if old data is still there
        if (ingredientDisposable != null) {
            ingredientDisposable.dispose();
        }

        ingredientDisposable = restAPI.getIngredients(ingredientsRequest)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientsListResponse -> {
                            if (ingredientsListResponse.getIngredientList()!=null && ingredientsListResponse.getIngredientList().size() > 0){
                                Iview.loadIngredientsList(ingredientsListResponse);
                            }
                            else{
                                Iview.showNoDataView();
                            }
                        },
                        throwable -> {Iview.error("error on ingredients list");Iview.showNoDataView();});

        compositeDisposable.add(ingredientDisposable);
    }

}

