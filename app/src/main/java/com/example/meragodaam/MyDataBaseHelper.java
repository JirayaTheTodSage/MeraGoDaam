package com.example.meragodaam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Product_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "PRODUCT_TABLE";
    private static final String KEY_PRODUCT_ID = "PRODUCT_ID";
    private static final String KEY_PRODUCT_NAME = "PRODUCT_NAME";
    private static final String KEY_PRICE = "PRICE";
    private static final String KEY_IMAGE = "IMAGE";
    private static final String KEY_RATING = "Rating";
    private static final String KEY_DESCRIPTION = "DESCRIPTION";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + KEY_PRODUCT_ID + " Integer Primary key AutoIncrement, " +
                KEY_PRODUCT_NAME + " Text, " +
                KEY_IMAGE + " Text, " +
                KEY_PRICE + " Float, " +
                KEY_DESCRIPTION + " Text, " +
                KEY_RATING + " Integer )"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addProduct(String name, String image, float price, String description, int rating) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(KEY_PRODUCT_NAME, name);
        values.put(KEY_IMAGE, image);
        values.put(KEY_PRICE, price);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_RATING, rating);
        Long longResult = database.insert(TABLE_NAME, null, values);

        database.close();

        if (longResult != -1) {
            return true;
        } else {
            return false;
        }

    }

    public ArrayList< ProductAddModel > fetchProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList< ProductAddModel > arrayList = new ArrayList<>();


        try {
            Field field = CursorWindow.class.getDeclaredField(KEY_IMAGE);
            field.setAccessible(true);
            field.set(null, 100 * 1024 * 1024); //the 100MB is the new size
            System.out.println("Now cursor size code executed");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (cursor.moveToNext()) {
            ProductAddModel model = new ProductAddModel(cursor.getInt(0),
                    cursor.getString(2),
                    cursor.getString(1),
                    cursor.getString(4),
                    String.valueOf(cursor.getFloat(3)));
            arrayList.add(model);


        }
        return arrayList;

    }
}
