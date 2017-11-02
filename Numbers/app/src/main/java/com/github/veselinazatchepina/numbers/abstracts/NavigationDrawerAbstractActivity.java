package com.github.veselinazatchepina.numbers.abstracts;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.github.veselinazatchepina.numbers.R;
import com.github.veselinazatchepina.numbers.about.AboutActivity;
import com.github.veselinazatchepina.numbers.numbers.NumbersActivity;
import com.github.veselinazatchepina.numbers.savedhistorynumbers.SavedOrHistoryActivity;
import com.github.veselinazatchepina.numbers.utils.BaseSchedulerProvider;
import com.github.veselinazatchepina.numbers.utils.ColorationTextChar;
import com.github.veselinazatchepina.numbers.utils.SchedulerProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class NavigationDrawerAbstractActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        ButterKnife.bind(this);
        defineNavigationDrawer();
        defineTitle();
        setTitle(ColorationTextChar.setFirstVowelColor(getTitle().toString(), this));
        defineFragment();
        createPresenter();
    }

    private void defineNavigationDrawer() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        Menu menu = mNavigationView.getMenu();
        MenuItem tools = menu.findItem(R.id.other);
        SpannableString s = new SpannableString(tools.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.NavigationViewStyle), 0, s.length(), 0);
        tools.setTitle(s);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    public void defineTitle() {

    }

    private void defineFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (currentFragment == null) {
            currentFragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, currentFragment)
                    .commit();
        }
    }

    public abstract Fragment createFragment();

    public abstract void createPresenter();

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.menu_numbers:
                intent = NumbersActivity.newIntent(this, getString(R.string.numbers_activity_title));
                break;
            case R.id.menu_saved_numbers:
                intent = SavedOrHistoryActivity.newIntent(this, getString(R.string.saved_numbers_activity_title));
                break;
            case R.id.menu_history:
                intent = SavedOrHistoryActivity.newIntent(this, getString(R.string.history_numbers_activity_title));
                break;
            case R.id.menu_about:
                intent = AboutActivity.newIntent(this, getString(R.string.about_activity_title));
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return true;
    }
}
