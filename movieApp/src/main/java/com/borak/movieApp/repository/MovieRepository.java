package com.borak.movieApp.repository;

import com.borak.movieApp.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findByName(String name);
    Page<Movie> findByNameContaining(String name, Pageable pageable);
}
