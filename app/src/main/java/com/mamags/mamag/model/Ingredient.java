package com.mamags.mamag.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samer on 19/03/2018.
 */

public class Ingredient implements Parcelable{
    private int Id;
    private String Price;
    private String Description;

    public Ingredient() {
    }

    public int getId() {

        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    protected Ingredient(Parcel in) {
        Id = in.readInt();
        Price = in.readString();
        Description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Price);
        dest.writeString(Description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
