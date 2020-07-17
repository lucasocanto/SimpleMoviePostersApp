package com.example.moviesapp.movies;

import io.reactivex.rxjava3.core.Observable;

public class MoviesModel implements MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<PosterMovieItem> result() {
        return Observable.zip(repository.getTopMoviesData(),
                repository.getPostersData(),
                (result, poster) -> new PosterMovieItem(poster));
    }
}