package com.example.fuelstationclient.util;

import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.model.User;
import com.example.fuelstationclient.model.UserQueue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    String BASE_URL = "http://10.0.2.2:5224/api/";
    @GET("FuelStation")
    Call<List<FuelStation>> getFuelStations();

    @POST("FuelStation/{id}/VehiclesInQueue")
    Call<UserQueue> joinQueue(@Path("id") String id,@Body UserQueue userQueue);

    @PATCH("FuelStation/{id}/VehiclesInQueue")
    Call<UserQueue> leaveQueue(@Path("id") String id,@Body UserQueue userQueue);


    @POST("User")
    Call<User> registerVehicle(@Body User user);

    @POST("FuelStation")
    Call<FuelStation> registerFuelStation(@Body FuelStation fuelStation);
}
