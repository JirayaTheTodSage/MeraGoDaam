package com.example.meragodaam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        int time = 2000;
        TextView tv = findViewById(R.id.name_s);
        CardView cd = findViewById(R.id.cd);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.alfa);
        cd.setAnimation(animation);
        tv.setAnimation(animation2);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,LogInActivity.class);
                startActivity(i);
                finish();

            }
        }, time);

    }
}