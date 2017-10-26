package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.BasePresenter;
import com.github.veselinazatchepina.numbers.BaseView;

public interface NumbersContract {

    interface View extends BaseView<Presenter> {

        void setNumberDescription(String description);

        void setSpinnerPosition(int position);
    }

    interface Presenter extends BasePresenter {

        void saveNumber();

        void getNumberDescription(String number, String queryType);

        void defineSpinnerPosition(CharSequence charSequence);
    }
}
