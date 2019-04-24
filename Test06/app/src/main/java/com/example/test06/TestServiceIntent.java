package com.example.test06;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */


/*
    IntentService documentation:
    =============================

    IntentService implements onBind() that returns null. This means that the IntentService can not be bound by default.

    Also intent service is a type of service that does a few specific things very well.

    1. Thread management: It automatically processes all incoming requests in a separate thread taking the burden of thread management away from the developer.
    2. Request Queue: It queues up all the incoming requests and they are processed one by one
    3. Stop point: Once the request Queue is empty it automatically stops itself, so the developer need not worry about handling the service lifecycle.

*/

public class TestServiceIntent extends IntentService {

    // TODO: Rename parameters
    private static final String DATA_MAX_TIME = "max time";
    private static final String EXTRA_PARAM2 = "com.example.test06.extra.PARAM2";

    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    private static final String TAG = "my_app - " + TestServiceIntent.class.getSimpleName();
    public static final String ACTION_STATUS_BAR_ONLY = "com.example.test06.action.STATUS_BAR_ONLY";
    public static final String ACTION_STATUS = "com.example.test06.action.STATUS";




    public TestServiceIntent() {
        super("TestServiceIntent_thread");
        Log.w(TAG, "---> service intent constructor");
    }


    // --------------------------------------------------------
    // activity callbacks
    // --------------------------------------------------------
    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.w(TAG, "---> service intent onStartCommand");
        return super.onStartCommand(intent, flag, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.w(TAG, "---> service intent onDestroy");
    }


//    /**
//     * Starts this service to perform action Foo with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Log.w(TAG, "---> service intent FOO");
//
//        Intent intent = new Intent(context, TestServiceIntent.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }
//



//    /**
//     * Starts this service to perform action Baz with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Log.w(TAG, "---> service intent BAZ");
//
//        Intent intent = new Intent(context, TestServiceIntent.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }




    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(TAG, "---> service intent onHandleIntent START");

        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_STATUS_BAR_ONLY.equals(action)) {
                Log.w(TAG, "---> service intent onHandleIntent ACTION STATUS BAR ONLY");
                int time = intent.getIntExtra("timeout", 0);
                handleActionFoo(time);
            }
            else if (ACTION_STATUS.equals(action)) {
                Log.w(TAG, "---> service intent onHandleIntent ACTION STATUS");
                int time = intent.getIntExtra("timeout", 0);
                handleActionBaz(time);
            }
        }

        Log.w(TAG, "---> service intent onHandleIntent END");

    }




    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(int time) {
        Log.w(TAG, "---> ACTIVITY STATUS BAR ONLY");

        int i = 0;
        while( i < time )
        {
            i = i + 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }

            //Comunicamos el progreso
            Intent bcIntent = new Intent();
            bcIntent.setAction(ACTION_STATUS_BAR_ONLY);
            bcIntent.putExtra("count", i);
            sendBroadcast(bcIntent);

            Log.w(TAG, "i = " + String.valueOf(i));
        }
    }




    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(int time) {
        Log.w(TAG, "---> ACTIVITY STATUS");

        int i = 0;
        while( i < time )
        {
            i = i + 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Restore interrupt status.
                Thread.currentThread().interrupt();
            }

            //Comunicamos el progreso
            Intent bcIntent = new Intent();
            bcIntent.setAction(ACTION_STATUS);
            bcIntent.putExtra("count", i);
            sendBroadcast(bcIntent);

            Log.w(TAG, "i = " + String.valueOf(i));
        }
    }
}
