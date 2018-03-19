package com.mamags.mamag.model.Responses;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.Ingredient;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by samer on 19/03/2018.
 */

public class IngredientsResponse extends FDSresponse {

    @SerializedName("Items")
    @Nullable
    private List<Ingredient> ingredientList;

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }


}
