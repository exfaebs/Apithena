
package net.ictcampus.apithena.controller.services;

import net.ictcampus.apithena.controller.repositories.UserRepository;
import net.ictcampus.apithena.model.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.Collections.emptyList;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Weil Spring Boot bzw. Spring Security nur ihren User akzeptiert für Authentication und Authorization
    // machen wir mit dieser Methode aus unserem User Objekt ein Spring Security User Objekt.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                emptyList());
    }
}
 