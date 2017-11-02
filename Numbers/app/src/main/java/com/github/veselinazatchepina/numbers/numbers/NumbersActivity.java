package com.github.veselinazatchepina.numbers.numbers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.veselinazatchepina.numbers.abstracts.NavigationDrawerAbstractActivity;
import com.github.veselinazatchepina.numbers.data.NumbersRepository;
import com.github.veselinazatchepina.numbers.data.local.NumbersLocalDataSource;
import com.github.veselinazatchepina.numbers.data.remote.NumbersRemoteDataSource;

public class NumbersActivity extends NavigationDrawerAbstractActivity {

    private static final String NUMBERS_TITLE = "numbers_title";

    private NumbersFragment mNumbersView;
    private NumbersPresenter mNumbersPresenter;

    public static Intent newIntent(Context context, String title) {
        Intent intent = new Intent(context, NumbersActivity.class);
        intent.putExtra(NUMBERS_TITLE, title);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        mNumbersView = NumbersFragment.newInstance();
        return mNumbersView;
    }

    @Override
    public void defineTitle() {
        super.defineTitle();
        String title = getIntent().getStringExtra(NUMBERS_TITLE);
        if (title != null) {
            setTitle(title);
        }
    }

    @Override
    public void createPresenter() {
        NumbersRepository numbersRepository = NumbersRepository.getInstance(
                NumbersRemoteDataSource.getInstance(),
                NumbersLocalDataSource.getInstance(this, provideSchedulerProvider()));
        mNumbersPresenter = new NumbersPresenter(numbersRepository, mNumbersView);
    }
}
