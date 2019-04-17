package com.example.test01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from screen
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tv3 = findViewById(R.id.tv3);
    }


    // add operation
    public void sumar(View view) {
        String st1 = et1.getText().toString();
        String st2 = et2.getText().toString();

        int int1 = Integer.parseInt(st1);
        int int2 = Integer.parseInt(st2);

        int suma = int1 + int2;

        tv3.setText(String.valueOf(suma));
    }


}



