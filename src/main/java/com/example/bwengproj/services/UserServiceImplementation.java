package com.example.bwengproj.services;

import com.example.bwengproj.dto.PasswordDto;
import com.example.bwengproj.dto.UserDto;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.model.status.UserStatus;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            return u.get();
        } else {
            throw new EntityNotFoundException("No user found with ID " + id);
        }
    }

    @Override
    public User get(String username) {
        Optional<User> u = userRepository.findUserByUsername(username);
        if (u.isPresent()) {
            return u.get();
        } else {
            throw new EntityNotFoundException("No user found with username " + username);
        }
    }

    @Override
    public User update(Long id, UserDto dto) {
        User db = get(id);
        db.setFirstname(dto.firstname());
        db.setLastname(dto.lastname());
        db.setUsername(dto.username());
        db.setEmail(dto.email());
        db.setImagePath(dto.imagePath());
        return userRepository.save(db);
    }

    @Override
    public void changeStatus(Long id, Boolean status) {
        User db = get(id);
        db.setStatus(status ? UserStatus.USER_UNLOCKED : UserStatus.USER_LOCKED);
        userRepository.save(db);
    }

    @Override
    public void updatePassword(Long id, PasswordDto dto) {
        User db = get(id);
        String oldPasswordEncrypted = encoder.encode(dto.oldPassword());
        if (oldPasswordEncrypted.equals(db.getPassword())) {
            String newPasswordEncrypted = encoder.encode(dto.newPassword());
            db.setPassword(newPasswordEncrypted);
            userRepository.save(db);
        }
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User create(UserDto dto) {
        User db = new User();
        db.setFirstname(dto.firstname());
        db.setLastname(dto.lastname());
        db.setUsername(dto.username());
        db.setEmail(dto.email());
        db.setImagePath(dto.imagePath());
        if (!dto.password().isBlank()) {
            String encryptedPassword = encoder.encode(dto.password());
            db.setPassword(encryptedPassword);
        } else {
            throw new IllegalArgumentException("Password cannot be blank.");
        }
        db.addRole(Role.ROLE_USER);
        db.setStatus(UserStatus.USER_UNLOCKED);
        return userRepository.save(db);
    }
}
