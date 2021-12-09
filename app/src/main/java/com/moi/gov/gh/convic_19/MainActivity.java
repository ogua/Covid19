package com.moi.gov.gh.convic_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setDarkMode(getWindow());

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, DataEntryActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(MainActivity.this, AllDetailsActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

        } else if (id == R.id.nav_slideshow) {
            //Profile Here
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);

        }else if (id == R.id.nav_password) {
            //setting here
            startActivity(new Intent(MainActivity.this, RestPasswordActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);


        }else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT,"CORVIC 19 DATA ENTRY APP, POWERED BY GHANA HEALTH SERVICE");
            List<ResolveInfo> possibleactivitylist = getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_ALL);
            if (possibleactivitylist.size() > 1){
                String title = "Share Fire Report App";
                Intent chooser = Intent.createChooser(shareIntent, title);
                startActivity(chooser);
            }else if (shareIntent.resolveActivity(getPackageManager()) != null){
                startActivity(shareIntent);
            }

        } /* else if (id == R.id.nav_dark_mode) {
            //code for setting dark mode
            //true for dark mode, false for day mode, currently toggling on each click
            DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //create a seperate class file, if required in multiple activities
    public void setDarkMode(Window window){
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(0,window);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(1,window);
        }
    }
    public void changeStatusBar(int mode, Window window){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentBodyColor));
            //white mode
            if(mode==1){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    return true;
                case R.id.navigationMyCourses:
                    //Logout Here
                    mAuth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    return true;
                case R.id.navigationHome:
                    return true;
                /*case  R.id.navigationSearch:
                    //setting here
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                    return true;*/
                case  R.id.navigationMenu:
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
            return false;
        }
    };

    public void EnterData(View view) {
        startActivity(new Intent(MainActivity.this, DataEntryActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void ALLDetails(View view) {
        startActivity(new Intent(MainActivity.this, AllDetailsActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void ViewBarcode(View view) {
        startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void viewconfirmed(View view) {
        startActivity(new Intent(MainActivity.this, ConfirmedActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void ViewProbable(View view) {
        startActivity(new Intent(MainActivity.this, ProbableActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    public void viewsuspected(View view) {
        startActivity(new Intent(MainActivity.this, SuspectedCaseActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }


    @Override
    protected void onResume() {
        super.onResume();
        opendrawer();
        requestforcamera();
    }


    private void requestforcamera() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }


    private void opendrawer() {
        //the handle is udes to run things at short time
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        }, 1000);

    }
}
