package com.mamags.mamag.model;

/**
 * Created by Samer on 18/10/2017.
 */

public enum RequestAction

{
    undefined(0),
    Add(1),
    Update(2),
    delete(3),
    list(4);
    private final int value;



    RequestAction(int i) {
        this.value = i;
    }
    public int getValue() {
        return value;
    }
}
