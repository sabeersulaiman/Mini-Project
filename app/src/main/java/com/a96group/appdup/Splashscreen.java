package com.a96group.appdup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread mythread=new Thread(){
            public void run()
            {
                try{
                    sleep(4000);
                    Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}
