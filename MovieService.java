
package com.cineverse.cineverse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cineverse.cineverse.entity.Movie;
import com.cineverse.cineverse.repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    // Constructor Injection
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // Add Movie
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Get All Movies
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Get Movie By ID
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    // Update Movie
    public Movie updateMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // Delete Movie
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
