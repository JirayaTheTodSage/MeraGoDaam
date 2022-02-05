package com.example.meragodaam;

public class ProductAddModel {
    int rating;
    String name,description,price;
    String img;

    public ProductAddModel(int rating, String img, String name, String description, String price) {
        this.img = img;
        this.rating = rating;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
