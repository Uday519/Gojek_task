package com.uday.gojek_task;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkDetection extends Application{

    private static NetworkDetection instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if(instance == null){
            instance = this;
        }
    }

    public static NetworkDetection getInstance(){
        return instance;
    }

    public static boolean hasInternet(){
        return instance.isInternetAvaliable();
    }

    private boolean isInternetAvaliable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
