package com.mamags.mamag.Utils;

/**
 * Created by Samer on 11/10/2017.
 */

public class TabsList {

    public TabsList(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }


    public TabsList() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String name;

    public int getIcon_drawable() {
        return icon_drawable;
    }

    public void setIcon_drawable(int icon_drawable) {
        this.icon_drawable = icon_drawable;
    }

    private String icon;
    int icon_drawable;


    private long number_of_items;
}
