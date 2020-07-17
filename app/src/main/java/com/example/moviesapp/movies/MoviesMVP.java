package com.example.moviesapp.movies;

import io.reactivex.rxjava3.core.Observable;

public interface MoviesMVP {

    interface View {
        void updateData(PosterMovieItem posterMovieItem);
        void showSnackBar(int message);
    }

    interface Presenter {
        void loadData();
        void unsubscribeRxJava();
        void setView(MoviesMVP.View view);
    }

    interface Model {
     Observable<PosterMovieItem> result();
    }
}