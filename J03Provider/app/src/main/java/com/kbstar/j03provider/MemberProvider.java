package com.kbstar.j03provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MemberProvider extends ContentProvider {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private static String AUTH = "com.kbstar.j03provider";
    private static String BASE = "member";
    private Uri CONTENT_URI = Uri.parse("content://" + AUTH + "/" + BASE);  // content://com.kbstar.j03provider/member/3

    public MemberProvider() {
    }

    public static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTH, BASE, 1);                // content://com.kbstar.j03provider/member/1   (idx : 1)
        uriMatcher.addURI(AUTH, BASE + "/#", 2);    // content://com.kbstar.j03provider/member/#2  (idx : 2)
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new DatabaseHelper(getContext());
        db = dbHelper.getWritableDatabase();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

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
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}