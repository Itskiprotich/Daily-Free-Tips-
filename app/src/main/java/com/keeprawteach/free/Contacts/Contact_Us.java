package com.keeprawteach.free.Contacts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;

import java.net.URLEncoder;

public class Contact_Us extends AppCompatActivity {
    private AdView mAdView;
    Toolbar toolbar;
    Context context;
    ImageView Personal, Group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us);
        context = Contact_Us.this;
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Personal = findViewById(R.id.support);
        Group = findViewById(R.id.group);
        Personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSupport();

            }
        });
        Group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup();

            }
        });
    }

    private void joinGroup() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://chat.whatsapp.com/LjsqKrY6kqkDOrFZrZycoO")));
    }

    private void textSupport() {
        String phone = "254724743788";
        String message = "Hello Daily Sports Tips..\nWant to subscribe for the Vip tips..!! ";
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(message, "UTF-8");
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "Whatsapp not Installed", Toast.LENGTH_SHORT).show();
        }

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
        startActivity(new Intent(Contact_Us.this, AllSettings.class));
        Animatoo.animateSplit(context);
        Contact_Us.this.finish();

    }
}
