package com.uday.gojek_task.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        recyclerViewHolder.author_name.setText(trendingList.get(i).getAuthor()+" / "+trendingList.get(i).getName());
        recyclerViewHolder.current_period_stars.setText(trendingList.get(i).getCurrentPeriodStars());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author_name;
        TextView current_period_stars;
//        TextView employee_category_id;
//        TextView employee_category;
//        TextView employee_address;
//        TextView employee_code;
//        TextView employee_contact;
//        TextView employee_id_description;
//        ImageView employee_image;

        OnRowClick onRowClick;

        public RecyclerViewHolder(@NonNull View itemView, OnRowClick onEmployeeListner) {
            super(itemView);
            this.author_name = itemView.findViewById(R.id.author_name);
            this.current_period_stars = itemView.findViewById(R.id.current_period_stars);
//            this.employee_category_id = itemView.findViewById(R.id.employee_category_id);
//            this.employee_category = itemView.findViewById(R.id.employee_category);
//            this.employee_address = itemView.findViewById(R.id.employee_address);
//            this.employee_code = itemView.findViewById(R.id.employee_code);
//            this.employee_contact = itemView.findViewById(R.id.employee_contact);
//            this.employee_id_description = itemView.findViewById(R.id.employee_id_description);
//            this.employee_image = itemView.findViewById(R.id.employee_image);

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
