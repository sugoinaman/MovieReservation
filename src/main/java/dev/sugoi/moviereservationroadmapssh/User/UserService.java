package dev.sugoi.moviereservationroadmapssh.User;

import dev.sugoi.moviereservationroadmapssh.Security.Annotation.IsAdmin;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @IsAdmin
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    void addUser(User user) {
        userRepository.save(user);
    }

    // I am under the assumption that userNames are unique

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }


    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')") // users can modify their own details + admins can change
    void modifyUser(User updatedUser, Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User newUser = new User(id, updatedUser.getUserName(), updatedUser.getEmail(), updatedUser.getPassword(), optionalUser.get().getReservations());
            userRepository.save(newUser);
        }
    }

    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    void deleteUser(Integer id) {
        if(getUserById(id).isEmpty()){
            throw EntityNotFoundException;
        }
        userRepository.deleteById(id);
    }
}


//ToDO we can pre filter the inputs that these methods take for more security
