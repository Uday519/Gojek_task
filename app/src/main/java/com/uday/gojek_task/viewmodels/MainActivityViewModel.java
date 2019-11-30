package com.uday.gojek_task.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.uday.gojek_task.interfaces.Api;
import com.uday.gojek_task.models.GithubTrending;
import com.uday.gojek_task.models.GithubTrendingList;
import com.uday.gojek_task.repo.RetrofitGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<GithubTrendingList> trendinglist ;
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();


    public MutableLiveData<GithubTrendingList> getTrendinglist() {
        if(trendinglist == null){
            showProgressBar.setValue(true);
            trendinglist = new MutableLiveData<>();
            Api api = RetrofitGenerator.getApi();
            Call<GithubTrendingList> getlist = api.getTendingList();
            getlist.enqueue(new Callback<GithubTrendingList>() {
                @Override
                public void onResponse(Call<GithubTrendingList> call, Response<GithubTrendingList> response) {
                    trendinglist.setValue(response.body());
                    showProgressBar.setValue(false);
                }

                @Override
                public void onFailure(Call<GithubTrendingList> call, Throwable t) {
                    String error_message= t.getMessage();
                    Log.d("Error loading data", error_message);
                    showProgressBar.setValue(false);

                }
            });
        }

        return trendinglist;
    }

    public MutableLiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }
}
