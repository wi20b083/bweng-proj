package com.example.bwengproj.services;

import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User fetchUserById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new EntityNotFoundException("No user found with ID " + id);
        }
    }

    @Override
    public User updateUser(Long userId, User user) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            User u = result.get();
            u.setFirstName(user.getFirstName());
            u.setLastName(user.getLastName());
            u.setUserName(user.getUserName());
            u.setImgLink(user.getImgLink());
            u.setEmail(user.getEmail());
            u.setStreet(user.getStreet());
            u.setStreetNr(user.getStreetNr());
            u.setZip(user.getZip());
            u.setCity(user.getCity());
            return userRepository.save(u);
        } else {
            throw new EntityNotFoundException("No user found with ID " + userId);
        }
    }

    @Override
    public void updateUserPassword(Long userId, String Password) {

    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User fetchUserByUsername(String username) {
        Optional<User> result = userRepository.findByUserName(username);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new EntityNotFoundException("No user found with username " + username);
        }
    }
}
