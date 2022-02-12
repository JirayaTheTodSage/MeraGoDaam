package com.example.meragodaam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter< ProductListAdapter.MyViewHolder > {
    Context context;
    ArrayList< ProductModel > arrayList;
    String TAG = ProductListAdapter.class.getSimpleName();

    public ProductListAdapter(Context context, ArrayList< ProductModel > arr) {
        this.context = context;
        arrayList = arr;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_template, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(arrayList.get(position).name);
        String name = arrayList.get(position).name;
        holder.description.setText(arrayList.get(position).description);
        String des = arrayList.get(position).description;
        holder.price.setText(arrayList.get(position).price);
        String price = arrayList.get(position).price;
        Bitmap image = arrayList.get(position).image;

        System.out.println("set image " + arrayList.get(position).image);

        System.out.println("getted image " + arrayList.get(position).bitImage);


        //decode image
        // if (position > 11) {
        Log.d(TAG, "this is get image " + arrayList.get(position).bitImage);
        Glide.with(context)


                .load(arrayList.get(position).bitImage)
                .placeholder(R.drawable.default22)
                .into(holder.image)

        ;

        //holder.image.setImageBitmap(arrayList.get(position).bitImage);
        //}
        /*else {
            holder.image.setImageResource(arrayList.get(position).img);
        }*/


        int i = arrayList.get(position).rating;
        System.out.println("value of star " + arrayList.get(position).rating);
        if (i == 4) {
            holder.r5.setVisibility(View.GONE);
            holder.rh5.setVisibility(View.VISIBLE);
        } else if (i == 3) {
            holder.r5.setVisibility(View.GONE);
            holder.r4.setVisibility(View.GONE);
            holder.rh5.setVisibility(View.VISIBLE);
            holder.rh4.setVisibility(View.VISIBLE);
        } else if (i == 2) {
            holder.r5.setVisibility(View.GONE);
            holder.r4.setVisibility(View.GONE);
            holder.r3.setVisibility(View.GONE);
            holder.rh5.setVisibility(View.VISIBLE);
            holder.rh4.setVisibility(View.VISIBLE);
            holder.rh3.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            holder.r5.setVisibility(View.GONE);
            holder.r4.setVisibility(View.GONE);
            holder.r3.setVisibility(View.GONE);
            holder.r2.setVisibility(View.GONE);
            //
            holder.rh5.setVisibility(View.VISIBLE);
            holder.rh4.setVisibility(View.VISIBLE);
            holder.rh3.setVisibility(View.VISIBLE);
            holder.rh2.setVisibility(View.VISIBLE);
        } else if (i == 0) {
            holder.r5.setVisibility(View.GONE);
            holder.r4.setVisibility(View.GONE);
            holder.r3.setVisibility(View.GONE);
            holder.r2.setVisibility(View.GONE);
            holder.r1.setVisibility(View.GONE);
            holder.rh5.setVisibility(View.VISIBLE);
            holder.rh4.setVisibility(View.VISIBLE);
            holder.rh3.setVisibility(View.VISIBLE);
            holder.rh2.setVisibility(View.VISIBLE);
            holder.rh1.setVisibility(View.VISIBLE);

        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductModel productModel = arrayList.get(position);
                //System.out.println("position " + position);
                //System.out.println("position arrSize" + arrayList.size());
                //String data = Integer.toString(position);
                Intent i = new Intent(context, ProductShow.class);
                Bundle b = new Bundle();
                b.putInt("key", position);
                b.putString("name",name);
                b.putString("des",des);
                b.putString("price",price);
                //b.putString("imageS",arrayList.get(position).imgS);
                //i.putExtra("key",productModel);
               // b.putParcelableArrayList("key",arrayList);
               // b.putSerializable("key",arrayList);

                i.putExtras(b);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image, r1, r2, r3, r4, r5, rh1, rh2, rh3, rh4, rh5;
        TextView price, title, description;
        LinearLayout layout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.product_img);
            price = itemView.findViewById(R.id.product_price);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            r1 = itemView.findViewById(R.id.star_icon_1);
            r2 = itemView.findViewById(R.id.star_icon_2);
            r3 = itemView.findViewById(R.id.star_icon_3);
            r4 = itemView.findViewById(R.id.star_icon_4);
            r5 = itemView.findViewById(R.id.star_icon_5);
            rh1 = itemView.findViewById(R.id.star_icon_holo_1);
            rh2 = itemView.findViewById(R.id.star_icon_holo_2);
            rh3 = itemView.findViewById(R.id.star_icon_holo_3);
            rh4 = itemView.findViewById(R.id.star_icon_holo_4);
            rh5 = itemView.findViewById(R.id.star_icon_holo_5);

            layout = itemView.findViewById(R.id.layoutOnClick);


        }
    }
}
