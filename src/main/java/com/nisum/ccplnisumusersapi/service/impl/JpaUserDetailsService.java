package com.nisum.ccplnisumusersapi.service.impl;

import com.nisum.ccplnisumusersapi.dataprovider.jpa.entity.UserEntity;
import com.nisum.ccplnisumusersapi.dataprovider.jpa.repository.IUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public JpaUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntity = userRepository.findByEmail(username);

        if (!userEntity.isPresent()) {
            throw new UsernameNotFoundException("Username '" + username + "' does not exist on the system!");
        }

        UserEntity user = userEntity.get();
        return new User(user.getEmail(), user.getPassword(), user.getIsActive(), true, true, true, Collections.emptyList());
    }

}
