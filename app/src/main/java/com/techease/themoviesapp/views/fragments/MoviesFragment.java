package com.techease.themoviesapp.views.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.techease.themoviesapp.R;
import com.techease.themoviesapp.adapters.MoviesAdapter;
import com.techease.themoviesapp.models.Movie;
import com.techease.themoviesapp.models.MoviesResponse;
import com.techease.themoviesapp.networking.ApiClient;
import com.techease.themoviesapp.networking.ApiInterface;
import com.techease.themoviesapp.utilities.AlertUtils;
import com.techease.themoviesapp.utilities.Configuration;
import com.techease.themoviesapp.utilities.GeneralUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends Fragment {

    AlertDialog alertDialog;
    View view;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_movies)
    RecyclerView gvWallpapers;
    MoviesAdapter moviesAdapter;
    public List<Movie> movieList;
    GridLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movies, container, false);
        initUI();
        return view;
    }


    private void initUI() {
        ButterKnife.bind(this, view);

        int mNoOfColumns = GeneralUtils.calculateNoOfColumns(getActivity(), 120);
        layoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
        gvWallpapers.setLayoutManager(layoutManager);
        movieList = new ArrayList<>();
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        moviesAdapter = new MoviesAdapter(getActivity(), movieList);
        gvWallpapers.setAdapter(moviesAdapter);
        apiCallGetMovies();

        searchMovies();

    }

    private void apiCallGetMovies() {
        ApiInterface services = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<MoviesResponse> allUsers = services.getPopularMovies(Configuration.API_TOKEN);
        allUsers.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                alertDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    movieList.addAll(response.body().getResults());
                    moviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                moviesAdapter.getFilter().filter(etSearch.getText().toString());
            }
        });
    }


}
