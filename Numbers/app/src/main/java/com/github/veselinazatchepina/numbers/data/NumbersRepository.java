package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public class NumbersRepository implements NumbersDataSource {

    private static NumbersRepository INSTANCE = null;

    private final NumbersDataSource mNumbersRemoteDataSource;

    private final NumbersDataSource mNumbersLocalDataSource;

    public static NumbersRepository getInstance(NumbersDataSource numbersRemoteDataSource,
                                              NumbersDataSource numbersLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new NumbersRepository(numbersRemoteDataSource, numbersLocalDataSource);
        }
        return INSTANCE;
    }

    private NumbersRepository(NumbersDataSource numbersRemoteDataSource,
                            NumbersDataSource numbersLocalDataSource) {
        mNumbersRemoteDataSource = numbersRemoteDataSource;
        mNumbersLocalDataSource = numbersLocalDataSource;
    }

    @Override
    public Flowable<List<Number>> getNumbers() {
        return null;
    }

    @Override
    public Flowable<Number> getNumberDescription(String number) {
        return mNumbersRemoteDataSource.getNumberDescription(number);
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
