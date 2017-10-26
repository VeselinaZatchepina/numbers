package com.github.veselinazatchepina.numbers.data.remote;


import com.github.veselinazatchepina.numbers.data.Number;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitNumbersInterface {

    @GET("/{number}/{type}?json")
    Flowable<Number> getNumberDescription(@Path("number") String number,
                                          @Path("type") String type);

    @GET("/{number}/{type}?json")
    Flowable<Map<String, Number>> getNumbersDescription(@Path("number") String number,
                                                        @Path("type") String type);

}
