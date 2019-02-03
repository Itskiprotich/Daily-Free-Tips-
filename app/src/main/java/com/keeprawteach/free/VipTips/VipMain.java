package com.keeprawteach.free.VipTips;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.keeprawteach.free.AnimData.AnimData;
import com.keeprawteach.free.R;
import com.keeprawteach.free.Settings.AllSettings;
import com.keeprawteach.free.VipTips.Frags.VipHome;

public class VipMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    private static long get_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = VipMain.this;

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        view.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);

        VipHome vipHome=new VipHome();
        Bundle bundle = new Bundle();
        bundle.putInt("number", 0);
        vipHome.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, vipHome, "Home");
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (get_out + 2000 > System.currentTimeMillis()) {
                goodbye();

            } else {
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
                get_out = System.currentTimeMillis();
            }

        }
    }

    private void goodbye() {
        startActivity(new Intent(VipMain.this, AnimData.class));
        Animatoo.animateSplit(context);
        VipMain.this.finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vip_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(VipMain.this, AllSettings.class));
            Animatoo.animateSplit(context);
            VipMain.this.finish();
            return true;
        }if (id == R.id.action_share) {

            Intent jeff = new Intent(Intent.ACTION_SEND);

            jeff.setType("text/plain");

            String url = "https://play.google.com/store/apps/details?id=com.keeprawteach.free";

            jeff.putExtra(Intent.EXTRA_TEXT, url);

            context.startActivity(jeff);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            VipHome home = new VipHome();
            loadFlag(home, 0);
        } else if (id == R.id.nav_gallery) {
            VipHome home = new VipHome();
            loadFlag(home, 1);

        } else if (id == R.id.nav_slideshow) {
            VipHome home = new VipHome();
            loadFlag(home, 2);

        } else if (id == R.id.nav_share) {
            shareLink();

        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareLink() {

        Intent jeff = new Intent(Intent.ACTION_SEND);
        jeff.setType("text/plain");
        String url = "https://play.google.com/store/apps/details?id=com.keeprawteach.free";
        jeff.putExtra(Intent.EXTRA_TEXT, url);
        context.startActivity(jeff);
    }

    private void loadFlag(Fragment fragment, int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("number", i);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "Home");
        fragmentTransaction.commit();
    }
}
