package com.example.moviesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.ViewGroup;
import com.example.moviesapp.movies.PosterMovieItem;
import com.example.moviesapp.movies.MoviesAdapter;
import com.example.moviesapp.movies.MoviesMVP;
import com.example.moviesapp.root.App;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/** A simple app that gets via Retrofit the first page of the top rated movies on IMDB
 *  and displays the posters of those results in a recycler view,
 *  getting them from another api with a lot of data about movies
 *  implementing, besides Retrofit, OkHttp, RxJava, Dagger 2, Picasso and Butter knife */

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    @BindView(R.id.main_activity_root_view)
    ViewGroup root;

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @Inject
    MoviesMVP.Presenter presenter;

    private MoviesAdapter adapter;
    private List<PosterMovieItem> posterMovieItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ((App)getApplication()).getComponent().inject(this);

        adapter = new MoviesAdapter(posterMovieItems);
        initializeRvMovies();
    }

    private void initializeRvMovies(){
        rvMovies.setAdapter(adapter);
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribeRxJava();
        posterMovieItems.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(PosterMovieItem posterMovieItem) {
        posterMovieItems.add(posterMovieItem);
        adapter.notifyItemInserted(posterMovieItems.size()-1);
    }

    @Override
    public void showSnackBar(int message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }
}
