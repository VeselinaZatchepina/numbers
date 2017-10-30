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
    public Flowable<List<Number>> getNumbersByItsValue(String number, String numberType) {
        return mNumbersRemoteDataSource.getNumbersByItsValue(number, numberType);
    }

    @Override
    public Flowable<Number> getNumberByItsValue(String number, String numberType) {
        return mNumbersRemoteDataSource.getNumberByItsValue(number, numberType);
    }

    @Override
    public void saveHistoryNumber(Number number) {
        mNumbersLocalDataSource.saveHistoryNumber(number);
    }

    @Override
    public void saveUserNumber(Number number) {
        mNumbersLocalDataSource.saveUserNumber(number);
    }

    @Override
    public void deleteUserNumber(Number number) {
        mNumbersLocalDataSource.deleteUserNumber(number);
    }

    @Override
    public void deleteHistoryNumber(Number number) {
        mNumbersLocalDataSource.deleteHistoryNumber(number);
    }

    @Override
    public void deleteAllUserNumbers() {
        mNumbersLocalDataSource.deleteAllUserNumbers();
    }

    @Override
    public void deleteAllHistoryNumbers() {
        mNumbersLocalDataSource.deleteAllHistoryNumbers();
    }

    @Override
    public Flowable<List<Number>> getUserNumbers() {
        return mNumbersLocalDataSource.getUserNumbers();
    }

    @Override
    public Flowable<List<Number>> getHistoryNumbers() {
        return mNumbersLocalDataSource.getHistoryNumbers();
    }
}
