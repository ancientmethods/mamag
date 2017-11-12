package com.mamags.mamag.viewmodel;

import android.app.Application;

import com.mamags.mamag.BaseViewModel;
import com.mamags.mamag.api.RestAPI;
import com.mamags.mamag.interfaces.MealTypeView;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samer on 11/11/2017.
 */

public class MealTypeViewModel extends BaseViewModel<MealTypeView>{

    //interface object of rest api
    RestAPI restAPI;

    Disposable mealTypeDisposable;


    public MealTypeViewModel(Application application, RestAPI restAPI) {
        super(application);
        this.restAPI = restAPI;
    }

    public void getMealTypeList(MealTypeRequest mealTypeRequest) {
        //this should reload data if old data is still there
        if (mealTypeDisposable != null) {
            mealTypeDisposable.dispose();
        }

        mealTypeDisposable = restAPI.getMealTypes(mealTypeRequest)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealTypeListResponse -> {
                            if (mealTypeListResponse.getMealTypeList()!=null && mealTypeListResponse.getMealTypeList().size() > 0){
                                Iview.loadMealTypeResults(mealTypeListResponse);
                            }
                            else{
                                Iview.showNoDataView();
                            }
                        },
                        throwable -> {Iview.error("error on menu list");Iview.showNoDataView();});

        compositeDisposable.add(mealTypeDisposable);
    }

}
