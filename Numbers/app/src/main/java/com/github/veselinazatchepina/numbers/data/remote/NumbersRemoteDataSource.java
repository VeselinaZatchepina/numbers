package com.github.veselinazatchepina.numbers.data.remote;

import android.util.Log;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.List;

import io.reactivex.Flowable;


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
    public String getNumber() {
        Log.v("OK", "OK");
        return "hello hello me dear!";
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
