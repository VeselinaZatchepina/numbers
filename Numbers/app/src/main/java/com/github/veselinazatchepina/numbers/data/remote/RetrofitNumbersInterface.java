package com.github.veselinazatchepina.numbers.data.remote;


import com.github.veselinazatchepina.numbers.data.Number;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitNumbersInterface {

    @GET("/{number}?json")
    Flowable<Number> getNumberDescription(@Path("number") String number);

}
