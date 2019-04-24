package com.example.test06;

import android.app.Service;
import android.content.Intent;
//import android.os.IBinder;
import android.os.IBinder;
import android.util.Log;


/*
    service type : startService()

 */

public class TestService extends Service {

    // variables
    private final String TAG = "my_app - " + TestService.class.getSimpleName();

    public TestService() {
        Log.w(TAG, "Constructor...");

    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG, "onBind");

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        Log.w(TAG, "onCreate");

//        int t = 0;
//        while ( t < 10000000 )
//            t = t + 1;
//        stopSelf();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(TAG, "onStartCommand");

        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy");
    }
}
