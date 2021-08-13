package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

public class NRMTabActivity extends TabActivity {
    String TAG = "NRMTabActivity";
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Intent intent = getIntent();
        id = intent.getIntExtra("tabIndex", 0);

        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;
        Intent intents;                   // Reusable Intent for each tab

        intent = new Intent().setClass(this, RainWaterHarvestingActivity.class);
        spec = tabHost.newTabSpec("A").setIndicator("A").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, InsituMoistureActivity.class);
        spec = tabHost.newTabSpec("B").setIndicator("B").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, MicroIrrigationActivity.class);
        spec = tabHost.newTabSpec("C").setIndicator("C").setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ManualWeatherStationActivity.class);
        spec = tabHost.newTabSpec("D").setIndicator("D").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(id);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        startActivity(new Intent(NRMTabActivity.this, NRMActivity.class));
        finish();
    }
}