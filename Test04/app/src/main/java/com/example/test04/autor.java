package com.example.test04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class autor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor);
    }


    // exit
    public void exit(View view) {
        finish();
    }
}