package dev.sugoi.moviereservationroadmapssh.Movie;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // get all movies

    List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // add a movie
    Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // get movie by ID
    Optional<Movie> getMovieById(Integer id) {
        return movieRepository.findById(id);
    }

    // Modify a movie details?

    Movie modifyMovie(Movie newMovie, Integer id) {

        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setDescription(newMovie.getDescription());
                    movie.setGenre(newMovie.getGenre());
                    movie.setShowTimes(newMovie.getShowTimes());
                    return movieRepository.save(movie);
                })
                .orElseGet(() -> {
                    return movieRepository.save(newMovie);
                });
    }

    // Delete movie by ID
    private void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

}
