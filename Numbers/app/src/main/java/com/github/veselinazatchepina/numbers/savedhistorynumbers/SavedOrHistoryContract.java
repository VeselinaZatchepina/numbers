package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import com.github.veselinazatchepina.numbers.BasePresenter;
import com.github.veselinazatchepina.numbers.BaseView;
import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.enums.NumbersListType;

import java.util.List;

public interface SavedOrHistoryContract {

    interface View extends BaseView<Presenter> {
        void showNumbersList(List<Number> numbers);

        void showSnackBarDeletedNumber();

        void showSnackBarDeletedAllNumbers();
    }

    interface Presenter extends BasePresenter {

        void deleteNumber(Number number, NumbersListType type);

        void deleteNumbers(NumbersListType type);

        void getNumbersList(NumbersListType type);
    }
}
