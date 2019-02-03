package com.keeprawteach.free.Every;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.Frags.DailyResults;
import com.keeprawteach.free.Model.Tips;
import com.keeprawteach.free.R;

import java.util.ArrayList;
import java.util.List;

public class EachResult extends AppCompatActivity {
    String Date, Url;
    Context context;
    Toolbar toolbar;
    private AdView mAdView;

    private String exitAddString;
    private FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private ProgressBar progressBar;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference reference;
    InterstitialAd refreshAdd,toka;
    List<Tips> arrayList;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_result);
        context = EachResult.this;
        exitAddString = this.context.getString(R.string.inters_id_test);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrayList = new ArrayList();
        recyclerView =  findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(arrayList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        progressBar=findViewById(R.id.loadme);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdView = findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        SharedPreferences preferences = getSharedPreferences("Results", Context.MODE_PRIVATE);
        String date_here = "date", url_here = "url";
        Date = preferences.getString("dates", date_here);
        Url = preferences.getString("url", url_here);

        getSupportActionBar().setTitle("" + Date);
        addData();
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        refreshAdd = new InterstitialAd(getApplicationContext());
        refreshAdd.setAdUnitId(this.exitAddString);
        refreshAdd.loadAd(new AdRequest.Builder().build());
        refreshAdd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                refreshAdd.loadAd(new AdRequest.Builder().build());
                addData();
            }
        });
        toka = new InterstitialAd(getApplicationContext());
        toka.setAdUnitId(this.exitAddString);
        toka.loadAd(new AdRequest.Builder().build());
        toka.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                toka.loadAd(new AdRequest.Builder().build());
                toka();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        tokasai();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        tokasai();
    }

    private void tokasai() {
        if (toka.isLoaded()) {
            toka.show();
        } else {
            toka();
        }
    }

    private void toka() {
        startActivity(new Intent(EachResult.this, AnimData.class));
        Animatoo.animateSplit(context);
        EachResult.this.finish();

    }
    private void addData() {
        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/" + Url);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                progressBar.setVisibility(View.GONE);

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String date = (String) messageSnapshot.child("date").getValue();
                    String game = (String) messageSnapshot.child("team").getValue();
                    String league = (String) messageSnapshot.child("league").getValue();
                    String results = (String) messageSnapshot.child("result").getValue();
                    String score = (String) messageSnapshot.child("score").getValue();
                    String time = (String) messageSnapshot.child("time").getValue();
                    String tip = (String) messageSnapshot.child("tip").getValue();

                    Tips resultData = new Tips();
                    resultData.setDate(date);
                    resultData.setGame(game);
                    resultData.setLeague(league);
                    resultData.setResults(results);
                    resultData.setScore(score);
                    resultData.setTime(time);
                    resultData.setTip(tip);
                    arrayList.add(resultData);
                    recyclerAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

    }

    private class RecyclerAdapter extends RecyclerView.Adapter<MyHoder> {
        List<Tips> arrayList;
        Context context;

        public RecyclerAdapter(List<Tips> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHoder(LayoutInflater.from(this.context).inflate(R.layout.eachresult, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHoder myHoder, int i) {
            final Tips resultData = arrayList.get(i);
            myHoder.Game.setText(resultData.getGame());
            myHoder.League.setText(resultData.getLeague());
            myHoder.Tip.setText(resultData.getTip());
            myHoder.Time.setText(resultData.getTime());
            myHoder.Score.setText(resultData.getScore());
            String status=resultData.getResults();
            if (status.equalsIgnoreCase("Lost")){
                myHoder.cardView.setCardBackgroundColor(Color.RED);

            }

        }

        @Override
        public int getItemCount() {
            int arr = 0;
            try {
                if (arrayList.size() == 0) {
                    arr = 0;
                } else {
                    arr = arrayList.size();
                }
            } catch (Exception e) {
            }
            return arr;
        }
    }


    private class MyHoder extends RecyclerView.ViewHolder {
        TextView Game, League, Tip, Time, Score;
CardView cardView;
        public MyHoder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.redcolor);
            Game =  itemView.findViewById(R.id.game);
            League =  itemView.findViewById(R.id.league);
            Tip =  itemView.findViewById(R.id.tip);
            Time =  itemView.findViewById(R.id.time);
            Score =  itemView.findViewById(R.id.score);


        }
    }

    private void refresh() {
        if (refreshAdd.isLoaded()) {
            refreshAdd.show();
        } else {
            addData();
        }
    }
}
