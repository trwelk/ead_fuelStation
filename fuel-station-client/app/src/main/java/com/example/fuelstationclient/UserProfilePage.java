package com.example.fuelstationclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fuelstationclient.model.User;
import com.example.fuelstationclient.session.UserSession;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfilePage extends AppCompatActivity {
    String responseId;
    UserSession userSession;
    EditText updateNameEditText;
    EditText updateEmailEditText;
    EditText updatePasswordEditText;
    EditText updateTypeEditText;
    Button updateSubmitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);

        userSession = new UserSession(this);
        HashMap<String, String> userDetails = userSession.getUserDetails();
        User user = new User(userDetails.get(userSession.KEY_NAME),userDetails.get(userSession.KEY_EMAIL),userDetails.get(userSession.KEY_PASSWORD),userDetails.get(userSession.KEY_USER_TYPE));
        updateNameEditText = findViewById(R.id.updateNameEditText);
        updateEmailEditText = findViewById(R.id.updateEmailEditText);
        updatePasswordEditText = findViewById(R.id.updatePasswordEditText);
        updateTypeEditText = findViewById(R.id.updateTypeEditText);
        updateSubmitBtn = findViewById(R.id.updateSubmitBtn);
        updateNameEditText.setText(user.getName());
        updateEmailEditText.setText(user.getEmail());
        updatePasswordEditText.setText(user.getPassword());
        updateTypeEditText.setText(user.getUserType());

        updateSubmitBtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                update(user);
            }
        });

    }
    private User update(User user) {
        final User[] userResponse = new User[1];
        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<List<User>> call = webService.updateUser(user);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null : "Response Body Empty";
                List<User> userResponse;
                userResponse = response.body();
                User updatedUser = userResponse.get(0);
                if ( userResponse != null) {
                    updateToSharedPreferences(updatedUser);
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("Update Fail", "onFailure: " + t.getLocalizedMessage());
            }
        });
        return userResponse[0];
    }

    private void updateToSharedPreferences(User user){

        userSession.createUserLoginSession(user.getName(),
                user.getPassword(),
                user.getId(),
                user.getUserType());

    }



}