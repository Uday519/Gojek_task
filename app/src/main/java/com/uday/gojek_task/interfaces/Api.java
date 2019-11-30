package com.uday.gojek_task.interfaces;

import com.uday.gojek_task.models.GithubTrending;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "https://github-trending-api.now.sh/";

    @GET("repositories")
    Call<GithubTrending> getTendingList();
}
