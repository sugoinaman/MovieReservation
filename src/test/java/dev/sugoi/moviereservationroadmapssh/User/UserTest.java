package dev.sugoi.moviereservationroadmapssh.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void shouldCreateANewUser() throws InterruptedException {
        User testUser = new User(null, "sugoi", "sugoi571@gmail.com", "secret", Role.ADMIN);
        ResponseEntity<Void> responseEntity = testRestTemplate.postForEntity("/user/signup", testUser, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI locationOfNewUser = responseEntity.getHeaders().getLocation();

        //get the newly created user

        ResponseEntity<User> fetchedResponse = testRestTemplate.getForEntity(locationOfNewUser, User.class);
        assertThat(fetchedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        User fetchedUser = fetchedResponse.getBody();
        assertThat(fetchedUser)
                .isNotNull()
                .extracting(User::getUserName, User::getEmail, User::getPassword, User::getRole)
                .containsExactly("sugoi", "sugoi571@gmail.com", "secret", Role.ADMIN);
    }

    @Test
    void shouldNotReturnUsersDataWhenUsingBadCredentials() {
        ResponseEntity<String> response = testRestTemplate
                .withBasicAuth("john_doe","password123")
                .getForEntity("/user/1", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response.getBody());
    }

    @Test
    void userCanOnlySeeTheirData() {

    }

    @Test
    void adminCanAccessAllData() {

    }

}
