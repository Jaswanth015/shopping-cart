package com.shashi.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.shashi.beans.UserBean;
import com.shashi.service.UserService;
import com.shashi.utility.DBUtil;

public class UserServiceImpl implements UserService {

    @Override
    public String registerUser(UserBean user) {
        String status = "User Registration Failed!";

        if (isRegistered(user.getEmail())) {
            return "Email Id Already Registered!";
        }

        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;

        try {
        	ps = conn.prepareStatement(
        		    "INSERT INTO user (email, name, mobile, address, pincode, password, usertype) VALUES (?, ?, ?, ?, ?, ?, ?)"
        		);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setLong(3, user.getMobile());
            ps.setString(4, user.getAddress());
            ps.setInt(5, user.getPinCode());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getUserType().toUpperCase());

            int k = ps.executeUpdate();
            if (k > 0) {
                status = "User Registered Successfully!";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            status = "Error: " + e.getMessage();
        } finally {
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return status;
    }

    @Override
    public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode, String password) {
        UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password, "CUSTOMER");
        return registerUser(user);
    }

    @Override
    public boolean isRegistered(String emailId) {
        boolean flag = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE email=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();
            if (rs.next()) flag = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return flag;
    }

    @Override
    public String isValidCredential(String emailId, String password) {
        String status = "Login Denied! Incorrect Username or Password";

        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                status = "valid";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return status;
    }

    @Override
    public UserBean getUserDetails(String emailId, String password) {
        UserBean user = null;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserBean();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("name"));
                user.setMobile(rs.getLong("mobile"));
                user.setAddress(rs.getString("address"));
                user.setPinCode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
                user.setUserType(rs.getString("usertype"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return user;
    }

    @Override
    public String getUserAddr(String emailId) {
        String addr = "";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT address FROM user WHERE email=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();

            if (rs.next()) addr = rs.getString("address");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return addr;
    }

    @Override
    public String getFName(String emailId) {
        String fname = "";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT name FROM user WHERE email=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();

            if (rs.next()) {
                fname = rs.getString("name").split(" ")[0];
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return fname;
    }
}
