package com.example.moviesapp.http;

import com.example.moviesapp.http.apimodel.OmdbApi;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesFullDataService {

    @GET("/")
    Observable<OmdbApi> getMovieData(@Query("t") String title);
}
