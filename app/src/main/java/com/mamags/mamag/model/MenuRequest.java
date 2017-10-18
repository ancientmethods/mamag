package com.mamags.mamag.model;

/**
 * Created by Samer on 18/10/2017.
 */

public class MenuRequest extends FDSRequest {

    Menu MenuEntity;

    public Menu getMenu() {
        return MenuEntity;
    }

    public void setMenu(Menu menu) {
        this.MenuEntity = menu;
    }
}
