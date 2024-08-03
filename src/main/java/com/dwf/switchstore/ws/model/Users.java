package com.dwf.switchstore.ws.model;

/**
 * The User class represents a user entity with properties such as id, username, password,
 * name, and created_at. It provides getter and setter methods for these properties.
 */
public class Users {

    private int id; // Unique identifier for the user
    private String username; // Username of the user
    private String password; // Password of the user
    private String name; // Name of the user
    private String created_at; // Timestamp when the user was created

    /**
     * Gets the unique identifier for the user.
     *
     * @return the id of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the timestamp when the user was created.
     *
     * @return the timestamp when the user was created
     */
    public String getCreated_at() {
        return created_at;
    }

    /**
     * Sets the timestamp when the user was created.
     *
     * @param created_at the timestamp to set
     */
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
