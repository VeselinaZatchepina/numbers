package com.github.veselinazatchepina.numbers.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NumbersDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Numbers.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES_HISTORY =
            "CREATE TABLE " + HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME + " (" +
                    HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER + TEXT_TYPE + COMMA_SEP +
                    HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_ENTRIES_USER =
            "CREATE TABLE " + UserNumbersPersistenceContract.NumberEntry.TABLE_NAME + " (" +
                    UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + " PRIMARY KEY," +
                    UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER + TEXT_TYPE + COMMA_SEP +
                    UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE + TEXT_TYPE +
                    " )";

    public NumbersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_HISTORY);
        db.execSQL(SQL_CREATE_ENTRIES_USER);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
