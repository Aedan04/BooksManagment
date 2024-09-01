package com.example.Database_First.Services;

import com.example.Database_First.Model.UserEntity;
import com.example.Database_First.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> authenticateUser(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }


    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
//    public Optional<UserEntity> getUserById(Long id) {
//        return userRepository.findById(id);
//    }


    // Update an existing user
//    public Optional<UserEntity> updateUser(Long id, UserEntity updatedUser) {
//        return userRepository.findById(id).map(user -> {
//            user.setName(updatedUser.getName());
//            user.setLastName(updatedUser.getLastName());
//            user.setUserName(updatedUser.getUserName());
//            user.setPassword(updatedUser.getPassword());
//            user.setBookName(updatedUser.getBookName());
//            return userRepository.save(user);
//        });
//    }

    // Delete user by ID
//    public boolean deleteUser(Long id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }

    // Validate user credentials
}
