package com.example.bwengproj.services;

import com.example.bwengproj.dto.PasswordDto;
import com.example.bwengproj.dto.UserDto;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.UserStatus;

import java.util.List;


public interface UserService {
    User save(User user);
    User create(UserDto dto);
    List<User> getAll();
    User get(Long id);
    User get(String username);
    User update(Long id, UserDto dto);
    User changeStatus(Long id, UserStatus status);
    void updatePassword(Long id, PasswordDto dto);
    void delete(Long id);
    void deleteAll();
}
