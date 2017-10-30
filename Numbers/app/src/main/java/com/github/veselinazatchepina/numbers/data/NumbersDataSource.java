package com.github.veselinazatchepina.numbers.data;


import com.github.veselinazatchepina.numbers.enums.NumbersListType;

import java.util.List;

import io.reactivex.Flowable;

public interface NumbersDataSource {

    Flowable<List<Number>> getNumbersByItsValue(String number, String numberType);

    Flowable<Number> getNumberByItsValue(String number, String numberType);

    Flowable<List<Number>> getNumbers(NumbersListType type);

    void saveHistoryNumber(Number number);

    void saveUserNumber(Number number);

    void deleteNumber(Number number, NumbersListType type);

    void deleteAllNumbers(NumbersListType type);
}
