package com.techease.themoviesapp.networking;

import com.techease.themoviesapp.models.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("movie/popular?")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

}
