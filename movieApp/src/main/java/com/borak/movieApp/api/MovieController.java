package com.borak.movieApp.api;

import com.borak.movieApp.domain.Movie;
import com.borak.movieApp.domain.MovieCategoryDTO;
import com.borak.movieApp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/get/{pageNo}/{pageSize}/{filter}")
    public List<Movie> getMovies(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String filter) {
        return movieService.getMovies(pageNo, pageSize, filter);
    }

    @PostMapping("/save")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/movie/save").toUriString());
        return ResponseEntity.created(uri).body(movieService.saveMovie(movie));
    }

    @PutMapping("/update")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/movie/update").toUriString());
        return ResponseEntity.created(uri).body(movieService.updateMovie(movie));
    }

    @PostMapping("/bindCategory")
    public ResponseEntity<?> bindCategoryToMovie(@RequestBody MovieCategoryDTO form) {

        movieService.bindCategoryToMovie(form.getMovieName(), form.getCategoryName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovieById(@RequestParam Long id) {

        movieService.deleteMovieById(id);
    }

    @DeleteMapping("/delete/{name}")
    public void deleteMovieByName(@RequestParam String name) {

        movieService.deleteMovieByName(name);
    }
}
