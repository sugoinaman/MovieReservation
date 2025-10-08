package dev.sugoi.moviereservationroadmapssh.Security.UserStuff;


import dev.sugoi.moviereservationroadmapssh.User.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service("securityUtils")
public class SecurityUtils {

    public boolean canAccessUser(User user, Authentication authentication) {
        if (!user.getUserName().equals(authentication.getName())) return false;

        return user.getUserName().equals(authentication.getName()) || authentication.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toString().equals("ADMIN");
    }

}
