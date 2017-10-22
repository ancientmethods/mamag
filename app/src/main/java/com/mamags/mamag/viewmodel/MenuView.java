package com.mamags.mamag.viewmodel;

import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Responses.FDSresponse;
import com.mamags.mamag.model.Responses.MenuListResponse;

import java.util.List;

/**
 * Created by samer on 01/10/2017.
 */

public interface MenuView extends IView {

    void loadMenuResults(MenuListResponse menuListResponse);
    void createMenuResponse(FDSresponse fdSresponse);

}
