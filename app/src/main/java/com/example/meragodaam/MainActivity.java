package com.example.meragodaam;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0 ;
    RecyclerView recyclerView;
    ArrayList<ProductModel> arrayList = new ArrayList<>();
    String description = "lol What is Lorem Ipsum  Lorem Ipsum is simply dummy text of the printing and typesett Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book.It has survived not only five centuries, but also the leap into ele remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.Why do we use itIt is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years,ometimes by accident, sometimes on purpose injected humour and the like) ";
    byte[] b;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button btnload = findViewById(R.id.btnLoad);
        ExtendedFloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        ArrayList<ProductModel> arrayList = new ArrayList<>();
        ArrayList<ProductAddModel> arrAdd = new ArrayList<>();
        MyDataBaseHelper db = new MyDataBaseHelper(this);

        /*btnload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TabLayoutLoadActivity.class);
                startActivity(intent);
            }
        });*/
        recyclerView = findViewById(R.id.recyclerProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        arrayList.add(new ProductModel(0, R.drawable.mobi2, "Readme china", description, "17000"));
//        arrayList.add(new ProductModel(3, R.drawable.mobi7_, "Readme china", description, "17000"));
//        arrayList.add(new ProductModel(4, R.drawable.mobi1, "Samsumg galaxy", description, "14000"));
//        arrayList.add(new ProductModel(5, R.drawable.mobi2, "Readme china", description, "17000"));
//        arrayList.add(new ProductModel(0, R.drawable.mobi3, "OnePlus standard china", description, "13000"));
//        arrayList.add(new ProductModel(2, R.drawable.mobi4, "Samsumg galaxy mw2", description, "20000"));
//        arrayList.add(new ProductModel(5, R.drawable.mobi5, "HalfApple", description, "12000"));
//        arrayList.add(new ProductModel(1, R.drawable.mobi6, "realmy", description, "21000"));
//        arrayList.add(new ProductModel(4, R.drawable.mobi7_, "Apple ", description, "35999"));
//        arrayList.add(new ProductModel(5, R.drawable.mobi8, "Apple", description, "78000"));
//        arrayList.add(new ProductModel(4, R.drawable.mobi9, "Apple", description, "80000"));
//        arrayList.add(new ProductModel(2, R.drawable.mobi10, "Apple", description, "124000"));



        arrAdd = db.fetchProduct();
        System.out.println("fetched arrayList size: " + arrAdd.size());


        for (int i = 0; i < arrAdd.size(); i++) {
            Bitmap bitImage = null;
            System.out.println("getting IMage "+arrAdd.get(i).img);
            String image=arrAdd.get(i).img;
            if(!image.equalsIgnoreCase("")) {
                b = Base64.decode(image, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);

                bitImage = bitmap;
                System.out.println("model"+bitmap);

            }else{
                System.out.println("did not get any encoded image");
            }

            ProductModel model = new ProductModel(arrAdd.get(i).rating
                    , bitImage
                    , arrAdd.get(i).name
                    , arrAdd.get(i).description
                    , arrAdd.get(i).price);
            //image decode function is calling




            arrayList.add(model);
        }
        ProductListAdapter adapter = new ProductListAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddProduct.class);
                startActivity(i);
            }
        });
        
        //permission for storage
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant

            return;
        }


    }
    

}



