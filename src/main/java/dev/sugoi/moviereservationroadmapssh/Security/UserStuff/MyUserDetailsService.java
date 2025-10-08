package dev.sugoi.moviereservationroadmapssh.Security.UserStuff;


import dev.sugoi.moviereservationroadmapssh.Exceptions.UserNotFoundException;
import dev.sugoi.moviereservationroadmapssh.User.User;
import dev.sugoi.moviereservationroadmapssh.User.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username).orElseThrow(() -> new UserNotFoundException(username + " not found!"));
        return new MyUserDetails(user);
    }
}
