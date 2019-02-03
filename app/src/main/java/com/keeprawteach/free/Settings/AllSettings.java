package com.keeprawteach.free.Settings;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.Contacts.Contact_Us;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.Sub.AppInfor;
import com.keeprawteach.free.Settings.Sub.General;
import com.keeprawteach.free.Settings.Sub.Notification;
import com.keeprawteach.free.Terms.TnCs;

public class AllSettings extends AppCompatActivity {
    ListView home;
    Toolbar toolbar;
    Context context;
    String[] values = new String[]{ "General ", "Notification Messages", "Contact Us", "Terms and Privacy Policy", "App Info"};
    int[] images = {
            R.drawable.cog,
            R.drawable.note,
            R.drawable.contact,
            R.drawable.tncs,
            R.drawable.terms};


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);
        context=AllSettings.this;
        mAdView =  findViewById(R.id.adView);
        mAdView.loadAd(new AdRequest.Builder().build());
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        home =  findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(AllSettings.this, values, images);
        home.setAdapter(adapter);
        home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        startActivity(new Intent(AllSettings.this, General.class));
                        Animatoo.animateSplit(context);
                        AllSettings.this.finish();
                        break;
                    case 1:
                        startActivity(new Intent(AllSettings.this, Notification.class));
                        Animatoo.animateSplit(context);
                        AllSettings.this.finish();
                        break;
                    case 2:

                        startActivity(new Intent(AllSettings.this, Contact_Us.class));
                        Animatoo.animateSplit(context);
                        AllSettings.this.finish();
                        break;
                    case 3:
//                        Toast.makeText(AllSettings.this, "Terms and Privacy Policy", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AllSettings.this, TnCs.class));
                        Animatoo.animateSplit(context);
                        AllSettings.this.finish();
                        break;
                    case 4:
                        startActivity(new Intent(AllSettings.this, AppInfor.class));
                        Animatoo.animateSplit(context);
                        AllSettings.this.finish();

                        break;

                }

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
        startActivity(new Intent(AllSettings.this, AnimData.class));
        Animatoo.animateSplit(context);
        AllSettings.this.finish();

    }

    private class MyAdapter extends ArrayAdapter {
        Context context;
        String[] values;
        int[] imageArray;

        public MyAdapter(Context context, String[] values, int[] imageArray) {
            super(context, R.layout.settings, R.id.bb, values);
            this.context = context;
            this.values = values;
            this.imageArray = imageArray;
        }

        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.settings, parent, false);
            ImageView imageView =  row.findViewById(R.id.aa);
            TextView textView =  row.findViewById(R.id.bb);
            imageView.setImageResource(imageArray[position]);
            textView.setText(values[position]);
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/user_one.ttf");
            textView.setTypeface(typeface);
            return row;
        }
    }
}
