package com.tricktech.sqlitecrudoperations.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by am on 1/27/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "simple";
    private static final String DB_NAME = "simple_db";
    private static final int VERSION = 1;
    public static final String TABLE_NAME = "simple_table";
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";

    public static final String TABLE_QUERY = "CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +KEY_TITLE+" TEXT)";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
         db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
