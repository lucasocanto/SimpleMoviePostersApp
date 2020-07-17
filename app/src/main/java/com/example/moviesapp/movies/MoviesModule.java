package com.example.moviesapp.movies;

import com.example.moviesapp.http.TopMoviesService;
import com.example.moviesapp.http.MoviesFullDataService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter providesMoviesPresenter(MoviesMVP.Model moviesModule){
        return new MoviesPresenter(moviesModule);
    }

    @Provides
    public MoviesMVP.Model providesMoviesModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Provides
    @Singleton
    public Repository providesMoviesRepository(TopMoviesService topMoviesService,
                                               MoviesFullDataService dataApiService){
        return new MoviesRepository(dataApiService, topMoviesService);
    }
}
