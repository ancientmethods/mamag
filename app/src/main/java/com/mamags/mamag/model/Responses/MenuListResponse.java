package com.mamags.mamag.model.Responses;

import com.google.gson.annotations.SerializedName;
import com.mamags.mamag.model.Menu;

import java.util.List;

import io.reactivex.annotations.Nullable;

/**
 * Created by samer on 19/10/2017.
 */

public class MenuListResponse extends FDSresponse {

    @SerializedName("Items")
    @Nullable
    private List<Menu> menuList;

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}
