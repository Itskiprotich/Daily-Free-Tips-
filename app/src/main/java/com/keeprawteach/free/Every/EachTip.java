package com.keeprawteach.free.Every;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.R;

public class EachTip extends AppCompatActivity {
    Context context;
    Toolbar toolbar;
    private AdView mAdView;
    private String exitAddString;
    TextView Match, League, Date, Time, Tip, Result, Score;
    String match, league, date, time, tip, result, score;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_tip);
        context = EachTip.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exitAddString = this.context.getString(R.string.inters_id_test);
        getSupportActionBar().setTitle("Each Tip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());

        Match = findViewById(R.id.match);
        League = findViewById(R.id.game);
        Date = findViewById(R.id.date);
        Time = findViewById(R.id.time);
        Tip = findViewById(R.id.tip);
        Result = findViewById(R.id.result);
        Score = findViewById(R.id.score);
        imageView=findViewById(R.id.won);

        SharedPreferences preferences=getSharedPreferences("Daily",Context.MODE_PRIVATE);

        String match_here = "match", league_here = "league", date_here = "date", time_here = "time", tip_here = "tip", result_here = "result", score_here = "score";

        match = preferences.getString("game",match_here);
        league = preferences.getString("league",league_here);//getIntent().getStringExtra("league");
        date = preferences.getString("date",date_here);//getIntent().getStringExtra("date");
        time = preferences.getString("time",time_here);//getIntent().getStringExtra("time");
        tip = preferences.getString("tip",tip_here);//getIntent().getStringExtra("tip");
        result = preferences.getString("results",result_here);//getIntent().getStringExtra("results");
        score = preferences.getString("score",score_here);//getIntent().getStringExtra("score");


        if (result.equalsIgnoreCase("Won")){
            imageView.setVisibility(View.VISIBLE);
        }
        Date.setText(date);
        Match.setText(match);
        League.setText(league);
        Time.setText(time);
        Tip.setText(tip);
        Result.setText(result);
        Score.setText(score);


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
        startActivity(new Intent(EachTip.this, AnimData.class));
        Animatoo.animateSplit(context);

        EachTip.this.finish();

    }
}
