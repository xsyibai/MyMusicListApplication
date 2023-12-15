package edu.mkit.myapplication.model;

public class Song {

    private String name; //歌曲名
    private String singer; //歌手
    private long size; //歌曲所占空间大小
    private int duration; //歌曲时间长度
    private String path; //歌曲地址

    public Song(String name, String singer, long size, int duration, String path) {
        this.name = name;
        this.singer = singer;
        this.size = size;
        this.duration = duration;
        this.path = path;
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
