package edu.mkit.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.mkit.myapplication.model.Song;
import edu.mkit.myapplication.uitls.MyUtils;

public class MusicListActivity extends AppCompatActivity {

    private RecyclerView musicListRv;

    private List<Song> musicList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        musicListRv = findViewById(R.id.music_list_rv);

        // 先获取资源文件
        musicList = MyUtils.getSystemMusicList(getContentResolver());
        // 数据装配
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
                    int adapterPosition = getAdapterPosition();
                    Intent intent = new Intent(MusicListActivity.this, PlayActivity.class);
                    intent.putExtra("song",musicList.get(adapterPosition));
                    intent.putExtra("currentIndex",adapterPosition);
                    startActivity(intent);
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

}