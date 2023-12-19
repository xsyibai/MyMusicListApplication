package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import edu.mkit.myapplication.model.Song;
import edu.mkit.myapplication.service.MusicService;
import edu.mkit.myapplication.uitls.MyUtils;

public class PlayActivity extends AppCompatActivity {


    private static TextView tvSongAndAuthor,tvProgress,tvTotal,tvUserName;
    private ImageButton iBtnPlay,iBtnNext,iBtnPre,iBtnStop,iBtnRepeat;
    private ImageView iv_cover;
    private static SeekBar seekBar;
    private Intent intent;

    private int playMode = 0;

    private List<Song> musicList;

    private int currentIndex;

    private Song song;

    private MusicService musicService;

    private MusicService.MusicControlBinder musicControlBinder;

    private ObjectAnimator animator;

    private MyServiceConnection conn;

    public static Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // 这里的数据是service传过来的
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            Song currentSong = bundle.getParcelable("song");
            updateView(currentSong);
        }
    };

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            musicControlBinder = (MusicService.MusicControlBinder) iBinder;

            if (musicControlBinder != null) {
//                animator.start();
                try {
                    // 把数据传入service
                    musicControlBinder.play(song);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicControlBinder = null;
            musicService = null;
        }
    }

    public static void updateView(Song song){
        seekBar.setMax(song.getDuration());
        seekBar.setProgress(song.getCurrentProgress());
        tvProgress.setText(MyUtils.transformTime(song.getCurrentProgress()));
        tvTotal.setText(MyUtils.transformTime(song.getDuration()));
        tvSongAndAuthor.setText(song.getName()+"-"+song.getSinger());
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        song = (Song)getIntent().getParcelableExtra("song");
        currentIndex = getIntent().getIntExtra("currentIndex",-1);
        init();
        musicList = MyUtils.getSystemMusicList(getContentResolver());
        animator = ObjectAnimator.ofFloat(iv_cover, "rotation", 0f, 360.0f);
        animator.setDuration(20000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);

        // 注册广播
        IntentFilter filter = new IntentFilter(MusicService.ACTION_MUSIC_PLAYBACK_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (playMode == 0){
                    switchMusic(1);
                } else if (playMode == 1) {
                    switchMusic(0);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    private void init(){
        iv_cover = findViewById(R.id.iv_MusicCover);
        tvSongAndAuthor = findViewById(R.id.tv_songAndAuthor);
        iBtnPlay = findViewById(R.id.iBtn_play);
        iBtnNext = findViewById(R.id.iBtn_next);
        iBtnPre = findViewById(R.id.iBtn_pre);
        iBtnStop = findViewById(R.id.iBtn_stop);
        iBtnRepeat = findViewById(R.id.iBtn_repeat);
        seekBar = findViewById(R.id.sb_music);
        tvProgress = findViewById(R.id.tv_progress);
        tvTotal = findViewById(R.id.tv_total);
        tvUserName = findViewById(R.id.tv_userName);
        SharedPreferences userData = getSharedPreferences("userData", MODE_PRIVATE);
        tvUserName.setText(userData.getString("username", ""));
        iBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicControlBinder.pause();
            }
        });
        iBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicControlBinder.isPlaying()){
                    animator.pause();
                    musicControlBinder.pause();
                    iBtnPlay.setImageResource(R.drawable.play);
                }else {
                    animator.resume();
                    musicControlBinder.continuePlay();
                    iBtnPlay.setImageResource(R.drawable.pause);
                }
            }
        });
        iBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchMusic(1);
            }
        });

        iBtnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchMusic(-1);
            }
        });
        iBtnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMode+=1;
                if (playMode>1){
                    playMode = 0;
                }
                if (playMode == 0){
                    iBtnRepeat.setImageResource(R.drawable.repeat);
                } else if (playMode == 1) {
                    iBtnRepeat.setImageResource(R.drawable.repeat_once);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (progress == seekBar.getMax()) {
////                    animator.pause();
//                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                musicControlBinder.seekTo(progress);
            }
        });
    }

    private void switchMusic(int index){
            currentIndex += index;
        if (currentIndex == -1){
            currentIndex = musicList.size()-1;
        }
        if (currentIndex == musicList.size()){
            currentIndex = 0;
        }
        try {
            Song currentSong = musicList.get(currentIndex);
            musicControlBinder.play(currentSong);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        intent = new Intent(this, MusicService.class);
        conn = new MyServiceConnection();
        startService(intent);
        bindService(intent, conn, BIND_AUTO_CREATE);
        animator.start();
    }
}