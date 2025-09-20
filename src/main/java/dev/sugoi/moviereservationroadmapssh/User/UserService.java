package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    User addUser(User user) {
        return userRepository.save(user);
    }

    Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    // Will be used later
    Optional<User> getUserByIdAndAuthentication(Integer id, Authentication authentication) {
        return userRepository.findByIdAndUserName(id, authentication.getName());
    }

    //ToDo: Need to setup auth, only admins can change info for all users. and only owning user can change himself
    User modifyUser(User newUser, Integer id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUserName(newUser.getUserName());
                    user.setEmail(newUser.getEmail());
                    user.setPassword(newUser.getPassword());
                    return user;
                })
                .orElseGet(() -> {
                    return userRepository.save(newUser);
                });
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }


}


//ToDo: To remember:  a user can only see his own reservation but an admin can see everything.
// So we protect all endpoints for admins with security filter chain which is ez
