package com.keeprawteach.free.Settings.Sub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;

public class General extends AppCompatActivity {
    private AdView mAdView;
    Toolbar toolbar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        context=General.this;
        mAdView =  findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("General");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        toka();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        toka();
    }

    private void toka() {
        startActivity(new Intent(General.this, AllSettings.class));
        Animatoo.animateSplit(context);
        General.this.finish();

    }
}
