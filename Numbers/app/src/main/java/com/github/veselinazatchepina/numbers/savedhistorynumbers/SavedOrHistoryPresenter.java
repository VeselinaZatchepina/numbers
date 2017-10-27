package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SavedOrHistoryPresenter implements SavedOrHistoryContract.Presenter {

    private final NumbersDataSource mNumbersRepository;

    private final SavedOrHistoryContract.View mSavedOrHistoryView;

    private CompositeDisposable mCompositeDisposable;

    public SavedOrHistoryPresenter(NumbersDataSource numbersRepository,
                                   SavedOrHistoryContract.View savedOrHistoryView) {
        mNumbersRepository = numbersRepository;
        mSavedOrHistoryView = savedOrHistoryView;
        mSavedOrHistoryView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void deleteNumber(Number number) {

    }

    @Override
    public void deleteNumbers(Number number) {

    }

    @Override
    public List<Number> getNumbersList() {
        return new ArrayList<>();
    }
}
