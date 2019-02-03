package com.keeprawteach.free.Settings.Sub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.Contacts.Contact_Us;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;
import com.keeprawteach.free.Terms.TnCs;

public class AppInfor extends AppCompatActivity {
    private AdView mAdView;
    Toolbar toolbar;
    Context context;
    TextView Tell, Rate, Contact, Terms;
    Typeface typeface;
    InterstitialAd tell, rate, contact, terms;
    String add_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_infor);
        context = AppInfor.this;
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        add_link = getString(R.string.inters_id_test);
        getSupportActionBar().setTitle("App Infor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typeface = Typeface.createFromAsset(context.getAssets(), "fonts/user_two.ttf");

        Tell = findViewById(R.id.tell);
        Rate = findViewById(R.id.rate);
        Contact = findViewById(R.id.cont);
        Terms = findViewById(R.id.terms);

        tell = new InterstitialAd(context);
        tell.setAdUnitId(this.add_link);
        tell.loadAd(new AdRequest.Builder().build());
        tell.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                tell.loadAd(new AdRequest.Builder().build());
                shareLink();
            }
        });

        rate = new InterstitialAd(context);
        rate.setAdUnitId(this.add_link);
        rate.loadAd(new AdRequest.Builder().build());
        rate.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                rate.loadAd(new AdRequest.Builder().build());
                rateUs();
            }
        });

        contact = new InterstitialAd(context);
        contact.setAdUnitId(this.add_link);
        contact.loadAd(new AdRequest.Builder().build());
        contact.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                contact.loadAd(new AdRequest.Builder().build());
                contactSupport();
            }
        });

        terms = new InterstitialAd(context);
        terms.setAdUnitId(this.add_link);
        terms.loadAd(new AdRequest.Builder().build());
        terms.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                terms.loadAd(new AdRequest.Builder().build());
                termsConditions();
            }
        });

        Tell.setTypeface(typeface);
        Rate.setTypeface(typeface);
        Contact.setTypeface(typeface);
        Terms.setTypeface(typeface);
        Tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharetheLink();
            }
        });
        Rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUsHere();
            }
        });
        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactSupportHere();
            }
        });
        Terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsConditionsHere();
            }
        });

    }

    private void termsConditionsHere() {
        if (terms.isLoaded()) {
            terms.show();
        } else {
            termsConditions();
        }
    }

    private void contactSupportHere() {
        if (contact.isLoaded()) {
            contact.show();
        } else {
            contactSupport();
        }
    }

    private void rateUsHere() {
        if (rate.isLoaded()) {
            rate.show();
        } else {
            rateUs();
        }
    }

    private void sharetheLink() {
        if (tell.isLoaded()) {
            tell.show();
        } else {
            shareLink();
        }
    }

    private void termsConditions() {
        startActivity(new Intent(AppInfor.this, TnCs.class));
        Animatoo.animateSplit(context);
        AppInfor.this.finish();
    }

    private void contactSupport() {
        startActivity(new Intent(AppInfor.this, Contact_Us.class));
        Animatoo.animateSplit(context);
        AppInfor.this.finish();
    }

    private void rateUs() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.keeprawteach.free")));

    }

    private void shareLink() {
        Intent jeff = new Intent(Intent.ACTION_SEND);
        jeff.setType("text/plain");
        String url = "https://play.google.com/store/apps/details?id=com.keeprawteach.free";
        jeff.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(jeff);
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
        startActivity(new Intent(AppInfor.this, AllSettings.class));
        Animatoo.animateSplit(context);
        AppInfor.this.finish();

    }
}
