package com.example.moviesapp.http;

import com.example.moviesapp.http.apimodel.TopRatedMovies;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TopMoviesService {

    @GET("top_rated")
    Observable<TopRatedMovies> getTopMovies(@Query("page") Integer page);
}