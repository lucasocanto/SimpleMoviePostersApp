package com.example.moviesapp.root;

import com.example.moviesapp.MainActivity;
import com.example.moviesapp.http.MoviesFullDataModule;
import com.example.moviesapp.http.TopMoviesModule;
import com.example.moviesapp.movies.MoviesModule;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, TopMoviesModule.class, MoviesFullDataModule.class, MoviesModule.class})
public interface ApplicationComponent {

    void inject(MainActivity target);
}