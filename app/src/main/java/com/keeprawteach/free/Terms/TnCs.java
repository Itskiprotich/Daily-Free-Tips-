package com.keeprawteach.free.Terms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdView;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;

public class TnCs extends AppCompatActivity {
    Context context;
    String link = "https://termsfeed.com/privacy-policy/b0390f937b80bf919c8e21819556577f";
    private AdView mAdView;
    ProgressBar progressBar;
    Toolbar toolbar;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tn_cs);
        context = TnCs.this;
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Terms and Conditions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar=findViewById(R.id.pb);
        webView =  findViewById(R.id.aabb);
        webView.setWebViewClient(new WebViewClientDemo());
        webView.setWebChromeClient(new WebChromeClientDemo());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }


    @Override
    public boolean onSupportNavigateUp() {
        toka();
        return super.onSupportNavigateUp();
    }
    private class WebViewClientDemo extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }
    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress);
        }
    }

    @Override
    public void onBackPressed() {
        toka();
    }

    private void toka() {
        startActivity(new Intent(TnCs.this, AllSettings.class));
        Animatoo.animateSplit(context);
        TnCs.this.finish();

    }
}
