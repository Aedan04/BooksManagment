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

}
