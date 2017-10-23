package com.github.veselinazatchepina.numbers.numbers;

import android.support.v4.app.Fragment;

import com.github.veselinazatchepina.numbers.abstracts.NavigationDrawerAbstractActivity;

public class NumbersActivity extends NavigationDrawerAbstractActivity {

    @Override
    public Fragment createFragment() {
        return NumbersFragment.newInstance();
    }
}
