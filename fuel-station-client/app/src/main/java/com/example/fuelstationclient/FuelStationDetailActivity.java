package com.example.fuelstationclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelstationclient.model.FuelStation;
import com.example.fuelstationclient.model.UserQueue;
import com.example.fuelstationclient.session.UserSession;
import com.example.fuelstationclient.util.DateUtil;
import com.example.fuelstationclient.util.WebService;
import com.example.fuelstationclient.util.WebServiceClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FuelStationDetailActivity extends AppCompatActivity {
    FuelStation fuelStation;
    UserSession userSession;
    private TextView vehicleCount;
    private TextView averageTime ;
    private Button joinBtn ;
    private Button leaveBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fuelStation = (FuelStation) getIntent().getSerializableExtra("fuelStation");
        setContentView(R.layout.activity_fuel_station_detail);
        init();

    }

    public void init(){
        userSession = new UserSession(this);
        averageTime  = (TextView) findViewById(R.id.fuelAverageTime);
        vehicleCount = (TextView) findViewById(R.id.fuelVehicleCount);
        joinBtn     = (Button) findViewById(R.id.fuelStationDetailActivityJoinBtn);
        leaveBtn     = (Button) findViewById(R.id.fuelStationDetailActivityLeaveBtn);

        boolean userInQueue = userInQueue();
        joinBtn.setEnabled(!userInQueue);
        leaveBtn.setEnabled(userInQueue);
        int    numberOfVehicles = getVehicleCount(fuelStation);
        double averageTimeInQueue = getAverageTime(fuelStation);
        this.vehicleCount.setText(Integer.toString(numberOfVehicles));
        this.averageTime.setText(Double.toString(averageTimeInQueue));
    }

    private boolean userInQueue() {
        for (UserQueue userQueue : this.fuelStation.getUsersInQueue()){
            if (userQueue.getUserId().equals(userSession.getUserDetails().get(userSession.KEY_ID)) && userQueue.getTimeLeft() == null){
                return true;
            }
        }
        return false;
    }

    private UserQueue getUserQueue() {
        for (UserQueue userQueue : this.fuelStation.getUsersInQueue()){
            System.out.println("USER_QUEUE");
            System.out.println(userQueue.getUserId() + " - " + userSession.getUserDetails().get(userSession.KEY_ID));

            if (userQueue.getUserId().equals(userSession.getUserDetails().get(userSession.KEY_ID)) && userQueue.getTimeLeft() == null){
                return userQueue;
            }
        }
        return null;
    }

    private double getAverageTime(FuelStation fuelStation) {
        long totalDiffInMinutes = 0;
        int count = 0;
        for (UserQueue userQueue : fuelStation.getUsersInQueue()){
            if (userQueue.getTimeArrived() != null && userQueue.getTimeLeft() != null) {
                Date timeLeft = null;
                Date timeArrived = null;
                try {
                    timeLeft = DateUtil.getDateFromTimeStampString(userQueue.getTimeLeft());
                    timeArrived = DateUtil.getDateFromTimeStampString(userQueue.getTimeArrived());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long duration  = timeLeft.getTime() - timeArrived.getTime();
                long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

                count++;
                totalDiffInMinutes = totalDiffInMinutes + diffInMinutes;
            }
        }
        if (fuelStation.getUsersInQueue().isEmpty() || count == 0)
            return 0;
        return totalDiffInMinutes/count;
    }

    public void leaveQueue(View view){
//        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date d = null;
//        try {
//            d = output.parse(String.valueOf(new Date()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String formattedTime = output.format(d);

        UserQueue userQueue = getUserQueue();
        userQueue.setTimeLeft(DateUtil.getCurrentTimeStamp());

        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<UserQueue> call = webService.leaveQueue(fuelStation.getId(),userQueue);
        Log.d("INFO", "JOING QUEUE CALLED for station" + fuelStation.getId());
        Log.d("INFO", "JOING QUEUE CALLED for station" + userQueue.getTimeLeft());

        call.enqueue(new Callback<UserQueue>() {
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
//                progressBar.setVisibility(View.GONE);
//                Log.d("INFO", "onResponse: " + response.code());
//                assert response.body() != null : "Response Body Empty";
//                UserQueue userQueueResponse = response.body();
//                Log.d("INFO", "onResponse: " + response.code() + "user ID - " + userQueueResponse.getUserId());
                joinBtn.setEnabled(true);
                leaveBtn.setEnabled(false);
            }

            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("ERROR", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    public void joinQueue(View view){

        UserQueue userQueue = new UserQueue(userSession.getUserDetails().get(userSession.KEY_ID));
        Log.d("INFO", "JOING QUEUE CALLED for station" + userQueue.getId());
        Log.d("INFO", "JOING QUEUE CALLED for 2" + userQueue.getUserId());
        Log.d("INFO", "JOING QUEUE CALLED for 3" + userQueue.getTimeArrived());
        WebService webService = WebServiceClient.getInstance().getWebService();
        Call<UserQueue> call = webService.joinQueue(fuelStation.getId(),userQueue);
        Log.d("INFO", "JOING QUEUE CALLED for station" + fuelStation.getId());
        Log.d("INFO", "JOING QUEUE CALLED for station" + userSession.getUserDetails().get(userSession.KEY_ID));

        call.enqueue(new Callback<UserQueue>() {
            @Override
            public void onResponse(Call<UserQueue> call, Response<UserQueue> response) {
//                progressBar.setVisibility(View.GONE);
                Log.d("INFO", "onResponse: " + response.code());
//                assert response.body() != null : "Response Body Empty";
//                UserQueue userQueueResponse = response.body();
//                Log.d("INFO", "onResponse: " + response.code() + "user ID - " + userQueueResponse.getUserId());
                joinBtn.setEnabled(false);
                leaveBtn.setEnabled(true);
            }

            @Override
            public void onFailure(Call<UserQueue> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.d("ERROR", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
    private int getVehicleCount(FuelStation fuelStation) {
        int count = 0;
        for(UserQueue userQueue : fuelStation.getUsersInQueue()){
            Log.d("INFO", "userQueue: " + userQueue.getUserId());

            if (userQueue.getTimeArrived() != null && userQueue.getTimeLeft() == null) {
                count++;
            }
        }
        return count;
    }
}