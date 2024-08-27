package com.example.Database_First.Repository;

import com.example.Database_First.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
}
