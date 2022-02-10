package com.example.meragodaam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
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

import java.util.concurrent.TimeUnit;

public class GetOtpActivity extends AppCompatActivity {
    Context context = GetOtpActivity.this;
    private FirebaseAuth mAuth;
    String number;
    String verificationCode;
    ProgressBar p;
    in.aabhasjindal.otptextview.OtpTextView edt_Otp;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);
        p = findViewById(R.id.getOtpProgressBar);
        edt_Otp = findViewById(R.id.otp_view);
        Bundle bundle = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
// getting the string back
        number = bundle.getString("key", "Default");
        number = "+91" + number;
        System.out.println("this number"+number);
        (findViewById(R.id.btnSubmit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number= FixPhoneNumber(context,number);
                Log.d(LogInActivity.class.getSimpleName(), "onClick: " + number);
                getOtp(number);

            }
        });


    }
    public static String FixPhoneNumber(Context ctx, String rawNumber)
    {
        String      fixedNumber = "";

        // get current location iso code
        TelephonyManager telMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

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
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + number,
                60,
                TimeUnit.SECONDS,
               this,
                mCallbacks);
    }


    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            System.out.println("info:OnVerificationComplete is called");
            System.out.println("getted otp  "+code);
            if (code != null&& !code.equals("")) {
                edt_Otp.setOTP(code);
                p.setVisibility(View.VISIBLE);
                verifyCode(code);
                System.out.println("getted otp not null "+code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            System.out.println("info:OnCodeSent is called");
            System.out.println("this is verification code "+s);

            verificationCode = s;
            mResendToken =  forceResendingToken;
            String code = edt_Otp.getOTP();
            verifyCode(code);

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
                .addOnCompleteListener(GetOtpActivity.this, new OnCompleteListener< AuthResult >() {
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