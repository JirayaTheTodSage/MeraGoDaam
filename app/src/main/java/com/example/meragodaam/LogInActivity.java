package com.example.meragodaam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    com.google.android.material.textfield.TextInputEditText edtNumber;

    Button btnOTP;
    Context context;
    String verificationCode;
    ProgressBar progressBar;
    in.aabhasjindal.otptextview.OtpTextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        context = LogInActivity.this;

        edtNumber = findViewById(R.id.edtEnterNumber);

        String concd = null;
        tv = findViewById(R.id.otp_view);

        btnOTP = findViewById(R.id.btnOTP);
        progressBar = findViewById(R.id.progressBar);


        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!edtNumber.getText().toString().isEmpty()) {
                    if ((edtNumber.getText().toString()).length() == 10) {
                        Toast.makeText(context, edtNumber.getText().toString(), Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(context, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Please enter Number", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(LogInActivity.this, GetOtpActivity.class);
                intent.putExtra("key", edtNumber.getText().toString());
                startActivity(intent);
            }
        });


    }


}