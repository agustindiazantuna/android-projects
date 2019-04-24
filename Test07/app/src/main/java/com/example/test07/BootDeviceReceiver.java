package com.example.test07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootDeviceReceiver extends BroadcastReceiver {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app - " + BootDeviceReceiver.class.getSimpleName();

    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(TAG, "onReceive");

        String action = intent.getAction();
        if(Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Intent i = new Intent(context, com.example.test07.TestJobIntentService.class);

            Log.w(TAG, "launching service...");
//            TestJobIntentService.enqueueWork(context, new Intent());
            Log.w(TAG, "services launched");
        }
    }


    // --------------------------------------------------------
    // methods
    // --------------------------------------------------------
    public void startServiceDirectly(Context context){
        Log.w(TAG, "startServiceDirectly");

        Intent startServiceIntent = new Intent(context, TestService.class);
        context.startService(startServiceIntent);

        Log.w(TAG, "services launched");
    }
}
