package com.daemon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.daemon.Daemon;
import com.daemon.onepixel.OnePixelActivity;
import com.daemon.utils.ActivityUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;

public  class ScreenReceiver {
    public  interface Observer{
        void    screenStatusChanged(Boolean screenON);
    }
    static class ScreenBroadcastReceiver extends BroadcastReceiver {
        //public Context mContext;
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                for (Observer ob:sObservers){
                    ob.screenStatusChanged(true);
                }
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                for (Observer ob:sObservers){
                    ob.screenStatusChanged(false);
                }
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {

            }
        }
    }


    //private static OnePixelActivity sActivity = null;
    private static ScreenBroadcastReceiver sReceiver = null;
    private static LinkedList<Observer> sObservers = new LinkedList<>();

    public static  void addObserver(Observer observer){
        sObservers.add(observer);
    }

    public static  void removeObserver(Observer observer){
        sObservers.remove(observer);
    }

    public static void register(Context context) {
        if (sReceiver == null){
            sReceiver = new ScreenBroadcastReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(sReceiver,filter);
    }

    public static void deregister(Context context) {
        if (sReceiver != null){
            context.unregisterReceiver(sReceiver);
            sReceiver = null;
        }
    }
}
