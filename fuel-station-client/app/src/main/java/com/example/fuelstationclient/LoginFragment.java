package com.example.fuelstationclient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginPassword = getActivity().findViewById(R.id.loginPassword);
        loginEmail = getActivity().findViewById(R.id.loginEmailEditText);
        loginBtn = getActivity().findViewById(R.id.loginBtn);
        userSession = new UserSession(getContext());
        webService = WebServiceClient.getInstance().getWebService();
        loginBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                login();
            }
        });
    }

    private void login() {
        User user = new User(loginEmail.getText().toString(),loginPassword.getText().toString());

        Call<List<User>> call = webService.loginUser(user);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null : "Response Body Empty";
                List<User> userResponse;
                userResponse = response.body();
                //sdasdsadsadsadsadsadas
                if ( userResponse != null) {
                    if (userResponse.get(0).getId() != null && userResponse.get(0).getUserType().equals("Vehicle")){
                        Log.d("TAG", "Vehicle logged in: ");
                        Intent intent = new Intent(getContext(), FuelStationList.class);
                        startActivity(intent);                    }
                    else {
                        Log.d("TAG", "Station logged in: ");
                        navigateFuelTypes();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void registerToSharedPreferences(User user){

        userSession.createUserLoginSession(user.getName(),
                user.getPassword(),
                user.getId(),
                user.getUserType());

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

                Intent intent = new Intent(getContext(), FuelStationFuelTypesActivity.class);
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
}