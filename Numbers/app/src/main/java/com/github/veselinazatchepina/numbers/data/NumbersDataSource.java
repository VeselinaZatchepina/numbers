package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public interface NumbersDataSource {

    Flowable<List<Number>> getNumbersByItsValue(String number, String numberType);

    Flowable<Number> getNumberByItsValue(String number, String numberType);

    Flowable<List<Number>> getUserNumbers();

    Flowable<List<Number>> getHistoryNumbers();

    void saveHistoryNumber(Number number);

    void saveUserNumber(Number number);

    void deleteUserNumber(Number number);

    void deleteHistoryNumber(Number number);

    void deleteAllUserNumbers();

    void deleteAllHistoryNumbers();
}
