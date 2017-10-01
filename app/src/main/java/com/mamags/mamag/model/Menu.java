package com.mamags.mamag.model;

/**
 * Created by samer on 30/09/2017.
 */

public class Menu {

    private int id;
    private String name;
    private String description;

    public Menu() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
