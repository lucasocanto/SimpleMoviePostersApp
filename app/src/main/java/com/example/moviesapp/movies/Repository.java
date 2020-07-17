package com.example.moviesapp.movies;

import com.example.moviesapp.http.apimodel.Result;

import io.reactivex.rxjava3.core.Observable;

public interface Repository {

    Observable<String> getPostersData();
    Observable<String> getPostersFromNetwork();
    Observable<String> getPostersFromCache();

    Observable<Result> getTopMoviesData();
    Observable<Result> getMoviesFromNetwork();
    Observable<Result> getMoviesFromCache();
}
