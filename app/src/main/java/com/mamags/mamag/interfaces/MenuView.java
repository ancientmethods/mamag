package com.mamags.mamag.interfaces;

import com.mamags.mamag.interfaces.IView;
import com.mamags.mamag.model.Responses.MenuListResponse;

/**
 * Created by samer on 01/10/2017.
 */

public interface MenuView extends IView {

    void loadMenuResults(MenuListResponse menuListResponse);

}
