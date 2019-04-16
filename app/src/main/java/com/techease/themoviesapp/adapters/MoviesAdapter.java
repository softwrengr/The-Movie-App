package com.techease.themoviesapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.techease.themoviesapp.R;
import com.techease.themoviesapp.models.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    List<Movie> movieList;
    Context context;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;

    }


    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_movie_layout, parent, false);

        return new MoviesAdapter.MyViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        final Movie model = movieList.get(position);
        if (movieList != null && movieList.size() > position)
            return movieList.size();
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapter.MyViewHolder viewHolder, final int position) {
        final Movie model = movieList.get(position);

        String poster = "https://image.tmdb.org/t/p/w500" + movieList.get(position).getPosterPath();

        Glide.with(context)
                .load(poster)
                .into(viewHolder.ivPoster);


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.iv_poster);

        }
    }
}