package com.borak.movieApp.service;

import com.borak.movieApp.domain.Category;
import com.borak.movieApp.domain.Movie;
import com.borak.movieApp.repository.CategoryRepository;
import com.borak.movieApp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Movie saveMovie(Movie movie) {
        try {
            if (movieRepository.findByName(movie.getName()) == null) {
                return movieRepository.save(movie);
            }

        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
        log.error("Category {} already exists in database" + movie.getName());
        return null;
    }

    @Override
    public Movie getMovie(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        try {

            if (movieRepository.findById(movie.getId()).isPresent()) {
                return movieRepository.save(movie);
            }

        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
        log.info("There is no movie with this id in database");
        return null;
    }

    @Override
    public List<Movie> getMovies(int pageNo, int pageSize, String filter) {


        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Movie> paginated = movieRepository.findByNameContaining(filter, paging);
        return paginated.toList();

    }

    @Override
    public void deleteMovieById(Long id) {

        try {
            movieRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
    }

    @Override
    public void deleteMovieByName(String name) {

        try {
            Movie movie = movieRepository.findByName(name);
            movieRepository.delete(movie);
        } catch (Exception e) {
            log.error("Something gone wrong: " + e);
        }
    }

    @Override
    public Movie bindCategoryToMovie(String movieName, String categoryName) {

        Category category = categoryRepository.findByName(categoryName);
        Movie movie = movieRepository.findByName(movieName);

        movie.getCategories().add(category);
        return movieRepository.save(movie);
    }
}
