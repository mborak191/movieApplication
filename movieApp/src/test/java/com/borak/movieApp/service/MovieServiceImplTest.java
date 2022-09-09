package com.borak.movieApp.service;

import com.borak.movieApp.domain.Category;
import com.borak.movieApp.domain.Movie;
import com.borak.movieApp.repository.CategoryRepository;
import com.borak.movieApp.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@SpringBootTest
public class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void saveMovie() {

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setName("American pie");
        movie.setCategories(new ArrayList<>());

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);

        assertEquals((movie), movieService.saveMovie(movie));

    }

    @Test
    public void itShouldThrowNullPointerExceptionWhenMovieNameIsNull() {

        Movie movie = new Movie(null, null, null);
        Mockito.when(movieRepository.save(movie)).thenThrow(NullPointerException.class);

        assertNull(movieService.saveMovie(movie));
    }

    @Test
    public void getMovie() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Comedy"));
        Movie movie = new Movie(1L, "American pie", categories);

        Mockito.when(movieRepository.findByName(movie.getName())).thenReturn(movie);

        assertEquals((movie), movieService.getMovie(movie.getName()));
    }

    @Test
    public void updateMovie() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Comedy"));

        Movie oldMovie = new Movie(1L, "American pie", categories);
        Movie replacingMovie = new Movie(1L, "Meet the spartans", categories);

        Mockito.when(movieRepository.findById(replacingMovie.getId())).thenReturn(Optional.of(oldMovie));
        Mockito.when(movieRepository.save(replacingMovie)).thenReturn(replacingMovie);

        assertEquals((replacingMovie), movieService.updateMovie(replacingMovie));
    }

    @Test
    public void updateMoiveThrowsNull() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Comedy"));
        Movie oldMovie = new Movie(1L, "Spyder's nest", categories);
        Movie replacingMovie = new Movie(null, "Inferno", categories);

        Mockito.when(movieRepository.findById(replacingMovie.getId())).thenThrow(NullPointerException.class);

        assertEquals((null), movieService.updateMovie(replacingMovie));

    }

    @Test
    public void getMovies() {

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Action"));
        String filter = "a";

        Pageable paging = PageRequest.of(1, 3);
        List<Movie> movies = new ArrayList<>();

        movies.add(new Movie((long) 1, "Lock, Stock and Two Smoking Barrels", categories));
        movies.add(new Movie((long) 2, "Snatch", categories));
        movies.add(new Movie((long) 3, "RocknRolla", categories));

        Page<Movie> pageMovie = new PageImpl<>(movies, paging, movies.size());

        Mockito.when(movieRepository.findByNameContaining(filter, paging)).thenReturn(pageMovie);

        assertEquals((pageMovie.toList()), movieService.getMovies(1, 3, filter));
    }

    @Test
    public void deleteMovieById() {

        Movie movie = new Movie(1L, "Snatch", new ArrayList<>());

        doNothing().when(movieRepository).deleteById(movie.getId());
        movieService.deleteMovieById(movie.getId());
        Mockito.verify(movieRepository, times(1)).deleteById(movie.getId());

    }

    @Test
    public void deleteMovieByName() {

        Movie movie = new Movie(null, "Snatch", new ArrayList<>());

        String movieName = "Snatch";
        Mockito.when(movieRepository.findByName(movieName)).thenReturn(movie);

        doNothing().when(movieRepository).delete(movie);
        movieService.deleteMovieByName(movieName);
        Mockito.verify(movieRepository, times(1)).delete(movie);

    }

    @Test
    public void bindCategoryToMovie() {

        Category category = new Category(1L, "Comedy");
        Movie movie = new Movie(1L, "Half brother", new ArrayList<>());

        Movie movieWithBindedCategory = new Movie(1L, "Half brother", new ArrayList<>());
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        movieWithBindedCategory.setCategories(categories);

        Mockito.when(movieRepository.findByName(movie.getName())).thenReturn(movie);
        Mockito.when(categoryRepository.findByName(category.getName())).thenReturn(category);
        Mockito.when(movieRepository.save(movie)).thenReturn(movieWithBindedCategory);

        assertEquals((movieWithBindedCategory), movieService.bindCategoryToMovie(movie.getName(), category.getName()));

    }
}
