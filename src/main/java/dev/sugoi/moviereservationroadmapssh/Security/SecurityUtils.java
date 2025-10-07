package dev.sugoi.moviereservationroadmapssh.Security;

import dev.sugoi.moviereservationroadmapssh.User.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service("securityUtils")
public class SecurityUtils {

    public boolean canAccessUser(Optional<User> optionalUser, Authentication authentication) {
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        String userNameToCheckAgainst = authentication.getName();
        return userNameToCheckAgainst.equals(user.getUserName()) || authentication.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toString().equals("ADMIN");
    }

}
