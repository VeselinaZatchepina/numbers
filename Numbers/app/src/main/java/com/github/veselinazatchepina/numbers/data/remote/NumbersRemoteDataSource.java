package com.github.veselinazatchepina.numbers.data.remote;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NumbersRemoteDataSource implements NumbersDataSource {

    private static NumbersRemoteDataSource INSTANCE = null;

    private String mDescription = " ";

    public static NumbersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NumbersRemoteDataSource();
        }
        return INSTANCE;
    }

    private NumbersRemoteDataSource() {
    }

    @Override
    public Flowable<List<Number>> getNumbers() {
        return null;
    }

    @Override
    public String getNumberDescription(String number) {
        startRequest("http://numbersapi.com/", number);
        return mDescription;
    }

    private void startRequest(String baseUrl, String request) {
        RetrofitNumbersInterface service = defineRetrofit(baseUrl).create(RetrofitNumbersInterface.class);
        Call<Map<String, String>> call = service.getNumberDescription(request);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    Map<String, String> responseMap = response.body();
                    for(Map.Entry<String, String> entry : responseMap.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        if (key.equals("text")) {
                            mDescription = value;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

            }
        });
    }

    private Retrofit defineRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void saveNumber() {

    }

    @Override
    public void deleteNumber() {

    }

    @Override
    public void deleteAllNumbers() {

    }
}
