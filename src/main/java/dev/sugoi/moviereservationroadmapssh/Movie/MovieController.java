package dev.sugoi.moviereservationroadmapssh.Movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    private static final Logger log = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // ToDo: Need to add pagination for this end point
    @GetMapping()
    ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> listOfMovies = movieService.getAllMovies();
        log.info("get all movies endpoint used");
        return ResponseEntity.ok(listOfMovies);
    }

    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable Integer id) {
        log.info("get movie by id: {} used", id);
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping()
    ResponseEntity<Void> addMovie(@RequestBody Movie movie, UriComponentsBuilder ucb) {


        Movie movieToBeAdded = new Movie(movie.getTitle(), movie.getDescription(), movie.getGenre(), movie.getShowTimes(), List.of());

        movieService.addMovie(movieToBeAdded);
        URI location = ucb
                .path("movies/{id}")
                .buildAndExpand(movieToBeAdded.getid())
                .toUri();

        log.info("New movie added :{} ", movie.toString());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateMovie(@PathVariable Integer id, @RequestBody Movie newMovie) {
        log.info("trying to modify movie with id {}, new movie: {} ", id, newMovie.toString());
        movieService.modifyMovie(newMovie, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        log.info("deleting movie with id: {}", id);
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

}
