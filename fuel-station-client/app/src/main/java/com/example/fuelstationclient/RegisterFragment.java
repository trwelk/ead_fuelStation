package com.example.fuelstationclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.model.User;
import com.example.fuelstationclient.session.UserSession;
import com.example.fuelstationclient.util.FuelStationListAdapter;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    String[] registerTypes = { "Station","Vehicle" };
    View view;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserSession userSession;

    String responseId;
    EditText registerNameEditText;
    EditText registerEmailEditText;
    EditText registerPasswordEditText;
    EditText registerTypeEditText;
    Button registerSubmitBtn;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        userSession = new UserSession(getContext());
        setDropDownValues();
        registerNameEditText = getActivity().findViewById(R.id.registerNameEditText);
        registerEmailEditText = getActivity().findViewById(R.id.registerEmailEditText);
        registerPasswordEditText = getActivity().findViewById(R.id.registerPasswordEditText);
        registerTypeEditText = getActivity().findViewById(R.id.registerTypeEditText);
        registerSubmitBtn = getActivity().findViewById(R.id.registerSubmitBtn);
        registerSubmitBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                register();
                System.out.println("trwelk123");
            }
        });
    }

    private void registerVehicle() {

        User user = new User(registerNameEditText.getText().toString(),registerEmailEditText.getText().toString(),registerPasswordEditText.getText().toString());

        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<User> call = webService.registerVehicle(user);
        Log.d("INFO", "Vehicle: " + registerNameEditText.getText().toString() );

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null : "Response Body Empty";
                User userResponse = response.body();
                responseId = userResponse.getId();
                Log.d("TAG", "onResponse: " + response.code() + "user ID - " + responseId);
                registerToSharedPreferences();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


    private void registerFuelStation() {

        FuelStation fuelStation = new FuelStation(registerNameEditText.getText().toString(),registerEmailEditText.getText().toString(),registerPasswordEditText.getText().toString());

        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<FuelStation> call = webService.registerFuelStation(fuelStation);
        Log.d("TAG", "TRWELK: " + registerNameEditText.getText().toString() );

        call.enqueue(new Callback<FuelStation>() {
            @Override
            public void onResponse(Call<FuelStation> call, Response<FuelStation> response) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onResponse: " + response.code());
                assert response.body() != null : "Response Body Empty";
                FuelStation fuelStationResponse = response.body();
                responseId = fuelStationResponse.getId();
                Log.d("TAG", "onResponse: " + response.code() + "user ID - " + responseId);
                registerToSharedPreferences();

            }

            @Override
            public void onFailure(Call<FuelStation> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void register(){
        if (registerTypeEditText.getText().toString().equals("Vehicle")) {
            Log.d("INFO", "Registering Vehicle");
            registerVehicle();
            Intent intent = new Intent(getActivity(), FuelStationList.class);
            startActivity(intent);

        } else {
            Log.d("INFO", "Registering FuelStation");
            registerFuelStation();

        }
        Log.d("INFO", "Registering Done");

    }
    private void setDropDownValues() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.select_dialog_singlechoice, registerTypes);
        AutoCompleteTextView dropDown = (AutoCompleteTextView) view.findViewById(R.id.registerTypeEditText);
        dropDown.setThreshold(1);
        dropDown.setAdapter(adapter);

    }

    private void registerToSharedPreferences(){
        Log.d("TAG", "onResponse:trwe " + "user ID - " + responseId);

        userSession.createUserLoginSession(registerNameEditText.getText().toString(),
                                            registerPasswordEditText.getText().toString(),
                                            responseId,
                                            registerTypeEditText.getText().toString());
//        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("User", 0);
//        editor = sharedPreferences.edit();
//        editor.putString("name", registerNameEditText.getText().toString());
//        editor.putString("email",registerEmailEditText.getText().toString());
//        editor.putString("password",registerPasswordEditText.getText().toString());
//        editor.putBoolean("isLoggedIn", true);
//        editor.commit();
    }   // commit the values

}