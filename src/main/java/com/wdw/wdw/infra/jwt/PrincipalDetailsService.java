package com.wdw.wdw.infra.jwt;

import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
<<<<<<< HEAD:src/main/java/com/wdw/wdw/infra/jwt/PrincipalDetailsService.java
        log.info("PrincipalDetailsService loadUserByUsername 실행");
        return userRepository.findByUsername(username)
                .map(PrincipalDetails::new)
                .orElse(null);
=======
        System.out.println("PrincipalDetailsService loadUserByUsername 실행");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new PrincipalDetails(user);
        }
        return null;
>>>>>>> 6e31e2cf61d92c40f0bcc04b9e4e64da41f21bdd:src/main/java/com/wdw/wdw/config/auth/PrincipalDetailsService.java
    }
}

