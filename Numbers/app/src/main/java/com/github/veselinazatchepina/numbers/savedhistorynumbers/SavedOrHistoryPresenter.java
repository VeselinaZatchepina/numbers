package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.github.veselinazatchepina.numbers.enums.NumbersListType;

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
    public void deleteNumber(Number number, NumbersListType type) {
        if (type.equals(NumbersListType.HISTORY)) {
            mNumbersRepository.deleteHistoryNumber(number);
        } else {
            mNumbersRepository.deleteUserNumber(number);
        }
        mSavedOrHistoryView.showSnackBarDeletedNumber();
    }

    @Override
    public void deleteNumbers(NumbersListType type) {
        if (type.equals(NumbersListType.HISTORY)) {
            mNumbersRepository.deleteAllHistoryNumbers();
        } else {
            mNumbersRepository.deleteAllUserNumbers();
        }
        mSavedOrHistoryView.showSnackBarDeletedAllNumbers();
    }

    @Override
    public void getNumbersList(NumbersListType type) {
        if (type.equals(NumbersListType.HISTORY)) {
            mCompositeDisposable.add(mNumbersRepository.getHistoryNumbers()
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
        } else {
            mCompositeDisposable.add(mNumbersRepository.getUserNumbers()
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
}
