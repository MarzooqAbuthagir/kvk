package com.kvknamakkal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MicroIrrigationActivity extends AppCompatActivity {

    ArrayList<TableDataClass> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_irrigation);

        initData();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        TableViewAdapter adapter = new TableViewAdapter(MicroIrrigationActivity.this, arrayList);

        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        arrayList.add(new TableDataClass("For 1 ac", "Compartmental bunding", "Rainhose laser spray", "sprinkler", "Raingun", "drip"));
        arrayList.add(new TableDataClass("Water required", "3 lakhs", "1 lakhs", "1 lakhs", "1 lakhs", "50 k"));
        arrayList.add(new TableDataClass("Days of irrigation Sandy soil", "5 days", "3 days", "3 days", "3 days", "3 days"));
        arrayList.add(new TableDataClass("Clay  soil", "7 days", "4 days", "4 days", "4 days", "4 days"));
        arrayList.add(new TableDataClass("Hours of irrigation", "10 hrs", "2 hrs", "2 hrs", "2 hrs", "4 - 5 hrs"));
        arrayList.add(new TableDataClass("Hp", "5", "5","5","5", "5"));
        arrayList.add(new TableDataClass("Length", "Small - 5 hrs Length - 8 hrs", "12 feet", "120 nos.", "60 feet 4 Nos.", ""));

    }
}