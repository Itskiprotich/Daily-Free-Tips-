package com.keeprawteach.free.AnimData;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.keeprawteach.free.AnimData.Menus.DrawerAdapter;
import com.keeprawteach.free.AnimData.Menus.DrawerItem;
import com.keeprawteach.free.AnimData.Menus.SimpleItem;
import com.keeprawteach.free.AnimData.Menus.SpaceItem;
import com.keeprawteach.free.Frags.DailyResults;
import com.keeprawteach.free.Frags.Home;
import com.keeprawteach.free.Livescore.LiveData;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AnimData extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_FREE_TIPS = 0;
    private static final int POS_DAILY_RESULTS = 1;
    private static final int POS_VIP_TIPS = 2;
    private static final int POS_LIVESCORES = 3;
    private static final int POS_SETTINGS = 4;
    private static final int POS_LOGOUT = 6;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    Context context;
    private static long back_pressed;
    InterstitialAd Goodbye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_data);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = AnimData.this;
        Goodbye = new InterstitialAd(this);
        Goodbye.setAdUnitId(getString(R.string.inters_id_test));
        Goodbye.loadAd(new AdRequest.Builder().build());
        Goodbye.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
            }
        });
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.sidebar)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_FREE_TIPS).setChecked(true),
                createItemFor(POS_DAILY_RESULTS),
                createItemFor(POS_VIP_TIPS),
                createItemFor(POS_LIVESCORES),
                createItemFor(POS_SETTINGS),
                new SpaceItem(30),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_FREE_TIPS);
    }

    @Override
    public void onItemSelected(int position) {
        if (position == POS_FREE_TIPS) {
            Home home = new Home();
            loadFlag(home, 0);
            slidingRootNav.closeMenu();

        }
        if (position == POS_DAILY_RESULTS) {
            Home home = new Home();
            loadFlag(home, 1);
            slidingRootNav.closeMenu();
        }
        if (position == POS_VIP_TIPS) {
            Home home = new Home();
            loadFlag(home, 2);
            slidingRootNav.closeMenu();
        }
        if (position == POS_LIVESCORES) {
            startActivity(new Intent(AnimData.this, LiveData.class));
            Animatoo.animateSplit(context);
            slidingRootNav.closeMenu();
            AnimData.this.finish();
        }
        if (position == POS_SETTINGS) {
            startActivity(new Intent(AnimData.this, AllSettings.class));
            Animatoo.animateSplit(context);
            slidingRootNav.closeMenu();
            AnimData.this.finish();
        }
        if (position == POS_LOGOUT) {
            finish();
        }

        slidingRootNav.closeMenu();
    }

    private void loadFlag(Fragment fragment, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("number", i);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "Home");
        fragmentTransaction.commit();

    }


    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.titles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.icons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            goodbye();

        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vip_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(AnimData.this, AllSettings.class));
            Animatoo.animateSplit(context);
            AnimData.this.finish();
            return true;
        }
        if (id == R.id.action_share) {
            Intent jeff = new Intent(Intent.ACTION_SEND);
            jeff.setType("text/plain");
            String url = "https://play.google.com/store/apps/details?id=com.keeprawteach.free";
            jeff.putExtra(Intent.EXTRA_TEXT, url);
            context.startActivity(jeff);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goodbye() {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(AnimData.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Exit");
        sweetAlertDialog.setContentText("Are you sure you want to exit?");
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
        sweetAlertDialog.setConfirmButton("Exit", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                AnimData.this.finish();
            }
        });
        sweetAlertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
    }
}
