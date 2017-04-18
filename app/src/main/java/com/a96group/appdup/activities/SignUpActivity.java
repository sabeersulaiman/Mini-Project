package com.a96group.appdup.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a96group.appdup.Auth;
import com.a96group.appdup.MainActivity2;
import com.a96group.appdup.MainActivity3;
import com.a96group.appdup.R;
import com.a96group.appdup.interfaces.APIInterface;
import com.a96group.appdup.models.User;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private APIInterface api;

    private FloatingActionButton signFab;
    private EditText heightBox;
    private EditText weightBox;
    private TextView genderBox;
    private EditText nameBox;
    private EditText ageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mCompositeDisposable = new CompositeDisposable();

        api = new Retrofit
                .Builder()
                .baseUrl(Auth.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface.class);

        signFab = (FloatingActionButton) findViewById(R.id.fab);
        signFab.setOnClickListener(this::signUp);

        //initiate data elements
        nameBox = (EditText) findViewById(R.id.name);
        ageBox = (EditText) findViewById(R.id.age);
        genderBox = (TextView) findViewById(R.id.genderChooser);
        weightBox = (EditText) findViewById(R.id.weight);
        heightBox = (EditText) findViewById(R.id.height);
    }

    private void signUp(View v) {
        String bmiValue = "";
        String message = "";

        String heightStr = heightBox.getText().toString();
        String weightBoxStr = weightBox.getText().toString();

        if (heightStr != null && !"".equals(heightStr)
                && weightBoxStr != null && !"".equals(weightBoxStr)) {
            float heightvalue = Float.parseFloat(heightStr) / 100;
            float weightBoxvalue = Float.parseFloat(weightBoxStr);
            float bmi = weightBoxvalue / (heightvalue * heightvalue);
            bmiValue = String.valueOf(bmi);
            message = displayBMI(bmi);
        }

        if (nameBox.getText().toString().length()==0) {

            Toast.makeText(getApplicationContext(), "Name cannot be Blank", Toast.LENGTH_SHORT).show();
            nameBox.setError("name cannot be Blank");
            return;
        }
        if (ageBox.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(), "Age cannot be Blank", Toast.LENGTH_SHORT).show();
            ageBox.setError("Age cannot be Blank");
            return;
        }
        if(heightBox.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Enter your Height",Toast.LENGTH_SHORT).show();
            heightBox.setError("Height cannot be Blank");
            return;
        }
        if(weightBox.getText().toString().length()==0) {
            Toast.makeText(getApplicationContext(), "Enter your weight", Toast.LENGTH_SHORT).show();
            weightBox.setError("weight cannot be Blank");
            return;
        }

        String diabetes = "yes";
        String veg = "yes";

        SharedPreferences sp = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        String mob = sp.getString("MOB", "");

        User user = new User(nameBox.getText().toString(), mob, message, diabetes, veg);

        mCompositeDisposable.add(
                api.userReg(user)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleSaveUser,this::handleSaveUserError)
        );

//        intent.putExtra("bmi", bmiValue);
//        intent.putExtra("message", message);
//
//        startActivity(intent);
//        finish();
    }

    private void handleSaveUserError(Throwable throwable) {
        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleSaveUser(User user) {
        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedpreferences.edit();
        ed.putString("BMI", user.bmi);
        ed.putString("DIA", user.diabetes);
        ed.putString("VEG", user.veg);
        ed.putString("NAME", user.name);
        ed.apply();

        Intent intent = new Intent(SignUpActivity.this, MainActivity3.class);
        startActivity(intent);
        finish();
    }


    private String displayBMI(float bmi) {
        String bmilabel = "";
        if (Float.compare(bmi, 18.5f) <= 0) {
            bmilabel = getString(R.string.underweight);
        } else if (Float.compare(bmi, 18.5f) > 0 && Float.compare(bmi, 25f) <= 0) {
            bmilabel = getString(R.string.normal);
        } else
            bmilabel = getString(R.string.overweight);

        return bmilabel;
    }



    public void DialogCreator(View view)
    {
        final CharSequence[] items = new CharSequence[]{"Male", "Female"};
        //final CharSequence[] vals = {"Bank one of India","Bank two of India","Bank three of India"};
        final TextView bankchoose = (TextView)findViewById(R.id.genderChooser);


        AlertDialog.Builder builder =
                new AlertDialog.Builder(SignUpActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Select Your Gender");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                if(bankchoose!=null)
                    bankchoose.setText(items[which]);
            }
        });
        builder.create().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
