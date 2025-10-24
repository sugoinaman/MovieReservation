package dev.sugoi.moviereservationroadmapssh.User;

import dev.sugoi.moviereservationroadmapssh.Movie.Movie;
import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerTest {


    TestRestTemplate testRestTemplate;

    @Test
    void everyoneCanGetAllMovieList(){
        Response<List<Movie>> movieResponse = testRestTemplate.getForObject("/movies", String.class);
    }

    @Test
    void everyoneCanGetMovieById(){

    }

    @Test
    void adminCanAddMovie(){

    }

    @Test
    void adminCanModifyMovie(){

    }

    @Test
    void adminCanDeleteMovie(){

    }

    @Test
    void onlyAdminCanAddModifyAndDeleteMovie(){

    }
}
