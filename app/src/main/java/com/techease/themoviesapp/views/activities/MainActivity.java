package com.techease.themoviesapp.views.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.techease.themoviesapp.interfaces.FragmentActionListener;
import com.techease.themoviesapp.views.fragments.MovieDetailFragment;
import com.techease.themoviesapp.views.fragments.MoviesFragment;
import com.techease.themoviesapp.R;
import com.techease.themoviesapp.utilities.GeneralUtils;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AppCompatActivity) this).getSupportActionBar().hide();

        if (savedInstanceState == null) {
            GeneralUtils.connectFragmentWithoutBack(this, new MoviesFragment());
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


}
