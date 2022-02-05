package com.example.meragodaam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductShow extends AppCompatActivity  {
    Bundle bundle = new Bundle();
    TextView title, price, description;
    ImageView img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);
        findid();

        bundle = getIntent().getExtras();
        int position = bundle.getInt("key", 69);

        System.out.println("position in product show activity" + position);
        ArrayList<ProductModel> arrayList = (ArrayList<ProductModel>) bundle.getSerializable("k");


        title.setText(arrayList.get(position).name);
        price.setText(arrayList.get(position).price);
        img.setImageResource(arrayList.get(position).img);
        description.setText(arrayList.get(position).description);
        int i = arrayList.get(position).rating;

       /* System.out.println("position in product show activity"+arrayList.get(position).rating);
        System.out.println("position in product show activity"+arrayList.get(position).price);
        System.out.println("position in product show activity"+arrayList.get(position).name);
        System.out.println("position in product show activity"+arrayList.get(position).description);*/


    }

    private void findid() {
        title = findViewById(R.id.pro_title);
        price = findViewById(R.id.pro_price);
        img = findViewById(R.id.pro_img);
        description = findViewById(R.id.pro_description);
    }
}