package com.example.test07;

import android.app.Service;
import android.content.Intent;
//import android.os.IBinder;
import android.os.IBinder;
import android.util.Log;


/*
    service type : startService()
    It will be started after boot

 */

public class TestService extends Service {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app - " + TestService.class.getSimpleName();


//    public TestService() {
//        Log.w(TAG, "Constructor...");
//    }


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG, "onBind");

        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        Log.w(TAG, "onCreate");
        super.onCreate();
        Log.w(TAG, "onCreate");
    }


//    @Override
//    public static int onStartCommand(Intent intent, int flags, int startId) {
//        Log.w(TAG, "onStartCommand");
//
//        int i = 0;
//        while(true)
//        {
//            try {
//                Thread.sleep(3 * 1000);
//            } catch (InterruptedException e) {
//                // Restore interrupt status.
//                Thread.currentThread().interrupt();
//            }
//
//            i++;
//            Log.w(TAG, "i = " + String.valueOf(i));
//        }
////        return START_NOT_STICKY;
//    }


    @Override
    public void onDestroy() {
        Log.w(TAG, "onDestroy");
    }
}
