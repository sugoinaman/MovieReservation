package dev.sugoi.moviereservationroadmapssh.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    ResponseEntity<List<User>> getAllUsers() {
        log.info("get all user endpoint accessed");
        List<User> listOfUsers = userService.getAllUsers();
        return ResponseEntity.ok(listOfUsers);
    }
    // ToDo: Need to add pagination for this end point


    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id) {
        log.info("get user by id: {} used", id);
        User user = userService.getUserById(id);
        return  ResponseEntity.ok(user);
    }

    @PostMapping()
    ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucb) {

        log.info("new user trying to sign up with credentials:{} ", user.toString());
        User userToBeSaved = new User(user.getUserName(), user.getEmail(), user.getPassword(), Role.USER, List.of());

        userService.addUser(userToBeSaved);
        URI location = ucb
                .path("user/{id}")
                .buildAndExpand(userToBeSaved.getid())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody User newUser) {
        log.info("trying to modify user with id {}, new user: {} ", id, newUser.toString());
        userService.modifyUser(newUser, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
