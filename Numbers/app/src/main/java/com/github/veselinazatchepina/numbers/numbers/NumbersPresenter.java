package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.Calendar;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class NumbersPresenter implements NumbersContract.Presenter {

    private final NumbersDataSource mNumbersRepository;

    private final NumbersContract.View mNumbersView;

    private CompositeDisposable mCompositeDisposable;

    private Number mNumberForSave = null;

    public NumbersPresenter(NumbersDataSource numbersRepository, NumbersContract.View numbersView) {
        mNumbersRepository = numbersRepository;
        mNumbersView = numbersView;
        mNumbersView.setPresenter(this);
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
    public void saveNumber() {
        mNumberForSave.setId(UUID.randomUUID().toString());
        mNumberForSave.setDate(getCurrentDate());
        mNumbersRepository.saveNumber(mNumberForSave);
    }

    private String getCurrentDate() {
        Calendar currentDateInstance = Calendar.getInstance();
        return String.format("%1$td %1$tb %1$tY", currentDateInstance);
    }

    @Override
    public void getNumberDescription(String number, String queryType) {
        mCompositeDisposable.add(mNumbersRepository.getNumber(number, queryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Number>() {
                    @Override
                    public void onNext(Number number) {
                        mNumbersView.setNumberDescription(number.getText());
                        mNumberForSave = number;
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void defineSpinnerPosition(CharSequence charSequence) {
        if (charSequence.toString().contains("/")) {
            mNumbersView.setSpinnerPosition(2);
        }
    }
}
