package com.mamags.mamag.model.Requests;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.MealInfo;
import com.mamags.mamag.model.MealType;

/**
 * Created by Samer on 14/11/2017.
 */

public class MealRequest extends FDSRequest{

    @SerializedName("MealInfoEntity")
    MealInfo mealInfo;

    public MealInfo getMealInfo() {
        return mealInfo;
    }

    public void setMealInfo(MealInfo mealInfo) {
        this.mealInfo = mealInfo;
    }

}
