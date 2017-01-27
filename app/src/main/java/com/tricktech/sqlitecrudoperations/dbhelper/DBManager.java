package com.tricktech.sqlitecrudoperations.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tricktech.sqlitecrudoperations.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by am on 1/27/2017.
 */

public class DBManager {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    Context context;

    public DBManager(Context context) {
        this.context = context;
        dbhelper = new DBHelper(context);
    }

    public void open() {
        Log.e("DB", "Database open!");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.e("DB", "Database close!");
        dbhelper.close();
    }

    public long insertItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_TITLE, item.title);
        long id = database.insert(DBHelper.TABLE_NAME, null, values);
        return id;
    }

    public void update(Item item, int id) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.KEY_TITLE, item.title);
        database.update(DBHelper.TABLE_NAME, values, DBHelper.KEY_ID + " = " + id, null);
    }

    public List<Item> getAllItem() {

        List<Item> itemList = new ArrayList<>();
        String select = "SELECT * FROM " + DBHelper.TABLE_NAME;
        Cursor cursor = database.rawQuery(select, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.id = cursor.getInt(0);
                item.title = cursor.getString(1);
                Log.i("@ANWAR", item.title+"");
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return itemList;
    }

    public int remove(int id){
        return database.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + " = " + id, null);
    }
}
