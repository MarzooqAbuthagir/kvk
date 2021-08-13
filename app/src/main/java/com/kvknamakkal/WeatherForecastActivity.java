package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WeatherForecastActivity extends AppCompatActivity {
    String TAG = "WeatherForecastActivity";
    Toolbar toolbar;
    ActionBar actionBar = null;
    WebView webView;
    ProgressBar progressBar;
    LinearLayout refreshLayout;
    Button btnRefresh;
    String webviewUrl = "https://kvknamakkal.com/app/weatherforecast.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        initToolbar();

        progressBar = findViewById(R.id.progress);
        refreshLayout = findViewById(R.id.refreshLayout);
        btnRefresh = findViewById(R.id.btnRefresh);
        webView = findViewById(R.id.webView);

        if(Utilis.isInternetOn()) {
            webView.setVisibility(View.VISIBLE);
            refreshLayout.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setWebChromeClient( new MyWebChromeClient());
            webView.setWebViewClient( new webClient());
            webView.loadUrl(webviewUrl);
        } else {
            webView.setVisibility(View.GONE);
            refreshLayout.setVisibility(View.VISIBLE);
        }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utilis.isInternetOn()) {
                    webView.setVisibility(View.VISIBLE);
                    refreshLayout.setVisibility(View.GONE);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setBuiltInZoomControls(true);
                    webView.setWebChromeClient( new MyWebChromeClient());
                    webView.setWebViewClient( new webClient());
                    webView.loadUrl(webviewUrl);

                } else {
                    webView.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(WeatherForecastActivity.this, "Please enable internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        System.out.println("weather forecast onback "+webviewUrl);
        if (webviewUrl.equalsIgnoreCase("https://kvknamakkal.com/app/weatherforecast.php")) {
            Intent intent = new Intent(WeatherForecastActivity.this, MainActivity.class);
            intent.putExtra("checkGps",false);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            startActivity(new Intent(WeatherForecastActivity.this, WeatherForecastActivity.class));
            finish();
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }

    public class webClient extends WebViewClient {
        public boolean  shouldOverrideUrlLoading(WebView view, String url) {
            webviewUrl = url;
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}