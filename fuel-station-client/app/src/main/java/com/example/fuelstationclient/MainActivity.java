package com.example.fuelstationclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.fuelstationclient.model.Fuel;
import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.session.UserSession;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager());
    UserSession userSession;
    WebService webService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webService = WebServiceClient.getInstance().getWebService();
        userSession = new UserSession(this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        pagerAdapter.addFragmet(new LoginFragment());
        pagerAdapter.addFragmet(new RegisterFragment());
        viewPager.setAdapter(pagerAdapter);
        Log.d("INFO", "MainActivity checking user session" + userSession.getUserDetails().get("id"));

        if ( userSession != null) {
            if (userSession.isUserLoggedIn() /*&& userSession.getUserDetails().get(userSession.KEY_USER_TYPE).equals("Vehicle")*/){
                Log.d("INFO", "Already logged in" + userSession.getUserDetails().get("name"));
                navigateToList();
            }
            else {
                navigateFuelTypes();
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

    public void navigateFuelTypes() {

        Call<List<FuelStation>> call = webService.getFuelStationByUser(userSession.getUserDetails().get("id"));
        Log.d("TAG", "TRWELK: " );
        call.enqueue(new Callback<List<FuelStation>>() {
            @Override
            public void onResponse(Call<List<FuelStation>> call, Response<List<FuelStation>> response) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onResponse: " + response.code());
                List<FuelStation> fuelStations = response.body() ;

                Intent intent = new Intent(MainActivity.this, FuelStationFuelTypesActivity.class);
                intent.putExtra("fuelStation", fuelStations.get(0));
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<FuelStation>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });

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