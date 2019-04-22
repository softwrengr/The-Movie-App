package com.techease.themoviesapp.networking;

import com.techease.themoviesapp.models.movieModel.MoviesResponse;
import com.techease.themoviesapp.models.allMoviesDetailsModels.MoviesDetailModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiServices {

    @GET("movie/{version}?")
    Call<MoviesResponse> getPopularMovies(@Path("version") String category,
                                          @Query("api_key") String apiKey);

    @GET("movie/{version}?/")
    Call<MoviesDetailModel> getMovieDetail(@Path("version") int id,
                                           @Query("api_key") String apiKey);

}
