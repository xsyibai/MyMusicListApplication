package edu.mkit.myapplication.uitls;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

import edu.mkit.myapplication.model.Song;

public class ReadFileUtils {




    @SuppressLint("Range")
    public static List<Song> getSystemMusicList(ContentResolver contentResolver){
        List<Song> list = new ArrayList<>();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(musicUri, null, null, null, null);
        if (cursor != null){
            while (cursor.moveToNext()){
                Song item = new Song();
                item.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                item.setSinger(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                item.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
                item.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                item.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));

                list.add(item);
            }
            cursor.close();
        }

        return list;
    }

//    @SuppressLint("Range")
//    public static List<Song> getSystemMusicList(Context context){
//        List<Song> list = new ArrayList<>();
//        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        ContentResolver contentResolver = context.getContentResolver();
//        Cursor cursor = contentResolver.query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Audio.Media.DATA},
//                null,
//                null,
//                null);
//        if (cursor != null){
//            while (cursor.moveToNext()){
//                Song item = new Song();
//                item.setName(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
//                item.setSinger(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
//                item.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
//                item.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
//                item.setSize(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
//
//                list.add(item);
//            }
//            cursor.close();
//        }
//
//        return list;
//    }
}
