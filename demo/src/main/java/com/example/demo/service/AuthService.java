package com.example.demo.service;

import com.example.demo.DAOs.UserDAO;
import com.example.demo.models.DTOs.LoginDTO;
import com.example.demo.models.DTOs.OutgoingUserDTO;
import com.example.demo.models.DTOs.RegisterDTO;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserDAO userDAO;

    @Autowired
    public AuthService (UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    //login
    public OutgoingUserDTO login (LoginDTO l) {
        Optional<User> u = userDAO.findByUsernameAndPassword(l.getUsername(), l.getPassword());
        if (u.isEmpty()) {
            return null;
        } else {
            return new OutgoingUserDTO(u.get());
        }
    }
    //register
    public OutgoingUserDTO register (RegisterDTO r) {
        Optional<User> u = userDAO.findByUsername(r.getUsername());
        System.out.print(r.toString());
        if (u.isEmpty()) {
            User v = new User(
                    0,
                    r.getfName(),
                    r.getlName(),
                    r.getUsername(),
                    r.getPassword()
            );
            return new OutgoingUserDTO(userDAO.save(v));
        } else {
            return null;
        }
    }
}
