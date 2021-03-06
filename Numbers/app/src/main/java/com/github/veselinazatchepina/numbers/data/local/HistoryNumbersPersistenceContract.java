package com.github.veselinazatchepina.numbers.data.local;


import android.provider.BaseColumns;

public class HistoryNumbersPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HistoryNumbersPersistenceContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class NumberEntry implements BaseColumns {
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_NUMBER = "number";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
