package com.example.moviesapp.root;

import android.app.Application;

import com.example.moviesapp.http.TopMoviesModule;
import com.example.moviesapp.http.MoviesFullDataModule;
import com.example.moviesapp.movies.MoviesModule;

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate(){
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .topMoviesModule(new TopMoviesModule())
                .moviesFullDataModule(new MoviesFullDataModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
