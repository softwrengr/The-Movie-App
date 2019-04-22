package com.techease.themoviesapp.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
