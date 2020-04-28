package com.example.discgolfapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        WebView w = (WebView) findViewById(R.id.web);

        WebSettings webSettings = w.getSettings();
        webSettings.setJavaScriptEnabled(true);

        w.loadUrl("https://www.pdga.com/book/export/html/236656");

    }
}