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
    private FirebaseAuth mAuth;
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

        String concd;
        tv = findViewById(R.id.otp_view);
        mAuth = FirebaseAuth.getInstance();
        btnOTP = findViewById(R.id.btnOTP);
        progressBar = findViewById(R.id.progressBar);


        btnOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (edtNumber.getText().toString() != null && !edtNumber.getText().toString().isEmpty()) {
                    if ((edtNumber.getText().toString()).length() == 10) {
                        Toast.makeText(context, edtNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                        String concd = "+91" + edtNumber.getText().toString();
                       concd= FixPhoneNumber(context,concd);
                        Log.d(LogInActivity.class.getSimpleName(), "onClick: " + concd);
                        getOtp(concd);
                    } else {
                        Toast.makeText(context, "Please enter correct Number", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Please enter Number", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public static String FixPhoneNumber(Context ctx, String rawNumber)
    {
        String      fixedNumber = "";

        // get current location iso code
        TelephonyManager    telMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        String              curLocale = telMgr.getNetworkCountryIso().toUpperCase();

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber     phoneNumberProto;

        // gets the international dialling code for our current location
        String              curDCode = String.format("%d", phoneUtil.getCountryCodeForRegion(curLocale));
        String              ourDCode = "";

        if(rawNumber.indexOf("+") == 0)
        {
            int     bIndex = rawNumber.indexOf("(");
            int     hIndex = rawNumber.indexOf("-");
            int     eIndex = rawNumber.indexOf(" ");

            if(bIndex != -1)
            {
                ourDCode = rawNumber.substring(1, bIndex);
            }
            else if(hIndex != -1)
            {
                ourDCode = rawNumber.substring(1, hIndex);
            }
            else if(eIndex != -1)
            {
                ourDCode = rawNumber.substring(1, eIndex);
            }
            else
            {
                ourDCode = curDCode;
            }
        }
        else
        {
            ourDCode = curDCode;
        }

        try
        {
            phoneNumberProto = phoneUtil.parse(rawNumber, curLocale);
        }

        catch (NumberParseException e)
        {
            return rawNumber;
        }

        if(curDCode.compareTo(ourDCode) == 0)
            fixedNumber = phoneUtil.format(phoneNumberProto, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        else
            fixedNumber = phoneUtil.format(phoneNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);

        return fixedNumber.replace(" ", "");
    }


    private void getOtp(String number) {
        //    Toast.makeText(context, "entered number " + number, Toast.LENGTH_SHORT).show();
       // progressBar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setActivity(this)                    // Activity (for callback binding)
                        .setPhoneNumber("+91" + number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            System.out.println("info:OnCodeSent is called");
            System.out.println("this is verification code "+s);

            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            System.out.println("info:OnVerificationComplete is called");
            System.out.println("getted otp  "+code);
            if (code != null&& !code.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
                System.out.println("getted otp not null "+code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code) {
        System.out.println("info:VerifyCode is called");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInMethod(credential);
    }

    private void signInMethod(PhoneAuthCredential c) {
        System.out.println("info:signInMethod is called");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(c)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, MainActivity.class);
                            //user not get here(AuthenticationActivity) onBack press So that is Done By this VVV
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}