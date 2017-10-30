package com.github.veselinazatchepina.numbers.data.remote;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.github.veselinazatchepina.numbers.enums.NumbersListType;
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

    public static NumbersRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NumbersRemoteDataSource();
        }
        return INSTANCE;
    }

    private NumbersRemoteDataSource() {
    }

    @Override
    public Flowable<List<Number>> getNumbersByItsValue(String number, String queryType) {
        return startNumbersRequest("http://numbersapi.com/", number, queryType);
    }

    @Override
    public Flowable<Number> getNumberByItsValue(String number, String queryType) {
        return startNumberRequest("http://numbersapi.com/", number, queryType);
    }

    private Flowable<Number> startNumberRequest(String baseUrl, String request, String queryType) {
        RetrofitNumbersInterface service = defineRetrofit(baseUrl).create(RetrofitNumbersInterface.class);
        return service.getNumberDescription(request, queryType);
    }

    private Flowable<List<Number>> startNumbersRequest(String baseUrl, String request, String queryType) {
        final RetrofitNumbersInterface service = defineRetrofit(baseUrl).create(RetrofitNumbersInterface.class);
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
    public void deleteNumber(Number number, NumbersListType type) {

    }

    @Override
    public void deleteAllNumbers(NumbersListType type) {

    }

    @Override
    public Flowable<List<Number>> getNumbers(NumbersListType type) {
        return null;
    }

    @Override
    public void saveUserNumber(Number number) {

    }
}
