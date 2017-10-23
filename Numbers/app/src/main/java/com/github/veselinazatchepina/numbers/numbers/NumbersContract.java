package com.github.veselinazatchepina.numbers.numbers;


import com.github.veselinazatchepina.numbers.BasePresenter;
import com.github.veselinazatchepina.numbers.BaseView;

public interface NumbersContract {

    interface View extends BaseView<Presenter> {

        void setNumberDescription();
    }

    interface Presenter extends BasePresenter {

        void saveNumber();

        void populateNumberCharacteristics();
    }
}
