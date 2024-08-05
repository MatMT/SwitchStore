package com.dwf.switchstore.ws.util;

import com.dwf.switchstore.ws.model.Users;

public class Validator {

    /**
     * Validates a user's username, password, and name.
     *
     * @param user the user to validate
     * @param isRegister true if the user is being registered, false if the user is being authenticated
     * @return true if the username, password, and name are valid, false otherwise
     */
    public static boolean validateUser(Users user, boolean isRegister) {
        if (isRegister) {
            return user == null || user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty() || user.getName() == null || user.getName().isEmpty();
        } else {
            return user == null || user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty();
        }
    }
}
