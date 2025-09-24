package dev.sugoi.moviereservationroadmapssh.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void adminCanAccessAllUsers(){




    }


    @Test
    void shouldCreateANewUser() throws InterruptedException {
        User testUser = new User( "sugoi", "sugoi571@gmail.com", "secret",Role.ADMIN, List.of());
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
                .extracting(User::getUserName, User::getEmail, User::getPassword, User::getRole,User::getReservations)
                .containsExactly("sugoi", "sugoi571@gmail.com", "secret", Role.ADMIN, List.of());
    }

    @Test
    void shouldNotReturnUsersDataWhenUsingBadCredentials() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe","password123")
                .getForEntity("/user/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        System.out.println(response.getBody());
    }

    @Test
    void userCanOnlySeeTheirData() {
        
    }

    @Test
    void adminsCanAccessAllData() {

    }

}

/*
Happy paths (200 OK / 201 Created cases)

GET all users returns list of users.

GET user by id returns a single user if it exists.

POST creates a new user and returns 201 Created with a Location header.

PUT updates an existing user and returns 200 OK.

DELETE removes a user and returns 200 OK or 204 No Content.

Error scenarios

GET user by id when the user does not exist → returns 404 Not Found.

POST with invalid request body (e.g., missing fields) → returns 400 Bad Request.

PUT with non-existing user id → returns 404 Not Found.

DELETE with non-existing user id → returns 404 Not Found.

Security / authorization behavior (if you enabled Spring Security)

A regular user cannot access an endpoint restricted to admins (expect 403 Forbidden).

An unauthenticated request to a secured endpoint returns 401 Unauthorized.

Validation checks

Invalid inputs (e.g., invalid email, too short password) return 400 Bad Request.

Optional: verify validation messages if you want detailed error responses.

Response structure and headers

Check that response contains correct headers (e.g., Location on POST).

Verify response body matches expected JSON format (fields, values).
 */