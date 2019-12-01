package com.uday.gojek_task.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uday.gojek_task.R;
import com.uday.gojek_task.models.GithubTrending;

import java.util.List;

public class RecyclerViewMain extends RecyclerView.Adapter<RecyclerViewMain.RecyclerViewHolder> {
    List<GithubTrending> trendingList;
    Context context;
    LayoutInflater inflater;
    private OnRowClick onRowClick;

    public RecyclerViewMain(List<GithubTrending> trendingList, Context context, OnRowClick onRowClick) {
        this.trendingList = trendingList;
        this.context = context;
        this.onRowClick = onRowClick;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTrendingList(List<GithubTrending> trendingList) {
        this.trendingList = trendingList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.github_trending_repo_row,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, onRowClick);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.author.setText(trendingList.get(i).getAuthor());
        recyclerViewHolder.description.setText(trendingList.get(i).getDescription());
        recyclerViewHolder.name.setText(trendingList.get(i).getName());
        recyclerViewHolder.languageColor.setText(trendingList.get(i).getLanguageColor());
        recyclerViewHolder.language.setText(trendingList.get(i).getLanguage());
        recyclerViewHolder.stars.setText(trendingList.get(i).getStars());
        recyclerViewHolder.forks.setText(trendingList.get(i).getForks());
        Picasso.with(context).load(trendingList.get(i).getAvatar()).fit().centerCrop().into(recyclerViewHolder.avatar);

    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author;
        TextView name;
        ImageView avatar;
        TextView description;
        TextView language;
        TextView languageColor;
        TextView stars;
        TextView forks;


        OnRowClick onRowClick;

        public RecyclerViewHolder(@NonNull View itemView, OnRowClick onEmployeeListner) {
            super(itemView);
            this.author = itemView.findViewById(R.id.github_author);
            this.description = itemView.findViewById(R.id.github_description);
            this.avatar = itemView.findViewById(R.id.github_avatar);
            this.name = itemView.findViewById(R.id.github_name);
            this.language = itemView.findViewById(R.id.language);
            this.languageColor = itemView.findViewById(R.id.language_color);
            this.stars = itemView.findViewById(R.id.github_stars);
            this.forks = itemView.findViewById(R.id.forks);

            this.onRowClick = onEmployeeListner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRowClick.onRowClick(getAdapterPosition());
        }
    }

    public interface OnRowClick{
        void onRowClick(int index);
    }

}


