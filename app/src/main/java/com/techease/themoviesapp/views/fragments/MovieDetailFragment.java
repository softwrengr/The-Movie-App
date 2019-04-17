package com.techease.themoviesapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techease.themoviesapp.R;
import com.techease.themoviesapp.models.MoviesResponse;
import com.techease.themoviesapp.models.allMoviesDetailsModels.MoviesDetailModel;
import com.techease.themoviesapp.networking.ApiClient;
import com.techease.themoviesapp.networking.ApiInterface;
import com.techease.themoviesapp.utilities.Configuration;
import com.techease.themoviesapp.utilities.GeneralUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailFragment extends Fragment {
    AlertDialog alertDialog;
    View view;
    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_overview)
    TextView tvOverView;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        initViews();
        return view;
    }

    private void initViews() {
        ButterKnife.bind(this, view);
        apiCallGetMovies();

    }

    private void apiCallGetMovies() {

        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        Call<MoviesDetailModel> allUsers = services.getMovieDetail(GeneralUtils.getMovieID(getActivity()), Configuration.API_TOKEN);
        allUsers.enqueue(new Callback<MoviesDetailModel>() {
            @Override
            public void onResponse(Call<MoviesDetailModel> call, Response<MoviesDetailModel> response) {
                //   alertDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    tvMovieTitle.setText(response.body().getOriginalTitle());
                    tvOverView.setText(response.body().getOverview());
                    tvLanguage.setText(response.body().getSpokenLanguages().get(0).getName());
                    tvReleaseDate.setText(response.body().getReleaseDate());

                    for(int i=0;i<response.body().getGenres().size();i++){
                        Toast.makeText(getActivity(), response.body().getGenres().get(i).getName(), Toast.LENGTH_SHORT).show();
                    }


                    String poster = "https://image.tmdb.org/t/p/w500" + response.body().getPosterPath();
                    Glide.with(getActivity())
                            .load(poster)
                            .into(ivMoviePoster);
                }
            }

            @Override
            public void onFailure(Call<MoviesDetailModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
