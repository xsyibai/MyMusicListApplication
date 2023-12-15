package edu.mkit.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.mkit.myapplication.model.Song;
import edu.mkit.myapplication.service.MusicService;
import edu.mkit.myapplication.service.MusicServiceConnection;

public class PlayActivity extends AppCompatActivity {


    private TextView songAndAuthor;
    private ImageButton iBtnPlay,iBtnNext,iBtnPre,iBtnStop;

    private MusicService musicService;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = new Intent(this, MusicService.class);
        MusicServiceConnection musicServiceConnection = new MusicServiceConnection();
        bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE);
        musicService = musicServiceConnection.getMusicService();
        init();
    }

    private void init(){
        songAndAuthor = findViewById(R.id.tv_songAndAuthor);
        iBtnPlay = findViewById(R.id.iBtn_play);
        iBtnNext = findViewById(R.id.iBtn_next);
        iBtnPre = findViewById(R.id.iBtn_pre);
        iBtnStop = findViewById(R.id.iBtn_stop);
        Song song = musicService.getCurrentSong();
        String str = song.getName() + "-" + song.getSinger();
        songAndAuthor.setText(str);


        iBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        iBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



}