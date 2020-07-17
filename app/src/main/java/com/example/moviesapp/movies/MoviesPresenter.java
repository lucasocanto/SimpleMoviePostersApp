package com.example.moviesapp.movies;

import com.example.moviesapp.R;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviesPresenter implements MoviesMVP.Presenter {

    private MoviesMVP.View view;
    private MoviesMVP.Model model;

    private Disposable subscription = null;

    public MoviesPresenter(MoviesMVP.Model model){
        this.model = model;
    }

    @Override
    public void loadData() {
        subscription = model.result()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<PosterMovieItem>() {

                    @Override
                    public void onNext(@NonNull PosterMovieItem posterMovieItem) {
                        if(view != null)
                            view.updateData(posterMovieItem);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        if(view != null)
                            view.showSnackBar(R.string.error_loading_movies);
                    }

                    @Override
                    public void onComplete() {
                        if (view != null)
                            view.showSnackBar(R.string.success_loading_movies);
                    }

                });
    }

    @Override
    public void unsubscribeRxJava() {
        if (subscription != null && !subscription.isDisposed()) subscription.dispose();
    }

    @Override
    public void setView(MoviesMVP.View view) {
        this.view = view;
    }
}
