package com.example.test07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;


public class WebViewer extends AppCompatActivity {

    // --------------------------------------------------------
    // tags
    // --------------------------------------------------------
    private final String TAG = "my_app - " + WebViewer.class.getSimpleName();


    // --------------------------------------------------------
    // callbacks
    // --------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Log.w(TAG, "onReceive");

        WebView web1 = (WebView) findViewById(R.id.webView1);

        Bundle bundle = getIntent().getExtras();
        String dir = bundle.getString("direccion");
        web1.loadUrl("http://" + dir);
    }


    // --------------------------------------------------------
    // methods
    // --------------------------------------------------------
    public void exit(View view) {
        Log.w(TAG, "exit");
        finish();
    }
}
