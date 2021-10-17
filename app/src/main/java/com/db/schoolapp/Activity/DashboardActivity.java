package com.db.schoolapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.schoolapp.Constants.AppConstants;
import com.db.schoolapp.Fragments.HomeFragment;
import com.db.schoolapp.Fragments.SettingsFragment;
import com.db.schoolapp.R;

public class DashboardActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    String ScreenStatus="Home";
    CardView hometab,ProfileTab;
    ImageView homeimage,ProfileImage ;
    Toolbar toolbar1;
    TextView toolbar_title;
    TextView hometext,ProfileText,headertitle;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        hometab= findViewById(R.id.hometab);
        ProfileTab= findViewById(R.id.ProfileTab);
        homeimage= findViewById(R.id.homeimage);
        ProfileImage = findViewById(R.id.ProfileImage);
        hometext= findViewById(R.id.hometext);
        ProfileText= findViewById(R.id.ProfileText);
        headertitle=findViewById(R.id.header_title);
        getSupportActionBar().hide();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.btncolor));
        }
        headertitle.setText("Examica");

        getFragment(new HomeFragment(), "Home");
        hometab.setOnClickListener(v -> {
            hometab.setCardBackgroundColor(getResources().getColor(R.color.white1, null));
            homeimage.setColorFilter(getResources().getColor(R.color.btncolor, null));
            hometext.setTextColor(getResources().getColor(R.color.btncolor, null));
            ProfileTab.setCardBackgroundColor(getResources().getColor(R.color.btncolor, null));
            ProfileImage.setColorFilter(getResources().getColor(R.color.white1, null));
            ProfileText.setTextColor(getResources().getColor(R.color.white1, null));
            headertitle.setText("Examica");
            getFragment(new HomeFragment(), "Home");
        });
        ProfileTab.setOnClickListener(v -> {
            hometab.setCardBackgroundColor(getResources().getColor(R.color.btncolor, null));
            homeimage.setColorFilter(getResources().getColor(R.color.white1, null));
            hometext.setTextColor(getResources().getColor(R.color.white1, null));
            ProfileTab.setCardBackgroundColor(getResources().getColor(R.color.white1, null));
            ProfileImage.setColorFilter(getResources().getColor(R.color.btncolor, null));
            ProfileText.setTextColor(getResources().getColor(R.color.btncolor, null));
            getFragment(new SettingsFragment(), "profile");
            headertitle.setText("Settings");

        });
    }

    public void getFragment(Fragment fragment, String current1) {
        AppConstants.current = current1;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentSection, fragment, AppConstants.current);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ScreenStatus=current1;
    }



    @Override
    public void onBackPressed() {
        if (!ScreenStatus.equals("Home")) {
            hometab.setCardBackgroundColor(getResources().getColor(R.color.white1, null));
            homeimage.setColorFilter(getResources().getColor(R.color.btncolor, null));
            hometext.setTextColor(getResources().getColor(R.color.btncolor, null));
            ProfileTab.setCardBackgroundColor(getResources().getColor(R.color.btncolor, null));
            ProfileImage.setColorFilter(getResources().getColor(R.color.white1, null));
            ProfileText.setTextColor(getResources().getColor(R.color.white1, null));
            getFragment(new HomeFragment(), "Home");
        }
        else {
            if (doubleBackToExitPressedOnce) {
                finish();
                System.exit(0);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(DashboardActivity.this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 3000);
        }
    }

}