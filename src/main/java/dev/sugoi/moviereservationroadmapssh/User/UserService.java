package dev.sugoi.moviereservationroadmapssh.User;

import dev.sugoi.moviereservationroadmapssh.Exceptions.UserNotFoundException;
import dev.sugoi.moviereservationroadmapssh.Security.SecurityAnnotations.IsAdmin;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @IsAdmin
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id: " + id + " not found."));
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    void modifyUser(User updatedUser, Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User tempUser = optionalUser.get();
            tempUser.setEmail(updatedUser.getEmail());
            tempUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            tempUser.setUserName(updatedUser.getUserName());
            userRepository.save(tempUser);
        }
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}


//ToDO we can pre filter the inputs that these methods take for more security
