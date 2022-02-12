package com.example.meragodaam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductShow extends AppCompatActivity {
    Bundle bundle = new Bundle();
    TextView title, price, description;
    ImageView img;
    byte[] b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);
        findid();

        bundle = getIntent().getExtras();
        int position = bundle.getInt("key", 69);
        Intent i = getIntent();


        System.out.println("info:position in product show activity" + position);
        Serializable obj = getIntent().getSerializableExtra("key");
        System.out.println("info:serializable object" + obj);
        //ProductModel productModel = (ProductModel) getIntent().getSerializableExtra("key");
        Bitmap bitImage = null;
        String name = i.getStringExtra("name");
        String des = i.getStringExtra("des");
        String sprice = i.getStringExtra("price");
        String sImage = i.getStringExtra("image");
        String imageS = i.getStringExtra("imageS");
      /*  b = Base64.decode(imageS, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        bitImage = bitmap;*/
       // System.out.println("model" + bitmap);
        System.out.println("info: this is " + name);
        title.setText(name);
        price.setText(sprice);
       // img.setImageBitmap(bitImage);
        description.setText(des);

           /*  @SuppressWarnings("unchecked")
        ArrayList< ProductModel > arrayList = (ArrayList< ProductModel >) obj;
        if (arrayList == null)
            Toast.makeText(this, "Arraylist is nulll", Toast.LENGTH_SHORT).show();
        if (arrayList.isEmpty()) {
            //System.out.println("info:ON show products get Arraylist"+arrayList.size());
            Log.d("", "info:" + arrayList.size());
            title.setText(arrayList.get(position).name);
            price.setText(arrayList.get(position).price);
            img.setImageResource(arrayList.get(position).img);
            description.setText(arrayList.get(position).description);
            int i = arrayList.get(position).rating;
        } else
            Toast.makeText(this, "Does not get Arraylist" + arrayList.size(), Toast.LENGTH_SHORT).show();*/

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