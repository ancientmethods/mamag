package com.mamags.mamag.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samer on 11/11/2017.
 */

public class MealType {

    @SerializedName("Id")
    private int id;
    @SerializedName("Description")
    private String description;

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
}
