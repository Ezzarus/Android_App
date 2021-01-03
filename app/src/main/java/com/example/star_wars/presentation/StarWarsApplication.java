package com.example.star_wars.presentation;

import android.app.Application;

import com.example.star_wars.data.di.FakeDependencyInjection;

public class StarWarsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FakeDependencyInjection.setContext(this);
    }
}
