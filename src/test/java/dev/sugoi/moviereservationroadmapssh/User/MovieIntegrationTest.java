package dev.sugoi.moviereservationroadmapssh.User;

import dev.sugoi.moviereservationroadmapssh.Movie.Genre;
import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @BeforeAll
    static void setUp(){
        postgres.start();
    }

    @AfterAll
    static void cleanUp(){
        postgres.stop();
    }


    @Test
    void everyoneCanGetAllMovieList() {
        ResponseEntity<String> response = testRestTemplate
                .getForEntity("/movies", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        //ToDo: Probably a good idea to check if we got the actual data as well
    }

    @Test
    void everyoneCanGetMovieById() {
        ResponseEntity<Movie> response = testRestTemplate
                .getForEntity("/movies/1", Movie.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DirtiesContext
    void adminCanAddMovie() {
        Movie testMovie = new Movie("Fight Club", "Guy with sleep problems starts to hallucinate"
                , Genre.THRILLER, List.of(), List.of());

        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("sugoi", "secret")
                .postForEntity("/movies", testMovie, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI uriOfCreatedMovie = response.getHeaders().getLocation();
        assertThat(uriOfCreatedMovie).isNotNull();

        ResponseEntity<Movie> getMovieResponse = testRestTemplate
                .getForEntity(uriOfCreatedMovie, Movie.class);

        assertThat(getMovieResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Movie insertedMovie = getMovieResponse.getBody();
        assertThat(insertedMovie)
                .isNotNull()
                .extracting(Movie::getTitle, Movie::getDescription, Movie::getGenre)
                .containsExactly("Fight Club", "Guy with sleep problems starts to hallucinate", Genre.THRILLER);

    }

    @Test
    @DirtiesContext
    void adminCanModifyMovie() {
        Movie testMovie = new Movie("Fight Club", "Guy with sleep problems starts to hallucinate"
                , Genre.THRILLER, List.of(), List.of());

        HttpEntity<Movie> movieHttpEntity = new HttpEntity<>(testMovie);
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("sugoi", "secret")
                .exchange("/movies/1", HttpMethod.PUT, movieHttpEntity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Get the movie
        ResponseEntity<Movie> getMovieResponse = testRestTemplate.getForEntity("/movies/1", Movie.class);
        assertThat(getMovieResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getMovieResponse.getBody())
                .isNotNull()
                .extracting(Movie::getTitle, Movie::getDescription, Movie::getGenre)
                .containsExactly("Fight Club", "Guy with sleep problems starts to hallucinate", Genre.THRILLER);
    }

    @Test
    @DirtiesContext
    void adminCanDeleteMovie() {
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("sugoi", "secret")
                .exchange("/movies/1", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void onlyAdminCanAddMovie() {

        Movie testMovie = new Movie("Fight Club", "Guy with sleep problems starts to hallucinate"
                , Genre.THRILLER, List.of(), List.of());

        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("not admin", "secret")
                .postForEntity("/movies", testMovie, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void onlyAdminsCanModifyMovie() {

        Movie testMovie = new Movie("Fight Club", "Guy with sleep problems starts to hallucinate"
                , Genre.THRILLER, List.of(), List.of());

        HttpEntity<Movie> movieHttpEntity = new HttpEntity<>(testMovie);
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("not admin", "secret")
                .exchange("/movies/1", HttpMethod.PUT, movieHttpEntity, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void onlyAdminsCanDeleteMovie() {
        ResponseEntity<Void> response = testRestTemplate
                .withBasicAuth("not admin", "secret")
                .exchange("/movies/1", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
