package com.example.demo.DAOs;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
    public Optional<User> findByUsernameAndPassword(String username, String password);

    public Optional<User> findByUsername(String username);
}
