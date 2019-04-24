package com.example.test07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class ExtrasDialog extends AppCompatActivity {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app - " + ExtrasDialog.class.getSimpleName();

    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autordialog);

        Log.w(TAG, "onCreate");

        TextView tv_received = findViewById(R.id.textView4);

        Bundle bundle = getIntent().getExtras();
        String data_received = bundle.getString("data");

        tv_received.setText(data_received);
    }
}
