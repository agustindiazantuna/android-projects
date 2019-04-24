package com.example.test05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class autor extends AppCompatActivity {

    private static final String TAG = autor.class.getSimpleName();
//    private Button bt_save;
    private TextView tv_show;
    private EditText et_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor);
        Log.w(TAG, "onCreate()");

        // bind variables with layout elements
//        bt_save = findViewById(R.id.button4);
        tv_show = findViewById(R.id.textView3);
        et_input = findViewById(R.id.editText);

        if ( savedInstanceState != null ) {
            Log.w(TAG, "--> before read bundle...");
            String data_restored = savedInstanceState.getString("data");
            Log.w(TAG, "--> after reading bundle...");

            tv_show.setText(data_restored);
        }
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



    public void readandsave(View view) {
        String st_input = et_input.getText().toString();

        tv_show.setText(st_input);

        // to launch a new activity
        Intent i = new Intent(this, com.example.test05.autordialog.class);
        i.putExtra("data", st_input);
        startActivity(i);
    }




    // exit
    public void exit(View view) {
        finish();
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.w(TAG, "onSaveInstanceState()");

        if( !tv_show.getText().toString().isEmpty() ) {
            Log.w(TAG, "--> bundle -> " + tv_show.getText().toString());

            outState.putString("data", tv_show.getText().toString());
        }
    }
}
