package com.kvknamakkal;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String TAG = "MainActivity";
    LinearLayout craLayout;
    Utilis utilis;
    LinearLayout contactLayout;
    LinearLayout kvkLayout;
    LinearLayout locLayout;
    LinearLayout appLayout;
    LinearLayout weatherforecastLayout;

    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    TextView toolbar_title, toolbar_sub_title;

    LocationTrack currentLocation;
    double latitude = 0.0;
    double longitude = 0.0;
    private static final int REQUEST_CODE = 101;

    int gpsAlertCount = 0;
    Dialog add_dialog;
    TextView tvTemperature, tvHumidity, tvSpeed, tvPressure;

    LinearLayout noNewsLayout, newsLayout;
    Button btnRefresh;
    WebView webView;
    ProgressBar progressBar;
    boolean isCheckGps = false;

    ImageView ivTemp, ivHum, ivPres, ivWind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        isCheckGps = intent.getBooleanExtra("checkGps", false);
        utilis = new Utilis(MainActivity.this);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvSpeed = findViewById(R.id.tvWind);
        tvPressure = findViewById(R.id.tvPressure);
        craLayout = findViewById(R.id.craLayout);
        craLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CRAActivity.class));
                finish();
            }
        });
        contactLayout = findViewById(R.id.contactLayout);
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                finish();
            }
        });

        kvkLayout = findViewById(R.id.kvkLayout);
        kvkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                finish();
            }
        });

        appLayout = findViewById(R.id.appLayout);
        appLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AppActivity.class));
                finish();
            }
        });

        weatherforecastLayout = findViewById(R.id.weatherforecastLayout);
        weatherforecastLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeatherForecastActivity.class));
                finish();
//                String urlString = "https://kvknamakkal.com/app/weatherforecast.php";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setPackage("com.android.chrome");
//                try {
//                    startActivity(intent);
//                    isCheckGps = false;
//                } catch (ActivityNotFoundException ex) {
//                    // Chrome browser presumably not installed so allow user to choose instead
//                    intent.setPackage(null);
//                    startActivity(intent);
//                }
            }
        });

        locLayout = findViewById(R.id.locLayout);
        locLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String urlString = "https://goo.gl/maps/T6u1JcM8pGUvPiEZ7";
                String urlString = "https://goo.gl/maps/DcSvuscmDrBch2iW8";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                    isCheckGps = false;
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_sub_title = findViewById(R.id.toolbar_sub_title);

        noNewsLayout = findViewById(R.id.noNewsLayout);
        newsLayout = findViewById(R.id.newsLayout);
        btnRefresh = findViewById(R.id.btnRefresh);
        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.webView);
