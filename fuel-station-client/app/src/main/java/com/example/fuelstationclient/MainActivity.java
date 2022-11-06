package com.example.fuelstationclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.session.UserSession;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
    UserSession userSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userSession = new UserSession(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
        Log.d("INFO", "MainActivity checking user session" + userSession.getUserDetails().get("id"));

        if ( userSession != null) {
            if (userSession.isUserLoggedIn() && userSession.getUserDetails().get(userSession.KEY_USER_TYPE).equals("Vehicle")){
                navigateToList();
            }
        }
        Log.d("INFO", "MainActivity checking user session" + userSession.getUserDetails().get("name"));
        Log.d("INFO", "MainActivity checking user session" + userSession.getUserDetails().get("userType"));
        Log.d("INFO", "MainActivity checking user session" + userSession.getUserDetails().get("id"));

    }



    public void navigateToList() {
        Intent intent = new Intent(MainActivity.this, FuelStationList.class);
        startActivity(intent);
    }

    class AuthenticationPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentList = new ArrayList<>();

        public AuthenticationPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        void addFragmet(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}