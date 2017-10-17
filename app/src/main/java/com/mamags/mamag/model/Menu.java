package com.mamags.mamag.model;

/**
 * Created by samer on 30/09/2017.
 */

public class Menu {

    private int id;
    private String Name;
    private String Description;

    public Menu() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
