package com.example.meragodaam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddProduct extends AppCompatActivity {

    ImageView imageView;
    Button btnChooseImage, btnSave;
    Intent iGallery;
    MyDataBaseHelper db = new MyDataBaseHelper(AddProduct.this);
    private static final int GALLERY_REQ_CODE = 69;
    int rating = 0;
    String name, des, img, str;
    float price;
    EditText edt_name, edt_des, edt_price, put_rating;
    InputStream iStream;
    byte[] inputImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        imageView = findViewById(R.id.iVchoose);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);


            }
        });

        edt_name = findViewById(R.id.put_Title);
        edt_price = findViewById(R.id.put_price);
        edt_des = findViewById(R.id.put_des);
        btnSave = findViewById(R.id.btnSave);
        put_rating = findViewById(R.id.put_rating);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("data from edt \n name" + name +
                        "\n price " + price
                        + "\n des" + des +
                        "\n image " + img);
                name = edt_name.getText().toString();
                des = edt_des.getText().toString();
                price = Float.parseFloat(edt_price.getText().toString());
                str = String.valueOf(iGallery.getData());

                rating = Integer.valueOf(put_rating.getText().toString());
                System.out.println("rating" + rating);


                //get image encoded
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Drawable d = imageView.getDrawable();
                BitmapDrawable realImage = (BitmapDrawable) d;
                realImage.getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] b = baos.toByteArray();
                str = Base64.encodeToString(b, Base64.DEFAULT);
                System.out.println("entered image encoded " + str);
                System.out.println(" uri String " + str);


                boolean isSuccessFul = db.addProduct(name, str, price, des, rating);

                if (isSuccessFul) {
                    Toast.makeText(AddProduct.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddProduct.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 10240;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageView.setVisibility(View.VISIBLE);
        imageView.setImageURI(data.getData());
       /*
       //image to bitmap and then ByteArray
       Bitmap bitmapFromImage = (((BitmapDrawable) imageView.getDrawable()).getBitmap());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapFromImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        inputImage = byteArrayOutputStream.toByteArray();
*/
        img = String.valueOf(data.getData());
        Uri uri = data.getData();
        // System.out.println("URI :"+uri);
        try {
            iStream = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}