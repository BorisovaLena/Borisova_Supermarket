package com.example.supermarket;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private int ID;
    private String Title;
    private int Count;
    private String Image;

    protected Product(Parcel in) {
        ID = in.readInt();
        Title = in.readString();
        Count = in.readInt();
        Image = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public int getCount() {
        return Count;
    }

    public String getImage() {
        return Image;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Product(int ID, String title, int count, String image) {
        this.ID = ID;
        Title = title;
        Count = count;
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(Title);
        dest.writeInt(Count);
        dest.writeString(Image);
    }
}
