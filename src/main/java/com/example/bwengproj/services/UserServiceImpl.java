package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    // save operation
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // read operation
    @Override
    public Iterable<User> fetchUserList() {
        return userRepository.findAll();
    }

    // update operation
    @Override
    public User updateUser(User user, Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            User userDB = result.get();


            return userRepository.save(userDB);
        } else {
            throw new EntityNotFoundException();
        }
    }

    //update password
    //todo: validation
    @Override
    public User updatePassword(String oldPw, @Size(min = 8) String newPw, Long userId) {
        User userDB = userRepository.findById(userId).get();

        /*if (Objects.nonNull(oldPw) && !"".equalsIgnoreCase(oldPw) && Objects.nonNull(newPw) && !"".equalsIgnoreCase(newPw)) {
            if (userDB.getPassword().equals(hasher.getSHA256Hash(oldPw))) {
                if (!userDB.getPassword().equals(hasher.getSHA256Hash(newPw))) {
                    userDB.setPassword(hasher.getSHA256Hash(newPw));
                } else {
                    throw new IllegalArgumentException("New password cannot be the same as the old one.");
                }
            } else {
                throw new IllegalArgumentException("Wrong credentials.");
            }
        }*/
        return userRepository.save(userDB);
    }

    // delete operation
    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Iterable<Auction> fetchAuctionsByUser(Long userId) {
        return userRepository.findById(userId).get().getAuctions();
    }

    @Override
    public Iterable<Bid> fetchBidsByUser(Long userId) {
        return userRepository.findById(userId).get().getBids();
    }

    @Override
    public User fetchUserByUsername(@NotBlank @NotNull String username) {
        return userRepository.findByUserName(username).get();
    }

    @Override
    public User fetchUserByEmail(@Email String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User fetchUserById(Long userId) {
        return userRepository.findById(userId).get();
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
