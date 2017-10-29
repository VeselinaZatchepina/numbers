package com.github.veselinazatchepina.numbers.data.local;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.github.veselinazatchepina.numbers.data.Number;
import com.github.veselinazatchepina.numbers.data.NumbersDataSource;
import com.github.veselinazatchepina.numbers.utils.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class NumbersLocalDataSource implements NumbersDataSource {

    private static NumbersLocalDataSource INSTANCE = null;

    private final BriteDatabase mDatabaseHelper;

    private Function<Cursor, Number> mNumberMapperFunction;

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
        mNumberMapperFunction = this::createNumber;
    }

    private Number createNumber(Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID));
        String itemNumberValue = c.getString(c.getColumnIndexOrThrow(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER));
        String itemDescription = c.getString(c.getColumnIndexOrThrow(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION));
        String itemType = c.getString(c.getColumnIndexOrThrow(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE));
        String itemDate = c.getString(c.getColumnIndexOrThrow(NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE));
        return new Number(itemDescription, itemNumberValue, itemType, itemId, itemDate);
    }

    @Override
    public Flowable<List<Number>> getNumbersByItsValue(String number, String queryType) {
        return null;
    }

    @Override
    public Flowable<Number> getNumberByItsValue(String number, String queryType) {
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
    public void deleteNumber(Number number) {
        String selection = NumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {number.getId()};
        mDatabaseHelper.delete(NumbersPersistenceContract.NumberEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void deleteAllNumbers() {

    }

    @Override
    public Flowable<List<Number>> getNumbers() {
        String[] projection = {
                NumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID,
                NumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER,
                NumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE,
                NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION,
                NumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection),
                NumbersPersistenceContract.NumberEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(NumbersPersistenceContract.NumberEntry.TABLE_NAME, sql)
                .mapToList(mNumberMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }
}
