package com.example.demo.models.DTOs;


import com.example.demo.models.User;

public class OutgoingUserDTO {
    private int userId;
    private String username;
    private String role;
    private String fName;
    private String lName;

    public OutgoingUserDTO(int userId, String username, String role, String fName, String lName) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.fName = fName;
        this.lName = lName;
    }
    public OutgoingUserDTO(User u) {
        this.userId = u.getUserId();
        this.username = u.getUsername();
        this.role = u.getRole();
        this.fName = u.getfName();
        this.lName = u.getlName();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
