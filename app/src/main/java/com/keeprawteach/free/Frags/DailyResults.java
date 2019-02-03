package com.keeprawteach.free.Frags;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.keeprawteach.free.Every.EachResult;
import com.keeprawteach.free.Model.ResultData;
import com.keeprawteach.free.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DailyResults extends Fragment {
    List<ResultData> arrayList;
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

    public DailyResults() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layoutInflater = inflater.inflate(R.layout.fragment_daily_results, container, false);
        arrayList = new ArrayList();
        context = getContext();
        progressBar =  layoutInflater.findViewById(R.id.loadme);
        progressBar.setVisibility(View.GONE);
        exitAddString = this.context.getString(R.string.inters_id_test);
        mAdView = (AdView) layoutInflater.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        recyclerView = (RecyclerView) layoutInflater.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(arrayList, context);
        recyclerView.setLayoutManager(new GridLayoutManager(this.context, 3));
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
        exitAdd = new InterstitialAd(getContext());
        exitAdd.setAdUnitId(this.exitAddString);
        exitAdd.loadAd(new AdRequest.Builder().build());
        exitAdd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                exitAdd.loadAd(new AdRequest.Builder().build());
                openEach();
            }
        });
        return layoutInflater;
    }

    private void addData() {
        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/Daily Results Tips");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                progressBar.setVisibility(View.GONE);
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String date = (String) messageSnapshot.child("date").getValue();
                    String url = (String) messageSnapshot.child("url").getValue();
                    int day = Integer.parseInt(String.valueOf(messageSnapshot.child("day").getValue()));
                    ResultData resultData = new ResultData();
                    resultData.setDates(date);
                    resultData.setUrl(url);
                    resultData.setDay("" + day);
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
        List<ResultData> arrayList;
        Context context;

        public RecyclerAdapter(List<ResultData> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHoder(LayoutInflater.from(this.context).inflate(R.layout.results, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHoder myHoder, int i) {
            final ResultData resultData = arrayList.get(i);
            myHoder.A.setText(resultData.getDay());
            myHoder.B.setText(resultData.getDates());
            myHoder.A.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    addInfor(resultData.getDates(), resultData.getUrl());
                }
            });
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

    private void addInfor(String dates, String url) {
        SharedPreferences preferences=context.getSharedPreferences("Results",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("dates",dates);
        editor.putString("url",url);
        editor.commit();

        if (exitAdd.isLoaded()) {
            exitAdd.show();
        } else {
            openEach();
        }
        
    }

    private void openEach() {

        startActivity(new Intent(context, EachResult.class));
        Animatoo.animateSplit(context);
        getActivity().finish();
    }

    private class MyHoder extends RecyclerView.ViewHolder {
        TextView A, B;

        public MyHoder(@NonNull View itemView) {
            super(itemView);
            A = (TextView) itemView.findViewById(R.id.kk);
            B = (TextView) itemView.findViewById(R.id.bb);
        }
    }
}
