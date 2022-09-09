package com.borak.movieApp.service;

import com.borak.movieApp.domain.Movie;

import java.util.List;

public interface MovieService {

    Movie saveMovie(Movie movie);

    Movie getMovie(String name);

    Movie updateMovie(Movie movie);

    List<Movie> getMovies(int pageNo, int pageSize, String filter);

    void deleteMovieById(Long id);

    void deleteMovieByName(String name);

    Movie bindCategoryToMovie(String movieName, String categoryName);
}
