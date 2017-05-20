package com.a96group.appdup.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.a96group.appdup.R;
import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } catch (Exception e) {
            Log.d("Error : ", "Couldn't hide the Action bar.");
        }
        setContentView(R.layout.activity_login);

        /*if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.RECEIVE_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }*/

        DigitsAuthButton digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        try
        {
            digitsButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            digitsButton.setAuthTheme(R.style.CustomDigitsTheme);
        }
        catch (NullPointerException e){}

        try{ digitsButton.setText(getText(R.string.login));}
        catch (NullPointerException e){e.printStackTrace();}

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber)
            {
                if(phoneNumber != null) {
                    SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedpreferences.edit();
                    ed.putString("MOB", phoneNumber);
                    ed.apply();

                    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Unable to retrieve your phone number. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
                Toast.makeText(LoginActivity.this, "Login Failed. Please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
