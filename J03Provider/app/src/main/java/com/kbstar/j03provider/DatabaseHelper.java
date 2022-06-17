package com.kbstar.j03provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Variables
    private static String DB = "kbstar.db";
    private static int VERSION = 1;

    private final String IDX = "idx";
    private final String NAME = "name";
    private final String AGE = "age";
    private final String MOBILE = "mobile";
    private final String TABLE = "member";
    private final String CREATE = "CREATE TABLE " + TABLE + "(" + IDX + " integer, "
            + NAME + " text, " + AGE + " integer, " + MOBILE + " text, PRIMARY KEY(" + IDX + "))";

    private final String[] COLUMNS = {IDX, NAME, AGE, MOBILE};

    // Constructors
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB, null, VERSION);
    }

    public DatabaseHelper(@Nullable Context context, @Nullable String name, int version) {
        super(context, name, null, version);
    }

    // Implement Methods
    @Override
    public void onCreate(SQLiteDatabase db) {
        printLog("onCreate() by Helper");
        db.execSQL(CREATE); // create table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        printLog("onUpgrade()");

        if(newVersion > 1 ) {
            db.execSQL("DROP TABLE if exists " + DB);
            db.execSQL(CREATE);
        }
    }

    // Methods
    public void printLog(String msg) {
        Log.d("DBHelper", "======================================" + msg);
    }
}
