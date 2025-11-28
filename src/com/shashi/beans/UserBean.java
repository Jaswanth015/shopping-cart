package com.shashi.beans;

import java.io.Serializable;

public class UserBean implements Serializable {
    private String username;
    private Long mobile;
    private String email;
    private String address;
    private int pinCode;
    private String password;
    private String userType;

    public UserBean() {}

    // 6-argument constructor (customer default)
    public UserBean(String username, Long mobile, String email,
                    String address, int pinCode, String password) {
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.pinCode = pinCode;
        this.password = password;
        this.userType = "CUSTOMER"; // default
    }

    // ⭐ REQUIRED 7-argument constructor (PROFILE JSP EXPECTS THIS)
    public UserBean(String username, Long mobile, String email,
                    String address, int pinCode, String password, String userType) {
        this.username = username;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.pinCode = pinCode;
        this.password = password;
        this.userType = userType;
    }

    // Getters & Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Long getMobile() { return mobile; }
    public void setMobile(Long mobile) { this.mobile = mobile; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getPinCode() { return pinCode; }
    public void setPinCode(int pinCode) { this.pinCode = pinCode; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}
