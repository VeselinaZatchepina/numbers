package com.github.veselinazatchepina.numbers.data;


import java.util.List;

import io.reactivex.Flowable;

public class NumbersRepository implements NumbersDataSource {

    @Override
    public Flowable<List<Number>> getNumbers() {
        return null;
    }

    @Override
    public Flowable<Number> getNumber() {
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
