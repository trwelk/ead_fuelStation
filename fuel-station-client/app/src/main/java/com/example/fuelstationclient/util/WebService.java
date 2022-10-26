package com.example.fuelstationclient.util;

import com.example.fuelstationclient.model.Fuel;
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

    @GET("FuelStation/GetByUser/{id}")
    Call<List<FuelStation>> getFuelStationByUser(@Path("id") String id);

    @POST("FuelStation/{id}/FuelType/{fId}/VehiclesInQueue")
    Call<UserQueue> joinQueue(@Path("id") String id,@Path("fId") String fId,@Body UserQueue userQueue);

    @PATCH("FuelStation/{id}/FuelType/{fId}/VehiclesInQueue")
    Call<UserQueue> leaveQueue(@Path("id") String id,@Path("fId") String fId,@Body UserQueue userQueue);


    @POST("FuelStation/{id}/FuelType")
    Call<Fuel> addFuelType(@Path("id") String id,@Body Fuel fuel);

    @POST("User")
    Call<User> registerUser(@Body User user);

    @POST("User/Login")
    Call<User> loginUser(@Body User user);

    @POST("FuelStation")
    Call<FuelStation> registerFuelStation(@Body FuelStation fuelStation);
}
