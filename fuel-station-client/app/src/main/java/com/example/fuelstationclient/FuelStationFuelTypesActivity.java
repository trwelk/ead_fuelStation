package com.example.fuelstationclient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelstationclient.adapter.FuelStationListAdapter;
import com.example.fuelstationclient.adapter.FuelTypeListAdapter;
import com.example.fuelstationclient.model.Fuel;
import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationFuelTypesActivity extends AppCompatActivity {
    FuelTypeListAdapter adapter;
    private FuelStation fuelStation;
    RecyclerView recyclerView;
    Button fuelStationFuelTypeNewBtn;
    private String m_Text = "";
    WebService webService;
    TextView fuelStationFuelTypesName;
    TextView fuelStationFuelTypesLocation;
    TextView fuelStationFuelTypesPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_station_fuel_types);
        webService = WebServiceClient.getInstance().getWebService();
        init();
        loadList();
    }

    private void init() {
        recyclerView =  findViewById(R.id.fuelTypeListRecyclerView);
        fuelStation = (FuelStation) getIntent().getSerializableExtra("fuelStation");
        fuelStationFuelTypeNewBtn = findViewById(R.id.fuelStationFuelTypeNewBtn);
        fuelStationFuelTypesName = findViewById(R.id.fuelStationFuelTypesName);
        fuelStationFuelTypesLocation = findViewById(R.id.fuelStationFuelTypesLocation);
        fuelStationFuelTypesPhone = findViewById(R.id.fuelStationFuelTypesPhone);

        fuelStationFuelTypeNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
    }

    private void showDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("New Fuel Type");

        final EditText input = new EditText(view.getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Fuel Type");
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                addFuelType();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void addFuelType(){
        Fuel fuel = new Fuel(m_Text);

        Call<Fuel> call = webService.addFuelType(fuelStation.getId(),fuel);
        Log.d("TAG", "TRWELK: " );
        call.enqueue(new Callback<Fuel>() {
            @Override
            public void onResponse(Call<Fuel> call, Response<Fuel> response) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onResponse: " + response.code());
                assert response.body() != null : "Response Body Empty";
            }

            @Override
            public void onFailure(Call<Fuel> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void loadList() {
        fuelStationFuelTypesName.setText(fuelStation.getName());
        fuelStationFuelTypesLocation.setText(fuelStation.getAddress());
        fuelStationFuelTypesPhone.setText(fuelStation.getContactNumber());
        adapter = new FuelTypeListAdapter( fuelStation);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}