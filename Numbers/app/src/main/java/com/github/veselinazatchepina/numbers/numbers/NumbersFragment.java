package com.github.veselinazatchepina.numbers.numbers;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.veselinazatchepina.numbers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class NumbersFragment extends Fragment implements NumbersContract.View {

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
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getNumberDescription(mNumberValue.getText().toString(),
                        mSpinner.getSelectedItem().toString());
            }
        });
        defineFab();
        defineQueryTypeSpinner();
        return rootView;
    }

    @Override
    public void setPresenter(NumbersContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setNumberDescription(String description) {
        mDescription.setText(description);
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
        mPresenter.saveNumber();
    }

    private void defineQueryTypeSpinner() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter.addAll(getResources().getStringArray(R.array.query_types));
        mSpinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
