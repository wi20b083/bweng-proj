package com.example.bwengproj.services;

import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.UserStatus;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> u = userRepository.findUserByUsername(username);
        if (u.isPresent()) {
            User user = u.get();

            if (user.getStatus().equals(UserStatus.USER_LOCKED)) {
                throw new DisabledException("User " + username + " is currently locked.");
            } else {
                return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
            }
        } else throw new EntityNotFoundException("No user found with username " + username);
    }
}
