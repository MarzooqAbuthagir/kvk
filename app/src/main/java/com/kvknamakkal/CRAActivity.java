package com.kvknamakkal;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class CRAActivity extends AppCompatActivity {
    String TAG = "CRAActivity";
    Toolbar toolbar;
    ActionBar actionBar = null;
    Button btnBack;
    ImageView nrmImg, cropImg, icmImg, liveImg, insImg, nicraImg, faqImg;
    CardView NRMcard, Cropscard, LiveStockcard, Instcard, Nicracard, Faqcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_a);
        initToolbar();
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        nrmImg = findViewById(R.id.nrmImg);
        cropImg = findViewById(R.id.cropImg);
//        icmImg = findViewById(R.id.icmImg);
        liveImg = findViewById(R.id.liveImg);
        insImg = findViewById(R.id.insImg);
        nicraImg = findViewById(R.id.nicraImg);
        faqImg = findViewById(R.id.faqImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.nrm_menu)
                .load(R.drawable.farm_pond)
                .transform(new CircleTransform())
                .into(nrmImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.crop_module_menu)
                .load(R.drawable.blackgram)
                .transform(new CircleTransform())
                .into(cropImg);

//        Picasso.with(CRAActivity.this)
//                .load(R.drawable.icmandipdm_menu)
//                .transform(new CircleTransform())
//                .into(icmImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.livestock_module_menu)
                .load(R.drawable.lsmain_sl01)
                .transform(new CircleTransform())
                .into(liveImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.institutional_intervention_menu)
                .load(R.drawable.ii_sl01)
                .transform(new CircleTransform())
                .into(insImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.success_of_nicra_menu)
                .load(R.drawable.success_nicra)
                .transform(new CircleTransform())
                .into(nicraImg);

        Picasso.with(CRAActivity.this)
//                .load(R.drawable.success_of_nicra_menu)
                .load(R.drawable.faq)
                .transform(new CircleTransform())
                .into(faqImg);

        NRMcard = findViewById(R.id.card_nrm);
        Cropscard = findViewById(R.id.nrm_crop_module);
        LiveStockcard = findViewById(R.id.nrm_live_module);
        Instcard = findViewById(R.id.card_inst);
        Nicracard = findViewById(R.id.nrm_nicra);
        Faqcard = findViewById(R.id.card_faq);
        NRMcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CRAActivity.this, NRMActivity.class));
                finish();
            }
        });
        Cropscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(CRAActivity.this, CropsActivity.class));
//                finish();
                Intent intent = new Intent(CRAActivity.this, CropsActivity.class);
                intent.putExtra("url","file:///android_asset/crops.html");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        LiveStockcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(CRAActivity.this, LiveStockActivity.class));
//                finish();
                Intent intent = new Intent(CRAActivity.this, LiveStockActivity.class);
                intent.putExtra("url","file:///android_asset/livestock.html");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        Instcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CRAActivity.this, IIActivity.class));
                finish();
            }
        });
        Nicracard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(CRAActivity.this, SOAActivity.class));
//                finish();
                Intent intent = new Intent(CRAActivity.this, SOAActivity.class);
                intent.putExtra("url","file:///android_asset/success_nicra.html");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        Faqcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CRAActivity.this, FaqActivity.class);
                intent.putExtra("url","file:///android_asset/faqs.html");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
        Intent intent = new Intent(CRAActivity.this, MainActivity.class);
        intent.putExtra("checkGps",false);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}