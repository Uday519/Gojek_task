package com.uday.gojek_task.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.uday.gojek_task.MainActivity;
import com.uday.gojek_task.interfaces.Api;
import com.uday.gojek_task.models.Avatars;
import com.uday.gojek_task.models.GithubTrending;
import com.uday.gojek_task.models.GithubTrendingList;
import com.uday.gojek_task.repo.DbManager;
import com.uday.gojek_task.repo.RetrofitGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<List<GithubTrending>> trendinglist ;
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();

    private Call<List<GithubTrending>> getlist;
    private DbManager dbManager ;

    public LiveData<List<GithubTrending>> getTrendinglist(final Context context, final MainActivity mainActivity) {
        dbManager = new DbManager(context);
        if(trendinglist == null || trendinglist.getValue() == null){
            showProgressBar.setValue(true);
            trendinglist = new MutableLiveData<>();
            Api api = RetrofitGenerator.getApi();
            getlist = api.getTendingList();
            getlist.enqueue(new Callback<List<GithubTrending>>() {
                @Override
                public void onResponse(Call<List<GithubTrending>> call, Response<List<GithubTrending>> response) {
                    final List<GithubTrending> githubTrendings = response.body();
                    if(response.raw().networkResponse() !=  null){
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                int i;
                                for(i=0; i<githubTrendings.size();i++){
                                    final String image_url = githubTrendings.get(i).getAvatar();
                                    Bitmap bitmap = getImage(image_url);
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                                    byte[] input_array = stream.toByteArray();
                                    Boolean is_inserted = dbManager.insertImage(image_url,input_array);
                                    if(is_inserted){
                                        Avatars av  = new Avatars(image_url,input_array);
                                        githubTrendings.get(i).setImage_bytes(av);
                                    }
                                    Log.d("Insert into db", String.valueOf(is_inserted));
                                }
                                if(i == githubTrendings.size()){
                                    mainActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<Avatars> avatarsList = dbManager.readAvatars();
                                            trendinglist.setValue(githubTrendings);
                                            showProgressBar.setValue(false);
                                        }
                                    });

                                }
                            }

                        });

                    }
                    else if(response.raw().cacheResponse() !=  null && response.raw().networkResponse() ==  null){
                        List<Avatars> avatarsList = dbManager.readAvatars();
                        for(int i =0;i<avatarsList.size(); i++){
                            if(githubTrendings.get(i).getAvatar().equals(avatarsList.get(i).getUrl())){
                                githubTrendings.get(i).setImage_bytes(avatarsList.get(i));
                            }
                        }
                        trendinglist.setValue(githubTrendings);
                        showProgressBar.setValue(false);
                    }
//
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
        else {
            trendinglist.postValue(trendinglist.getValue());
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


    public Bitmap getImage(String imageUrl)
    {
        Bitmap image = null;
        int inSampleSize = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        options.inSampleSize = inSampleSize;

        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream stream = connection.getInputStream();
            image = BitmapFactory.decodeStream(stream, null, options);
            options.inJustDecodeBounds = false;
            connection = (HttpURLConnection)url.openConnection();
            stream = connection.getInputStream();
            image = BitmapFactory.decodeStream(stream, null, options);
            return image;
        }

        catch(Exception e)
        {
            Log.e("getImage", e.toString());
        }

        return image;
    }
}
