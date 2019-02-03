package com.keeprawteach.free.Frags;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.keeprawteach.free.R;
import com.keeprawteach.free.VipTips.Registration;
import com.keeprawteach.free.VipTips.VipMain;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class VipTips extends Fragment {

    FloatingActionButton floatingActionButton;
    Context context;

    EditText Phone, Pass;

    Button button;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressBar;
    private AdView mAdView;

    public VipTips() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vip_tips, container, false);
        context = getContext();
        mAdView =  view.findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        floatingActionButton =  view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupRegister();
            }
        });
        Phone =  view.findViewById(R.id.aa);
        Pass =  view.findViewById(R.id.bb);
        button =  view.findViewById(R.id.go);
        progressBar=view.findViewById(R.id.look);
        progressBar.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
        return view;
    }

    private void tryLogin() {
        String number = Phone.getText().toString();
        String pass = Pass.getText().toString();
        if (number.isEmpty()) {
            Toast.makeText(context, "Check phone number", Toast.LENGTH_SHORT).show();
        } else if (pass.isEmpty()) {
            Toast.makeText(context, "Empty password field", Toast.LENGTH_SHORT).show();
        } else {
            searchUser(number, pass);
        }
    }

    private void searchUser(String number, final String pass) {
        progressBar.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Daily Free/All Users");
        reference.orderByChild("phone").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.INVISIBLE);
                if (dataSnapshot.exists()) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        String password = (String) messageSnapshot.child("password").getValue();
                        if (pass.equalsIgnoreCase(password)) {
                            startActivity(new Intent(context, VipMain.class));
                            Phone.setText("");
                            Pass.setText("");
                            getActivity().finish();
                        } else {
                            Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "No Such Record... ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Error..!!" + databaseError, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void popupRegister() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, floatingActionButton, floatingActionButton.getTransitionName());
            startActivityForResult(new Intent(context, Registration.class), 100, options.toBundle());
        } else {
            startActivityForResult(new Intent(context, Registration.class), 100);
        }
    }

}
