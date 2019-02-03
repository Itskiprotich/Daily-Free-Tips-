package com.keeprawteach.free.Settings.Sub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.keeprawteach.free.Model.Messages;
import com.keeprawteach.free.Model.Tips;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;
import com.keeprawteach.free.VipTips.Frags.MultiOne;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    List<Messages> arrayList;
    Context context;
    FirebaseDatabase database;
    private String exitAddString;
    private FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    private AdView mAdView;
    private ProgressBar progressBar;
    RecyclerAdapter recyclerAdapter;
    DatabaseReference reference;
    InterstitialAd refreshAdd;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar=findViewById(R.id.toolbar);
        context=Notification.this;
        arrayList = new ArrayList();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar =  findViewById(R.id.loadme);
        progressBar.setVisibility(View.GONE);
        exitAddString = this.context.getString(R.string.inters_id_test);
        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        recyclerView =  findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(arrayList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerAdapter);
        addData();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
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
        startActivity(new Intent(Notification.this, AllSettings.class));
        Animatoo.animateSplit(context);
        Notification.this.finish();

    }

    private void addData() {
        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/Messages");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                progressBar.setVisibility(View.GONE);

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String title = (String) messageSnapshot.child("title").getValue();
                    String message = (String) messageSnapshot.child("message").getValue();

                    Messages messages = new Messages();
                    messages.setTitle(title);
                    messages.setMessage(message);
                    arrayList.add(messages);
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
        List<Messages> arrayList;
        Context context;

        public RecyclerAdapter(List<Messages> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public MyHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyHoder(LayoutInflater.from(this.context).inflate(R.layout.mess, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyHoder myHoder, int i) {
            final Messages messages = arrayList.get(i);
            myHoder.Title.setText(messages.getTitle());
            myHoder.Message.setText(messages.getMessage());
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
        TextView Title, Message;

        public MyHoder(@NonNull View itemView) {
            super(itemView);
            Title =  itemView.findViewById(R.id.title_name);
            Message =  itemView.findViewById(R.id.message_name);


        }
    }
}
