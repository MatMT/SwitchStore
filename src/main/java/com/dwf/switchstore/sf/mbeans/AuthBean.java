package com.dwf.switchstore.sf.mbeans;

import com.dwf.switchstore.sf.client.AuthClient;
import com.dwf.switchstore.sf.model.Users;
import com.dwf.switchstore.sf.util.SessionUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

/**
 * This class is a Managed Bean that handles authentication
 */
@Named
@SessionScoped
public class AuthBean implements Serializable {

    @Inject // Inject the AuthClient to authenticate users
    private AuthClient authClient;

    @Inject // Inject the SessionUtils to manage session attributes
    private SessionUtils sessionUtils;

    private Users user = new Users();
    private String loginUsername;
    private String loginPassword;

    /**
     * Login to the application
     * @return the next page to navigate to after login
     */
    public String login() {
        try {
            Users loggedInUser = authClient.login(loginUsername, loginPassword); // Authenticate the user
            sessionUtils.add("currentUser", loggedInUser); // Add the user to the session
            return "/games/list?faces-redirect=true"; // Redirect to the games list page
        } catch (IOException | InterruptedException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Login failed: " + e.getMessage()); // Add an error message
            return null; // Stay on the same page
        }
    }

    /**
     * Register a new user
     * @return the next page to navigate to after registration
     */
    public String register() {
        try {
            authClient.register(user); // Register the user
            addMessage(FacesMessage.SEVERITY_INFO, "Registration successful. You can now login."); // Add a success message
            return "/auth/login?faces-redirect=true"; // Redirect to the login page
        } catch (IOException | InterruptedException e) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Registration failed: " + e.getMessage()); // Add an error message
            return null; // Stay on the same page
        }
    }

    /**
     * Logout from the application
     * @return the login page
     */
    public String logout() throws IOException, InterruptedException {
        authClient.logout(getCurrentUser().getToken()); // Logout the user on the web service
        sessionUtils.remove("currentUser"); // Remove the user from the session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Invalidate the session
        return "/auth/login?faces-redirect=true"; // Redirect to the login page
    }

    /**
     * Check if a user is logged in
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() throws IOException, InterruptedException {
        return getCurrentUser() != null && authClient.validateToken(getCurrentUser().getToken());
    }

    /**
     * Get the current logged in user
     * @return the current logged in user
     */
    public Users getCurrentUser() {
        return (Users) sessionUtils.get("currentUser");
    }

    /**
     * Add a message to the FacesContext
     * @param severity the severity of the message
     * @param summary the summary of the message
     */
    private void addMessage(FacesMessage.Severity severity, String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, null));
    }

    // Getters and setters
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
