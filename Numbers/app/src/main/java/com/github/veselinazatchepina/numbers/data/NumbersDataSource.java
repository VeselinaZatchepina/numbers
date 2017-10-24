package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public interface NumbersDataSource {

    Flowable<List<Number>> getNumbers();

    Flowable<Number> getNumber(String number);

    void saveNumber();

    void deleteNumber();

    void deleteAllNumbers();
}
