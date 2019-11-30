package com.uday.gojek_task;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.uday.gojek_task.adapters.RecyclerViewMain;
import com.uday.gojek_task.models.GithubTrending;
import com.uday.gojek_task.models.GithubTrendingList;
import com.uday.gojek_task.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewMain.OnRowClick {

    private MainActivityViewModel mViewModel;
    private RecyclerView rv;
    private RecyclerViewMain recycleAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_retry;
    private RelativeLayout layout_nonetwork;
    List<GithubTrending> trendingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.github_trending_repo);

        rv = findViewById(R.id.github_recyclerview);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        btn_retry = findViewById(R.id.retry);
        layout_nonetwork = findViewById(R.id.no_network);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getTrendinglist().observe(this, new Observer<List<GithubTrending>>() {
            @Override
            public void onChanged(@Nullable List<GithubTrending> githubTrendingList) {
                trendingList = githubTrendingList;
                if (trendingList.size() > 0 ) {
                    swipeRefreshLayout.setRefreshing(false);
                    initRecyclerview(trendingList);
                }
                else {
                    layout_nonetwork.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        mViewModel.getShow_networkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean ) {
                    layout_nonetwork.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                }
                else {
                    layout_nonetwork.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.retry();
            }
        });

        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_nonetwork.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                mViewModel.retry();
            }
        });




    }

    public void initRecyclerview(List<GithubTrending> trendingList){
        rv.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecyclerViewMain(trendingList,this,this);
        rv.setAdapter(recycleAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onRowClick(int index) {
    }
}
