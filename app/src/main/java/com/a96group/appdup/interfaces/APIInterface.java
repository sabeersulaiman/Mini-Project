package com.a96group.appdup.interfaces;

import com.a96group.appdup.models.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ss on 18/4/17.
 */

public interface APIInterface {
    @POST("user/")
    Observable<User> userReg(
            @Body User detail
    );
}
