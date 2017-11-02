package com.github.veselinazatchepina.numbers.numbers;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.veselinazatchepina.numbers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NumbersFragment extends Fragment implements NumbersContract.View {

    @BindView(R.id.question_button)
    Button mQuestionButton;
    @BindView(R.id.number_value)
    EditText mNumberValue;
    @BindView(R.id.submit_button)
    Button mSubmitButton;
    @BindView(R.id.number_description)
    TextView mDescription;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.query_type_spinner)
    Spinner mSpinner;
    private Unbinder unbinder;

    private NumbersContract.Presenter mPresenter;
    int mFabImageResourceId = setFabImageResourceId();
    private Intent mSharingIntent;

    public NumbersFragment() {
    }

    public static NumbersFragment newInstance() {
        return new NumbersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        defineQuestionButton();
        defineEditText();
        defineQueryTypeSpinner();
        defineSpinnerListener();
        defineSubmitButton();
        defineFab();
        return rootView;
    }

    private void defineQuestionButton() {
        mQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupMenu(view);
            }
        });
    }

    private void openPopupMenu(View view) {
        PopupWindow popup = new PopupWindow(getActivity());
        View layout = getActivity().getLayoutInflater().inflate(R.layout.poup_hint, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popup.setElevation(2);
        }
        // Show anchored to button
        popup.showAsDropDown(view, -250, 20);
    }

    private void defineEditText() {
        mNumberValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.defineSpinnerPosition(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPresenter.updateNumberForSave();
            }
        });
    }

    private void defineQueryTypeSpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter.addAll(getResources().getStringArray(R.array.query_types));
        mSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void defineSpinnerListener() {
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPresenter.defineSpinnerPosition(mNumberValue.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void defineSubmitButton() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getNumberDescription(mNumberValue.getText().toString(),
                        mSpinner.getSelectedItem().toString().toLowerCase());
            }
        });
    }

    private void defineFab() {
        setFabBackgroundImage(mFloatingActionButton, mFabImageResourceId);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defineActionWhenFabIsPressed();
            }
        });
    }

    private int setFabImageResourceId() {
        return R.drawable.ic_check_white_24dp;
    }

    private void setFabBackgroundImage(FloatingActionButton fab, int imageResourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(imageResourceId, getActivity().getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(imageResourceId));
        }
    }

    private void defineActionWhenFabIsPressed() {
        mPresenter.saveUserNumber();
    }

    @Override
    public void setPresenter(NumbersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setNumberDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void setSpinnerPosition(int position) {
        mSpinner.setSelection(position);
    }

    @Override
    public void showInputError() {
        Toast.makeText(getActivity(), getString(R.string.input_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.numbers_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                defineShareIntent();
                startActivity(Intent.createChooser(mSharingIntent, getString(R.string.share_chooser_title)));
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void defineShareIntent() {
        mSharingIntent = new Intent(Intent.ACTION_SEND);
        mSharingIntent.setType("text/plain");
        String quoteTextForShareBody = "\"" + mDescription.getText().toString() + "\"";
        mSharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.current_number_share_intent_theme));
        mSharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, quoteTextForShareBody);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
