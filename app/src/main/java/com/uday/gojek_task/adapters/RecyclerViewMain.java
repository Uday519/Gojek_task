package com.uday.gojek_task.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.uday.gojek_task.PicassoCache;
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
        recyclerViewHolder.author_name.setText(trendingList.get(i).getAuthor());
        recyclerViewHolder.description.setText(trendingList.get(i).getName());
        String imageUrl = trendingList.get(i).getAvatar();
        ImageView imageView = recyclerViewHolder.avatar;
//        ImageLoader imageLoader = ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context.getApplicationInfo()));
//        imageLoader.displayImage(imageUrl, imageView);


//        Picasso.with(context).load(imageUrl)
//                .fit()
//                .centerCrop()
//                .into(imageView,
//                        new PicassoCache(imageView, context)
//                );
        Picasso.with(context).load(trendingList.get(i).getAvatar()).fit().centerCrop().into(recyclerViewHolder.avatar);
//        PicassoCache.getPicassoInstance(context).load(trendingList.get(i).getAvatar()).fit().into(recyclerViewHolder.avatar);
//        Picasso.with(context)
//                .load(imageUrl)
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .into(recyclerViewHolder.avatar, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        int k =0;
//                        Log.d("Picasso","bu");
//                    }
//
//                    @Override
//                    public void onError() {
//                        //Try again online if cache failed
//                        Picasso.with(context)
//                                .load(imageUrl)
//                                .into(imageView, new Callback() {
//                                    @Override
//                                    public void onSuccess() {
//                                    }
//
//                                    @Override
//                                    public void onError() {
//                                        Log.v("Picasso","Could not fetch image");
//                                    }
//                                });
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author_name;
        TextView description;
        ImageView avatar;
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
            this.author_name = itemView.findViewById(R.id.github_author);
            this.description = itemView.findViewById(R.id.github_description);
            this.avatar = itemView.findViewById(R.id.github_avatar);

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


