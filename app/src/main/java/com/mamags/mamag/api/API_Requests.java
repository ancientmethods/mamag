package com.mamags.mamag.api;

import com.mamags.mamag.constants.RequestAction;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.MealType;
import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Requests.MealRequest;
import com.mamags.mamag.model.Requests.MealTypeRequest;
import com.mamags.mamag.model.Requests.MenuRequest;

/**
 * Created by Samer on 05/03/2018.
 */

public class API_Requests {

    public API_Requests(){

    }

    /******************* Meal Type Screen ********************/

    public MealTypeRequest deleteMealType (int id){

        MealTypeRequest mealTypeRequest = new MealTypeRequest();
        MealType mealType = new MealType();
        mealType.setId(id);

        //initiate delete API call
        mealTypeRequest.CrudOption = RequestAction.Delete.getValue();
        mealTypeRequest.setMealType(mealType);
        return  mealTypeRequest;
    }

    public MealTypeRequest getMealTypes (){

        MealTypeRequest request = new MealTypeRequest();
        request.CrudOption = RequestAction.list.getValue();
        return  request;
    }

    /******************* Menu Screen ********************/

    public FDSRequest getMenus (){

        //if this class did not infer type MainViewModel, we wouldn't be able to call the method below
        FDSRequest request = new FDSRequest();
        request.CrudOption = RequestAction.list.getValue();
        return  request;
    }


    public MenuRequest deleteMenu (int id){

        MenuRequest menuRequest = new MenuRequest();
        Menu menu = new Menu();
        menu.setId(id);

        //initiate delete API call
        menuRequest.CrudOption = RequestAction.Delete.getValue();
        menuRequest.setMenu(menu);
        return  menuRequest;
    }

}
