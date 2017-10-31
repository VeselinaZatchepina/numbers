package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.Calendar;
import java.util.List;
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

    private List<Number> mNumbersForSave = null;

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
    public void saveUserNumber() {
        saveNumber();
        saveNumbersList();
    }

    private void saveNumber() {
        if (mNumberForSave != null) {
            setIdAndDateForNumber(mNumberForSave);
            mNumbersRepository.saveUserNumber(mNumberForSave);
            mNumberForSave = null;
        }
    }

    private void saveNumbersList() {
        if (mNumbersForSave != null && !mNumbersForSave.isEmpty()) {
            for (Number number : mNumbersForSave) {
                setIdAndDateForNumber(number);
                mNumbersRepository.saveUserNumber(number);
            }
            mNumbersForSave = null;
        }
    }

    private void setIdAndDateForNumber(Number number) {
        number.setId(UUID.randomUUID().toString());
        number.setDate(getCurrentDate());
    }

    private String getCurrentDate() {
        Calendar currentDateInstance = Calendar.getInstance();
        return String.format("%1$td %1$tb %1$tY", currentDateInstance);
    }

    @Override
    public void getNumberDescription(final String number, String queryType) {
        if (number.contains("..") || number.contains(",")) {
            getDescriptionFromNumbersList(number, queryType);
        } else {
            getDescriptionFromNumber(number, queryType);
        }
    }

    private void getDescriptionFromNumbersList(String number, String queryType) {
        mCompositeDisposable.add(mNumbersRepository.getNumbersByItsValue(number, queryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<Number>>() {
                    @Override
                    public void onNext(List<Number> numbers) {
                        mNumbersView.setNumberDescription(createDescriptionFromNumbersList(numbers));
                        mNumbersForSave = numbers;
                        for (Number number : numbers) {
                            setIdAndDateForNumber(number);
                            mNumbersRepository.saveHistoryNumber(number);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mNumbersForSave = null;
                        mNumbersView.showInputError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void getDescriptionFromNumber(String number, String queryType) {
        mCompositeDisposable.add(mNumbersRepository.getNumberByItsValue(number, queryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Number>() {
                    @Override
                    public void onNext(Number number) {
                        mNumbersView.setNumberDescription(number.getText());
                        mNumberForSave = number;
                        setIdAndDateForNumber(number);
                        mNumbersRepository.saveHistoryNumber(number);
                    }

                    @Override
                    public void onError(Throwable t) {
                        mNumberForSave = null;
                        mNumbersView.showInputError();
                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private String createDescriptionFromNumbersList(List<Number> numbers) {
        StringBuilder builder = new StringBuilder();
        for (Number number : numbers) {
            builder.append(number.getText())
                    .append("\n");
        }
        return builder.toString();
    }

    @Override
    public void defineSpinnerPosition(CharSequence charSequence) {
        if (charSequence.toString().contains("/")) {
            mNumbersView.setSpinnerPosition(2);
        }
    }

    @Override
    public void updateNumberForSave() {
        if (mNumberForSave != null) {
            mNumberForSave = null;
        }
        if (mNumbersForSave != null) {
            mNumbersForSave = null;
        }
    }
}
