package com.a96group.appdup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.a96group.appdup.activities.LoginActivity;
import com.a96group.appdup.activities.ResultActivity;
import com.a96group.appdup.activities.SignUpActivity;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class Splashscreen extends AppCompatActivity {

    private static final String TWITTER_KEY = "lItD7LjzvERODX0iBd3Icjzn4";
    private static final String TWITTER_SECRET = "yS3jZlgh2SP43Dguk54KOxQtfBeyxMOHNbMza9k8sJMDLugpLK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String mob = sharedpreferences.getString("MOB","");
        String bmi = sharedpreferences.getString("BMI","");

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());

        Thread mythread=new Thread(){
            public void run()
            {
                try{
                    sleep(4000);
                    if(mob.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(!bmi.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}
