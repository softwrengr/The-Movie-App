package com.techease.themoviesapp.views.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.techease.themoviesapp.R;
import com.techease.themoviesapp.adapters.MoviesAdapter;
import com.techease.themoviesapp.models.movieModel.Movie;
import com.techease.themoviesapp.models.movieModel.MoviesResponse;
import com.techease.themoviesapp.networking.ApiClient;
import com.techease.themoviesapp.networking.ApiServices;
import com.techease.themoviesapp.utilities.AlertUtils;
import com.techease.themoviesapp.utilities.Configuration;
import com.techease.themoviesapp.utilities.GeneralUtils;
import com.techease.themoviesapp.utilities.NetworkUtilities;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


public class MoviesFragment extends Fragment {

    AlertDialog alertDialog;
    View view;
    @BindView(R.id.iv_filter)
    ImageView ivFilter;
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

        if (NetworkUtilities.isNetworkConnected(getActivity())) {
            initUI("popular");
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void initUI(String movie) {
        ButterKnife.bind(this, view);

        int mNoOfColumns = GeneralUtils.calculateNoOfColumns(getActivity(), 120);
        layoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
        gvWallpapers.setLayoutManager(layoutManager);
        movieList = new ArrayList<>();
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        apiCallGetMovies(movie);

        searchMovies();

    }

    private void apiCallGetMovies(String string) {
        ApiServices services = ApiClient.getApiClient().create(ApiServices.class);
        final Call<MoviesResponse> allUsers = services.getPopularMovies(string, Configuration.API_TOKEN);
        allUsers.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                alertDialog.dismiss();
                if (response.body() == null) {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Timber.e(e.getMessage());
                    }

                } else {
                    movieList.addAll(response.body().getResults());
                    moviesAdapter = new MoviesAdapter(getActivity(), movieList);
                    gvWallpapers.setAdapter(moviesAdapter);
                    moviesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                alertDialog.dismiss();
                Timber.e(t.getMessage());
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

        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDownMenu(ivFilter);
            }
        });


    }

    private void showDropDownMenu(ImageView filter) {

        PopupMenu popup = new PopupMenu(getActivity(), filter);
        popup.getMenuInflater()
                .inflate(R.menu.filter_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.top_rated:
                        initUI("top_rated");
                        break;
                    case R.id.popuar:
                        initUI("popular");
                        break;
                    case R.id.coming:
                        initUI("upcoming");
                        break;
                }
                return true;
            }
        });

        popup.show();
    }


}
