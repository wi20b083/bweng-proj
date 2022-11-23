package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;
import com.example.bwengproj.repository.AuctionRepository;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    // save operation
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // read operation
    @Override
    public List<User> fetchUserList(){
        return (List<User>) userRepository.findAll();
    }

    /* update operation
    @Override
    public Auction updateAuction(Auction auction, Long auctionId) {
        Auction aucDB = auctionRepository.findById(auctionId).get();

        if (Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())) {
            depDB.setDepartmentName(department.getDepartmentName());
        }

        if (Objects.nonNull(department.getDepartmentAddress()) && !"".equalsIgnoreCase(department.getDepartmentAddress())) {
            depDB.setDepartmentAddress(department.getDepartmentAddress());
        }

        if (Objects.nonNull(department.getDepartmentCode()) && !"".equalsIgnoreCase(department.getDepartmentCode())) {
            depDB.setDepartmentCode(department.getDepartmentCode());
        }

        return departmentRepository.save(depDB);
    }*/

    @Override
    public User updateUser(User user, Long userId) {
        User userDB = userRepository.findById(userId).get();

        // insert code with finished methods from Auction in model

        return null;
    }

    // delete operation
    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
