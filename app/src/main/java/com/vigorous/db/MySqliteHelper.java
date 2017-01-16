package com.vigorous.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vigorous.db.table.MessageTable;

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String tag = "11111";
    private static final String DB_NAME = "vigorousdb";
    private static final int DB_VERSION = 2;

    public MySqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag,"OpenHelper onCreate");
        MessageTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(tag,"OpenHelper onUpgrade " + oldVersion + "=>" + newVersion);
        MessageTable.onUpdate(db, oldVersion, newVersion);
    }

    public static void deleteTable() {
        MessageTable.onDelete(getDb());
    }

    private static MySqliteHelper instance = null;

    public static MySqliteHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MySqliteHelper.class) {
                instance = new MySqliteHelper(context);
            }
        }
        return instance;
    }

    public static SQLiteDatabase getDb() {
        if (instance == null)
            throw new IllegalStateException("db not init yet");
        return instance.getWritableDatabase();
    }
}
