package com.example.moviesapp.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<PosterMovieItem> posterMovieItems;

    public MoviesAdapter(List<PosterMovieItem> posterMovieItems) {
        this.posterMovieItems = posterMovieItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PosterMovieItem posterMovieItem = posterMovieItems.get(position);
        if (posterMovieItem != null) holder.onBindMovies(posterMovieItem);
    }

    @Override
    public int getItemCount() {
        return posterMovieItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_movie)
        public AppCompatImageView movieImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void onBindMovies(PosterMovieItem posterMovieItem) {
            Picasso.get().load(posterMovieItem.getPoster()).into(movieImage);
        }
    }
}