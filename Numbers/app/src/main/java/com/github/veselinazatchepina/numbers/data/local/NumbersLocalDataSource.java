package com.github.veselinazatchepina.numbers.data.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.github.veselinazatchepina.numbers.utils.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.Flowable;

public class NumbersLocalDataSource implements NumbersDataSource {

    private static NumbersLocalDataSource INSTANCE = null;

    private final BriteDatabase mDatabaseHelper;

    public static NumbersLocalDataSource getInstance(Context context,
                                                     BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new NumbersLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    private NumbersLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        NumbersDbHelper dbHelper = new NumbersDbHelper(context);
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(dbHelper, schedulerProvider.io());
    }

    @Override
    public Flowable<List<Number>> getNumbers(String number, String queryType) {
        return null;
    }

    @Override
    public Flowable<Number> getNumber(String number, String queryType) {
        return null;
    }

    @Override
    public void saveNumber(Number number) {
        ContentValues values = new ContentValues();
        values.put(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID, number.getId());
        values.put(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER, number.getNumber());
        values.put(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION, number.getText());
        values.put(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE, number.getType());
        values.put(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE, number.getDate());
        mDatabaseHelper.insert(NumbersPersistenceContract.NumberEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public void deleteNumber() {

    }

    @Override
    public void deleteAllNumbers() {

    }
}
