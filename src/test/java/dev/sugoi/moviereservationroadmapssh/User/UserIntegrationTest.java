package dev.sugoi.moviereservationroadmapssh.User;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// ToDo: tests run but when user is not authenticated it throw
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers

class UserIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @BeforeAll
    static void setUp() {
        postgres.start();
    }
    @AfterAll
    static void cleanUp() {
        postgres.stop();
    }

    @Test
    void userCanAccessHimself() {
        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .getForEntity("/users/1", User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User responseUser = response.getBody();
        assertThat(responseUser.getUserName()).isNotNull().isEqualTo("john_doe");
    }

    @Test
    @DirtiesContext
    void userCanUpdateHimself() {
        User newUser = new User("not_john_doe", "ooga@gmail.com", "ooga", Role.USER, List.of());
        HttpEntity<User> request = new HttpEntity<>(newUser);

        ResponseEntity<String> postResponse = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .exchange("/users/1", HttpMethod.PUT, request, String.class);
        // the normal put method doesn't let us check the status code apparently
        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("not_john_doe", "ooga")
                .getForEntity("/users/1", User.class);

        User responseUser = response.getBody();
        System.out.println(responseUser);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


        assertThat(responseUser.getUserName()).isNotNull().isEqualTo("not_john_doe");
        assertThat(responseUser.getEmail()).isNotNull().isEqualTo("ooga@gmail.com");

    }

    @Test
    @DirtiesContext
    void userCanDeleteHimself() {

        ResponseEntity<Void> deleteResponse = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .exchange("/users/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        System.out.println("The body is " + deleteResponse.getBody());

        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("sugoi", "secret")
                .getForEntity("/users/1", User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext
    void shouldCreateANewUser() throws InterruptedException {
        User testUser = new User("poop", "poop@gmail.com", "idk", Role.USER, List.of());
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/users", testUser, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI locationOfNewUser = responseEntity.getHeaders().getLocation();
        assertThat(locationOfNewUser).isNotNull();
        System.out.println("Location of created user was " + locationOfNewUser);
        //get the newly created user
        ResponseEntity<User> fetchedResponse =
                testRestTemplate.withBasicAuth("poop", "idk")
                        .getForEntity(locationOfNewUser, User.class);

        System.out.println("response user was " + fetchedResponse.getBody());
        assertThat(fetchedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        User fetchedUser = fetchedResponse.getBody();

        System.out.println(fetchedUser.toString());

        assertThat(fetchedUser)
                .isNotNull()
                .extracting(User::getUserName, User::getEmail, User::getRole, User::getReservations)
                .containsExactly("poop", "poop@gmail.com", Role.USER, List.of());
    }


    @Test
    void shouldNotReturnUsersDataWhenUsingBadCredentials() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe", "wrongPassword")
                .getForEntity("/users/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void userCanOnlySeeTheirData() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .getForEntity("/users/3", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    void adminCanGetAllUsers() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("sugoi", "secret")
                .getForEntity("/users", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Todo:  Check if I get all users here
    }

    @Test
    void userCannotAccessOtherUsers(){

        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("john_doe","password123")
                .getForEntity("/users/3",User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void userCannotAccessAdminEndpoint(){
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe","password123")
                .getForEntity("/users",String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }
}
