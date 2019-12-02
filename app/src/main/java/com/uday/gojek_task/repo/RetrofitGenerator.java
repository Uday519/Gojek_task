package com.uday.gojek_task.repo;

import android.util.Log;

import com.uday.gojek_task.NetworkDetection;
import com.uday.gojek_task.interfaces.Api;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenerator {

    private static final String TAG = "RetrofitGenerator";
    private static final String BASE_URL = "https://github-trending-api.now.sh";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    private static RetrofitGenerator instance;

    public static RetrofitGenerator getInstance(){
        if(instance == null){
            instance = new RetrofitGenerator();
        }
        return instance;
    }

    private static final long cacheSize = 5 * 1024 * 1024;


    private static Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor())
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor())
                .build();
    }

    private static Cache cache(){
        return new Cache(new File(NetworkDetection.getInstance().getCacheDir(),"github_repo"), cacheSize);
    }


    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                if(!NetworkDetection.hasInternet()){
                    Log.d(TAG, "offline interceptor: called.");
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(2, TimeUnit.HOURS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Log.d(TAG, "network interceptor: called.");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2, TimeUnit.HOURS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor ()
    {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor( new HttpLoggingInterceptor.Logger()
                {
                    @Override
                    public void log (@NotNull String message)
                    {
                        Log.d(TAG, "log: http log: " + message);
                    }
                } );
        httpLoggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public static Api getApi(){
        return retrofit().create(Api.class);
    }

}
