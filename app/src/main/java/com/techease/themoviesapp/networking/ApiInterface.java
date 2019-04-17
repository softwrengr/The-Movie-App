package com.techease.themoviesapp.networking;

import com.techease.themoviesapp.models.MoviesResponse;
import com.techease.themoviesapp.models.allMoviesDetailsModels.MoviesDetailModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("movie/popular?")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/{version}?/")
    Call<MoviesDetailModel> getMovieDetail(@Path("version") int id,
                                           @Query("api_key") String apiKey);

}
