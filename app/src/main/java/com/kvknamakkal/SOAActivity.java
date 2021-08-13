package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class SOAActivity extends AppCompatActivity {
    String TAG = "SOAActivity";
    Toolbar toolbar;
    ActionBar actionBar = null;
    WebView webView;
    String webViewUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_o_a);
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
        System.out.println("soa back url "+webView.getUrl());
        if (webView.getUrl().equalsIgnoreCase("file:///android_asset/success_nicra.html")) {
            startActivity(new Intent(SOAActivity.this, CRAActivity.class));
            finish();
        } else if (webView.getUrl().equalsIgnoreCase("file:///android_asset/sn_nrm.html")) {
            Intent intent = new Intent(SOAActivity.this, SOAActivity.class);
            intent.putExtra("url","file:///android_asset/kvki.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (webView.getUrl().equalsIgnoreCase("file:///android_asset/sn_cp.html")) {
            Intent intent = new Intent(SOAActivity.this, SOAActivity.class);
            intent.putExtra("url","file:///android_asset/kvki.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (webView.getUrl().equalsIgnoreCase("file:///android_asset/sn_lm.html")) {
            Intent intent = new Intent(SOAActivity.this, SOAActivity.class);
            intent.putExtra("url","file:///android_asset/kvki.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else if (webView.getUrl().equalsIgnoreCase("file:///android_asset/sn_ii.html")) {
            Intent intent = new Intent(SOAActivity.this, SOAActivity.class);
            intent.putExtra("url","file:///android_asset/kvki.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(SOAActivity.this, SOAActivity.class);
            intent.putExtra("url","file:///android_asset/success_nicra.html");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}