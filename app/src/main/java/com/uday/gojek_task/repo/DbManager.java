package com.uday.gojek_task.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


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
        url = url.replace("https://","");
        Log.d("this is url insert", url);
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", url);
        contentValues.put("bitmap",image);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME,null, contentValues) != -1;

    }

    public Cursor getALLItems(String url){
        url = url.replace("https://","");
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE url=?", new String[] { url});
    }

    public int checkImagedata(String url) {
        url = url.replace("https://","");
        url = '"'+ url + '"';
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE url =" +url;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        // return count
        return cursor.getCount();

    }

    public Cursor fullList(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public List<HashMap<String,String>> readall(){
        Cursor cursor = fullList();
        List<HashMap<String,String>> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> temphashmap = new HashMap<>();
                temphashmap.put("url",cursor.getString(cursor.getColumnIndex("url")));
                list.add(temphashmap);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public byte[] readAvatars(String url) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getALLItems(url);
        byte[] image_data = null;
        if (cursor.moveToFirst()) {
            do {
                image_data =  cursor.getBlob(cursor.getColumnIndex("bitmap"));
            } while (cursor.moveToNext());
        }
        return image_data;
    }


    public boolean deleteTable() {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "id =?", new String[]{String.valueOf(1)}) == 1;
    }


    public String tableToString() {
        SQLiteDatabase db = getWritableDatabase();
        Log.d("","tableToString called");
        String tableString = String.format("Table %s:\n", TABLE_NAME);
        Cursor allRows  = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        tableString += cursorToString(allRows);
        return tableString;
    }

    public String cursorToString(Cursor cursor){
        String cursorString = "";
        if (cursor.moveToFirst() ){
            String[] columnNames = {"url"};
            for (String name: columnNames)
                cursorString += String.format("%s ][ ", name);
            cursorString += "\n";
            do {
                for (String name: columnNames) {
                    cursorString += String.format("%s ][ ",
                            cursor.getString(cursor.getColumnIndex(name)));
                }
                cursorString += "\n";
            } while (cursor.moveToNext());
        }
        return cursorString;
    }

}