package com.tricktech.sqlitecrudoperations.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by am on 1/27/2017.
 */

public class Item implements Parcelable{
    public int id;
    public String title;

    public Item() {
    }


    protected Item(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
