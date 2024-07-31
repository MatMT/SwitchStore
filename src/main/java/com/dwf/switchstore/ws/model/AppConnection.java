package com.dwf.switchstore.ws.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppConnection {
    // JDBC driver & database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/switch_store_db?useSSL=false";

    // Database credentials
    static final String USER = "root";
    static final String PASSWORD = "";

    protected Connection conn = null;
    protected Statement stmt = null;
    protected PreparedStatement pstmt = null;
    protected ResultSet rs = null;

    public AppConnection () {
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            Logger.getLogger(AppConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public void close() throws SQLException {
        if (rs !=null) {
            rs.close();
        }

        if (stmt != null) {
            stmt.close();
        }

        if (pstmt != null) {
            pstmt.close();
        }

        if (conn != null) {
            conn.close();
        }
    }

}

