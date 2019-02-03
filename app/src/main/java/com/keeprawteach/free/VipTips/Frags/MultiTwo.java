package com.keeprawteach.free.VipTips.Frags;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.keeprawteach.free.Model.Tips;
import com.keeprawteach.free.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultiTwo extends Fragment {
    List<Tips> arrayList;
    Context context;
    FirebaseDatabase database;
    InterstitialAd exitAdd;
    private String exitAddString;
    private FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private AdView mAdView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference reference;
    InterstitialAd refreshAdd;


    public MultiTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutInflater= inflater.inflate(R.layout.fragment_multi_two, container, false);
        arrayList = new ArrayList();
        context = getContext();
        progressBar = (ProgressBar) layoutInflater.findViewById(R.id.loadme);
        progressBar.setVisibility(View.GONE);
        exitAddString = this.context.getString(R.string.inters_id_test);
        mAdView = (AdView) layoutInflater.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        recyclerView = (RecyclerView) layoutInflater.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(arrayList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        addData();
        floatingActionButton = (FloatingActionButton) layoutInflater.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        refreshAdd = new InterstitialAd(getContext());
        refreshAdd.setAdUnitId(this.exitAddString);
        refreshAdd.loadAd(new AdRequest.Builder().build());
        refreshAdd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                refreshAdd.loadAd(new AdRequest.Builder().build());
                addData();
            }
        });
        return layoutInflater;
    }

    private void addData() {
        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/Vip Tips/Two");
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

    private void refresh() {
        if (refreshAdd.isLoaded()) {
            refreshAdd.show();
        } else {
            addData();
        }
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
            return new MyHoder(LayoutInflater.from(this.context).inflate(R.layout.vipdata, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHoder myHoder, int i) {
            final Tips resultData = arrayList.get(i);
            myHoder.Game.setText(resultData.getGame());
            myHoder.League.setText(resultData.getLeague());
            myHoder.Tip.setText(resultData.getTip());
            myHoder.Time.setText(resultData.getTime());

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
        TextView Game, League, Tip, Time;

        public MyHoder(@NonNull View itemView) {
            super(itemView);
            Game = (TextView) itemView.findViewById(R.id.game);
            League = (TextView) itemView.findViewById(R.id.league);
            Tip = (TextView) itemView.findViewById(R.id.tip);
            Time = (TextView) itemView.findViewById(R.id.time);


        }
    }

}

