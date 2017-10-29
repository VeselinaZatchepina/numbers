package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

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
        mNumbersRepository.deleteNumber(number);
    }

    @Override
    public void deleteNumbers() {
        mNumbersRepository.deleteAllNumbers();
    }

    @Override
    public void getNumbersList() {
        mCompositeDisposable.add(mNumbersRepository.getNumbers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<List<Number>>() {
                    @Override
                    public void onNext(List<Number> numbers) {
                        mSavedOrHistoryView.showNumbersList(numbers);
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
