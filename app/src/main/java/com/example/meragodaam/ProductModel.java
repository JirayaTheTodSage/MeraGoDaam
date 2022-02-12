package com.example.meragodaam;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class ProductModel implements Serializable {
    int rating, img;
    String name, description, price,imgS;
    Bitmap image;
    Bitmap bitImage;
    byte[] imageByte;


    /* public ProductModel(int rating, int img, String name, String description, String price) {
         this.img = img;
         this.rating = rating;
         this.name = name;
         this.description = description;
         this.price = price;
     }*/
    public ProductModel(String imgS ,int rating, Bitmap bitImage, String name, String description, String price) {
        this.imgS = imgS;
        this.bitImage = bitImage;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.price = price;
    }




}
