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
        String itemId = c.getString(c.getColumnIndexOrThrow(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID));
        String itemNumberValue = c.getString(c.getColumnIndexOrThrow(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER));
        String itemDescription = c.getString(c.getColumnIndexOrThrow(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION));
        String itemType = c.getString(c.getColumnIndexOrThrow(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE));
        String itemDate = c.getString(c.getColumnIndexOrThrow(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE));
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
    public void saveHistoryNumber(Number number) {
        mDatabaseHelper.insert(HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME,
                createSaveHistoryNumberContentValues(number),
                SQLiteDatabase.CONFLICT_REPLACE);
    }


    private ContentValues createSaveHistoryNumberContentValues(Number number) {
        ContentValues values = new ContentValues();
        values.put(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID, number.getId());
        values.put(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER, number.getNumber());
        values.put(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION, number.getText());
        values.put(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE, number.getType());
        values.put(HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE, number.getDate());
        return values;
    }

    @Override
    public void saveUserNumber(Number number) {
        mDatabaseHelper.insert(UserNumbersPersistenceContract.NumberEntry.TABLE_NAME,
                createSaveUserNumberContentValues(number),
                SQLiteDatabase.CONFLICT_REPLACE);
    }

    private ContentValues createSaveUserNumberContentValues(Number number) {
        ContentValues values = new ContentValues();
        values.put(UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID, number.getId());
        values.put(UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER, number.getNumber());
        values.put(UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION, number.getText());
        values.put(UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE, number.getType());
        values.put(UserNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE, number.getDate());
        return values;
    }

    @Override
    public void deleteNumber(Number number) {
        String selection = HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
        String[] selectionArgs = {number.getId()};
        mDatabaseHelper.delete(HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public void deleteAllNumbers() {
        mDatabaseHelper.delete(HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME, null);
    }

    @Override
    public Flowable<List<Number>> getNumbers() {
        String[] projection = {
                HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_ENTRY_ID,
                HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_NUMBER,
                HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_TYPE,
                HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DESCRIPTION,
                HistoryNumbersPersistenceContract.NumberEntry.COLUMN_NAME_DATE
        };
        String sql = String.format("SELECT %s FROM %s", TextUtils.join(",", projection),
                HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME);
        return mDatabaseHelper.createQuery(HistoryNumbersPersistenceContract.NumberEntry.TABLE_NAME, sql)
                .mapToList(mNumberMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }
}
