package com.github.veselinazatchepina.numbers.data.remote;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NumbersRemoteDataSource implements NumbersDataSource {

    private static NumbersRemoteDataSource INSTANCE = null;

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
    public Flowable<Number> getNumber(String number) {
        return startRequest("http://numbersapi.com/", number);
    }

    private Flowable<Number> startRequest(String baseUrl, String request) {
        RetrofitNumbersInterface service = defineRetrofit(baseUrl).create(RetrofitNumbersInterface.class);
        return service.getNumberDescription(request);
    }

    private Retrofit defineRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public void saveNumber(Number number) {

    }

    @Override
    public void deleteNumber() {

    }

    @Override
    public void deleteAllNumbers() {

    }
}
