package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucb) {
        User userToBeSaved = new User(null, user.getUserName(), user.getEmail(), user.getPassword(), user.getPriveleges());
        userService.addUser(userToBeSaved);
        URI location = ucb
                .path("users/{id}")
                .buildAndExpand(userToBeSaved.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }
}
