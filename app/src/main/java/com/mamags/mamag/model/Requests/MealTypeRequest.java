package com.mamags.mamag.model.Requests;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.MealType;

/**
 * Created by samer on 11/11/2017.
 * Defines the Request entity that adds a new Meal Type to the system
 */

public class MealTypeRequest extends FDSRequest {
    @SerializedName("MealTypeEntity")
    MealType mealType;

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
