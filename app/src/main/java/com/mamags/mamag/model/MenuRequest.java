package com.mamags.mamag.model;

import com.google.gson.annotations.SerializedName;

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
