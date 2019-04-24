package com.example.test07;

import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;


public class TestJobIntentService extends JobIntentService {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private static String TAG = "my_app - " + TestJobIntentService.class.getSimpleName();
    public static final int JOB_ID = 777;
    public static final String ACTION_START_COUNT = "action.ACTION_START_COUNT";
    public static final String ACTION_STOP_AND_RETURN = "a.ACTION_STOP_AND_RETURN";
    public static final String SHOW_RESULT = "a.ACTION_START_COUNT";

    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    public static int counter = 1;
    private ResultReceiver mResultReceiver;

    // --------------------------------------------------------
    // enqueue public method
    // --------------------------------------------------------
    public static void enqueueWork(Context context, Intent work) {
        Log.w(TAG, "enqueueWork");

        // the intent work to enqueue may have EXTRA data and ACTION
        enqueueWork(context, TestJobIntentService.class, JOB_ID, work);

        if (work.getAction() != null) {
            switch (work.getAction()) {
                case ACTION_STOP_AND_RETURN:
                    Log.w(TAG, "counter = " + String.valueOf(counter));

//                    Bundle bundle = new Bundle();
//                    bundle.putString("data", "Count value: %d" + String.valueOf(counter));
//                    mResultReceiver.send(SHOW_RESULT, bundle);

                    //Comunicamos el progreso
                    Intent bcIntent = new Intent();
                    bcIntent.setAction("com.example.test07.action.STATUS_BAR_ONLY");
                    bcIntent.putExtra("count2", counter);
                    context.sendBroadcast(bcIntent);

                    Log.w(TAG, "data transmition OK!");


                    counter = 10;
                    break;
            }
        }

        Log.w(TAG, "enqueue OK!");
    }


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
//    @Override
//    public IBinder onBind(Intent intent) {
//        Log.w(TAG, "onBind");
//
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }


    @Override
    public void onCreate() {
        Log.w(TAG, "onCreate");
        super.onCreate();
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.w(TAG, "onHandleWork");

        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_START_COUNT:
                    counter = 0;
                    do{
                        try {
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            // Restore interrupt status.
                            Thread.currentThread().interrupt();
                        }
                        counter++;
                        Log.w(TAG, "i = " + String.valueOf(counter));
                    }while ( counter < 10 );
                    break;
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy");
    }




    // --------------------------------------------------------
    // private methods
    // --------------------------------------------------------
    private void dummyCounter(int count){
        int i = 0;
        while(i < count)
        {
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }

            i++;
            Log.w(TAG, "i = " + String.valueOf(i));
        }
    }
}



