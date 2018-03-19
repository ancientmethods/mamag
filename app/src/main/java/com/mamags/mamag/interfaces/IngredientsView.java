package com.mamags.mamag.interfaces;

import com.mamags.mamag.model.Requests.IngredientsRequest;
import com.mamags.mamag.model.Responses.IngredientsResponse;

/**
 * Created by samer on 19/03/2018.
 */

public interface IngredientsView extends IView {
    void loadIngredientsList(IngredientsResponse ingredientListResponse);
}
