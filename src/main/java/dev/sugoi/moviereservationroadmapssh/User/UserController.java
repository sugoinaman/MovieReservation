package dev.sugoi.moviereservationroadmapssh.User;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    ResponseEntity<List<User>> getAllUsers() {
        List<User> listOfUsers = userService.getAllUsers();
        return ResponseEntity.ok(listOfUsers);
    }


    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id) {

        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser
                .map(user -> ResponseEntity.ok(user)).
                orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }

    @PostMapping("/signup")
    ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucb) {
        User userToBeSaved = new User(user.getUserName(), user.getEmail(), user.getPassword(),Role.USER,List.of());

        userService.addUser(userToBeSaved);
        URI location = ucb
                .path("user/get/{id}")
                .buildAndExpand(userToBeSaved.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/update/{id}")
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody User newUser) {
        userService.modifyUser(newUser,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
