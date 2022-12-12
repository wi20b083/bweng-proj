package com.example.bwengproj.repository;

import com.example.bwengproj.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(@Email String email);

    Optional<User> findByUserName(String username);
}
