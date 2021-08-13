package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class CropsActivity extends AppCompatActivity {
    String TAG = "ContactActivity";
    Toolbar toolbar;
    ActionBar actionBar = null;
    WebView webView;
    String webViewUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);
        Intent intent = getIntent();
        webViewUrl = intent.getStringExtra("url");

        initToolbar();

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webViewUrl);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    private void back() {
        System.out.println("crops back url "+webView.getUrl());
        if (webView.getUrl().equalsIgnoreCase("file:///android_asset/crops.html")) {
            startActivity(new Intent(CropsActivity.this, CRAActivity.class));
            finish();
        } else if (webView.getUrl().equalsIgnoreCase("file:///android_asset/pulses.html") ||
                webView.getUrl().equalsIgnoreCase("file:///android_asset/oilseeds.html") ||
                webView.getUrl().equalsIgnoreCase("file:///android_asset/intercropping.html")) {
            Intent intent = new Intent(CropsActivity.this, CropsActivity.class);
            intent.putExtra("url","file:///android_asset/agro_crops.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(CropsActivity.this, CropsActivity.class);
            intent.putExtra("url","file:///android_asset/crops.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}