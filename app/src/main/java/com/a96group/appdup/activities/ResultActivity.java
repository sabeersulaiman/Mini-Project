package com.a96group.appdup.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.a96group.appdup.R;

public class ResultActivity extends AppCompatActivity {

    private Button findDiet;
    private TextView greeting;
    private TextView statusText;
    private ImageView statusImage;

    private CheckBox vegBox;
    private CheckBox diaBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        greeting = (TextView) findViewById(R.id.greeting);
        statusText = (TextView) findViewById(R.id.statusText);
        statusImage = (ImageView) findViewById(R.id.statusImage);

        vegBox = (CheckBox) findViewById(R.id.veg_box);
        diaBox = (CheckBox) findViewById(R.id.dia_box);

        SharedPreferences sp = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String name = sp.getString("NAME", "Guest");
        String bmi = sp.getString("BMI", "-");

        String veg = sp.getString("VEG", "");
        String dia = sp.getString("DIA", "");

        greeting.setText("Hello, " + name);
        statusText.setText("" + bmi);

        if(bmi.equals("Normal")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_normal, getApplicationContext().getTheme()));
            } else {
                statusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_normal));
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_underweight, getApplicationContext().getTheme()));
            } else {
                statusImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_underweight));
            }
        }

        if(veg.equals("true")) vegBox.setChecked(true);
        if(dia.equals("true")) diaBox.setChecked(true);

        vegBox.setOnClickListener(this::checkClick);
        diaBox.setOnClickListener(this::checkClick);

        findDiet = (Button) findViewById(R.id.find_btn);
        findDiet.setOnClickListener(this::go);
    }

    private void go(View view) {
        Intent intent = new Intent(ResultActivity.this, ListDiets.class);
        startActivity(intent);
        finish();
    }

    public void checkClick(View v) {
        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedpreferences.edit();

        CheckBox c = (CheckBox) v;
        if(c.getText().toString().equals("Vegetarian")) {
            if(c.isChecked()) {
                ed.putString("VEG", "true");
                ed.apply();
            }
            else {
                ed.putString("VEG", "false");
                ed.apply();
            }
        }
        else {
            if(c.isChecked()) {
                ed.putString("DIA", "true");
                ed.apply();
            }
            else {
                ed.putString("DIA", "false");
                ed.apply();
            }
        }
    }
}
