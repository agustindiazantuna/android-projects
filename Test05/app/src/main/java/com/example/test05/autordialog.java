package com.example.test05;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class autordialog extends AppCompatActivity {

    private TextView tv_received;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autordialog);

        tv_received = findViewById(R.id.textView4);

        Bundle bundle = getIntent().getExtras();
        String data_received = bundle.getString("data");

        tv_received.setText(data_received);

    }
}
