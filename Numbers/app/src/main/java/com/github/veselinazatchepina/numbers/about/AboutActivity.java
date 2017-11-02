package com.github.veselinazatchepina.numbers.about;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.github.veselinazatchepina.numbers.abstracts.NavigationDrawerAbstractActivity;

public class AboutActivity extends NavigationDrawerAbstractActivity {

    private static final String ABOUT_TITLE = "about_title";

    public static Intent newIntent(Context context, String title) {
        Intent intent = new Intent(context, AboutActivity.class);
        intent.putExtra(ABOUT_TITLE, title);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return AboutFragment.newInstance();
    }

    @Override
    public void createPresenter() {

    }

    @Override
    public void defineTitle() {
        super.defineTitle();
        String title = getIntent().getStringExtra(ABOUT_TITLE);
        if (title != null) {
            setTitle(title);
        }
    }
}
