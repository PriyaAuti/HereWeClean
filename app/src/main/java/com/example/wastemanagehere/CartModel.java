package com.example.wastemanagehere;

public class CartModel {
    String Name;
    int Imagepath;

    public CartModel(String name, int imagepath) {
        Name = name;
        Imagepath = imagepath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImagepath() {
        return Imagepath;
    }

    public void setImagepath(int imagepath) {
        Imagepath = imagepath;
    }
}
