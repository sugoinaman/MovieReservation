package dev.sugoi.moviereservationroadmapssh.User;

import org.apache.coyote.Response;
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


    @GetMapping("/get/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> optionalUser = userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucb) {
        User userToBeSaved = new User(user.getUserName(), user.getEmail(), user.getPassword());

        userService.addUser(userToBeSaved);
        URI location = ucb
                .path("user/get/{id}")
                .buildAndExpand(userToBeSaved.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/update/{id}")
    ResponseEntity<Void> updateUser(@PathVariable Integer id, @RequestBody User user) {

        // If a user exists at given id -> update, else create
        if (userService.getUserById(id).isPresent()) {
             userService.modifyUser(user, id);
             return ResponseEntity.noContent().build();
        }
        userService.modifyUser(user, id);
        return ResponseEntity.created(URI.create(id.toString())).build(); // um?
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if(userService.getUserById(id).isPresent()){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
