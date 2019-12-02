package com.uday.gojek_task.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.uday.gojek_task.interfaces.Api;
import com.uday.gojek_task.models.GithubTrending;
import com.uday.gojek_task.repo.RetrofitGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<List<GithubTrending>> trendinglist ;
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();

    private Call<List<GithubTrending>> getlist;

    public LiveData<List<GithubTrending>> getTrendinglist() {
        if(trendinglist == null){
            showProgressBar.setValue(true);
            trendinglist = new MutableLiveData<>();
            Api api = RetrofitGenerator.getApi();
            getlist = api.getTendingList();
            getlist.enqueue(new Callback<List<GithubTrending>>() {
                @Override
                public void onResponse(Call<List<GithubTrending>> call, Response<List<GithubTrending>> response) {
                    trendinglist.setValue(response.body());
                    showProgressBar.setValue(false);
                }

                @Override
                public void onFailure(Call<List<GithubTrending>> call, Throwable t) {
                    String error_message= t.getMessage();
                    Log.d("Error loading data", error_message);
                    showProgressBar.setValue(false);
                    show_networkError.setValue(true);

                }
            });
        }

        return trendinglist;
    }

    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }

    public void retry(){
        showProgressBar.setValue(true);
        getlist = getlist.clone();
        getlist.enqueue(new Callback<List<GithubTrending>>() {
            @Override
            public void onResponse(Call<List<GithubTrending>> call, Response<List<GithubTrending>> response) {
                List<GithubTrending> responseList = response.body();
                if(responseList.size() >0){
                    show_networkError.setValue(false);
                    trendinglist.setValue(responseList);
                    showProgressBar.setValue(false);
                }
                else{
                    show_networkError.setValue(true);
                }

            }

            @Override
            public void onFailure(Call<List<GithubTrending>> call, Throwable t) {
                String error_message= t.getMessage();
                Log.d("Error loading data", error_message);
                showProgressBar.setValue(false);
                show_networkError.setValue(true);

            }
        });
    }

    public MutableLiveData<Boolean> getShow_networkError() {
        return show_networkError;
    }
}
