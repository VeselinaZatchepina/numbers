package com.github.veselinazatchepina.numbers.savedhistorynumbers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.veselinazatchepina.numbers.abstracts.NavigationDrawerAbstractActivity;
import com.github.veselinazatchepina.numbers.data.NumbersRepository;
import com.github.veselinazatchepina.numbers.data.local.NumbersLocalDataSource;
import com.github.veselinazatchepina.numbers.data.remote.NumbersRemoteDataSource;


public class SavedOrHistoryActivity extends NavigationDrawerAbstractActivity {

    private static final String SAVED_OR_HISTORY_TITLE = "saved_or_history_title";

    private SavedOrHistoryFragment mSavedOrHistoryView;
    private SavedOrHistoryPresenter mSavedOrHistoryPresenter;

    public static Intent newIntent(Context context, String title) {
        Intent intent = new Intent(context, SavedOrHistoryActivity.class);
        intent.putExtra(SAVED_OR_HISTORY_TITLE, title);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        mSavedOrHistoryView = SavedOrHistoryFragment.newInstance();
        return mSavedOrHistoryView;
    }

    @Override
    public void createPresenter() {
        NumbersRepository numbersRepository = NumbersRepository.getInstance(
                NumbersRemoteDataSource.getInstance(),
                NumbersLocalDataSource.getInstance(this, provideSchedulerProvider()));
        mSavedOrHistoryPresenter = new SavedOrHistoryPresenter(numbersRepository, mSavedOrHistoryView);
    }
}
