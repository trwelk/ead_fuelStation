package com.example.fuelstationclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.model.User;
import com.example.fuelstationclient.session.UserSession;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    EditText loginPassword;
    EditText loginEmail;
    Button loginBtn;
    UserSession userSession;
    WebService webService;
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
        webService = WebServiceClient.getInstance().getWebService();        loginBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Do stuff here
                login();
                System.out.println("trwelk123");
            }
        });
    }

    private void login() {
        User user = new User(loginEmail.getText().toString(),loginPassword.getText().toString());

        Call<User> call = webService.loginUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null : "Response Body Empty";
                User userResponse;
                userResponse = response.body();
                //sdasdsadsadsadsadsadas
                if ( userResponse != null) {
                    if (userSession.isUserLoggedIn() && userSession.getUserDetails().get(userSession.KEY_USER_TYPE).equals("Vehicle")){
                        Intent intent = new Intent(getContext(), FuelStationList.class);
                        startActivity(intent);                    }
                    else {
                        navigateFuelTypes();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
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