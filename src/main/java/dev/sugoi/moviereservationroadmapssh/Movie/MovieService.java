package dev.sugoi.moviereservationroadmapssh.Movie;

import dev.sugoi.moviereservationroadmapssh.Exceptions.MovieNotFoundException;
import dev.sugoi.moviereservationroadmapssh.Security.Annotation.IsAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie with id: " + " not found" + id));
    }

    @IsAdmin
    Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @IsAdmin
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

    @IsAdmin
     void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

}
