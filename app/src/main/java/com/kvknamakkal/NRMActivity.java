package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class NRMActivity extends AppCompatActivity {
    String TAG = "NRMActivity";
    Toolbar toolbar;
    ActionBar actionBar = null;
    LinearLayout tabA, tabB, tabC, tabD;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_r_m);
        initToolbar();

        tabA = findViewById(R.id.tabA);
        tabB = findViewById(R.id.tabB);
        tabC = findViewById(R.id.tabC);
        tabD = findViewById(R.id.tabD);

        tabA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NRMActivity.this, NRMTabActivity.class);
                intent.putExtra("tabIndex", 0);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        tabB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NRMActivity.this, NRMTabActivity.class);
                intent.putExtra("tabIndex", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        tabC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NRMActivity.this, NRMTabActivity.class);
                intent.putExtra("tabIndex", 2);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        tabD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NRMActivity.this, NRMTabActivity.class);
                intent.putExtra("tabIndex", 3);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/der-nrm.html");
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
        System.out.println("back url "+webView.getUrl());
        if (webView.getUrl().equalsIgnoreCase("file:///android_asset/der-nrm.html")) {
            startActivity(new Intent(NRMActivity.this, CRAActivity.class));
            finish();
        } else {
//            webView.loadUrl("file:///android_asset/nrm.html");
            startActivity(new Intent(NRMActivity.this, NRMActivity.class));
            finish();
        }
    }
}