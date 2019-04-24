package com.example.test07;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test07.R;
import com.example.test07.TestServiceIntent;


public class Extras extends AppCompatActivity {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app - " + Extras.class.getSimpleName();
    private final String BC_DATA = "broadcast status";
    private static final String DATA_MAX_TIME = "max time";
    private static final String SERVICE_DATA = "service data";
    public static final String ACTION_START_COUNT = "action.ACTION_START_COUNT";
    public static final String ACTION_STOP_AND_RETURN = "a.ACTION_STOP_AND_RETURN";
    public static final String SHOW_RESULT = "a.ACTION_START_COUNT";

    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    private TextView tv_userword, tv_status;
    private EditText et_userinput;
    private RadioButton myservice, myintentservice;
    private ProgressBar pb_status;




    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        Log.w(TAG, "onCreate()");

        // bind variables with layout elements
        tv_userword = findViewById(R.id.textView3);
        et_userinput = findViewById(R.id.editText);
        myservice = findViewById(R.id.rbss);
        myintentservice = findViewById(R.id.rbis);
        tv_status = findViewById(R.id.textView8);
        pb_status = findViewById(R.id.progressBar);

        if ( savedInstanceState != null ) {
            Log.w(TAG, "--> before read bundle...");
            String data_restored = savedInstanceState.getString("data");
            Log.w(TAG, "--> after reading bundle...");

            tv_userword.setText(data_restored);
        }

        // broadcast to receive service data!!!
        IntentFilter filter = new IntentFilter();
        filter.addAction(TestServiceIntent.ACTION_STATUS_BAR_ONLY);
        filter.addAction(TestServiceIntent.ACTION_STATUS);
        ProgressReceiver rcv = new ProgressReceiver();
        registerReceiver(rcv, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(TAG, "onSaveInstanceState()");

        if( !tv_userword.getText().toString().isEmpty() ) {
            Log.w(TAG, "--> bundle -> " + tv_userword.getText().toString());

            outState.putString("data", tv_userword.getText().toString());
        }
    }




    // --------------------------------------------------------
    // methods
    // --------------------------------------------------------
    public void read_and_save(View view) {
        Log.w(TAG, "readandsave");

        String st_input = et_userinput.getText().toString();

        tv_userword.setText(st_input);

        // to launch a new activity
        Intent i = new Intent(this, com.example.test07.ExtrasDialog.class);
        i.putExtra("data", st_input);
        startActivity(i);
    }

    public void start_service(View view) {
        Log.w(TAG, "start1_service");

        // check for operation
        if (myservice.isChecked()) {
            Intent i = new Intent(this, com.example.test07.TestJobIntentService.class);
            i.setAction(ACTION_START_COUNT);
            TestJobIntentService.enqueueWork(this, i);
        }
        else if (myintentservice.isChecked()) {
            Intent i = new Intent(this, com.example.test07.TestServiceIntent.class);
            i.setAction(TestServiceIntent.ACTION_STATUS_BAR_ONLY);
//            i.setAction(TestServiceIntent.ACTION_STATUS);
            i.putExtra("timeout", 100);
            startService(i);
        }
        Log.w(TAG, "myservice1 OK");
    }

    public void start2_service(View view) {
        Log.w(TAG, "start2_service");

        // check for operation
        if (myservice.isChecked()) {
            Intent i = new Intent(this, com.example.test07.TestJobIntentService.class);
            i.setAction(ACTION_STOP_AND_RETURN);
            TestJobIntentService.enqueueWork(this, i);
        }
        Log.w(TAG, "myservice2 OK");
    }

    public void end_service(View view) {
        Log.w(TAG, "end_service");

//        // check for operation
//        if (myservice.isChecked()) {
//            stopService(new Intent(this, com.example.test07.TestService.class));
//        }
//        else if (myintentservice.isChecked()) {
//
//        }
    }

    // exit
    public void exit(View view) {
        Log.w(TAG, "exit");

        finish();
    }




    // --------------------------------------------------------
    // broadcast
    // --------------------------------------------------------
    public class ProgressReceiver extends BroadcastReceiver {
        private int prog = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.w(TAG, "INCOMING DATA");


            if(intent.getAction().equals(TestServiceIntent.ACTION_STATUS_BAR_ONLY)) {
                prog = intent.getIntExtra("count", -1);
                if(prog > 0)
                    pb_status.setProgress(prog);

                prog = intent.getIntExtra("count2", -1);
                if(prog > 0)
                    tv_status.setText(String.valueOf(prog));
            }
            else if(intent.getAction().equals(TestServiceIntent.ACTION_STATUS)) {
                prog = intent.getIntExtra("count", 0);
                pb_status.setProgress(prog);
                tv_status.setText(String.valueOf(prog));
            }

            if(prog == 100)
            {
                Toast.makeText(Extras.this, "Tarea finalizada!", Toast.LENGTH_SHORT).show();
            }


            if(intent.getAction().equals("com.example.test07.action.STATUS_BAR_ONLY")) {

            }
        }
    }

}
