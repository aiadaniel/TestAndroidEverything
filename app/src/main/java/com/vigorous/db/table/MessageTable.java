package com.vigorous.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class MessageTable implements BaseColumns {
    public static final String TABLE_NAME = "message";
    public static final String COLUMN_ID = _ID;
    public static final String COLUMN_CHAT_ID = "chat_id";
    public static final String COLUMN_UID = "uid";
    public static final String COLUMN_SEQ = "seq";
    public static final String COLUMN_DIRECTION = "direction";
    public static final String COLUMN_CONTENT = "content";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CHAT_ID + " INTEGER NOT NULL," +
            COLUMN_CONTENT + " TEXT," +
            COLUMN_UID + " INTEGER NOT NULL" + ");";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    public static void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
        }
    }

    public static void onDelete(SQLiteDatabase db) {
        db.execSQL(DROP_TABLE);
    }
}
