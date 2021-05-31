package com.daemon;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

public class DaemonService extends BaseService {
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                Daemon.getCallback().onStart();
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Daemon.getCallback().onStop();
    }
}
