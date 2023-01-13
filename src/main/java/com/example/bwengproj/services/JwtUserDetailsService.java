package com.example.bwengproj.services;

import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> u = userRepository.findByUserName(username);
        if (u.isPresent()) {
            User user = u.get();
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toSet()));
        } else throw new UsernameNotFoundException("No user found with username " + username);
    }
}
