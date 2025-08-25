package dev.sugoi.moviereservationroadmapssh.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

     void addUser(User user) {
        //ToDo: Do we need to check if he exists?
        userRepository.save(user);
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }

}
