package com.example.test06;

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


public class Extras extends AppCompatActivity {

    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    private final String TAG = "my_app - " + Extras.class.getSimpleName();
    private final String BC_DATA = "broadcast status";
    private static final String DATA_MAX_TIME = "max time";
    private static final String SERVICE_DATA = "service data";

    private TextView tv_userword, tv_status;
    private EditText et_userinput;
    private RadioButton myservice, myintentservice;
    private ProgressBar pb_status;




    // --------------------------------------------------------
    // activity callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor);

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
    // activity methods
    // --------------------------------------------------------
    public void read_and_save(View view) {
        Log.w(TAG, "readandsave");

        String st_input = et_userinput.getText().toString();

        tv_userword.setText(st_input);

        // to launch a new activity
        Intent i = new Intent(this, com.example.test06.autordialog.class);
        i.putExtra("data", st_input);
        startActivity(i);
    }

    public void start_service(View view) {
        Log.w(TAG, "start_service");

        // check for operation
//        if (myservice.isChecked()) {
//            startService(new Intent(this, com.example.test06.TestService.class));
//        }
//        else
        if (myintentservice.isChecked()) {
            Intent i = new Intent(this, com.example.test06.TestServiceIntent.class);
//            i.setAction(TestServiceIntent.ACTION_STATUS_BAR_ONLY);
            i.setAction(TestServiceIntent.ACTION_STATUS);
            i.putExtra("timeout", 100);
            startService(i);
        }
        Log.w(TAG, "myservice OK");
    }

    public void end_service(View view) {
        Log.w(TAG, "end_service");

//        // check for operation
//        if (myservice.isChecked()) {
//            stopService(new Intent(this, com.example.test06.TestService.class));
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
            if(intent.getAction().equals(TestServiceIntent.ACTION_STATUS_BAR_ONLY)) {
                prog = intent.getIntExtra("count", 0);
                pb_status.setProgress(prog);
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
        }
    }

}
