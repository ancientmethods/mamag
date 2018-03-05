package com.mamags.mamag.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samer on 30/09/2017.
 */

public class Menu implements Parcelable {

    private int Id;
    private String Name;
    private String Description;

    public Menu() {
    }

    public int getId() {

        return Id;
    }

    public void setId(int id) {
        this.Id = id;
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

    protected Menu(Parcel in) {
        Id = in.readInt();
        Name = in.readString();
        Description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Name);
        dest.writeString(Description);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
