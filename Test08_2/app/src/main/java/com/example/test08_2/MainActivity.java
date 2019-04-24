package com.example.test08_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app_08-2 - " + MainActivity.class.getSimpleName();


    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    private TextView tv_receivedText;


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w(TAG, "onCreate");

        // Bind layout with private variables
        tv_receivedText = findViewById(R.id.textView1);


        // Receive data from intent
        //      https://developer.android.com/training/sharing/receive
        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        Log.w(TAG, "action: " + action + " || type: " + type
                + " || data: " + intent.getStringExtra(Intent.EXTRA_TEXT));

        if(Intent.ACTION_SEND.equals(action)){
            if("text/plain".equals(type))
                handleReceiveText(intent);
        }
    }


    // --------------------------------------------------------
    // methods
    // --------------------------------------------------------
    void handleReceiveText(Intent intent){
        Log.w(TAG, "handleReceiveText");

        Toast toast = Toast.makeText(this, "Receiving something", Toast.LENGTH_LONG);
        toast.show();

        String receivedString = intent.getStringExtra(Intent.EXTRA_TEXT);

        tv_receivedText.setText(receivedString);

        Log.w(TAG, "handleReceiveText -> OK");
    }
}
