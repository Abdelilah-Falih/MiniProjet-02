package com.example.appminiprojet02.Database.FavoutiteQuotesDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.appminiprojet02.Database.FavoutiteQuotesTable.FavouriteQuotes;

public class FavouriteQuotesDB extends SQLiteOpenHelper {

    Context context;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ FavouriteQuotes.Info.TABLE_NAME+" ("+FavouriteQuotes.Info.COLUMN_ID+" INTEGER PRIMARY KEY, "+ FavouriteQuotes.Info.COLUMN_QUOTE+" TEXT, "+ FavouriteQuotes.Info.COLUMN_AUTHOR + " TEXT);";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+FavouriteQuotes.Info.TABLE_NAME;

    public FavouriteQuotesDB(@Nullable Context context) {
        super(context, FavouriteQuotes.Info.DATABASE_NAME, null, FavouriteQuotes.Info.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
    }

    public void addFavouriteQuote(int _id, String quote , String author){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", _id);
        cv.put("quote", quote);
        cv.put("author", author);
        db.insert(FavouriteQuotes.Info.TABLE_NAME, null, cv);
    }

    public void deleteQuote(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FavouriteQuotes.Info.TABLE_NAME, FavouriteQuotes.Info.COLUMN_ID+" LIKE ? ", new String[]{Integer.toString(_id)});

    }

    public void getQuotes(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
          FavouriteQuotes.Info.COLUMN_ID,
          FavouriteQuotes.Info.COLUMN_QUOTE,
          FavouriteQuotes.Info.COLUMN_AUTHOR
        };
        try (Cursor cursor = db.query(FavouriteQuotes.Info.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)) {


            while (cursor.moveToNext()) {
                Log.e("sql_cursor", String.format("%d  -  %s  -  %s",
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            }
        }
    }
}
