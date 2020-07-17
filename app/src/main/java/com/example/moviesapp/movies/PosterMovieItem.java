package com.example.moviesapp.movies;

public class PosterMovieItem {

    private String posterData;

    public PosterMovieItem(String posterData) {
        this.posterData = posterData;
    }

    public String getPoster() {
        return posterData;
    }

    public void setPoster(String posterData) {
        this.posterData = posterData;
    }

}
