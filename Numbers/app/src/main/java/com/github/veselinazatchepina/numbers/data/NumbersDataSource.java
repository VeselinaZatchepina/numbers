package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public interface NumbersDataSource {

    Flowable<List<Number>> getNumbersByItsValue(String number, String numberType);

    Flowable<Number> getNumberByItsValue(String number, String numberType);

    Flowable<List<Number>> getNumbers();

    void saveNumber(Number number);

    void deleteNumber(Number number);

    void deleteAllNumbers();
}
