package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

public class NumbersPresenter implements NumbersContract.Presenter {

    private final NumbersDataSource mNumbersRepository;

    private final NumbersContract.View mNumbersView;

    public NumbersPresenter(NumbersDataSource numbersRepository, NumbersContract.View numbersView) {
        mNumbersRepository = numbersRepository;
        mNumbersView = numbersView;
        mNumbersView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void saveNumber() {

    }

    @Override
    public void populateNumberCharacteristics() {

    }
}
