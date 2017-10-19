package com.mamags.mamag.viewmodel;

import com.mamags.mamag.model.Menu;
import com.mamags.mamag.model.Responses.FDSresponse;

import java.util.List;

/**
 * Created by samer on 01/10/2017.
 */

public interface MenuView extends IView {

    void loadMenuResults(List<Menu> menus);
    void createMenuResponse(FDSresponse fdSresponse);

}
