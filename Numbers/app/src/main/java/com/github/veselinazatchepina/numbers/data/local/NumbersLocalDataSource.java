package com.github.veselinazatchepina.numbers.data.local;


import android.content.Context;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.List;

import io.reactivex.Flowable;

public class NumbersLocalDataSource implements NumbersDataSource {

    private static NumbersLocalDataSource INSTANCE = null;

    public static NumbersLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NumbersLocalDataSource(context);
        }
        return INSTANCE;
    }

    private NumbersLocalDataSource(Context context) {

    }

    @Override
    public Flowable<List<Number>> getNumbers() {
        return null;
    }

    @Override
    public Flowable<Number> getNumber(String number) {
        return null;
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
