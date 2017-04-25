package com.a96group.appdup.interfaces;

import com.a96group.appdup.models.Diets;
import com.a96group.appdup.models.Plan;
import com.a96group.appdup.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableDebounce;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ss on 18/4/17.
 */

public interface APIInterface {
    @POST("user/")
    Observable<User> userReg(
            @Body User detail
    );

    @GET("diets/{veg}/{dia}")
    Observable<List<Plan>> listDiets(
            @Path("veg") String veg,
            @Path("dia") String dia
    );

    @GET("diets/mobile/{id}")
    Observable<Diets> getDiet(
            @Path("id") String id
    );

}
