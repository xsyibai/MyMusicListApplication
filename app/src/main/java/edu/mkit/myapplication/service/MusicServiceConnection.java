package edu.mkit.myapplication.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MusicServiceConnection implements ServiceConnection {
    private MusicService musicService;


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
        musicService = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService = null;
    }

    public MusicService getMusicService() {
        return musicService;
    }
}
