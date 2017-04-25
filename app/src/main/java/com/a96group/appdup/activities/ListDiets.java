package com.a96group.appdup.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.a96group.appdup.Auth;
import com.a96group.appdup.R;
import com.a96group.appdup.adapters.PlanAdapter;
import com.a96group.appdup.implementations.RecyclerItemListener;
import com.a96group.appdup.interfaces.APIInterface;
import com.a96group.appdup.models.Plan;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListDiets extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;
    private APIInterface api;

    private String veg;
    private String dia;

    private RecyclerView rv;

    private List<Plan> planList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diets);

        SharedPreferences sp = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        veg = sp.getString("VEG", "false");
        dia = sp.getString("DIA", "false");

        mCompositeDisposable = new CompositeDisposable();

        api = new Retrofit
                .Builder()
                .baseUrl(Auth.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface.class);

        planList = new ArrayList<Plan>();

        rv = (RecyclerView) findViewById(R.id.rView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        loadDiets();
    }

    private void loadDiets() {
        mCompositeDisposable.add(
                api.listDiets(veg, dia)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleList,this::handleListError)
        );
    }

    private void handleListError(Throwable throwable) {
        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
    }

    private void handleList(List<Plan> plans) {

        planList = plans;

        PlanAdapter ca = new PlanAdapter(planList);
        rv.setAdapter(ca);

        rv.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), rv,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        Plan p = planList.get(position);

                        SharedPreferences sharedpreferences = getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sharedpreferences.edit();
                        ed.putString("PLAN", p.get_id());
                        ed.apply();

                        Intent intent = new Intent(ListDiets.this, DietActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(getApplicationContext(), "RES : " + p.get_id(), Toast.LENGTH_SHORT).show();
                    }

                    public void onLongClickItem(View v, int position) {
                        System.out.println("On Long Click Item interface");
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent dashIntent = new Intent(ListDiets.this, ResultActivity.class);
        startActivity(dashIntent);
        finish();
    }
}
