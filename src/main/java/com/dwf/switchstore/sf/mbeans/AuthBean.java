package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.AuthClient;
import com.dwf.switchstore.sf.model.Users;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * This class is a Managed Bean that handles authentication
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger("com.dwf.switchstore.sf.mbeans.AuthBean");
    private final AuthClient authClient = new AuthClient();
    private Users user = new Users();
    private Users loggedInUser;

    /**
     * Login to the application
     * @return the next page to navigate to after login
     */
    public String login() {
        try {
            loggedInUser = authClient.login(user.getUsername(), user.getPassword());
            LOGGER.info("User logged in: " + loggedInUser.getUsername());

            // Store user in session without password
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", loggedInUser);
            LOGGER.info("User stored in session: " + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"));

            return "/games/list?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            LOGGER.severe("Login failed: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed: " + e.getMessage(), null));
            return null;
        }
    }

    /**
     * Register a new user
     * @return the next page to navigate to after registration
     */
    public String register() {
        try {
            authClient.register(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration successful", "You can now login"));
            return "login?faces-redirect=true";
        } catch (IOException | InterruptedException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage(), null));
            return null;
        }
    }

    /**
     * Logout from the application
     * @return the login page
     */
    public String logout() {
        loggedInUser = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/auth/login?faces-redirect=true";
    }

    /**
     * Get the logged-in user from the session
     * @return the logged-in user
     */
    public Users getLoggedInUser() {
        if (loggedInUser == null) {
            loggedInUser = (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        }
        return loggedInUser;
    }

    /**
     * Check if a user is logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        Users user = getLoggedInUser();
        return user != null && user.getToken() != null;
    }

    /**
     * Get the user for login/register forms
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * Set the user for login/register forms
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    public String getToken() {
        Users user = getLoggedInUser();
        return user != null ? user.getToken() : null;
    }
}
