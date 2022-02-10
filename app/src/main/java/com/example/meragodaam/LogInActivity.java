package com.example.meragodaam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

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


                if ( !edtNumber.getText().toString().isEmpty()) {
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