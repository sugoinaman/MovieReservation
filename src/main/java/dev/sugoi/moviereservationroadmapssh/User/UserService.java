package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

     void addUser(User user) {
        userRepository.save(user);
    }

    Optional<User> getUser(Integer id, Authentication authentication) {
        return userRepository.findByIdAndUserName(id, authentication.getName());
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }


}
