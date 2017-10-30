package com.github.veselinazatchepina.numbers.data;


import com.github.veselinazatchepina.numbers.enums.NumbersListType;

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
    public void deleteNumber(Number number, NumbersListType type) {
        mNumbersLocalDataSource.deleteNumber(number, type);
    }

    @Override
    public void deleteAllNumbers(NumbersListType type) {
        mNumbersLocalDataSource.deleteAllNumbers(type);
    }

    @Override
    public Flowable<List<Number>> getNumbers(NumbersListType type) {
        return mNumbersLocalDataSource.getNumbers(type);
    }
}
