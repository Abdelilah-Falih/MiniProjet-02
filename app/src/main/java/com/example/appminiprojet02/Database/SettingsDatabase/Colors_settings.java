package com.example.appminiprojet02.Database.SettingsDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appminiprojet02.Database.FavoutiteQuotesDatabase.FavoutiteQuotesTable.FavouriteQuotes;
import com.example.appminiprojet02.Database.SettingsDatabase.ColorsTable.Colors;
import com.example.appminiprojet02.Database.SettingsDatabase.ColorsTable.Settings;
import com.example.appminiprojet02.Models.Color;
import com.example.appminiprojet02.Models.Quote;

import java.util.ArrayList;

public class Colors_settings extends SQLiteOpenHelper {

    Context context;
    public static final String DATABASE_NAME = "colors_settings.db";
    private static final String CREATE_TABLE_COLORS = "CREATE TABLE IF NOT EXISTS "+ Colors.Info.TABLE_NAME+" ("+Colors.Info.COLUMN_ID+" INTEGER PRIMARY KEY, "+ Colors.Info.COLUMN_NAME+" TEXT, "+ Colors.Info.COLUMN_CODE + " TEXT);";
    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE IF NOT EXISTS "+ Settings.Info.TABLE_NAME+" ("+Settings.Info.COLUMN_ID+" INTEGER PRIMARY KEY, "+ Settings.Info.COLUMN_NAME+" TEXT, "+ Settings.Info.COLUMN_VALUE + " TEXT);";
    public static final String DROP_TABLE_COLORS = "DROP TABLE IF EXISTS "+Colors.Info.TABLE_NAME;
    public static final String DROP_TABLE_SETTINGS = "DROP TABLE IF EXISTS "+Settings.Info.TABLE_NAME;

    public Colors_settings(@Nullable Context context) {
        super(context, DATABASE_NAME, null, Colors.Info.DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COLORS);
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_COLORS);
        db.execSQL(DROP_TABLE_SETTINGS);
    }

    private void addColor(int id, String name , String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("code", code);
        db.insert(Colors.Info.TABLE_NAME, null, cv);
    }


   public void updateColorSetting(Color color){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("_id",color.getId() );
       cv.put("name",color.getName() );
       cv.put("value", color.getValue());
       db.update(Settings.Info.TABLE_NAME, cv, null,null);
   }

    public Color  getColorSettings(){
        SQLiteDatabase db = getReadableDatabase();
        Color color = null;
        String[] projection = {
                Settings.Info.COLUMN_ID,
                Settings.Info.COLUMN_NAME,
                Settings.Info.COLUMN_VALUE
        };
        try (Cursor cursor = db.query(Settings.Info.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)) {


            while (cursor.moveToNext()) {
                color = new Color(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            }
        }
        return color;
    }

    public void addSettingsColor(Color color) {
        SQLiteDatabase db = getWritableDatabase();
        if (getColorSettings() == null){
            Toast.makeText(context, "auccun color", Toast.LENGTH_SHORT).show();
            ContentValues cv = new ContentValues();
            cv.put("_id", color.getId());
            cv.put("value", color.getValue());
            cv.put("value", color.getValue());
            db.insert(Settings.Info.TABLE_NAME, null, cv);
        }else {
            updateColorSetting(color);
        }
    }
}
