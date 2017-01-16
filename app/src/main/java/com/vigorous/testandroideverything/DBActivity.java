package com.vigorous.testandroideverything;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vigorous.db.MySqliteHelper;
import com.vigorous.db.table.MessageTable;



public class DBActivity extends AppCompatActivity {
    private static final String tag = "11111";
    private static final int MAX_SQLNUM = 1000;
    SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        MySqliteHelper.getInstance(getApplicationContext());
        mDb = MySqliteHelper.getDb();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //insertTest();
                queryTest();
            }
        }).start();
    }

    public void queryTest() {
        Cursor cursor = mDb.query(MessageTable.TABLE_NAME,null,null,null,null,null,null);
        Log.d(tag,"cursor count " + cursor.getCount());
        while (cursor.moveToNext()){
            int chat_id = cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_CHAT_ID));
        }
        cursor.close();//remember
        mDb.close();
    }

    public void insertTest() {
        // 测试发现3种方式时间差别不大 red mi note 3
        long startTime = SystemClock.elapsedRealtime();
        for (int i = 0; i<MAX_SQLNUM;i++) {
            ContentValues values = new ContentValues();
            values.put(MessageTable.COLUMN_CHAT_ID, 9999);
            values.put(MessageTable.COLUMN_CONTENT, "this is content " + i);
            values.put(MessageTable.COLUMN_UID,11122333);
            mDb.insert(MessageTable.TABLE_NAME,null,values);
        }
        long endTime = SystemClock.elapsedRealtime();
        Log.d(tag,"insert elapse " + (endTime-startTime));

        for (int j=0;j<MAX_SQLNUM;j++) {
            SQLiteStatement statement = mDb.compileStatement("INSERT INTO message(chat_id,content,uid) VALUES (?,?,?)");
            statement.bindLong(1,8888);
            statement.bindString(2,"this is content " + j);
            statement.bindLong(3,444555666);
            statement.executeInsert();
        }
        long endTime2 = SystemClock.elapsedRealtime();
        Log.d(tag,"SQLiteStatement insert elapse " + (endTime2-endTime));

        for (int k=0;k<MAX_SQLNUM;k++) {
            String sql = "INSERT INTO message(chat_id,content,uid) VALUES (?,?,?)";
            Object[] bindAgrs = {7777,"this is conent " + k,777888999};
            mDb.execSQL(sql,bindAgrs);
        }
        long endTime3 = SystemClock.elapsedRealtime();
        Log.d(tag,"execSQL insert " + (endTime3-endTime2));

        //====================================for test transaction
        mDb.beginTransaction();
        for (int m = 0; m<MAX_SQLNUM;m++) {
            ContentValues values = new ContentValues();
            values.put(MessageTable.COLUMN_CHAT_ID, 66666);
            values.put(MessageTable.COLUMN_CONTENT, "this is content " + m);
            values.put(MessageTable.COLUMN_UID,222555888);
            mDb.insert(MessageTable.TABLE_NAME,null,values);
        }
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        long endTime4 = SystemClock.elapsedRealtime();
        Log.d(tag,"transaction insert elapse " + (endTime4-endTime3));
        //经最终测试： 未开启事务的前3种方法基本在4000ms+，开启后基本在100ms左右！！！
        //数据量扩大10倍，前三种基本按10倍时间增加，开启事务下只增长几倍
    }
}
