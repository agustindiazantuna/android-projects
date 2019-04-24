package com.example.test04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class web extends AppCompatActivity {

    // variables
//    private WebView web1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView web1 = (WebView) findViewById(R.id.webView1);

        Bundle bundle = getIntent().getExtras();
        String dir = bundle.getString("direccion");
        web1.loadUrl("http://" + dir);
    }




    // exit
    public void exit(View view) {
        finish();
    }
}
