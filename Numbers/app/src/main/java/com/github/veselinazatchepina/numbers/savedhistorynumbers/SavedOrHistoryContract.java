package com.github.veselinazatchepina.numbers.savedhistorynumbers;


import com.github.veselinazatchepina.numbers.BasePresenter;
import com.github.veselinazatchepina.numbers.BaseView;
import com.github.veselinazatchepina.numbers.data.Number;

import java.util.List;

public interface SavedOrHistoryContract {

    interface View extends BaseView<Presenter> {
        void showNumbersList(List<Number> numbers);
    }

    interface Presenter extends BasePresenter {

        void deleteNumber(Number number);

        void deleteNumbers(Number number);

        void getNumbersList();
    }
}
