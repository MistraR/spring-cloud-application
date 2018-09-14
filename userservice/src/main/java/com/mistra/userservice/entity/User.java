package com.mistra.userservice.entity;

import com.mistra.base.entity.BaseEntity;

/**
 * Author: RoronoaZoro丶WangRui
 * Time: 2018/7/15/015
 * Describe:
 */
public class User extends BaseEntity {

    private Long id;

    private String email;

    private String userName;

    private String password;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