//        if (Utilis.isInternetOn()) {
//            newsLayout.setVisibility(View.VISIBLE);
//            noNewsLayout.setVisibility(View.GONE);
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.setWebChromeClient( new MyWebChromeClient());
//            webView.setWebViewClient( new webClient());
//            webView.loadUrl("https://kvknamakkal.com/appnews.php");
//        } else {
//            newsLayout.setVisibility(View.GONE);
//            noNewsLayout.setVisibility(View.VISIBLE);
//        }
//
//        btnRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Utilis.isInternetOn()) {
//                    newsLayout.setVisibility(View.VISIBLE);
//                    noNewsLayout.setVisibility(View.GONE);
//                    webView.getSettings().setJavaScriptEnabled(true);
//                    webView.setWebChromeClient( new MyWebChromeClient());
//                    webView.setWebViewClient( new webClient());
//                    webView.loadUrl("https://kvknamakkal.com/appnews.php");
//                } else {
//                    newsLayout.setVisibility(View.GONE);
//                    noNewsLayout.setVisibility(View.VISIBLE);
//                    Toast.makeText(MainActivity.this, "Please enable internet connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        ivTemp = findViewById(R.id.iv_temp_refresh);
        ivHum = findViewById(R.id.iv_hum_refresh);
        ivPres = findViewById(R.id.iv_pres_refresh);
        ivWind = findViewById(R.id.iv_wind_refresh);

        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toolbar_title.getText().toString().equalsIgnoreCase("Enable GPS") ||
                        toolbar_title.getText().equals("")) {
//                    if (!Utilis.isGpsOn()) {
                        fetchLastLocation();
//                    }
                }
            }
        });

        toolbar_sub_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toolbar_sub_title.getText().toString().equalsIgnoreCase("To Show Location Data") ||
                        toolbar_sub_title.getText().equals("")) {
//                    if (!Utilis.isGpsOn()) {
                        fetchLastLocation();
//                    }
                }
            }
        });

        ivTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLastLocation();
            }
        });

        ivPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLastLocation();
            }
        });

        ivHum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLastLocation();
            }
        });

        ivWind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchLastLocation();
            }
        });

        TextView tvBottom = findViewById(R.id.tvBottom);
        tvBottom.setSelected(true);
        tvBottom.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_about_app) {
            drawer.closeDrawers();
            startActivity(new Intent(MainActivity.this, AppActivity.class));
            finish();
        } else if (id == R.id.nav_about_kvk) {
            drawer.closeDrawers();
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            finish();
        } else if (id == R.id.nav_nicra) {
            drawer.closeDrawers();
            startActivity(new Intent(MainActivity.this, CRAActivity.class));
            finish();
        } else if (id == R.id.nav_location) {
            drawer.closeDrawers();
//            String urlString = "https://goo.gl/maps/T6u1JcM8pGUvPiEZ7";
            String urlString = "https://goo.gl/maps/DcSvuscmDrBch2iW8";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
                isCheckGps = false;
            } catch (ActivityNotFoundException ex) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null);
                startActivity(intent);
            }
        } else if (id == R.id.nav_contact) {
            drawer.closeDrawers();
            startActivity(new Intent(MainActivity.this, ContactActivity.class));
            finish();
        } else if (id == R.id.nav_weather) {
            drawer.closeDrawers();
            startActivity(new Intent(MainActivity.this, WeatherForecastActivity.class));
            finish();
//            String urlString = "https://kvknamakkal.com/app/weatherforecast.php";
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setPackage("com.android.chrome");
//            try {
//                startActivity(intent);
//                isCheckGps = false;
//            } catch (ActivityNotFoundException ex) {
//                // Chrome browser presumably not installed so allow user to choose instead
//                intent.setPackage(null);
//                startActivity(intent);
//            }
        } else if (id == R.id.nav_share) {
            drawer.closeDrawers();
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
//                String shareMessage= "\nStudyBoat – One App For All Competitive Exams | Practice Test and Learning App\n\n";
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                String shareMessage = "Download CRA - Climate Resilient Agriculture App Now! Go to https://kvknamakkal.com/app/download";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isCheckGps)
            fetchLastLocation();
        if (!isCheckGps && Utilis.isGpsOn())
            fetchLastLocation();
        if (!Utilis.isGpsOn()) {
            toolbar_title.setText("Enable GPS");
            toolbar_sub_title.setText("To Show Location Data");
            ivTemp.setVisibility(View.VISIBLE);
            ivHum.setVisibility(View.VISIBLE);
            ivPres.setVisibility(View.VISIBLE);
            ivWind.setVisibility(View.VISIBLE);

            tvTemperature.setVisibility(View.GONE);
            tvHumidity.setVisibility(View.GONE);
            tvPressure.setVisibility(View.GONE);
            tvSpeed.setVisibility(View.GONE);
        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        System.out.println("AdminActivity running fetchLoc");
        if (Utilis.isGpsOn()) {
            currentLocation = new LocationTrack(MainActivity.this);
            gpsAlertCount = 1;
            if (add_dialog != null && add_dialog.isShowing()) {
                System.out.println("Dialog showing force to close");
                add_dialog.dismiss();
            }
            if (currentLocation.canGetLocation()) {
                System.out.println("MainActivity running inside");
                longitude = currentLocation.getLongitude();
                latitude = currentLocation.getLatitude();
            }
            try {
                ivTemp.setVisibility(View.GONE);
                ivHum.setVisibility(View.GONE);
                ivPres.setVisibility(View.GONE);
                ivWind.setVisibility(View.GONE);

                tvTemperature.setVisibility(View.VISIBLE);
                tvHumidity.setVisibility(View.VISIBLE);
                tvPressure.setVisibility(View.VISIBLE);
                tvSpeed.setVisibility(View.VISIBLE);

                getAddress(latitude, longitude);
            } catch (Exception e) {
                System.out.println("onSuccess Excep " + e.getMessage());
            }
        } else {
            if (gpsAlertCount == 0) {
                gpsAlertCount = 1;

                ivTemp.setVisibility(View.VISIBLE);
                ivHum.setVisibility(View.VISIBLE);
                ivPres.setVisibility(View.VISIBLE);
                ivWind.setVisibility(View.VISIBLE);

                tvTemperature.setVisibility(View.GONE);
                tvHumidity.setVisibility(View.GONE);
                tvPressure.setVisibility(View.GONE);
                tvSpeed.setVisibility(View.GONE);


                add_dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);

                add_dialog.setCancelable(false);
                add_dialog.getWindow().setContentView(R.layout.gps_alert_dialog);
                add_dialog.show();

                Button yesBtn = add_dialog.findViewById(R.id.yesbtn);
                Button noBtn = add_dialog.findViewById(R.id.nobtn);

                yesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gpsAlertCount = 0;
                        add_dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                });

                noBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gpsAlertCount = 0;
                        add_dialog.cancel();
                    }
                });

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Permission Granted");
                if (Utilis.isGpsOn()) {
                    fetchLastLocation();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Utilis.isGpsOn()) {
                fetchLastLocation();
            }
        }
    }

    private void getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address myAddress = addresses.get(0);

                for (int i = 0; i <= myAddress.getMaxAddressLineIndex(); i++) {
                    toolbar_title.setText(myAddress.getLocality());
                    toolbar_sub_title.setText(myAddress.getAdminArea() + ", " + myAddress.getCountryName() + ".");
                }

                if (Utilis.isInternetOn()) {
                    findWeather(latitude, longitude);
                } else {
                    Toast.makeText(MainActivity.this, "Please enable internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            System.out.println("getAddress Exception " + e.getMessage());
//            if (Utilis.isGpsOn() && Utilis.isInternetOn()) {
//                fetchLastLocation();
//            }
            ivTemp.setVisibility(View.VISIBLE);
            ivHum.setVisibility(View.VISIBLE);
            ivPres.setVisibility(View.VISIBLE);
            ivWind.setVisibility(View.VISIBLE);

            tvTemperature.setVisibility(View.GONE);
            tvHumidity.setVisibility(View.GONE);
            tvPressure.setVisibility(View.GONE);
            tvSpeed.setVisibility(View.GONE);
        }
    }

    private void findWeather(double latitude, double longitude) {
//        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=7bd2d7da60e701c8f7629d812e6a9a48&units=metric";
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=7bd2d7da60e701c8f7629d812e6a9a48&units=metric";
        System.out.println("values " + url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    JSONObject main2 = response.getJSONObject("wind");
//                    String speed = String.valueOf(main2.getDouble("speed"));
                    String temperature = String.format("%.0f", main.getDouble("temp"));
                    String humidity = String.format("%.0f", main.getDouble("humidity"));
                    String pressure = String.format("%.0f", main.getDouble("pressure"));
                    double windSpeed = 3.6 * main2.getDouble("speed");
                    String speed = String.format("%.1f", windSpeed);
                    tvTemperature.setText(temperature + "\u2103");
                    tvHumidity.setText(humidity + "%");
                    tvPressure.setText(pressure + "hpa");
                    tvSpeed.setText(speed + "km/h");

//                    if (Utilis.isInternetOn())
//                        recalling();
                } catch (JSONException e) {
                    e.printStackTrace();
//                    if (Utilis.isInternetOn())
//                        recalling();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "unable to get weather detail", Toast.LENGTH_SHORT).show();
//                if (Utilis.isInternetOn())
//                    recalling();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jor);
    }

    private void recalling() {
        if (toolbar_title.getText().toString().equalsIgnoreCase("Enable GPS") || toolbar_sub_title.getText().toString().equalsIgnoreCase("To Show Location Data")) {
            System.out.println("recalling called");
            if (Utilis.isGpsOn()) {
                fetchLastLocation();
            }
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }

    public class webClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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