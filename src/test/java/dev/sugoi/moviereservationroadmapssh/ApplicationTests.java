package dev.sugoi.moviereservationroadmapssh;

import dev.sugoi.moviereservationroadmapssh.User.Priveleges;
import dev.sugoi.moviereservationroadmapssh.User.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void shouldCreateANewUser() {
        User testUser = new User(null, "sugoi", "sugoi571@gmail.com", "secret", Priveleges.ADMIN);
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/user/signup", testUser, Void.class);
        System.out.println("lol" + responseEntity);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI locationOfNewUser = responseEntity.getHeaders().getLocation();
        System.out.println(locationOfNewUser);
    }
}
