package com.mamags.mamag.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Samer on 14/11/2017.
 */

public class MealInfo {
    @SerializedName("Id")
    private int id;
    @SerializedName("Description")
    private String description;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private float price;



    @SerializedName("isCustomisable")
    private boolean isCustomisable;

    @SerializedName("MealTypeId")

    private int mealTypeId;

    //private List<ImageEntity> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public boolean isCustomisable() {
        return isCustomisable;
    }

    public void setCustomisable(boolean customisable) {
        isCustomisable = customisable;
    }
    public int getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

}
