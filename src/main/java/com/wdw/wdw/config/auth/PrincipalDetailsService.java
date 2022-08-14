package com.wdw.wdw.config.auth;

import com.wdw.wdw.domain.User;
import com.wdw.wdw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("PrincipalDetailsService loadUserByUsername 실행");
        return userRepository.findByUsername(username)
                .map(PrincipalDetails::new)
                .orElse(null);
    }
}
