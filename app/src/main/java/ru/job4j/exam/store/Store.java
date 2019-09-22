package ru.job4j.exam.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Store {
    private static SQLiteDatabase store;

    private Store() {
    }

    public static SQLiteDatabase getStore(Context context) {
        if (store == null) {
            store = new ExamBaseHelper(context).getWritableDatabase();
        }
        return store;
    }

    public long add(String table, ContentValues newValue) {
        return this.store.insert(table, null, newValue);
    }

    public long update(String table, ContentValues newValue, String condition) {
        return store.update(table, newValue, condition, null);
    }

    public long delete(String table, String condition) {
        return store.delete(table, condition, null);
    }

    public Cursor find(String table, String condition){
        return this.store.query(
                table,
                null, condition, null,
                null, null, null
        );
    }

}
