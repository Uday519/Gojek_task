package com.uday.gojek_task.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uday.gojek_task.NetworkDetection;
import com.uday.gojek_task.R;
import com.uday.gojek_task.models.GithubTrending;

import java.net.URI;
import java.util.List;

public class RecyclerViewMain extends RecyclerView.Adapter<RecyclerViewMain.RecyclerViewHolder> {
    List<GithubTrending> trendingList;
    Context context;
    LayoutInflater inflater;

    public RecyclerViewMain(List<GithubTrending> trendingList, Context context) {
        this.trendingList = trendingList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setTrendingList(List<GithubTrending> trendingList) {
        this.trendingList = trendingList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.github_trending_repo_row,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        GradientDrawable circular_image =(GradientDrawable) recyclerViewHolder.languageColor.getBackground();
        recyclerViewHolder.author.setText(trendingList.get(i).getAuthor() == null ? "" : trendingList.get(i).getAuthor());
        recyclerViewHolder.description.setText(trendingList.get(i).getDescription() == null ? "" : trendingList.get(i).getDescription());
        recyclerViewHolder.name.setText(trendingList.get(i).getName() == null ? "" : trendingList.get(i).getName());
        circular_image.setColor(Color.parseColor(trendingList.get(i).getLanguageColor() == null ? "#ffffff" : trendingList.get(i).getLanguageColor()));
        recyclerViewHolder.language.setText(trendingList.get(i).getLanguage() == null ? "" : trendingList.get(i).getLanguage());
        recyclerViewHolder.stars.setText(trendingList.get(i).getStars() == null ? "" : trendingList.get(i).getStars());
        recyclerViewHolder.forks.setText(trendingList.get(i).getForks() == null ? "" : trendingList.get(i).getForks());
        byte[] image_bytes = trendingList.get(i).getImage_bytes().getImages();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image_bytes, 0, image_bytes.length);

        recyclerViewHolder.avatar.setImageBitmap(bitmap);

//        final String image_url = trendingList.get(i).getAvatar();
//        final ImageView image_holder = recyclerViewHolder.avatar;
//        Picasso.with(context).load(Uri.parse(image_url))
//                .fit().centerCrop()
//                .networkPolicy(NetworkPolicy.OFFLINE)
//                .into(image_holder, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                    }
//
//                    @Override
//                    public void onError() {
//                        Picasso.with(context).load(Uri.parse(image_url))
//                                .fit().centerCrop()
//                                .into(image_holder);
//                    }
//                });

        boolean is_expanded = trendingList.get(i).isExpanded();
        recyclerViewHolder.collapsible_layout.setVisibility(is_expanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView name;
        ImageView avatar;
        TextView description;
        TextView language;
        ImageView languageColor;
        TextView stars;
        TextView forks;
        RelativeLayout collapsible_layout;
        RelativeLayout parent_layout;

        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.author = itemView.findViewById(R.id.github_author);
            this.description = itemView.findViewById(R.id.github_description);
            this.avatar = itemView.findViewById(R.id.github_avatar);
            this.name = itemView.findViewById(R.id.github_name);
            this.language = itemView.findViewById(R.id.language);
            this.languageColor = itemView.findViewById(R.id.language_color);
            this.stars = itemView.findViewById(R.id.github_stars);
            this.forks = itemView.findViewById(R.id.forks);
            this.collapsible_layout = itemView.findViewById(R.id.collapsible_layout);
            this.parent_layout = itemView.findViewById(R.id.retalivelayout_item);


            this.parent_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current_position = getAdapterPosition();
                    for(int i=0; i<trendingList.size(); i++){
                        if(trendingList.get(i).isExpanded() && current_position != i){
                            GithubTrending githubTrending_previous = trendingList.get(i);
                            githubTrending_previous.setExpanded(false);
                            notifyItemChanged(i);
                            break;
                        }
                    }
                    GithubTrending githubTrending_current = trendingList.get(current_position);
                    githubTrending_current.setExpanded(!githubTrending_current.isExpanded());
                    notifyItemChanged(current_position);
                }
            });

        }

    }

}


