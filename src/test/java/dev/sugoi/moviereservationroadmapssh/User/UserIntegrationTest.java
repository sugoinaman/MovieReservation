package dev.sugoi.moviereservationroadmapssh.User;

import org.apache.coyote.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;


import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void userCanAccessHimself() {
        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .getForEntity("/user/1", User.class);

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
                .exchange("/user/1", HttpMethod.PUT, request, String.class);
            // the normal put method doesn't let us check the status code apparently
        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("not_john_doe","ooga")
                .getForEntity("/user/1", User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        User responseUser = response.getBody();
        assertThat(responseUser.getUserName()).isNotNull().isEqualTo("not_john_doe");
        assertThat(responseUser.getEmail()).isNotNull().isEqualTo("ooga@gmail.com");
        assertThat(responseUser.getPassword()).isNotNull().isEqualTo("ooga");

    }

    @Test
    @DirtiesContext
    void shouldDeleteUser(){


        ResponseEntity<Void> deleteResponse = testRestTemplate
                .withBasicAuth("john_doe", "secret")
                .exchange("user/1", HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<User> response = testRestTemplate
                .withBasicAuth("jane_admin", "secret")
                .getForEntity("user/1", User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

    @Test
    void shouldCreateANewUser() throws InterruptedException {
        User testUser = new User("sugoi", "sugoi571@gmail.com", "secret", Role.ADMIN, List.of());
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/user/signup", testUser, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI locationOfNewUser = responseEntity.getHeaders().getLocation();
        assertThat(locationOfNewUser).isNotNull();
        //get the newly created user
        ResponseEntity<User> fetchedResponse = testRestTemplate.getForEntity(locationOfNewUser, User.class);
        assertThat(fetchedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        User fetchedUser = fetchedResponse.getBody();
        assertThat(fetchedUser)
                .isNotNull()
                .extracting(User::getUserName, User::getEmail, User::getPassword, User::getRole, User::getReservations)
                .containsExactly("sugoi", "sugoi571@gmail.com", "secret", Role.ADMIN, List.of());
    }



    @Test
    void shouldNotReturnUsersDataWhenUsingBadCredentials() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .getForEntity("/user/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        System.out.println(response.getBody());
    }

    @Test
    void userCanOnlySeeTheirData() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe", "password123")
                .getForEntity("user/2", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    void adminsCanAccessAllData() {

    }

    @Test
    void adminCanGetAllUsers() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("jane_admin", "secret")
                .getForEntity("/user/getAllUsers", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        //Todo:  Check if I get all users here
    }
}

/*


ToDo: Get All users

ToDo: A User                                     Admin:
      get by id                                  Do what users do +
      update himself by id                       get all users
      delete himself by id


      ToDo: Sign up a user.

 */