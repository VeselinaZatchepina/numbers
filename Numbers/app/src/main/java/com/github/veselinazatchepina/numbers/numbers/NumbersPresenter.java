package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class NumbersPresenter implements NumbersContract.Presenter {

    private final NumbersDataSource mNumbersRepository;

    private final NumbersContract.View mNumbersView;

    private CompositeDisposable mCompositeDisposable;

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

    }

    @Override
    public void getNumberDescription(String number) {
        mCompositeDisposable.add(mNumbersRepository.getNumber(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Number>() {
                    @Override
                    public void onNext(Number number) {
                        mNumbersView.setNumberDescription(number.getText());
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
