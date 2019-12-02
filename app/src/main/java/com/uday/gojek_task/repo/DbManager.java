package com.uday.gojek_task.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import com.uday.gojek_task.models.Avatars;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Trending";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "images";
    private File DB_FILE;

    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DB_FILE = context.getDatabasePath(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + "id" + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + "url" + " INTEGER NOT NULL,\n" +
                "    " + "bitmap" + " BLOB NOT NULL\n" +
                ");";
        db.execSQL(sql);
    }

    private boolean checkDataBase() {
        return DB_FILE.exists();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean insertImage(String url,  byte[] image){

        ContentValues contentValues = new ContentValues();
        contentValues.put("url", url);
        contentValues.put("bitmap",image);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME,null, contentValues) != -1;

    }

    public Cursor getALLItems(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<Avatars> readAvatars() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getALLItems();
        List<Avatars> avatars = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Avatars temp = new Avatars(cursor.getString(cursor.getColumnIndex("url")),
                        cursor.getBlob(cursor.getColumnIndex("bitmap")));
                avatars.add(temp);
            } while (cursor.moveToNext());
        }
        return avatars;
    }


    public boolean deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "id =?", new String[]{String.valueOf(1)}) == 1;
    }
}
