package com.mamags.mamag.Utils;

/**
 * Created by Samer on 20/02/2018.
 */

public class TT_Tabs {


    private String orders;



    private String menus;

    private String dashboard;
    private String mealtypes;
    private String meals;

    private String ingredients;






    private static final TT_Tabs ourInstance = new TT_Tabs();

    public static TT_Tabs getInstance() {
        return ourInstance;
    }

    private TT_Tabs() {
    }


    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getDashboard() {
        return dashboard;
    }

    public void setDashboard(String dashboard) {
        this.dashboard = dashboard;
    }

    public String getMealtypes() {
        return mealtypes;
    }

    public void setMealtypes(String mealtypes) {
        this.mealtypes = mealtypes;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }



    public String getMenus() {
        return menus;
    }

    public void setMenus(String menus) {
        this.menus = menus;
    }
}
