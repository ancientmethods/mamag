package com.mamags.mamag.model.Responses;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.MealType;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by samer on 11/11/2017.
 */

public class MealTypeListResponse extends FDSresponse {

    @SerializedName("Items")
    @Nullable
    private List<MealType> mealTypeList;

    public List<MealType> getMealTypeList() {
        return mealTypeList;
    }

    public void setMealTypeList(List<MealType> mealTypeList) {
        this.mealTypeList = mealTypeList;
    }
}