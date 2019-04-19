package com.techease.themoviesapp.classes;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.techease.themoviesapp.views.activities.MainActivity;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(LeakCanary.isInAnalyzerProcess(this)){

        }
        LeakCanary.install(this);
    }
}
