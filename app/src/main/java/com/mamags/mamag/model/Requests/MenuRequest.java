package com.mamags.mamag.model.Requests;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.FDSRequest;
import com.mamags.mamag.model.Menu;

/**
 * Created by Samer on 18/10/2017.
 */

public class MenuRequest extends FDSRequest {

    @SerializedName("MenuEntity")
    Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
