package edu.mkit.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.mkit.myapplication.model.Song;
import edu.mkit.myapplication.uitls.LogUtils;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    private int currentIndex;

    private List<Song> musicList;

    private IBinder musicBinder;

    @Override
    public IBinder onBind(Intent intent) {
        musicBinder = new MusicBinder();
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // 解绑
        mediaPlayer.stop();
        mediaPlayer.release();
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setOnPreparedListener(this);
    }

    public void bindMusic(List<Song> list){
        this.musicList = list;
    }

    public void setCurrentIndex(int index){
        this.currentIndex = index;
    }

    public void playMusic(int index){
        this.currentIndex = index;
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicList.get(index).getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (IOException e){
            Log.e(LogUtils.ERROR_LOG,e.getMessage());
        }
    }

    public boolean isPlaying(){
        return this.mediaPlayer.isPlaying();
    }

    public void pauseMusic(boolean state){
        if (state == true){
            mediaPlayer.start();
        }else{
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        mediaPlayer.release();
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public Song getCurrentSong(){
        return this.musicList.get(this.currentIndex);
    }






}