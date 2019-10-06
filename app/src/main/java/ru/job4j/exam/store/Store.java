package ru.job4j.exam.store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Store {
    private static Store store;
    private static SQLiteDatabase db;

    private Store() {
    }

    public static Store getStore(Context context) {
        if (store == null) {
            store = new Store();
            db = new ExamBaseHelper(context).getWritableDatabase();
        }
        return store;
    }

    public long add(String table, ContentValues newValue) {
        return this.db.insert(table, null, newValue);
    }

    public long update(String table, ContentValues newValue, String condition) {
        return this.db.update(table, newValue, condition, null);
    }

    public long delete(String table, String condition) {
        return this.db.delete(table, condition, null);
    }

    public Cursor find(String table, String condition){
        return this.db.query(
                table,
                null, condition, null,
                null, null, null
        );
    }

}
