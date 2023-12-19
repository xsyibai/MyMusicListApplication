package edu.mkit.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import edu.mkit.myapplication.MusicListActivity;
import edu.mkit.myapplication.PlayActivity;
import edu.mkit.myapplication.model.Song;

public class MusicService extends Service {

    public static final String ACTION_MUSIC_PLAYBACK_COMPLETE  = "edu.mkit.myapplication.MUSIC_PLAYBACK_COMPLETE";

    private TimerTask task;

    private MediaPlayer player;

    private boolean isPause = true;

    private Timer timer;
    public MusicService(){}

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicControlBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

//    // 发送广播
//    private void sendProgressBroadcast(Song song) {
//        Intent intent = new Intent(ACTION_UPDATE_PROGRESS);
//        intent.putExtra("song", song);
//        sendBroadcast(intent);
//    }

    public void addTimer(Song song){
        if(timer == null){
            //创建计时器对象
            timer = new Timer();
            task = new TimerTask() {
//                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    if (!isPause){
                        if (player == null) return;
                        int duration = player.getDuration();//获取歌曲总时长
                        int currentPosition = player.getCurrentPosition();//获取播放进度
                        song.setCurrentProgress(currentPosition);
                        song.setDuration(duration);
                        Message msg = PlayActivity.handler.obtainMessage();//创建消息对象
                        //将音乐的总时长和播放进度封装至消息对象中
                        Bundle data = msg.getData();
                        data.putParcelable("song",song);
                        //将消息发送到主线程的消息队列
                        PlayActivity.handler.sendMessage(msg);
                    }
                }
            };
            //开始计时任务后的5毫秒，第一次执行task任务，以后每500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }




    public class  MusicControlBinder extends Binder{

        public MusicService getService() {
            return MusicService.this;
        }

        public void play(Song currentSong) throws IOException {
            Song song = currentSong;
            if (task !=null) task.cancel();// 如果task已经存在，就关闭之前的task
            timer = null;
            isPause = false;
            try {
                player.reset();
                player.setDataSource(song.getPath());
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                player.prepareAsync();
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                        addTimer(song);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Intent intent = new Intent(MusicService.ACTION_MUSIC_PLAYBACK_COMPLETE );
                    sendBroadcast(intent);
                }
            });
        }

        public void pause(){
            isPause = true;
            player.pause();
        }

        public boolean isPlaying(){
            return player.isPlaying();
        }

        public void continuePlay(){
            isPause = false;
            player.start();
        }

        public void seekTo(int progress){
            player.seekTo(progress);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player == null){
            return;
        }
        if (player.isPlaying()){
            player.stop();
        }
        player.release();
        player = null;
    }


}