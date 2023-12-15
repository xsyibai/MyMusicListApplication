package edu.mkit.myapplication.uitls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Map;

public class MyDBHelper extends SQLiteOpenHelper {

    public final String USER_TABLE = "mkit_user";

    public MyDBHelper(@Nullable Context context) {
        super(context, "my_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE mkit_user(id INTEGER PRIMARY \n" +
                "                KEY AUTOINCREMENT, username VARCHAR(20), password INTEGER)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
