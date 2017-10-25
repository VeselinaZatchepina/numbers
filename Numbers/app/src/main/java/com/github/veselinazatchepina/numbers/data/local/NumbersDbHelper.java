package com.github.veselinazatchepina.numbers.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NumbersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Numbers.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NumbersPersistenceContract.NumberEntry.TABLE_NAME + " (" +
                    NumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    NumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER + TEXT_TYPE + COMMA_SEP +
                    NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    NumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE + TEXT_TYPE +
                    " )";

    public NumbersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
