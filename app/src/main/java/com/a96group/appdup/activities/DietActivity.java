package com.a96group.appdup.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a96group.appdup.Auth;
import com.a96group.appdup.R;
import com.a96group.appdup.interfaces.APIInterface;
import com.a96group.appdup.models.Day;
import com.a96group.appdup.models.Diets;
import com.a96group.appdup.models.Food;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DietActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private APIInterface api;

    private String planId;
    private Diets diet;

    private TextView breakText;
    private TextView lunchText;
    private TextView dinnerText;
    private TextView planText;

    private LinearLayout breakfast;
    private  LinearLayout lunch;
    private LinearLayout dinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);

        SharedPreferences sp = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        planId = sp.getString("PLAN", "none");

        breakText = (TextView) findViewById(R.id.break_text);
        planText = (TextView) findViewById(R.id.plan);
        lunchText = (TextView) findViewById(R.id.lunch_text);
        dinnerText = (TextView) findViewById(R.id.dinner_text);

        breakfast = (LinearLayout) findViewById(R.id.breakfast);
        lunch = (LinearLayout) findViewById(R.id.lunch);
        dinner = (LinearLayout) findViewById(R.id.dinner);

        mCompositeDisposable = new CompositeDisposable();

        api = new Retrofit
                .Builder()
                .baseUrl(Auth.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface.class);

        loadDiet();
    }

    private void showAll() {
        breakfast.setVisibility(View.VISIBLE);
        lunch.setVisibility(View.VISIBLE);
        dinner.setVisibility(View.VISIBLE);
    }

    private void loadDiet() {
        mCompositeDisposable.add(
                api.getDiet(planId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handlePlan,this::handlePlanError)
        );
    }

    private void handlePlanError(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handlePlan(Diets diets) {
        diet = diets;
        Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_SHORT).show();

        Day monday = diets.getDay();

        planText.setText(diets.getPlan());

        breakText.setText(formatTime(monday.get_break()));
        lunchText.setText(formatTime(monday.getLunch()));
        dinnerText.setText(formatTime(monday.getDin()));
    }

    private String formatTime(List<Food> foods) {
        String s = "";
        for(Food food:foods) {
            s += food.getItem() + " : "+ food.getQuantity() +"( " + food.getCalories() + " )\r\n";
        }

        return s;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent dashIntent = new Intent(DietActivity.this, ListDiets.class);
        startActivity(dashIntent);
        finish();
    }


}
