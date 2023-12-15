package edu.mkit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.mkit.myapplication.model.Song;
import edu.mkit.myapplication.service.MusicService;
import edu.mkit.myapplication.service.MusicServiceConnection;
import edu.mkit.myapplication.uitls.ReadFileUtils;

public class MusicListActivity extends AppCompatActivity {

    private RecyclerView musicListRv;

    private List<Song> musicList;

    private MusicService musicService;
    private int currentIndex = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // 先获取资源文件
        musicList = ReadFileUtils.getSystemMusicList(getContentResolver());

        //绑定服务
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);
        MusicServiceConnection musicServiceConnection = new MusicServiceConnection();
        bindService(intent, musicServiceConnection, Context.BIND_AUTO_CREATE);
//        musicService = musicServiceConnection.getMusicService();
        musicService.bindMusic(musicList);



        musicListRv = findViewById(R.id.music_list_rv);
        MusicListAdapter shopAdapter = new MusicListAdapter();
        musicListRv.setAdapter(shopAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MusicListActivity.this);
        musicListRv.setLayoutManager(linearLayoutManager);

    }


    class MusicListHolder extends RecyclerView.ViewHolder{
        TextView tv_music_index,tv_music_name,tv_music_singer,tv_music_time;

        public MusicListHolder(@NonNull View itemView) {
            super(itemView);
            tv_music_index = itemView.findViewById(R.id.tv_music_index);
            tv_music_name = itemView.findViewById(R.id.tv_music_name);
            tv_music_singer = itemView.findViewById(R.id.tv_music_singer);
            tv_music_time = itemView.findViewById(R.id.tv_music_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentIndex != getAdapterPosition()){
//                        currentIndex = getAdapterPosition();
//                        String path = musicList.get(currentIndex).getPath();
                        musicService.playMusic(getAdapterPosition());
//                        musicService.bindMusic(musicList,currentIndex);
                        Intent intent = new Intent(MusicListActivity.this, PlayActivity.class);
//                        startActivity(intent);
                    }
                }
            });
        }

    }
    class MusicListAdapter extends RecyclerView.Adapter<MusicListHolder> {

        @NonNull
        @Override
        public MusicListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(MusicListActivity.this).inflate(R.layout.music_list_item, parent, false);
            MusicListHolder musicListHolder = new MusicListHolder(inflate);
            return musicListHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MusicListHolder holder, int position) {
            holder.tv_music_index.setText(String.valueOf(position));
            holder.tv_music_name.setText(musicList.get(position).getName());
            holder.tv_music_singer.setText(musicList.get(position).getSinger());
            holder.tv_music_time.setText(transformTime(musicList.get(position).getDuration()));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }
    public String transformTime(int time){

        int minutes = time / 60000;
        int seconds = (time % 60000) / 1000;

        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        return formattedTime;
    }

    //    private ServiceConnection musicConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
//            musicService = binder.getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            musicService = null;
//        }
//    };

}