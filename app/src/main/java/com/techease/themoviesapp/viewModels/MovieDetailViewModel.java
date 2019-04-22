package com.techease.themoviesapp.viewModels;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import java.util.Observable;

public class MovieDetailViewModel extends Observable {
    public final ObservableField<String> username = new ObservableField<>("");
    public final ObservableField<String> userpass = new ObservableField<>("");

    public MovieDetailViewModel(Context context) {
    }

}
