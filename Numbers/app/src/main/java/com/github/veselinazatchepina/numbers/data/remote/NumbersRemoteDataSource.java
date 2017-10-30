package com.github.veselinazatchepina.numbers.data.remote;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NumbersRemoteDataSource implements NumbersDataSource {

    private static NumbersRemoteDataSource INSTANCE = null;

    Retrofit mRetrofit;

    public static NumbersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NumbersRemoteDataSource();
        }
        return INSTANCE;
    }

    private NumbersRemoteDataSource() {
        mRetrofit = defineRetrofit("http://numbersapi.com/");
    }

    @Override
    public Flowable<List<Number>> getNumbersByItsValue(String number, String queryType) {
        return getUserNumbers(number, queryType);
    }

    @Override
    public Flowable<Number> getNumberByItsValue(String number, String queryType) {
        return getNumber(number, queryType);
    }

    private Flowable<Number> getNumber(String request, String queryType) {
        RetrofitNumbersInterface service = mRetrofit.create(RetrofitNumbersInterface.class);
        return service.getNumberDescription(request, queryType);
    }

    private Flowable<List<Number>> getUserNumbers(String request, String queryType) {
        final RetrofitNumbersInterface service = mRetrofit.create(RetrofitNumbersInterface.class);
        return service.getNumbersDescription(request, queryType)
                .flatMap(new Function<Map<String, Number>, Flowable<Number>>() {
                    @Override
                    public Flowable<Number> apply(@NonNull Map<String, Number> stringNumberMap) throws Exception {
                        return Flowable.fromIterable(stringNumberMap.values());
                    }
                })
                .toList()
                .toFlowable();
    }

    private Retrofit defineRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Override
    public void saveHistoryNumber(Number number) {

    }

    @Override
    public void deleteUserNumber(Number number) {

    }

    @Override
    public void deleteHistoryNumber(Number number) {

    }

    @Override
    public void deleteAllUserNumbers() {

    }

    @Override
    public void deleteAllHistoryNumbers() {

    }

    @Override
    public Flowable<List<Number>> getUserNumbers() {
        return null;
    }

    @Override
    public Flowable<List<Number>> getHistoryNumbers() {
        return null;
    }

    @Override
    public void saveUserNumber(Number number) {

    }
}
