package com.kbstar.j02search;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
    Android DB : SQLite Embedded 되어있다

    SQLite : 파일로 만들어진 하위 수준의 데이터베이스
        데이터 복사, 이동, 삭제
        파일임에도 데이터 조회 속도가 빠르다.

        표준 SQL 지원 : Insert, Update, Select, Delete, Create, Alter
 */
public class MainActivity extends AppCompatActivity {

    EditText inputDB, inputTable, textName, textAge, textMobile;
    Button buttonDB, buttonTable, buttonSearch, buttonInsert;
    TextView debugText;
    String table;

    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputDB = findViewById(R.id.inputDB);
        inputTable = findViewById(R.id.inputTable);
        buttonDB = findViewById(R.id.buttonDB);
        buttonTable = findViewById(R.id.buttonTable);

        debugText = findViewById(R.id.debugText);
        textName = findViewById(R.id.textName);
        textAge = findViewById(R.id.textAge);
        textMobile = findViewById(R.id.textMobile);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonInsert = findViewById(R.id.buttonInsert);

        buttonDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDB(inputDB.getText().toString());
            }
        });

        buttonTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                table = inputTable.getText().toString();
                createTable();  // 여기에 파라미터로 createTable(table) 이렇게 해도 되는데, table이 전역변수라 굳이 없어도 됨!
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });
    }

    public void createDB(String dbName) {
        // Without Helper
//        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
//        printDebug("DB Created : " + dbName);

        // With Helper
//        dbHelper = new DatabaseHelper(this);  // this = getApplicationContext()
        dbHelper = new DatabaseHelper(this, dbName, 1);

        try {
            db = dbHelper.getWritableDatabase();
            printDebug("DB Created by Helper : " + dbName);
        } catch(Exception e) {
            printDebug("[ERROR] Insert DB Name..");
        }
    }

    public void createTable() {
        if(db == null) {
            printDebug("[ERROR] NO Database Selected..");
        } else {
            String sql = "CREATE TABLE if not exists " + table
                    + "(idx integer, name text, age integer, mobile text, primary key(idx) )";

            try {
                db.execSQL(sql);
                printDebug("Table Created : " + table);
            } catch(Exception e) {
                printDebug("[ERROR] Insert Table Name..");
            }
        }
    }

    public void printDebug(String msg) {
        debugText.append("\n" + msg);
    }

    public void insert() {
        String name = textName.getText().toString();
        String age = textAge.getText().toString();
        String mobile = textMobile.getText().toString();

        String sql = "INSERT INTO " + table + " (name, age, mobile) VALUES ('"
                + name + "', '" + age + "', '" + mobile + "')";
        db.execSQL(sql);
        printDebug("Record Added! : (name, age, mobile) = ('" + name + "', '" + age + "', '" + mobile + "');");
    }

    public void search() {

        try {
            printDebug("Search()");

            String sql = "SELECT * FROM " + table + " ORDER BY idx DESC";
            Cursor cursor = db.rawQuery(sql, null);

            int dataCnt = cursor.getCount();
            printDebug("data count = " + dataCnt);

            for(int i=0; i<dataCnt; i++) {
                cursor.moveToNext();

                int idx = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                String mobile = cursor.getString(3);

                String dataFormat = idx + "\t" + name + "\t" + age + "\t" + mobile;
                printDebug(dataFormat);
            }
            cursor.close();

        } catch(Exception e) {
            printDebug("[ERROR] No Table to Search..");
        }

    }
}