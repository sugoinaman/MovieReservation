package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id, Authentication authentication) {
        Optional<User> optionalUser = userService.getUser(id, authentication);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucb) {
        User userToBeSaved = new User(null, user.getUserName(), user.getEmail(), user.getPassword(), user.getRole());
        userService.addUser(userToBeSaved);
        URI location = ucb
                .path("user/get/{id}")
                .buildAndExpand(userToBeSaved.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }


}
