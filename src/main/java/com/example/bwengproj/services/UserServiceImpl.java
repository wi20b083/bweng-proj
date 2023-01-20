package com.example.bwengproj.services;

import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
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
    public User updateUser(Long userId, User user) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            User u = result.get();
            if (isValid(u.getFirstName(), "fname") &&
                    isValid(u.getLastName(), "lname") &&
                    isValid(u.getUserName(), "uname") &&
                    isValid(u.getImgLink(), "imgLink")
            ) {
                return null;
            }
        } else {
            throw new EntityNotFoundException("No user found with ID " + userId);
        }
        return null;
    }

    @Override
    public void updateUserPassword(Long userId, String Password) {

    }

    // delete operation
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

    private boolean isValid(String input, String fieldName) {
        switch (fieldName) {
            case "fname", "lname", "uname", "imgLink", "address" -> {
                return (Objects.nonNull(input) && !input.isBlank() && !input.isEmpty());
            }
            case "pw" -> {
                //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
                return input.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
            }
            case "email" -> {
                return input.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
            }
            default -> {
                return false;
            }
        }
    }
}
