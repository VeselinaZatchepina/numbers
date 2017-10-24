package com.github.veselinazatchepina.numbers.data.remote;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitNumbersInterface {

    @GET("/{number}?json")
    Call<Map<String, String>> getNumberDescription(@Path("number") String number);

}
