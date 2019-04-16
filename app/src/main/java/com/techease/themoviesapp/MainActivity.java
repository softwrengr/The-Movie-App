package com.techease.themoviesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.themoviesapp.utilities.GeneralUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeneralUtils.connectFragmentWithoutBack(this,new MoviesFragment());
    }
}
