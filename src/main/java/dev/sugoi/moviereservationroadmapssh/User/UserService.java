package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    void addUser(User user) {
        userRepository.save(user);
    }

    Optional<User> getUserById(Integer id, Authentication authentication) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
    }


    //ToDo: Need to setup auth, only admins can change info for all users. and only owning user can change himself
    void modifyUser(User updatedUser, Integer id, Authentication authentication) {


        Optional<User> optionalUser = userRepository.findByIdAndUserName(id, authentication.getName());
        if (optionalUser.isPresent()) {
            User newUser = new User(id, updatedUser.getUserName(), updatedUser.getEmail(), updatedUser.getPassword(), optionalUser.get().getReservations());
            userRepository.save(newUser);
        }
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }

    void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}


//ToDo: To remember:  a user can only see his own reservation but an admin can see everything.
// So we protect all endpoints for admins with security filter chain which is ez
