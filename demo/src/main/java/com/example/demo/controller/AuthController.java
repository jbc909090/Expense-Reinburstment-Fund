package com.example.demo.controller;


import com.example.demo.models.DTOs.LoginDTO;
import com.example.demo.models.DTOs.OutgoingUserDTO;
import com.example.demo.models.DTOs.RegisterDTO;
import com.example.demo.models.User;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(value= "http://localhost:5173", allowCredentials = "true")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController (AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<OutgoingUserDTO> registerUser(@RequestBody RegisterDTO user, HttpSession session) throws AccountException {
        System.out.print(user.toString());
        OutgoingUserDTO u = authService.register(user);
        if (u == null) {
            throw new AccountException("username or password not found");
        }
        session.setAttribute("userId", u.getUserId());
        session.setAttribute("username", u.getUsername());
        session.setAttribute("role", u.getRole());
        return ResponseEntity.ok(u);
    }
    @PostMapping("/login")
    public ResponseEntity<OutgoingUserDTO> loginUser(@RequestBody LoginDTO loginDTO, HttpSession session) throws AccountException {
        OutgoingUserDTO u = authService.login(loginDTO);
        if (u == null) {
            throw new AccountException("username or password not found");
        }
        session.setAttribute("userId", u.getUserId());
        session.setAttribute("username", u.getUsername());
        session.setAttribute("role", u.getRole());
        return ResponseEntity.ok(u);
    }
    @PostMapping("/logout")
    public ResponseEntity<OutgoingUserDTO> logoutUser(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new OutgoingUserDTO(0, "", "", "", ""));
    }
}
