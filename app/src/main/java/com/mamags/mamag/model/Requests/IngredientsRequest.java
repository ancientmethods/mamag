package com.mamags.mamag.model.Requests;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Ingredient;

/**
 * Created by samer on 19/03/2018.
 */

public class IngredientsRequest  extends FDSRequest {

    @SerializedName("IngredientEnity")
    Ingredient ingredient;

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
