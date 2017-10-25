package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public interface NumbersDataSource {

    Flowable<List<Number>> getNumbers();

    Flowable<Number> getNumber(String number, String queryType);

    void saveNumber(Number number);

    void deleteNumber();

    void deleteAllNumbers();
}
