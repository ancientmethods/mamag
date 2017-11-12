package com.mamags.mamag.interfaces;

import com.mamags.mamag.model.Responses.MealTypeListResponse;

/**
 * Created by samer on 11/11/2017.
 */

public interface MealTypeView extends IView {

    void loadMealTypeResults(MealTypeListResponse menuListResponse);


}