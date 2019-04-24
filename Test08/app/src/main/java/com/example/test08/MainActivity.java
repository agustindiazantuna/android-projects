package com.example.test08;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app_08 - " + MainActivity.class.getSimpleName();


    // --------------------------------------------------------
    // variables
    // --------------------------------------------------------
    private EditText et_textToSend;
    private RadioButton rb_mail, rb_text;


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind layout with private variables
        et_textToSend = findViewById(R.id.editText1);
        rb_mail = findViewById(R.id.RadioButton1);
        rb_text = findViewById(R.id.RadioButton2);
    }


    // --------------------------------------------------------
    // methods
    // --------------------------------------------------------
    public void sendSomething(View view) {
        Toast toast = Toast.makeText(this, "ERROR! No option", Toast.LENGTH_LONG);

        if(rb_mail.isChecked()) {
            Log.w(TAG, "sendMail");
            sendMail(view);
            toast = Toast.makeText(this, "Sending mail", Toast.LENGTH_LONG);
        }
        else if(rb_text.isChecked()){
            Log.w(TAG, "sendText");
            sendText(view);
            toast = Toast.makeText(this, "Sending text", Toast.LENGTH_LONG);
        }

        toast.show();
    }


    // ACTION_SEND
    //      https://developer.android.com/training/sharing/send
    public void sendMail(View view) {
        // It only work if the gmail app is installed
        // Without ACTION_SENDTO and data "mailto:" doesn't work
        Intent emailSelectorIntent = new Intent(Intent.ACTION_SENDTO);
        emailSelectorIntent.setData(Uri.parse("mailto:"));

        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"agustin.diazantuna@gmail.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        emailIntent.setSelector( emailSelectorIntent );

        if( emailIntent.resolveActivity(getPackageManager()) != null )
            startActivity(emailIntent);
    }


    public void sendText(View view) {
        final Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        String stringToSend = et_textToSend.getText().toString();
        textIntent.putExtra(Intent.EXTRA_TEXT, stringToSend);

        Log.w(TAG, "action: " + textIntent.getAction() + " || type: " + textIntent.getType()
                + " || data: " + textIntent.getStringExtra(Intent.EXTRA_TEXT));

        if(!stringToSend.isEmpty())
            startActivity(textIntent);
    }
}
