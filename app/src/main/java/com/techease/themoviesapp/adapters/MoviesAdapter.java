package com.techease.themoviesapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.techease.themoviesapp.R;
import com.techease.themoviesapp.models.movieModel.Movie;
import com.techease.themoviesapp.utilities.GeneralUtils;
import com.techease.themoviesapp.views.fragments.MovieDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements Filterable {
    List<Movie> movieList;
    List<Movie> filterList;
    Context context;

    public MoviesAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.filterList = movieList;

    }


    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_movie_layout, parent, false);

        return new MoviesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MoviesAdapter.MyViewHolder viewHolder, final int position) {
        final Movie model = filterList.get(position);

        viewHolder.tvTitle.setText(model.getTitle());

        String poster = "https://image.tmdb.org/t/p/original" + filterList.get(position).getPosterPath();
        Glide.with(context)
                .load(poster)
                .into(viewHolder.ivPoster);

        viewHolder.movieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralUtils.putIntegerValueInEditor(context, "movie_id", model.getId());
                GeneralUtils.connectFragementWithBack(context, new MovieDetailFragment());
            }
        });


    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = movieList;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : movieList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout movieLayout;
        ImageView ivPoster;
        TextView tvTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            movieLayout = itemView.findViewById(R.id.movies_layout);

        }
    }
}
