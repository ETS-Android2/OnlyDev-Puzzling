package com.example.myfirstapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebView;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help2_menu);

        WebView myWebView = (WebView) findViewById(R.id.help);
        myWebView.loadUrl("https://support.google.com/android/?hl=es#topic=7313011");
    }
}
