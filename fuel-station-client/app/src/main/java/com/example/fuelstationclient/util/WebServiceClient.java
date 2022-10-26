package com.example.fuelstationclient.util;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClient {


    private static WebServiceClient instance = null;
    private WebService webService;

    private WebServiceClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(WebService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webService = retrofit.create(WebService.class);
    }

    public static synchronized WebServiceClient getInstance() {
        if (instance == null) {
            instance = new WebServiceClient();
        }
        return instance;
    }

    public WebService getWebService() {
        return webService;
    }

}