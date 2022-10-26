package com.example.fuelstationclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.adapter.FuelStationListAdapter;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationList extends AppCompatActivity {

    private ProgressBar progressBar;
    FuelStationListAdapter adapter;
    private List<FuelStation> fuelStationList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_list);
        init();
        getNews();
    }


    private void init() {
        fuelStationList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.fuelStationListRecyclerView);
//        progressBar = findViewById(R.id.progress_bar);
    }
    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getNews() {
        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<List<FuelStation>> call = webService.getFuelStations();
        Log.d("TAG", "TRWELK: " );

        call.enqueue(new Callback<List<FuelStation>>() {
            @Override
            public void onResponse(Call<List<FuelStation>> call, Response<List<FuelStation>> response) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onResponse: " + response.code());
                assert response.body() != null : "Response Body Empty";
                fuelStationList = response.body();
                adapter = new FuelStationListAdapter( fuelStationList);
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<List<FuelStation>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}