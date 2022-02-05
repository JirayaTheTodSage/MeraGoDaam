package com.example.meragodaam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

public class ProductModel implements Serializable {
    int rating, img;
    String name, description, price;
    String image;
    Bitmap bitImage;




   /* public ProductModel(int rating, int img, String name, String description, String price) {
        this.img = img;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.price = price;
    }*/
    public ProductModel(int rating,  Bitmap bitImage, String name, String description, String price) {
        this.bitImage = bitImage;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.price = price;
    }



}
