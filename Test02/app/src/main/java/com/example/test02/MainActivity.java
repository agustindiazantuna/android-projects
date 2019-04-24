package com.example.test02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    // variables
    private EditText et1, et2;
    private TextView tv3, tvret;
    private RadioButton rbADD, rbSUB, rbMULT, rbDIV;
    private CheckBox cb;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get data from screen
        et1 = findViewById(R.id.NumberA);
        et2 = findViewById(R.id.NumberB);
        tv3 = findViewById(R.id.NumberC);

        cb = findViewById(R.id.checkBox);
    }




    // operation
    public void calcule(View view) {
        String st1 = et1.getText().toString();
        String st2 = et2.getText().toString();
        int opA, opB, res;

        // check for operators
        if (st1.isEmpty()) {
            tv3.setText("Ingrese A");
            return;
        }
        else
            opA = Integer.parseInt(st1);

        if (st2.isEmpty()) {
            tv3.setText("Ingrese B");
            return;
        }
        else
            opB = Integer.parseInt(st2);


        // check for operation
        rbADD = findViewById(R.id.RadioButtonADD);
        rbSUB = findViewById(R.id.RadioButtonSUB);
        rbMULT = findViewById(R.id.RadioButtonMULT);
        rbDIV = findViewById(R.id.RadioButtonDIV);


        if (rbADD.isChecked())
            res = opA + opB;
        else if (rbSUB.isChecked())
            res = opA - opB;
        else if (rbMULT.isChecked())
            res = opA * opB;
        else if (rbDIV.isChecked())
            res = opA / opB;
        else {
            tv3.setText("Ingrese operacion");
            return;
        }


        // check box to clear result
        if (cb.isChecked())
            res = 0;

        tv3.setText(String.valueOf(res));
    }


}


