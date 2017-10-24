package com.github.veselinazatchepina.numbers.numbers;

import android.support.v4.app.Fragment;

import com.github.veselinazatchepina.numbers.abstracts.NavigationDrawerAbstractActivity;
import com.github.veselinazatchepina.numbers.data.NumbersRepository;
import com.github.veselinazatchepina.numbers.data.local.NumbersLocalDataSource;
import com.github.veselinazatchepina.numbers.data.remote.NumbersRemoteDataSource;

public class NumbersActivity extends NavigationDrawerAbstractActivity {

    private NumbersFragment mNumbersView;
    private NumbersPresenter mNumbersPresenter;

    @Override
    public Fragment createFragment() {
        mNumbersView = NumbersFragment.newInstance();
        return mNumbersView;
    }

    @Override
    public void createPresenter() {
        NumbersRepository numbersRepository = NumbersRepository.getInstance(
                NumbersRemoteDataSource.getInstance(),
                NumbersLocalDataSource.getInstance(this));
        mNumbersPresenter = new NumbersPresenter(numbersRepository, mNumbersView);
    }
}
