package edu.mkit.myapplication.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.mkit.myapplication.uitls.MyDBHelper;
import edu.mkit.myapplication.model.User;

public class UserDao {


    @SuppressLint("Range")
    public User getOne(Context context, String username){
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase readableDatabase = myDBHelper.getReadableDatabase();
        String condition = "username = ?";
        String[] args = {username};
        Cursor cursor = readableDatabase.query(myDBHelper.USER_TABLE,null , condition, args, null, null, null, null);
        User user = new User();
        if (cursor != null && cursor.moveToFirst()) {
            do{
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            }while (cursor.moveToNext());
        }else{
            return null;
        }
        cursor.close();
        readableDatabase.close();
        return user;
    }

    public long saveOne(Context context, User user){
        MyDBHelper myDBHelper = new MyDBHelper(context);
        SQLiteDatabase writableDatabase = myDBHelper.getWritableDatabase();
        ContentValues contentValues = getContentValues(user);
        long insert = writableDatabase.insert(myDBHelper.USER_TABLE, null, contentValues);
        myDBHelper.close();
        return insert;
    }


    public ContentValues getContentValues(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user.getUsername());
        contentValues.put("password",user.getPassword());
        return contentValues;

    }
}
