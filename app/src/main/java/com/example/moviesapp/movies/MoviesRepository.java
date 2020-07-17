package com.example.moviesapp.movies;

import com.example.moviesapp.http.TopMoviesService;
import com.example.moviesapp.http.MoviesFullDataService;
import com.example.moviesapp.http.apimodel.OmdbApi;
import com.example.moviesapp.http.apimodel.Result;
import com.example.moviesapp.http.apimodel.TopRatedMovies;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;

public class MoviesRepository implements Repository {

    private TopMoviesService topMoviesService;
    private MoviesFullDataService dataApiService;

    private List<String> posters;
    private List<Result> movies;

    private long lastTimestamp;

    private static final long CACHE_LIFETIME = 20 * 1000;

    public MoviesRepository(MoviesFullDataService dataApiService, TopMoviesService topMoviesService){
        this.dataApiService = dataApiService;
        this.topMoviesService = topMoviesService;

        this.posters = new ArrayList<>();
        this.movies = new ArrayList<>();

        this.lastTimestamp = System.currentTimeMillis();
    }

    private boolean isUpdated(){
        return (System.currentTimeMillis() - lastTimestamp) < CACHE_LIFETIME;
    }

    @Override
    public Observable<String> getPostersData() {
        return getPostersFromCache().switchIfEmpty(getPostersFromNetwork());
    }

    @Override
    public Observable<String> getPostersFromNetwork() {
        return getMoviesFromNetwork().concatMap(((Function<Result, ObservableSource<OmdbApi>>)
                result -> dataApiService.getMovieData(result.getTitle())))
                .concatMap(omdbApi -> {
                    if (omdbApi == null || omdbApi.getPoster() == null) return Observable.just("");
                     else return Observable.just(omdbApi.getPoster());
                });
    }

    @Override
    public Observable<String> getPostersFromCache() {
        if (isUpdated()) return Observable.fromIterable(posters);
        else {
            lastTimestamp = System.currentTimeMillis();
            posters.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<Result> getTopMoviesData() {
        return getMoviesFromCache().switchIfEmpty(getMoviesFromNetwork());
    }

    @Override
    public Observable<Result> getMoviesFromNetwork() {
        Observable<TopRatedMovies> topMoviesApiObservable = topMoviesService.getTopMovies(1);
        return topMoviesApiObservable.concatMap(topRatedMovies ->
                Observable.fromIterable(topRatedMovies.getResults()))
                .doOnNext( result -> movies.add(result));
    }

    @Override
    public Observable<Result> getMoviesFromCache() {
        if (isUpdated()) return Observable.fromIterable(movies);
        else {
            lastTimestamp = System.currentTimeMillis();
            movies.clear();
            return Observable.empty();
        }
    }
}