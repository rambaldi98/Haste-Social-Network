package com.example.backback.dto.request;

import java.util.Set;

public class SignUpForm {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String birthday;
    private String city;

    private Set<String> roles;

    public SignUpForm() {
    }

    public SignUpForm(String username, String password, String email, String phone, String birthday, String city, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.city = city;
        this.roles = roles;
    }

    public SignUpForm(String username, String password, String email, String phone, String birthday, String city) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.city = city;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
