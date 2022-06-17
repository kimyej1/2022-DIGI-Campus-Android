package com.kbstar.j01helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Variables
    public static String dbName = "kbstar.db";
    public static int VERSION = 1;

    // Constructors
    public DatabaseHelper(@Nullable Context context) {
        super(context, dbName, null, VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    // Implement Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        printLog("onCreate()");
        String sql = "CREATE TABLE if not exists user_table "
                + "( idx integer, name text, age integer, mobile text, primary key(idx) )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        printLog("onUpgrade()");

        if(newVersion > 1 ) {
            db.execSQL("DROP TABLE if exists " + dbName);

            String sql = "ALTER TABLE user_table add mobile text";
            db.execSQL(sql);
        }
    }

    // Methods
    public void printLog(String msg) {
        Log.d("DBHelper", "======================================" + msg);
    }
}
