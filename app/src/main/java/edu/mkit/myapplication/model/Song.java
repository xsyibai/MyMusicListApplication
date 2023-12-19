package edu.mkit.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Song implements Parcelable {

    private String name; //歌曲名
    private String singer; //歌手
    private long size; //歌曲所占空间大小
    private int duration; //歌曲时间长度
    private String path; //歌曲地址

    private int currentProgress; // 播放进度

    public Song(String name, String singer, long size, int duration, String path) {
        this.name = name;
        this.singer = singer;
        this.size = size;
        this.duration = duration;
        this.path = path;
    }

    protected Song(Parcel in) {
        name = in.readString();
        singer = in.readString();
        size = in.readLong();
        duration = in.readInt();
        path = in.readString();
        currentProgress = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeString(singer);
        parcel.writeLong(size);
        parcel.writeInt(duration);
        parcel.writeString(path);
        parcel.writeInt(currentProgress);
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                '}';
    }

    public Song() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
