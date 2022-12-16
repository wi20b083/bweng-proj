package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.UserRepository;
import com.example.bwengproj.security.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final SHA256 hasher = new SHA256();
    @Autowired
    private UserRepository userRepository;

    // save operation
    @Override
    public User saveUser(@Valid User user) {
        return userRepository.save(user);
    }

    // read operation
    @Override
    public Iterable<User> fetchUserList() {
        return userRepository.findAll();
    }

    // update operation
    @Override
    public User updateUser(@Valid User user, Long userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            User userDB = result.get();

            if (Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getFirstName())) {
                if (!user.getFirstName().equals(userDB.getFirstName())) {
                    userDB.setFirstName(user.getFirstName());
                }
            }

            if (Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())) {
                if (!user.getLastName().equals(userDB.getLastName())) {
                    userDB.setLastName(user.getLastName());
                }
            }

            if (Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())) {
                if (!user.getLastName().equals(userDB.getLastName())) {
                    userDB.setLastName(user.getLastName());
                }
            }

            if (Objects.nonNull(user.getUserName()) && !"".equalsIgnoreCase(user.getUserName())) {
                if (!user.getUserName().equals(userDB.getUserName()) && fetchUserByEmail(user.getUserName()) == null) {
                    userDB.setUserName(user.getUserName());
                }
            }

            if (Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())) {
                if (!user.getEmail().equals(userDB.getEmail()) && fetchUserByEmail(user.getEmail()) == null) {
                    userDB.setEmail(user.getEmail());
                }
            }

            if (Objects.nonNull(user.getImgLink()) && !"".equalsIgnoreCase(user.getImgLink())) {
                if (!user.getImgLink().equals(userDB.getImgLink())) {
                    userDB.setImgLink(user.getImgLink());
                }
            }

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

        if (Objects.nonNull(oldPw) && !"".equalsIgnoreCase(oldPw) && Objects.nonNull(newPw) && !"".equalsIgnoreCase(newPw)) {
            if (userDB.getPassword().equals(hasher.getSHA256Hash(oldPw))) {
                if (!userDB.getPassword().equals(hasher.getSHA256Hash(newPw))) {
                    userDB.setPassword(hasher.getSHA256Hash(newPw));
                } else {
                    throw new IllegalArgumentException("New password cannot be the same as the old one.");
                }
            } else {
                throw new IllegalArgumentException("Wrong credentials.");
            }
        }
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
}
