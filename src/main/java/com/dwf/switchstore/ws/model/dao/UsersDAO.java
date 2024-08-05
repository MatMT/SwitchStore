package com.dwf.switchstore.ws.model.dao;

import com.dwf.switchstore.ws.model.AppConnection;
import com.dwf.switchstore.ws.model.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAO extends AppConnection {

    /**
     * Inserts a new user into the users table.
     *
     * @param user the user to insert
     * @throws SQLException if a database access error occurs
     */
    public void insert(Users user) throws SQLException {
        try {
            connect();
            pstmt = conn.prepareStatement("INSERT INTO users (username, password, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();

            if (keys.next()) {
                user.setId(keys.getInt(1));
            }
        } finally {
            close();
        }
    }

    /**
     * Fetches a user from the users table by its username.
     *
     * @param username the username of the user to fetch
     * @return the user fetched
     * @throws SQLException if a database access error occurs
     */
    public Users fetchByUsername(String username) throws SQLException {
        Users user = null;
        try {
            connect();
            pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setCreated_at(rs.getTimestamp("created_at").toString());
            }
        } finally {
            close();
        }
        return user;
    }
}
